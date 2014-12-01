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
package uk.ac.roe.wfau.firethorn.widgeon.ivoa;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.ac.roe.wfau.firethorn.entity.annotation.UpdateAtomicMethod;
import uk.ac.roe.wfau.firethorn.entity.exception.EntityNotFoundException;
import uk.ac.roe.wfau.firethorn.meta.base.BaseComponent;
import uk.ac.roe.wfau.firethorn.meta.ivoa.IvoaResource;
import uk.ac.roe.wfau.firethorn.webapp.control.AbstractEntityController;
import uk.ac.roe.wfau.firethorn.webapp.control.EntityBean;
import uk.ac.roe.wfau.firethorn.webapp.control.WebappLinkFactory;
import uk.ac.roe.wfau.firethorn.webapp.paths.Path;

/**
 * Spring MVC controller for <code>IvoaResource</code>.
 *
 */
@Slf4j
@Controller
@RequestMapping(IvoaResourceLinkFactory.RESOURCE_PATH)
public class IvoaResourceController
    extends AbstractEntityController<IvoaResource, IvoaResourceBean>
    {

    @Override
    public Path path()
        {
        return path(
            IvoaResourceLinkFactory.RESOURCE_PATH
            );
        }

    /**
     * Public constructor.
     *
     */
    public IvoaResourceController()
        {
        super();
        }

    /**
     * MVC property for the target resource.
     *
     */
    public static final String TARGET_ENTITY = "urn:ivoa.resource.entity" ;

    /**
     * MVC property for updating the name.
     *
     */
    public static final String UPDATE_NAME = "ivoa.resource.name" ;

    /**
     * MVC property for updating the status.
     *
     */
    public static final String UPDATE_STATUS = "ivoa.resource.status" ;

    @Override
    public IvoaResourceBean bean(final IvoaResource entity)
        {
        return new IvoaResourceBean(
            entity
            );
        }

    @Override
    public Iterable<IvoaResourceBean> bean(final Iterable<IvoaResource> iter)
        {
        return new IvoaResourceBean.Iter(
            iter
            );
        }

    /**
     * Get the target resource based on the identifier in the request.
     *
     */
    @ModelAttribute(TARGET_ENTITY)
    public IvoaResource entity(
        @PathVariable(WebappLinkFactory.IDENT_FIELD)
        final String ident
        ) throws EntityNotFoundException  {
        log.debug("entity() [{}]", ident);
        final IvoaResource entity = factories().ivoa().resources().select(
            factories().ivoa().resources().idents().ident(
                ident
                )
            );
        return entity ;
        }

    /**
     * JSON GET request.
     *
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.GET, produces=JSON_MIME)
    public IvoaResourceBean select(
        @ModelAttribute(TARGET_ENTITY)
        final IvoaResource entity
        ){
        return bean(
            entity
            );
        }

    /**
     * JSON POST update.
     *
     */
    @ResponseBody
    @UpdateAtomicMethod
    @RequestMapping(method=RequestMethod.POST, produces=JSON_MIME)
    public EntityBean<IvoaResource> update(
        @ModelAttribute(TARGET_ENTITY)
        final IvoaResource entity,
        @RequestParam(value=UPDATE_NAME, required=false) final
        String name,
        @RequestParam(value=UPDATE_STATUS, required=false) final
        String status
        ){

        if (name != null)
            {
            if (name.length() > 0)
                {
                entity.name(
                    name
                    );
                }
            }

        if (status != null)
            {
            entity.status(
                BaseComponent.Status.valueOf(
                    status
                    )
                );
            }

        return new IvoaResourceBean(
            entity
            );
        }
    }