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
package uk.ac.roe.wfau.firethorn.adql.query.atlas ;

import org.junit.Before;
import org.junit.Ignore;

import lombok.extern.slf4j.Slf4j;
import uk.ac.roe.wfau.firethorn.adql.query.AbstractQueryTestBase;
import uk.ac.roe.wfau.firethorn.meta.adql.AdqlResource;
import uk.ac.roe.wfau.firethorn.meta.jdbc.JdbcResource;

/**
 *
 *
 */
@Slf4j
@Ignore
public abstract class AtlasQueryTestBase
extends AbstractQueryTestBase
    {

    protected static final String ATLAS_VERSION = "ATLASDR1" ;

    /**
     * Load our resources.
     *
     */
    @Before
    public void loadAtlasResources()
    throws Exception
        {
        log.debug("loadAtlasResources()");

        replace(
            "{ATLAS_VERSION}",
            ATLAS_VERSION
            );

        JdbcResource jdbcspace = jdbcResource(
            "atlas.jdbc.resource",
            "*",
            "Atlas JDBC",
            config().property("firethorn.atlas.url"),
            config().property("firethorn.atlas.user"),
            config().property("firethorn.atlas.pass"),
            config().property("firethorn.atlas.driver")
            );

        AdqlResource adqlspace = adqlResource(
            "atlas.adql.resource",
            "atlas.adql.resource"
            );

        testSchema(adqlspace, jdbcspace, ATLAS_VERSION, ATLAS_VERSION, "dbo");
        testSchema(adqlspace, jdbcspace, "ROSAT",       "ROSAT",       "dbo");
        testSchema(adqlspace, jdbcspace, "BestDR9",     "BestDR9",     "dbo");
        testSchema(adqlspace, jdbcspace, "TWOMASS",     "TWOMASS",     "dbo");

        }
    }
