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
package uk.ac.roe.wfau.firethorn.tuesday.test.jdbc;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import uk.ac.roe.wfau.firethorn.test.TestBase;
import uk.ac.roe.wfau.firethorn.tuesday.TuesdayFactories;
import uk.ac.roe.wfau.firethorn.tuesday.TuesdayJdbcResource;

/**
 *
 *
 */
@Slf4j
public class TuesdayJdbcResourceTestCase
    extends TestBase
    {

    /**
     * Our TuesdayFactories instance.
     *
     */
    @Autowired
    private TuesdayFactories factories;

    /**
     * Access to our TuesdayFactories singleton instance.
     *
     */
    public TuesdayFactories factories()
        {
        return this.factories;
        }

    //
    // Local properties file.
    private Properties config = new Properties();

    public static final String CONFIG_PATH = "user.home" ;
    public static final String CONFIG_FILE = "firethorn.properties" ;

    @Before
    @Override
    public void before()
    throws Exception
        {
        log.debug("Before ----");
        this.config.load(
            new FileInputStream(
                new File(
                    System.getProperty(
                        CONFIG_PATH
                        ),
                    CONFIG_FILE
                    )
                )
            );
        }    

    @After
    @Override
    public void after()
        {
        log.debug("After ----");
        }

    @Test
    public void test000()
    throws Exception
        {
        TuesdayJdbcResource resource = factories().jdbc().resources().create(
            unique("resource")
            );
        assertNotNull(
            resource
            );
        }
    @Test
    public void test001()
    throws Exception
        {
        assertNotNull(
            factories()
            );
        TuesdayJdbcResource resource = factories().jdbc().resources().create(
            unique("resource")
            );
        assertNotNull(
            resource
            );
        //
        // Check the local PostgreSQL database.
        resource.connection().url(
            "spring:PgSqlLocalTest"
            );
        assertNotNull(
            resource.connection().open()
            );
        assertNotNull(
            resource.connection().metadata()
            );
        assertEquals(
            "PostgreSQL",
            resource.connection().metadata().getDatabaseProductName()
            );
        }
    @Test
    public void test002()
    throws Exception
        {
        assertNotNull(
            factories()
            );
        TuesdayJdbcResource resource = factories().jdbc().resources().create(
            unique("resource")
            );
        assertNotNull(
            resource
            );
        //
        // Check the local MySQL database.
        resource.connection().url(
            "spring:MySqlLocalTest"
            );
        assertNotNull(
            resource.connection().open()
            );
        assertNotNull(
            resource.connection().metadata()
            );
        assertEquals(
            "MySQL",
            resource.connection().metadata().getDatabaseProductName()
            );
        }
    @Test
    public void test003()
    throws Exception
        {
        assertNotNull(
            factories()
            );
        TuesdayJdbcResource resource = factories().jdbc().resources().create(
            unique("resource")
            );
        assertNotNull(
            resource
            );
        //
        // Check the live ROE database.
        resource.connection().url(
            "spring:RoeLiveData"
            );
        assertNotNull(
            resource.connection().open()
            );
        assertNotNull(
            resource.connection().metadata()
            );
        assertEquals(
            "Microsoft SQL Server",
            resource.connection().metadata().getDatabaseProductName()
            );
        }
    }
