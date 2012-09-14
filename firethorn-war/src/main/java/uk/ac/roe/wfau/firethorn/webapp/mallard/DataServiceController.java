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
package uk.ac.roe.wfau.firethorn.webapp.mallard;

import java.net.URL;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.context.request.WebRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.ac.roe.wfau.firethorn.webapp.control.ControllerBase;
import uk.ac.roe.wfau.firethorn.mallard.DataService ;

/**
 * Spring MVC controller for AdqlServices.
 *
 */
@Slf4j
@Controller
@RequestMapping(DataServiceController.CONTROLLER_PATH)
public class DataServiceController
extends ControllerBase
    {
    /**
     * URL path for this Controller.
     *
     */
    public static final String CONTROLLER_PATH = "adql/service/{ident}" ;

    /**
     * MVC property for a Service entity.
     *
     */
    public static final String SERVICE_ENTITY = "adql.service.entity" ;

    /**
     * GET request for a service.
     *
     */
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView select(
	    @PathVariable("ident") final String ident,
        final WebRequest request,
	    final ModelAndView model
	    ){
        log.debug("select(String ident)");
        log.debug("  Ident [{}]", ident);

        //
        // Try locating the service.
        try {
            final DataService service = womble().services().select(
                womble().services().ident(
                    ident
                    )
                );

		    model.addObject(
		        SERVICE_ENTITY,
		        service
		        );

		    model.setViewName(
		        "adql/services/display"
		        );

            return model ;
            }

        catch (final Exception ouch)
            {
            return null ;
            }
        }

	/**
	 * Bean wrapper to enable the JSON converter to process a DataService.  
	 *
	 */
	public class DataServiceBean
	    {
        private String url ;
        private DataService service ;
        private DataServiceBean(String url , DataService service)
            {
            this.url = url ;
            this.service = service ;
            }
	    public String getUrl()
	        {
	        return this.url;
	        }
        public String getIdent()
            {
            return service.ident().toString();
            }
        public String getName()
            {
            return service.name();
            }
        public Date getCreated()
            {
            return service.created();
            }
        public Date getModified()
            {
            return service.modified();
            }
	    }

	/**
     * JSON GET request.
     *
     */
	@ResponseBody
    @RequestMapping(method=RequestMethod.GET, produces="application/json")
    public DataServiceBean jsonGet(
        @PathVariable("ident") final String ident,
        final HttpServletRequest request,
        final ModelAndView model
        ){
        log.debug("select(String ident)");
        log.debug("  Ident [{}]", ident);
        try {
            return new DataServiceBean(
                request.getRequestURL().toString(),
                womble().services().select(
                    womble().services().ident(
                        ident
                        )
                    )
                );
            }
        catch (final Exception ouch)
            {
            return null ;
            }
        }
    }

