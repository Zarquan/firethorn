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
import org.springframework.web.bind.annotation.ResponseBody;

import uk.ac.roe.wfau.firethorn.entity.exception.EntityNotFoundException;
import uk.ac.roe.wfau.firethorn.meta.ivoa.IvoaColumn;
import uk.ac.roe.wfau.firethorn.webapp.control.AbstractEntityController;
import uk.ac.roe.wfau.firethorn.webapp.paths.Path;

/**
 * Spring MVC controller for <code>IvoaColumn</code>.
 *
 */
@Slf4j
@Controller
@RequestMapping(IvoaColumnLinkFactory.COLUMN_PATH)
public class IvoaColumnController
    extends AbstractEntityController<IvoaColumn, IvoaColumnBean>
    {

    @Override
    public Path path()
        {
        return path(
            IvoaColumnLinkFactory.COLUMN_PATH
            );
        }

    /**
     * Public constructor.
     *
     */
    public IvoaColumnController()
        {
        super();
        }

    /**
     * MVC property for the target entity.
     *
     */
    public static final String TARGET_ENTITY = "urn:ivoa.column.entity" ;

    /**
     * MVC property for updating the name.
     *
     */
    public static final String UPDATE_NAME = "urn:ivoa.column.name" ;

    @Override
    public Iterable<IvoaColumnBean> bean(final Iterable<IvoaColumn> iter)
        {
        return new IvoaColumnBean.Iter(
            iter
            );
        }

    @Override
    public IvoaColumnBean bean(final IvoaColumn entity)
        {
        return new IvoaColumnBean(
            entity
            );
        }

    /**
     * Get the target column based on the identifier in the request.
     * @throws EntityNotFoundException
     *
     */
    @ModelAttribute(TARGET_ENTITY)
    public IvoaColumn entity(
        @PathVariable("ident")
        final String ident
        ) throws EntityNotFoundException {
        log.debug("table() [{}]", ident);
        return factories().ivoa().columns().select(
            factories().ivoa().columns().idents().ident(
                ident
                )
            );
        }

    /**
     * JSON GET request.
     *
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.GET, produces=JSON_MIME)
    public IvoaColumnBean select(
        @ModelAttribute(TARGET_ENTITY)
        final IvoaColumn entity
        ){
        log.debug("select()");
        return bean(
            entity
            ) ;
        }
    }