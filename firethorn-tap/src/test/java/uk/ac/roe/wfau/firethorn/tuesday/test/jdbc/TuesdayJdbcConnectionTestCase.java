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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import uk.ac.roe.wfau.firethorn.test.TestBase;
import uk.ac.roe.wfau.firethorn.tuesday.TuesdayFactories;
import uk.ac.roe.wfau.firethorn.tuesday.TuesdayJdbcColumn;
import uk.ac.roe.wfau.firethorn.tuesday.TuesdayJdbcConnection;
import uk.ac.roe.wfau.firethorn.tuesday.TuesdayJdbcConnectionEntity;
import uk.ac.roe.wfau.firethorn.tuesday.TuesdayJdbcResource;
import uk.ac.roe.wfau.firethorn.tuesday.TuesdayJdbcSchema;
import uk.ac.roe.wfau.firethorn.tuesday.TuesdayJdbcTable;

/**
 * TODO experiment with this
 * http://static.springsource.org/spring/docs/3.1.x/javadoc-api/org/springframework/jdbc/datasource/embedded/EmbeddedDatabaseBuilder.html
 *
 */
@Slf4j
public class TuesdayJdbcConnectionTestCase
    extends TuesdayJdbcResourceTestCase
    {

    /**
     * Check the local PostgreSQL database.
     *
     */
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
        resource.connection().url(
            "spring:PgsqlLocalTest"
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
        assertEquals(
            TuesdayJdbcResource.JdbcProductType.PGSQL.alias(),
            resource.connection().metadata().getDatabaseProductName()
            );
        assertEquals(
            TuesdayJdbcResource.JdbcProductType.PGSQL,
            TuesdayJdbcResource.JdbcProductType.match(
                resource.connection().metadata().getDatabaseProductName()
                )
            );
        }

    /**
     * Check the local MySQL database.
     *
     */
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
        resource.connection().url(
            "spring:MysqlLocalTest"
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
        assertEquals(
            TuesdayJdbcResource.JdbcProductType.MYSQL.alias(),
            resource.connection().metadata().getDatabaseProductName()
            );
        assertEquals(
            TuesdayJdbcResource.JdbcProductType.MYSQL,
            TuesdayJdbcResource.JdbcProductType.match(
                resource.connection().metadata().getDatabaseProductName()
                )
            );
        }

    /**
     * Check the live ROE database.
     *
     */
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
        assertEquals(
            TuesdayJdbcResource.JdbcProductType.MSSQL.alias(),
            resource.connection().metadata().getDatabaseProductName()
            );
        assertEquals(
            TuesdayJdbcResource.JdbcProductType.MSSQL,
            TuesdayJdbcResource.JdbcProductType.match(
                resource.connection().metadata().getDatabaseProductName()
                )
            );
        }
/*
    @Test
    public void test004()
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
        resource.connection().url(
            "spring:RoeLiveData"
            //"spring:PgSqlLocalTest"
            //"spring:MySqlLocalTest"
            );
        resource.inport();

        display(resource);
        
        }
*/
    }
