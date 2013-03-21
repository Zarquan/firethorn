/*
 *  Copyright (C) 2012 Royal Observatory, University of Edinburgh, UK
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package uk.ac.roe.wfau.firethorn.widgeon.adql;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.ac.roe.wfau.firethorn.adql.query.AdqlQuery;
import uk.ac.roe.wfau.firethorn.entity.Identifier;
import uk.ac.roe.wfau.firethorn.entity.annotation.UpdateAtomicMethod;
import uk.ac.roe.wfau.firethorn.entity.annotation.UpdateEntityMethod;
import uk.ac.roe.wfau.firethorn.entity.exception.NotFoundException;
import uk.ac.roe.wfau.firethorn.job.Job;
import uk.ac.roe.wfau.firethorn.job.Job.Status;
import uk.ac.roe.wfau.firethorn.spring.ComponentFactories;
import uk.ac.roe.wfau.firethorn.webapp.control.AbstractEntityController;
import uk.ac.roe.wfau.firethorn.webapp.control.EntityBean;
import uk.ac.roe.wfau.firethorn.webapp.paths.Path;

/**
 * Spring MVC controller for <code>AdqlQuery</code>.
 *
 */
@Slf4j
@Controller
@RequestMapping(AdqlQueryLinkFactory.QUERY_PATH)
public class AdqlQueryController
extends AbstractEntityController<AdqlQuery>
    {

    @Override
    public Path path()
        {
        return path(
            AdqlQueryLinkFactory.QUERY_PATH
            );
        }

    /**
     * Public constructor.
     *
     */
    public AdqlQueryController()
        {
        super();
        }

    /**
     * MVC property for the target entity.
     *
     */
    public static final String TARGET_ENTITY = "urn:adql.query.entity" ;

    /**
     * MVC property for updating the name.
     *
     */
    public static final String UPDATE_NAME = "adql.query.update.name" ;

    /**
     * MVC property for updating the query.
     *
     */
    public static final String UPDATE_QUERY = "adql.query.update.query" ;

    /**
     * MVC property for updating the status.
     *
     */
    public static final String UPDATE_STATUS = "adql.query.update.status" ;

    /**
     * MVC property for the timelimit.
     *
     */
    public static final String UPDATE_TIMEOUT = "adql.query.update.timeout" ;

    @Override
    public EntityBean<AdqlQuery> bean(final AdqlQuery entity)
        {
        return new AdqlQueryBean(
            entity
            );
        }

    @Override
    public Iterable<EntityBean<AdqlQuery>> bean(Iterable<AdqlQuery> iter)
        {
        return new AdqlQueryBean.Iter(
            iter
            );
        }

    /**
     * Get the target entity based on the ident in the path.
     * @throws NotFoundException
     *
    @ModelAttribute(TARGET_ENTITY)
    public AdqlQuery entity(
        @PathVariable("ident")
        final String ident
        ) throws NotFoundException {
        return factories().adql().queries().select(
            factories().adql().queries().idents().ident(
                ident
                )
            );
        }
     */

    /**
     * JSON GET request.
     *
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.GET, produces=JSON_MAPPING)
    public EntityBean<AdqlQuery> select(
        @PathVariable("ident")
        final String ident
        ) throws NotFoundException {
        return bean(
            factories().adql().queries().select(
                factories().adql().queries().idents().ident(
                    ident
                    )
                )
            );
        }

    /**
     * JSON POST update.
     *
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.POST, produces=JSON_MAPPING)
    public EntityBean<AdqlQuery> update(
        @RequestParam(value=UPDATE_NAME, required=false)
        final String name,
        @RequestParam(value=UPDATE_QUERY, required=false)
        final String input,
        @PathVariable("ident")
        final String ident
        ) throws NotFoundException {
        return bean(
            this.helper.update(
                factories().adql().queries().idents().ident(
                    ident
                    ),
                name,
                input
                )
            );
        }

    /**
     * JSON POST update.
     *
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.POST, produces=JSON_MAPPING)
    public EntityBean<AdqlQuery> update(
        @RequestParam(value=UPDATE_STATUS, required=false)
        final Status status,
        @RequestParam(value=UPDATE_TIMEOUT, required=false)
        final Integer timeout,
        @PathVariable("ident")
        final String ident
        ) throws NotFoundException {
        return bean(
            this.helper.update(
                factories().adql().queries().idents().ident(
                    ident
                    ),
                status,
                timeout
                )
            );
        }

    /**
     * Transactional helper.
     * 
     */
    public static interface Helper
        {
        /**
         * Transactional update.
         *
         */
        public AdqlQuery update(final Identifier ident, final String  name, final String input)
        throws NotFoundException ;

        /**
         * Transactional update.
         *
         */
        public AdqlQuery update(final Identifier ident, final Job.Status next, final Integer timeout)
        throws NotFoundException ;

        }

    /**
     * Transactional helper.
     * 
     */
    @Autowired
    private Helper helper ;

    /**
     * Transactional helper.
     * 
     */
    @Slf4j
    @Component
    public static class HelperImpl
    implements Helper
        {
        /**
         * Autowired system services.
         *
         */
        @Autowired
        private ComponentFactories factories;

        /**
         * Our system services.
         *
         */
        public ComponentFactories factories()
            {
            return this.factories;
            }

        @Override
        @UpdateAtomicMethod
        public Status update(final AdqlQuery query, final Job.Status next, final Integer timeout)
            {
            Status result  = query.status();

            int pause = 2 ;
            if (timeout != null)
                {
                pause = timeout.intValue();
                }
            
            if ((next != null) && ( next != result))
                {
                if (next == Status.READY)
                    {
                    result = query.prepare();
                    }
                else if (next == Status.RUNNING)
                    {
                    try {
                        Future<Status> future = query.execute();
                        log.debug("Checking future");
                        result = future.get(
                            pause,
                            TimeUnit.SECONDS
                            );
                        log.debug("Status [{}]", query.status());
                        log.debug("Result [{}]", result);
                        }
                    catch (TimeoutException ouch)
                        {
                        log.debug("Future timeout");
                        }
                    catch (InterruptedException ouch)
                        {
                        log.debug("Future interrupted [{}]", ouch.getMessage());
                        }
                    catch (ExecutionException ouch)
                        {
                        log.debug("ExecutionException [{}]", ouch.getMessage());
                        result = Status.ERROR;
                        }
                    }
                else if (next == Status.CANCELLED)
                    {
                    result = query.cancel();
                    }
                else {
                    result = Status.ERROR;
                    }
                }
            return result ;
            }

        @Override
        @UpdateAtomicMethod
        public AdqlQuery update(Identifier ident, String name, String input)
        throws NotFoundException 
            {
            AdqlQuery query = factories().adql().queries().select(ident);
            if (name != null)
                {
                if (name.length() > 0)
                    {
                    query.name(
                        name
                        );
                    }
                }
   
            if (input != null)
                {
                if (input.length() > 0)
                    {
                    query.input(
                        input
                        );
                    }
                }
            return query;
            }

        @Override
        public AdqlQuery update(Identifier ident, Status next, Integer timeout)
        throws NotFoundException 
            {
            Status result  = query.status();

            int pause = 2 ;
            if (timeout != null)
                {
                pause = timeout.intValue();
                }
            
            if ((next != null) && ( next != result))
                {
                if (next == Status.READY)
                    {
                    result = query.prepare();
                    }
                else if (next == Status.RUNNING)
                    {
                    try {
                        Future<Status> future = query.execute();
                        log.debug("Checking future");
                        result = future.get(
                            pause,
                            TimeUnit.SECONDS
                            );
                        log.debug("Status [{}]", query.status());
                        log.debug("Result [{}]", result);
                        }
                    catch (TimeoutException ouch)
                        {
                        log.debug("Future timeout");
                        }
                    catch (InterruptedException ouch)
                        {
                        log.debug("Future interrupted [{}]", ouch.getMessage());
                        }
                    catch (ExecutionException ouch)
                        {
                        log.debug("ExecutionException [{}]", ouch.getMessage());
                        result = Status.ERROR;
                        }
                    }
                else if (next == Status.CANCELLED)
                    {
                    result = query.cancel();
                    }
                else {
                    result = Status.ERROR;
                    }
                }
            return result ;
            }
        }
    }
