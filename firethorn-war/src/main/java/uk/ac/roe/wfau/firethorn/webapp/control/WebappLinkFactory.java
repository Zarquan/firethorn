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
package uk.ac.roe.wfau.firethorn.webapp.control;

import uk.ac.roe.wfau.firethorn.common.entity.Entity;
import uk.ac.roe.wfau.firethorn.common.entity.EntityLinkFactory;

/**
 * Base class for IdentFactory implementations within the webapp.
 *
 */
public abstract class WebappLinkFactory<EntityType extends Entity>
extends EntityLinkFactory<EntityType>
implements Entity.LinkFactory<EntityType>
    {

    public static final String IDENT_FIELD = "ident" ;
    public static final String IDENT_TOKEN = "{ident}" ;
    public static final String IDENT_REGEX = "\\{ident\\}" ;

    public static final String SERVICE_BASE = "http://localhost:8080/" ;
    public static final String CONTEXT_PATH = "firethorn" ;
    public static final String SERVLET_PATH = "" ;

    public static final String SERVICE_PATH = SERVICE_BASE + CONTEXT_PATH ;

    protected WebappLinkFactory(String path)
        {
        super(path);
        }

    /*
     *
     * http://static.springsource.org/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-buildinguris
     * Re-use host, port, context path
     * Append the literal part of the servlet mapping to the path
     * Append "/accounts" to the path
     *
     * ServletUriComponentsBuilder ucb =
     *   ServletUriComponentsBuilder.fromServletMapping(request).path("/accounts").build()
     *
     */
    protected String link(final String path, final Entity entity)
        {
        return link(
            path,
            entity.ident().value().toString()
            ); 
        }

    protected String link(final String path, final String ident)
        {
        return SERVICE_PATH + path.replaceFirst(
            IDENT_REGEX,
            ident
            );
        }
    }
