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
import org.springframework.web.servlet.ModelAndView;

import uk.ac.roe.wfau.firethorn.common.entity.exception.IdentifierNotFoundException;
import uk.ac.roe.wfau.firethorn.webapp.control.AbstractController;
import uk.ac.roe.wfau.firethorn.webapp.control.RedirectHeader;
import uk.ac.roe.wfau.firethorn.webapp.paths.Path;
import uk.ac.roe.wfau.firethorn.webapp.paths.PathImpl;

/**
 * Spring MVC controller for schema tables.
 *
 */
@Slf4j
@Controller
@RequestMapping(JdbcSchemaIdentFactory.TABLES_PATH)
public class JdbcSchemaTablesController
extends AbstractController
    {
    @Override
    public Path path()
        {
        return new PathImpl(
            JdbcSchemaIdentFactory.TABLES_PATH
            );
        }

    /**
     * Public constructor.
     *
     */
    public JdbcSchemaTablesController()
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
     * URL path for the create method.
     * 
     */
    public static final String CREATE_PATH = "create" ;

    /**
     * MVC property for the Resource name.
     *
     */
    public static final String SELECT_NAME = "jdbc.schema.tables.select.name" ;

    /**
     * MVC property for the select results.
     *
     */
    public static final String SELECT_RESULT = "jdbc.schema.tables.select.result" ;

    /**
     * MVC property for the search text.
     *
     */
    public static final String SEARCH_TEXT = "jdbc.schema.tables.search.text" ;

    /**
     * MVC property for the search results.
     *
     */
    public static final String SEARCH_RESULT = "jdbc.schema.tables.search.result" ;

    /**
     * MVC property for the create name.
     *
     */
    public static final String CREATE_NAME = "jdbc.schema.tables.create.name" ;

    /**
     * Get the parent JdbcSchema based on the request ident.
     *
     */
    @ModelAttribute(JdbcSchemaController.SCHEMA_ENTITY)
    public JdbcSchema schema(
        @PathVariable("ident")
        final String ident
        ){
        log.debug("schema() [{}]", ident);
        try {
            return womble().resources().jdbc().schemas().select(
                womble().resources().jdbc().schemas().ident(
                    ident
                    )
                );
            }
        catch (IdentifierNotFoundException e)
            {
            log.error("Unable to locate schema [{}]", ident);
            return null ;
            }
        }

    /**
     * Select all.
     * 
     */
    public JdbcTableBeanIter select(
        @ModelAttribute(JdbcSchemaController.SCHEMA_ENTITY)
        final JdbcSchema schema
        ){
        log.debug("select()");
        return new JdbcTableBeanIter(
            schema.tables().select()
            );
        }

    /**
     * Default HTML GET request (select all).
     *
     */
    @RequestMapping(value="", method=RequestMethod.GET)
    public ModelAndView htmlIndex(
        @ModelAttribute(JdbcSchemaController.SCHEMA_ENTITY)
        final JdbcSchema schema,
        final ModelAndView model
        ){
        log.debug("htmlIndex()");
        model.addObject(
            JdbcSchemaTablesController.SELECT_RESULT,
            select(
                schema
                )
            );
        model.setViewName(
            "jdbc/schema/select"
            );
        return model ;
        }

    /**
     * HTML GET request to select all.
     *
     */
    @RequestMapping(value=SELECT_PATH, method=RequestMethod.GET)
    public ModelAndView htmlSelect(
        @ModelAttribute(JdbcSchemaController.SCHEMA_ENTITY)
        final JdbcSchema schema,
        final ModelAndView model
        ){
        log.debug("htmlSelect()");
        model.addObject(
            JdbcSchemaTablesController.SELECT_RESULT,
            select(
                schema
                )
            );
        model.setViewName(
            "jdbc/catalog/select"
            );
        return model ;
        }

    /**
     * JSON GET request to select all.
     *
     */
    @ResponseBody
    @RequestMapping(value=SELECT_PATH, method=RequestMethod.GET, produces=JSON_MAPPING)
    public JdbcTableBeanIter jsonSelect(
        @ModelAttribute(JdbcSchemaController.SCHEMA_ENTITY)
        final JdbcSchema schema,
        final ModelAndView model
        ){
        log.debug("jsonSelect()");
        return select(
            schema
            );
        }

    /**
     * Select by name.
     * 
     */
    public JdbcTableBean select(
        @ModelAttribute(JdbcSchemaController.SCHEMA_ENTITY)
        final JdbcSchema schema,
        String name
        ){
        log.debug("select(String) [{}]", name);
        return new JdbcTableBean(
            schema.tables().select(
                name
                )
            );
        }
    
    /**
     * HTML GET or POST request to select by name.
     * @todo Wrap the entities as beans (with URI) 
     *
     */
    @RequestMapping(value=SELECT_PATH, params=SELECT_NAME)
    public ModelAndView htmlSelect(
        @ModelAttribute(JdbcSchemaController.SCHEMA_ENTITY)
        final JdbcSchema schema,
        @RequestParam(SELECT_NAME)
        final String name,
        final ModelAndView model
        ){
        log.debug("htmlSelect(String) [{}]", name);
        model.addObject(
            SELECT_NAME,
            name
            );
        model.addObject(
            SELECT_RESULT,
            select(
                schema,
                name
                )
            );
        model.setViewName(
            "jdbc/catalog/select"
            );
        return model ;
        }

    /**
     * JSON GET or POST request to select by name.
     *
     */
    @ResponseBody
    @RequestMapping(value=SELECT_PATH, params=SELECT_NAME, produces=JSON_MAPPING)
    public JdbcTableBean jsonSelect(
        @ModelAttribute(JdbcSchemaController.SCHEMA_ENTITY)
        final JdbcSchema schema,
        @RequestParam(SELECT_NAME)
        final String name,
        final ModelAndView model
        ){
        log.debug("jsonSelect(String) [{}]", name);
        return select(
            schema,
            name
            );
        }

    /**
     * Search by text.
     * 
     */
    public JdbcTableBeanIter search(
        @ModelAttribute(JdbcSchemaController.SCHEMA_ENTITY)
        final JdbcSchema schema,
        String text
        ){
        log.debug("search(String) [{}]", text);
        return new JdbcTableBeanIter(
            schema.tables().search(
                text
                )
            );
        }
    
    /**
     * HTML GET request to display the search form.
     *
     */
    @RequestMapping(value=SEARCH_PATH, method=RequestMethod.GET)
    public ModelAndView htmlSearch(
        @ModelAttribute(JdbcSchemaController.SCHEMA_ENTITY)
        final JdbcSchema schema,
        final ModelAndView model
        ){
        log.debug("htmlSearch");
        model.setViewName(
            "jdbc/catalog/search"
            );
        return model ;
        }

    /**
     * HTML GET or POST request to search by text.
     * @todo Wrap the entities as beans (with URI) 
     *
     */
    @RequestMapping(value=SEARCH_PATH, params=SEARCH_TEXT)
    public ModelAndView htmlSearch(
        @ModelAttribute(JdbcSchemaController.SCHEMA_ENTITY)
        final JdbcSchema schema,
        @RequestParam(SEARCH_TEXT)
        final String text,
        final ModelAndView model
        ){
        log.debug("htmlSearch(String) [{}]", text);
        model.addObject(
            SEARCH_TEXT,
            text
            );
        model.addObject(
            SEARCH_RESULT,
            search(
                schema,
                text
                )
            );
        model.setViewName(
            "jdbc/catalog/search"
            );
        return model ;
        }

    /**
     * JSON GET or POST request to search by text.
     * @todo change the server side search return a list
     *  
     */
    @ResponseBody
    @RequestMapping(value=SEARCH_PATH, params=SEARCH_TEXT, produces=JSON_MAPPING)
    public JdbcTableBeanIter jsonSearch(
        @ModelAttribute(JdbcSchemaController.SCHEMA_ENTITY)
        final JdbcSchema schema,
        @RequestParam(SEARCH_TEXT)
        final String text,
        final ModelAndView model
        ){
        log.debug("jsonSearch(String) [{}]", text);
        return search(
            schema,
            text
            );
        }

    /**
     * HTML GET request to display the create form.
     *
     */
    @RequestMapping(value=CREATE_PATH, method=RequestMethod.GET)
    public ModelAndView htmlCreate(
        @ModelAttribute(JdbcSchemaController.SCHEMA_ENTITY)
        final JdbcSchema schema,
        final ModelAndView model
        ){
        log.debug("htmlCreate()");
        model.setViewName(
            "jdbc/catalog/create"
            );
        return model ;
        }

    /**
     * Create a new catalog.
     *
     */
    public JdbcTableBean create(
        @ModelAttribute(JdbcSchemaController.SCHEMA_ENTITY)
        final JdbcSchema schema,
        final String name
        ){
        log.debug("create(String) [{}]", name);
        return new JdbcTableBean(
            schema.tables().create(
                name
                )
            );
        }

    /**
     * HTML POST request to create a new AdqlService.
     *
     */
    @RequestMapping(value=CREATE_PATH, method=RequestMethod.POST)
    public ResponseEntity<String>  htmlCreate(
        @ModelAttribute(JdbcSchemaController.SCHEMA_ENTITY)
        final JdbcSchema schema,
        @RequestParam(CREATE_NAME)
        final String name,
        final ModelAndView model
        ){
        log.debug("htmlCreate(String) [{}]", name);
        return new ResponseEntity<String>(
            new RedirectHeader(
                create(
                    schema,
                    name
                    )
                ),
            HttpStatus.SEE_OTHER
            ); 
        }

    /**
     * JSON POST request to create a new AdqlService.
     *
     */
    @RequestMapping(value=CREATE_PATH, method=RequestMethod.POST, produces=JSON_MAPPING)
    public ResponseEntity<JdbcTableBean> jsonCreate(
        @ModelAttribute(JdbcSchemaController.SCHEMA_ENTITY)
        final JdbcSchema schema,
        @RequestParam(CREATE_NAME)
        final String name,
        final ModelAndView model
        ){
        log.debug("jsonCreate(String) [{}]", name);
        JdbcTableBean table = create(
            schema,
            name
            );
        return new ResponseEntity<JdbcTableBean>(
            table,
            new RedirectHeader(
                table
                ),
            HttpStatus.CREATED
            ); 
        }
    }
