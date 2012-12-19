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
package uk.ac.roe.wfau.firethorn.widgeon.jdbc ;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 *
 */
public class JdbcResourceTestCase
extends JdbcResourceTestBase
    {

    @Test
    public void test000()
    throws Exception
        {
        assertNotNull(
            jdbc().resource()
            );
        }

    @Test
    public void test001()
    throws Exception
        {
        assertIsNull(
            jdbc().resource().schemas().select(
                "schema-A"
                )
            );
        }

    @Test
    public void test002()
    throws Exception
        {
        assertNotNull(
            jdbc().resource().schemas().create(
                "schema-A"
                )
            );
        }

    @Test
    public void test003()
    throws Exception
        {
        assertNotNull(
            jdbc().resource().schemas().create(
                "schema-A"
                )
            );
        assertNotNull(
            jdbc().resource().schemas().select(
                "schema-A"
                )
            );
        }

    @Test
    public void test006()
    throws Exception
        {
        assertNotNull(
            jdbc().resource().schemas().create(
                "schema-A"
                )
            );
        assertIsNull(
            jdbc().resource().schemas().select(
                "schema-A"
                ).tables().select(
                    "table-A"
                    )
            );
        }

    @Test
    public void test007()
    throws Exception
        {
        assertNotNull(
            jdbc().resource().schemas().create(
                "schema-A"
                ).tables().create(
                    "table-A"
                    )
            );

        assertNotNull(
            jdbc().resource().schemas().select(
                "schema-A"
                ).tables().select(
                    "table-A"
                    )
            );
        }

    @Test
    public void test008()
    throws Exception
        {
        assertNotNull(
            jdbc().resource().schemas().create(
                "schema-A"
                ).tables().create(
                    "table-A"
                    )
            );
        assertIsNull(
            jdbc().resource().schemas().select(
                "schema-A"
                ).tables().select(
                    "table-A"
                    ).columns().select(
                        "column-A"
                        )
            );
        }

    @Test
    public void test009()
    throws Exception
        {
        assertNotNull(
            jdbc().resource().schemas().create(
                "schema-A"
                ).tables().create(
                    "table-A"
                    ).columns().create(
                        "column-A"
                        )
            );

        assertNotNull(
            jdbc().resource().schemas().select(
                "schema-A"
                ).tables().select(
                    "table-A"
                    ).columns().select(
                        "column-A"
                        )
            );
        }

    @Test
    public void test010()
    throws Exception
        {
        assertNotNull(
            jdbc().resource().schemas().create(
                "schema-A"
                ).tables().create(
                    "table-A"
                    ).columns().create(
                        "column-A"
                        )
            );

        assertIsNull(
            jdbc().resource().schemas().select(
                "schema-A"
                ).tables().select(
                    "table-A"
                    ).columns().select(
                        "column-a"
                        )
            );
        }

    @Test
    public void test011()
    throws Exception
        {
        assertNotNull(
            jdbc().resource().schemas().create(
                "schema-A"
                ).tables().create(
                    "table-A"
                    ).columns().create(
                        "column-A"
                        )
            );

        assertIsNull(
            jdbc().resource().schemas().select(
                "schema-A"
                ).tables().select(
                    "table-A"
                    ).columns().select(
                        "column-a"
                        )
            );

        jdbc().resource().schemas().select(
            "schema-A"
            ).tables().select(
                "table-A"
                ).columns().select(
                    "column-A"
                    ).name(
                        "column-a"
                        );

        assertNotNull(
            jdbc().resource().schemas().select(
                "schema-A"
                ).tables().select(
                    "table-A"
                    ).columns().select(
                        "column-a"
                        )
            );

        assertIsNull(
            jdbc().resource().schemas().select(
                "schema-A"
                ).tables().select(
                    "table-A"
                    ).columns().select(
                        "column-A"
                        )
            );
        }
    }

