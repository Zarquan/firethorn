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
package uk.ac.roe.wfau.firethorn.widgeon.name;

import org.springframework.stereotype.Component;

import uk.ac.roe.wfau.firethorn.adql.query.green.GreenQuery;
import uk.ac.roe.wfau.firethorn.meta.base.BaseNameFactory;

/**
 *
 *
 */
@Component
public class AdqlQueryNameFactory
extends BaseNameFactory<GreenQuery>
implements GreenQuery.NameFactory
    {
    @Override
    public String name(final String name)
        {
    	if (name != null)
    		{
    		return super.name(
				name
				);
    		}
    	else {
    		return name();
    		}
        }
    @Override
    public String name()
        {
        return datename(
            "QUERY"
            );
        }
    }
