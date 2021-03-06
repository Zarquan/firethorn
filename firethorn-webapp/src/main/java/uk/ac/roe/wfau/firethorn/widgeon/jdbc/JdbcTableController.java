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
package uk.ac.roe.wfau.firethorn.widgeon.jdbc;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import uk.ac.roe.wfau.firethorn.access.ProtectionError;
import uk.ac.roe.wfau.firethorn.access.ProtectionException;
import uk.ac.roe.wfau.firethorn.entity.exception.EntityNotFoundException;
import uk.ac.roe.wfau.firethorn.entity.exception.IdentifierFormatException;
import uk.ac.roe.wfau.firethorn.entity.exception.NameFormatException;
import uk.ac.roe.wfau.firethorn.meta.adql.AdqlTable;
import uk.ac.roe.wfau.firethorn.meta.jdbc.JdbcTable;
import uk.ac.roe.wfau.firethorn.webapp.control.AbstractEntityController;
import uk.ac.roe.wfau.firethorn.webapp.control.WebappLinkFactory;
import uk.ac.roe.wfau.firethorn.webapp.paths.Path;
import uk.ac.roe.wfau.firethorn.widgeon.name.JdbcTableLinkFactory;

/**
 * Spring MVC controller for <code>JdbcTables</code>.
 *
 */
@Slf4j
@Controller
@RequestMapping(JdbcTableLinkFactory.ENTITY_PATH)
public class JdbcTableController
extends AbstractEntityController<JdbcTable, JdbcTableBean>
implements JdbcTableModel
    {

    @Override
    public Path path()
        {
        return path(
            JdbcTableLinkFactory.ENTITY_PATH
            );
        }

    /**
     * Public constructor.
     *
     */
    public JdbcTableController()
        {
        super();
        }

    @Override
    public Iterable<JdbcTableBean> bean(final Iterable<JdbcTable> iter)
        {
        return new JdbcTableBean.Iter(
            iter
            );
        }

    @Override
    public JdbcTableBean bean(final JdbcTable entity)
        {
        return new JdbcTableBean(
            entity
            );
        }

    /**
     * Get the target table based on the identifier in the request.
     * @throws EntityNotFoundException
     * @throws ProtectionException 
     * @throws IdentifierFormatException 
     *
     */
    @ModelAttribute(JdbcTableController.TARGET_ENTITY)
    public JdbcTable entity(
        @PathVariable(WebappLinkFactory.IDENT_FIELD)
        final String ident
        )
    throws EntityNotFoundException, IdentifierFormatException, ProtectionException
        {
        return factories().jdbc().tables().entities().select(
            factories().jdbc().tables().idents().ident(
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
    public ResponseEntity<JdbcTableBean> select(
        @ModelAttribute(TARGET_ENTITY)
        final JdbcTable entity
        ){
        return selected(
            entity
            );
        }

    /**
     * POST update name request.
     * @throws ProtectionException 
     * @throws NameFormatException 
     *
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.POST, params={TABLE_NAME_PARAM}, produces=JSON_MIME)
    public ResponseEntity<JdbcTableBean> update(
        @ModelAttribute(TARGET_ENTITY)
        final JdbcTable entity,
        @RequestParam(value=TABLE_NAME_PARAM, required=true)
        final String name
        )
    throws NameFormatException, ProtectionException
        {
        //
        // Needs a transaction ..
        if (null != name)
            {
            entity.name(
                name
                );
            }
        return selected(
            entity
            );
        }

    /**
     * POST update request.
     *
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.POST, produces=JSON_MIME)
    public ResponseEntity<JdbcTableBean> update(
        @ModelAttribute(TARGET_ENTITY)
        final JdbcTable entity,
        @RequestParam(value=JDBC_STATUS_PARAM, required=false)
        final JdbcTable.TableStatus jdbcstatus,
        @RequestParam(value=ADQL_STATUS_PARAM, required=false)
        final AdqlTable.TableStatus adqlstatus
        ){
        log.debug("update(JdbcTable.JdbcStatus)");
        log.debug(" jdbcstatus [{}]", jdbcstatus);
        log.debug(" adqlstatus [{}]", adqlstatus);

        factories().spring().transactor().update(
            new Runnable()
                {
                @Override
                public void run()
                    {
                    try {
                        if (null != jdbcstatus)
                            {
                            entity.meta().jdbc().status(
                                jdbcstatus
                                );
                            }
                        if (null != adqlstatus)
                            {
                            entity.meta().adql().status(
                                adqlstatus
                                );
                            }
                        }
                    catch (ProtectionException ouch)
                        {
                        throw new ProtectionError(
                            ouch
                            );
                        }
                    }
                }
            );

        return selected(
            entity
            );
        }
    }
