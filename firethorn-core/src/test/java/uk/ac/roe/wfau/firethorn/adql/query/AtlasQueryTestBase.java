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
package uk.ac.roe.wfau.firethorn.adql.query ;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import uk.ac.roe.wfau.firethorn.adql.query.AdqlQuery.SelectField;
import uk.ac.roe.wfau.firethorn.entity.Identifier;
import uk.ac.roe.wfau.firethorn.entity.exception.IdentifierNotFoundException;
import uk.ac.roe.wfau.firethorn.entity.exception.NotFoundException;
import uk.ac.roe.wfau.firethorn.identity.Community;
import uk.ac.roe.wfau.firethorn.identity.CommunityMember;
import uk.ac.roe.wfau.firethorn.meta.adql.AdqlColumn;
import uk.ac.roe.wfau.firethorn.meta.adql.AdqlResource;
import uk.ac.roe.wfau.firethorn.meta.adql.AdqlSchema;
import uk.ac.roe.wfau.firethorn.meta.adql.AdqlTable;
import uk.ac.roe.wfau.firethorn.meta.base.BaseComponent;
import uk.ac.roe.wfau.firethorn.meta.base.BaseResource;
import uk.ac.roe.wfau.firethorn.meta.jdbc.JdbcColumn;
import uk.ac.roe.wfau.firethorn.meta.jdbc.JdbcResource;
import uk.ac.roe.wfau.firethorn.meta.jdbc.JdbcSchema;


/**
 *
 *
 */
@Slf4j
@Ignore
public class AtlasQueryTestBase
extends TestPropertiesBase
    {
    protected JdbcResource jdbcresource ;
    protected AdqlResource adqlresource ;
    protected AdqlResource testresource ;

    protected AdqlSchema queryschema ;
    //protected JdbcSchema userschema ;

    public void schema()
        {
        }

    /**
     * Test property names.
     * 
     */
    public static final String JDBC_RESOURCE_PROP = "atlas.jdbc.resource";
    public static final String ADQL_RESOURCE_PROP = "atlas.adql.resource";
    public static final String TEST_RESOURCE_PROP = "atlas.test.resource";

    public static final String QUERY_SPACE_NAME  = "queryschema";
    //public static final String USER_SCHEMA_PROP = "atlas.user.schema";

    /**
     * Test catalog names.
     * 
     */
    public static final String ATLAS_CATALOG_NAME   = "ATLASv20131029" ;
    public static final String BEST_CATALOG_NAME    = "BestDR8" ;
    public static final String ROSAT_CATALOG_NAME   = "ROSAT" ;
    public static final String TWOMASS_CATALOG_NAME = "TWOMASS" ;
    
    
    /**
     * Load our test resources.
     *
     */
    @Before
    public void loadResources()
    throws NotFoundException
        {
        //
        // Create our JDBC resource.
        if (jdbcresource == null)
            {
            log.debug("Loading JDBC resource");
            final String ident = testprops().getProperty(JDBC_RESOURCE_PROP);
            if (ident != null)
                {
                try {
                    jdbcresource = factories().jdbc().resources().select(
                        factories().jdbc().resources().idents().ident(
                            ident
                            )
                        );
                    }
                catch (final IdentifierNotFoundException ouch)
                    {
                    log.debug("Unable to load JDBC resource from test config [{}]", ouch.getMessage());
                    }
                }
            if (jdbcresource == null)
                {
                log.debug("Null JDBC resource, creating new one");
                jdbcresource = factories().jdbc().resources().create(
                    "atlas",
                    "*",
                    "atlas",
                    "spring:RoeATLAS"
                    );
                }
            }

        //
        // Create our ADQL resource.
        if (this.adqlresource == null)
            {
            log.debug("Loading ADQL resource");
            final String ident = testprops().getProperty(ADQL_RESOURCE_PROP);
            if (ident != null)
                {
                try {
                    this.adqlresource = factories().adql().resources().select(
                        factories().adql().resources().idents().ident(
                            ident
                            )
                        );
                    }
                catch (final IdentifierNotFoundException ouch)
                    {
                    log.debug("Unable to load ADQL resource from test config [{}]", ouch.getMessage());
                    }
                }
            if (this.adqlresource == null)
                {
                log.debug("Null ADQL resource, creating new one");
                this.adqlresource = factories().adql().resources().create(
                    "ADQL resource"
                    );
                this.adqlresource.schemas().create(
                    BaseComponent.CopyDepth.THIN,
                    ATLAS_CATALOG_NAME,
                    jdbcresource.schemas().select(
                        ATLAS_CATALOG_NAME,
                        "dbo"
                        )
                    );
                this.adqlresource.schemas().create(
                    BaseComponent.CopyDepth.THIN,
                    ROSAT_CATALOG_NAME,
                    jdbcresource.schemas().select(
                        ROSAT_CATALOG_NAME,
                        "dbo"
                        )
                    );
                this.adqlresource.schemas().create(
                        BaseComponent.CopyDepth.THIN,
                        BEST_CATALOG_NAME,
                        jdbcresource.schemas().select(
                            BEST_CATALOG_NAME,
                            "dbo"
                            )
                        );
                this.adqlresource.schemas().create(
                    BaseComponent.CopyDepth.THIN,
                    TWOMASS_CATALOG_NAME,
                    jdbcresource.schemas().select(
                        TWOMASS_CATALOG_NAME,
                        "dbo"
                        )
                    );
                }
            }

        //
        // Create our test workspace.
        if (this.testresource == null)
            {
            log.debug("Loading TEST resource");
            final String ident = testprops().getProperty(TEST_RESOURCE_PROP);
            if (ident != null)
                {
                try {
                    this.testresource = factories().adql().resources().select(
                        factories().adql().schemas().idents().ident(
                            ident
                            )
                        );
                    }
                catch (final IdentifierNotFoundException ouch)
                    {
                    log.debug("Unable to load TEST resource from test config [{}]", ouch.getMessage());
                    }
                }
            if (this.testresource == null)
                {
                this.testresource = factories().adql().resources().create(
                    "TEST resource"
                    );
                this.testresource.schemas().create(
                    BaseComponent.CopyDepth.THIN,
                    this.adqlresource.schemas().select(
                        ATLAS_CATALOG_NAME
                        )
                    );
                this.testresource.schemas().create(
                    BaseComponent.CopyDepth.THIN,
                    this.adqlresource.schemas().select(
                        ROSAT_CATALOG_NAME
                        )
                    );
                this.testresource.schemas().create(
                        BaseComponent.CopyDepth.THIN,
                        this.adqlresource.schemas().select(
                            BEST_CATALOG_NAME
                            )
                        );
                this.testresource.schemas().create(
                    BaseComponent.CopyDepth.THIN,
                    this.adqlresource.schemas().select(
                        TWOMASS_CATALOG_NAME
                        )
                    );
                }
            }

        //
        // Create our ADQL query space.
        if (this.queryschema == null)
            {
            log.debug("Loading QUERY space");
            this.queryschema = this.testresource.schemas().search(
                QUERY_SPACE_NAME
                );
            if (this.queryschema == null)
                {
                this.queryschema = this.testresource.schemas().create(
                    QUERY_SPACE_NAME
                    );
                }
            }
        }

    /**
     * Save our test resources.
     *
     */
    @After
    public void saveResources()
        {
        log.debug("Saving test resources");
        if (this.jdbcresource != null)
            {
            testprops().setProperty(
                JDBC_RESOURCE_PROP,
                this.jdbcresource.ident().toString()
                );
            }
        if (this.adqlresource != null)
            {
            testprops().setProperty(
                ADQL_RESOURCE_PROP,
                this.adqlresource.ident().toString()
                );
            }
        if (this.testresource != null)
            {
            testprops().setProperty(
                TEST_RESOURCE_PROP,
                this.testresource.ident().toString()
                );
            }
        }

    /**
     * An expected result.
     *
     */
    public static class ExpectedField
    implements AdqlQuery.SelectField
        {
        public ExpectedField(final String name, final AdqlColumn.Type type, final Integer size)
            {
            this.name = name ;
            this.type = type ;
            this.size = size ;
            }
        private final Integer size ;
        @Override
        public Integer arraysize()
            {
            return this.size;
            }
        private final AdqlColumn.Type type;
        @Override
        public  AdqlColumn.Type type()
            {
            return this.type;
            }
        private final String name ;
        @Override
        public String name()
            {
            return this.name;
            }
        void validate(final AdqlQuery.SelectField field)
            {
            log.debug("validate(SelectField)");
            log.debug("  name [{}][{}]", this.name, field.name());
            log.debug("  size [{}][{}]", this.size, field.arraysize());
            log.debug("  type [{}][{}]", this.type, field.type());
            assertEquals(
                this.name,
                field.name()
                ) ;
            assertEquals(
                this.size,
                field.arraysize()
                ) ;
            assertEquals(
                this.type,
                field.type()
                ) ;
            }
        @Override
        public AdqlColumn adql()
            {
            return null;
            }
        @Override
        public JdbcColumn jdbc()
            {
            return null;
            }
        }

    public void validate(final AdqlQuery query, final ExpectedField[] expected)
        {
        final Iterator<AdqlQuery.SelectField> iter = query.fields().iterator();
        int i = 0 ;
        while (iter.hasNext())
            {
            final AdqlQuery.SelectField field = iter.next();
            log.debug("Field [{}][{}][{}]", field.name(), field.type(), field.arraysize());
            expected[i++].validate(
                field
                );
            }
        assertEquals(
            i,
            expected.length
            );
        }

    /**
     * Debug display of a query.
     *
     */
    public void debug(final AdqlQuery query)
        {
        log.debug("Query -- ");
        log.debug("Mode   [{}]", query.mode());
        log.debug("Status [{}]", query.status());
        log.debug("ADQL   [{}]", query.adql());
        log.debug("OSQL   [{}]", query.osql());
        log.debug("Target [{}]", (query.primary() != null) ? query.primary().ident() : null);
        log.debug("Resources -- ");
        for (final BaseResource<?> target : query.resources())
            {
            log.debug("Resource [{}]", target.namebuilder());
            }
        log.debug("Tables -- ");
        for (final AdqlTable table : query.tables())
            {
            log.debug("Table [{}]", table.namebuilder());
            }
        log.debug("Columns -- ");
        for (final AdqlColumn column : query.columns())
            {
            log.debug("Column [{}]", column.namebuilder());
            }
        log.debug("Fields -- ");
        for (final SelectField field : query.fields())
            {
            log.debug("Field [{}][{}][{}]", field.name(), field.type(), field.arraysize());
            }
        }

    /**
     * Clean a query string.
     *
     */
    protected String clean(final String s1)
        {
        if (s1 == null)
            {
            return  null ;
            }

        final String s2 = StringUtils.trim(s1);
        final String s3 = s2.toLowerCase();
        final String s4 = s3.replaceAll("[\n\r]+", " ");
        final String s5 = s4.replaceAll("\\p{Space}+", " ");
        final String s6 = s5.replaceAll(" +,", ",");

        return s6;
        }

    /**
     * Compare an ADQL query and the resulting SQL output.
     *
     */
    public void compare(final String adql, final String osql)
    throws Exception
        {
        compare(
            this.queryschema.queries().create(
                adql
                ),
            osql
            );
        }

    /**
     * Compare an ADQL query and the resulting SQL output.
     *
     */
    public void compare(final AdqlQuery query, final String osql)
        {
        debug(
            query
            );
        assertEquals(
            clean(
                osql
                ),
            clean(
                query.osql()
                )
            );
        }
    }

