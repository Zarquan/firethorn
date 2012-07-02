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
package uk.ac.roe.wfau.firethorn.widgeon ;

import lombok.extern.slf4j.Slf4j;

import java.net.URI;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;

import uk.ac.roe.wfau.firethorn.test.TestBase;

import uk.ac.roe.wfau.firethorn.common.entity.Identifier;
import uk.ac.roe.wfau.firethorn.common.entity.exception.*;

/**
 *
 */
@Slf4j
public class TableViewTestCase
extends WidgeonViewTestBase
    {

    @Test
    public void test000()
    throws Exception
        {
        //
        // Create view.
        assertNotNull(
            base().views().create(
                "view-A"
                )
            );
        //
        // Create base catalog and schema.
        assertNotNull(
            base().catalogs().create(
                "catalog-A"
                ).schemas().create(
                    "schema-A"
                    )
            );
        //
        // Select schema view works.
        assertNotNull(
            base().views().select(
                "view-A"
                ).catalogs().select(
                    "catalog-A"
                    ).schemas().select(
                        "schema-A"
                        )

            );
        //
        // Select missing table view fails.
        try {
            base().views().select(
                "view-A"
                ).catalogs().select(
                    "catalog-A"
                    ).schemas().select(
                        "schema-A"
                        ).tables().select(
                            "table-A"
                            );
            fail("NameNotFoundException expected");
            }
        catch (NameNotFoundException ouch)
            {
            assertEquals(
                "table-A",
                ouch.name()
                );            
            }
        }

    @Test
    public void test001()
    throws Exception
        {
        //
        // Create view.
        assertNotNull(
            base().views().create(
                "view-A"
                )
            );
        //
        // Create base catalog and schema.
        assertNotNull(
            base().catalogs().create(
                "catalog-A"
                ).schemas().create(
                    "schema-A"
                    )
            );
        //
        // Select schema view works.
        assertNotNull(
            base().views().select(
                "view-A"
                ).catalogs().select(
                    "catalog-A"
                    ).schemas().select(
                        "schema-A"
                        )
            );
        //
        // Create base table.
        assertNotNull(
            base().catalogs().select(
                "catalog-A"
                ).schemas().select(
                    "schema-A"
                    ).tables().create(
                        "table-A"
                        )
            );
        //
        // Select table view works.
        assertNotNull(
            base().views().select(
                "view-A"
                ).catalogs().select(
                    "catalog-A"
                    ).schemas().select(
                        "schema-A"
                        ).tables().select(
                            "table-A"
                            )
            );
        }

    @Test
    public void test002()
    throws Exception
        {
        //
        // Create view.
        assertNotNull(
            base().views().create(
                "view-A"
                )
            );
        //
        // Create base catalog, schema and table.
        assertNotNull(
            base().catalogs().create(
                "catalog-A"
                ).schemas().create(
                    "schema-A"
                    ).tables().create(
                        "table-A"
                        )
            );
        //
        // Select catalog, schema and table view works.
        assertNotNull(
            base().views().select(
                "view-A"
                ).catalogs().select(
                    "catalog-A"
                    ).schemas().select(
                        "schema-A"
                        ).tables().select(
                            "table-A"
                            )
            );
        }
    }

