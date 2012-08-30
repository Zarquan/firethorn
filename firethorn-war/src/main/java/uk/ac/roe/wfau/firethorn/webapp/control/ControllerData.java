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

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.context.request.WebRequest;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Test MVC controller data.
 *
 */
@Slf4j 
public class ControllerData
    {

    /**
     * Spring MVC property name.
     *
     */
    public static final String MODEL_PROPERTY = "firethorn.request.data" ;

   
    /**
     * Protected constructor.
     *
     */
    protected ControllerData(WebRequest request)
        {
        this.request = request ;
        }

    /**
     * Our web request.
     *
     */
    private WebRequest request ;

    /**
     * Access to our web request.
     *
     */
    public WebRequest request()
        {
        return this.request ;
        }

    }

