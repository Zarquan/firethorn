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
package uk.ac.roe.wfau.firethorn.webapp.votable;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.ac.roe.wfau.firethorn.entity.exception.EntityNotFoundException;
import uk.ac.roe.wfau.firethorn.webapp.control.WebappLinkFactory;
import uk.ac.roe.wfau.firethorn.webapp.paths.Path;
import uk.ac.roe.wfau.firethorn.widgeon.jdbc.JdbcTableLinkFactory;

/**
 * Spring Controller to generate VOTable response for an AdqlTable.
 * 
 * Based on the VOTable-1.3 specification.
 * http://www.ivoa.net/documents/VOTable/20130315/PR-VOTable-1.3-20130315.html
 *
 */
@Controller
@RequestMapping(JdbcTableLinkFactory.VOTABLE_PATH)
public class JdbcTableVOTableController
extends BaseTableVOTableController
    {
    /**
     * Public constructor.
     *
     */
    public JdbcTableVOTableController()
        {
        super();
        }

    @Override
    public Path path()
        {
        return path(
            JdbcTableLinkFactory.VOTABLE_PATH
            );
        }

    /**
     * VOTable GET request.
     *
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.GET, produces=TEXT_XML_MIME)
    public void votable(
        @PathVariable(WebappLinkFactory.IDENT_FIELD)
        final String ident,
        final HttpServletResponse response
        ) throws EntityNotFoundException, IOException {
        response.setContentType(
            TEXT_XML_MIME
            );
        response.setCharacterEncoding(
            "UTF-8"
            );
		table(
		    response.getWriter(),
	        factories().jdbc().tables().select(
	            factories().jdbc().tables().idents().ident(
	                ident
	                )
	            )
		    );
        }
    }
