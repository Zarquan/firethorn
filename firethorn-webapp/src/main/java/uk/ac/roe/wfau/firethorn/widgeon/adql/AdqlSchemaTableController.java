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

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.ac.roe.wfau.firethorn.entity.exception.NotFoundException;
import uk.ac.roe.wfau.firethorn.meta.adql.AdqlSchema;
import uk.ac.roe.wfau.firethorn.meta.base.BaseComponent.CopyDepth;
import uk.ac.roe.wfau.firethorn.webapp.control.AbstractController;
import uk.ac.roe.wfau.firethorn.webapp.control.RedirectHeader;
import uk.ac.roe.wfau.firethorn.webapp.control.WebappLinkFactory;
import uk.ac.roe.wfau.firethorn.webapp.paths.Path;

/**
 * Spring MVC controller for <code>AdqlSchema</code> tables.
 *
 */
@Slf4j
@Controller
@RequestMapping(AdqlSchemaLinkFactory.SCHEMA_TABLE_PATH)
public class AdqlSchemaTableController
extends AbstractController
    {
    @Override
    public Path path()
        {
        return path(
            AdqlSchemaLinkFactory.SCHEMA_TABLE_PATH
            );
        }

    /**
     * Public constructor.
     *
     */
    public AdqlSchemaTableController()
        {
        super();
        }

    /**
     * URL path for the select method.
     *
     */
    public static final String SELECT_PATH = "select" ;

    /**
     * URL path for the search method.
     *
     */
    public static final String SEARCH_PATH = "search" ;

    /**
     * URL path for the import method.
     *
     */
    public static final String IMPORT_PATH = "import" ;

    /**
     * MVC property for the Resource name.
     *
     */
    public static final String SELECT_NAME = "adql.schema.table.select.name" ;

    /**
     * MVC property for the select results.
     *
     */
    public static final String SELECT_RESULT = "adql.schema.table.select.result" ;

    /**
     * MVC property for the search text.
     *
     */
    public static final String SEARCH_TEXT = "adql.schema.table.search.text" ;

    /**
     * MVC property for the search results.
     *
     */
    public static final String SEARCH_RESULT = "adql.schema.table.search.result" ;

    /**
     * MVC property for the copy depth (REAL or THIN).
     *
     */
    public static final String COPY_DEPTH = "adql.table.depth" ;

    /**
     * MVC property for the import base.
     *
     */
    public static final String IMPORT_BASE = "adql.schema.table.import.base" ;

    /**
     * MVC property for the import name.
     *
     */
    public static final String IMPORT_NAME = "adql.schema.table.import.name" ;


    /**
     * Get the parent entity based on the request ident.
     * @throws NotFoundException
     *
     */
    @ModelAttribute(AdqlSchemaController.TARGET_ENTITY)
    public AdqlSchema parent(
        @PathVariable(WebappLinkFactory.IDENT_FIELD)
        final String ident
        ) throws NotFoundException {
        log.debug("parent() [{}]", ident);
        return factories().adql().schemas().select(
            factories().adql().schemas().idents().ident(
                ident
                )
            );
        }

    /**
     * JSON GET request to select all.
     *
     */
    @ResponseBody
    @RequestMapping(value=SELECT_PATH, method=RequestMethod.GET, produces=JSON_MAPPING)
    public AdqlTableBean.Iter select(
        @ModelAttribute(AdqlSchemaController.TARGET_ENTITY)
        final AdqlSchema schema
        ){
        log.debug("select()");
        return new AdqlTableBean.Iter(
            schema.tables().select()
            );
        }

    /**
     * JSON request to select by name.
     *
     */
    @ResponseBody
    @RequestMapping(value=SELECT_PATH, params=SELECT_NAME, produces=JSON_MAPPING)
    public AdqlTableBean select(
        @ModelAttribute(AdqlSchemaController.TARGET_ENTITY)
        final AdqlSchema schema,
        @RequestParam(SELECT_NAME)
        final String name
        ){
        log.debug("select(String) [{}]", name);
        return new AdqlTableBean(
            schema.tables().select(
                name
                )
            );
        }

    /**
     * JSON request to search by name.
     *
     */
    @ResponseBody
    @RequestMapping(value=SEARCH_PATH, params=SEARCH_TEXT, produces=JSON_MAPPING)
    public AdqlTableBean.Iter search(
        @ModelAttribute(AdqlSchemaController.TARGET_ENTITY)
        final AdqlSchema schema,
        @RequestParam(SEARCH_TEXT)
        final String text
        ){
        log.debug("search(String) [{}]", text);
        return new AdqlTableBean.Iter(
            schema.tables().search(
                text
                )
            );
        }

    /**
     * A 'created' response entity.
     *
     */
    public ResponseEntity<AdqlTableBean> response(final AdqlTableBean bean)
        {
        return new ResponseEntity<AdqlTableBean>(
            bean,
            new RedirectHeader(
                bean
                ),
            HttpStatus.CREATED
            );
        }
    
    /**
     * JSON request to import a table.
     * @throws NotFoundException
     *
     */
    @ResponseBody
    @RequestMapping(value=IMPORT_PATH, params={IMPORT_BASE}, method=RequestMethod.POST, produces=JSON_MAPPING)
    public ResponseEntity<AdqlTableBean> inport(
        @ModelAttribute(AdqlSchemaController.TARGET_ENTITY)
        final AdqlSchema schema,
        @RequestParam(value=COPY_DEPTH, required=false)
        final CopyDepth type,
        @RequestParam(value=IMPORT_BASE, required=true)
        final String base
        ) throws NotFoundException {
        log.debug("inport(CopyDepth, String) [{}][{}]", type, base);
        return response(
            new AdqlTableBean(
                schema.tables().create(
                    ((type != null) ? type : CopyDepth.FULL),
                    factories().base().tables().select(
                        factories().base().tables().links().ident(
                            base
                            )
                        )
                    )
                )
            );
        }

    /**
     * JSON request to import a table.
     * @throws NotFoundException
     *
     */
    @ResponseBody
    @RequestMapping(value=IMPORT_PATH, params={IMPORT_BASE, IMPORT_NAME}, method=RequestMethod.POST, produces=JSON_MAPPING)
    public ResponseEntity<AdqlTableBean> inport(
        @ModelAttribute(AdqlSchemaController.TARGET_ENTITY)
        final AdqlSchema schema,
        @RequestParam(value=COPY_DEPTH, required=false)
        final CopyDepth type,
        @RequestParam(value=IMPORT_BASE, required=true)
        final String base,
        @RequestParam(value=IMPORT_NAME, required=true)
        final String name
        ) throws NotFoundException {
        log.debug("inport(CopyDepth, String, String) [{}][{}]", type, base, name);
        return response(
            new AdqlTableBean(
                schema.tables().create(
                    type,
                    factories().base().tables().select(
                        factories().base().tables().links().ident(
                            base
                            )
                        ),
                    name
                    )
                )
            );
        }
    }
