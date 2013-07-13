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
package uk.ac.roe.wfau.firethorn.meta.jdbc;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

import lombok.extern.slf4j.Slf4j;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import uk.ac.roe.wfau.firethorn.entity.AbstractEntityFactory;
import uk.ac.roe.wfau.firethorn.entity.annotation.CreateEntityMethod;
import uk.ac.roe.wfau.firethorn.entity.annotation.SelectEntityMethod;
import uk.ac.roe.wfau.firethorn.entity.exception.NotFoundException;
import uk.ac.roe.wfau.firethorn.identity.Identity;
import uk.ac.roe.wfau.firethorn.meta.adql.AdqlColumn;
import uk.ac.roe.wfau.firethorn.meta.base.BaseResourceEntity;

/**
 *
 *
 */
@Slf4j
@Entity()
@Access(
    AccessType.FIELD
    )
@Table(
    name = JdbcResourceEntity.DB_TABLE_NAME
    )
@NamedQueries(
        {
        @NamedQuery(
            name  = "JdbcResource-select-all",
            query = "FROM JdbcResourceEntity ORDER BY name asc, ident desc"
            ),
        @NamedQuery(
            name  = "JdbcResource-search-text",
            query = "FROM JdbcResourceEntity WHERE (name LIKE :text) ORDER BY ident desc"
            ),
        @NamedQuery(
            name  = "JdbcResource-select-ogsaid",
            query = "FROM JdbcResourceEntity WHERE (ogsaid = :ogsaid) ORDER BY ident desc"
            )
        }
    )
public class JdbcResourceEntity
    extends BaseResourceEntity<JdbcSchema>
    implements JdbcResource
    {
    /**
     * Hibernate table mapping.
     *
     */
    protected static final String DB_TABLE_NAME = "JdbcResourceEntity";

    /**
     * Hibernate column mapping.
     *
     */
    protected static final String DB_JDBC_CATALOG_COL = "jdbccatalog";
    protected static final String DB_JDBC_OGSAID_COL  = "jdbcogsaid";

    /**
     * Resource factory implementation.
     *
     */
    @Repository
    public static class Factory
    extends AbstractEntityFactory<JdbcResource>
    implements JdbcResource.Factory
        {

        @Override
        public Class<?> etype()
            {
            return JdbcResourceEntity.class ;
            }

        @Override
        @SelectEntityMethod
        public Iterable<JdbcResource> select()
            {
            return super.iterable(
                super.query(
                    "JdbcResource-select-all"
                    )
                );
            }

        @Override
        @Deprecated
        @SelectEntityMethod
        public Iterable<JdbcResource> search(final String text)
            {
            return super.iterable(
                super.query(
                    "JdbcResource-search-text"
                    ).setString(
                        "text",
                        searchParam(
                            text
                            )
                        )
                );
            }

        @Override
        @CreateEntityMethod
        public JdbcResource create(final String ogsaid, final String name, final String url)
            {
            return this.create(
                ogsaid,
                null,
                name,
                url
                );
            }

        @Override
        @CreateEntityMethod
        public JdbcResource create(final String ogsaid, final String catalog, final String name, final String url)
            {
            return super.insert(
                new JdbcResourceEntity(
                    ogsaid,
                    catalog,
                    name,
                    url
                    )
                );
            }

		@Override
        @CreateEntityMethod
		public JdbcResource create(final String ogsaid, final String catalog, final String name, final String url, final String user, final String pass) {
            return super.insert(
                new JdbcResourceEntity(
                    ogsaid,
                    catalog,
                    name,
                    url,
                    user,
                    pass
                    )
                );
			}

        @Autowired
        protected JdbcSchema.Factory schemas;
        @Override
        public JdbcSchema.Factory schemas()
            {
            return this.schemas;
            }

        @Autowired
        protected JdbcResource.IdentFactory idents ;
        @Override
        public JdbcResource.IdentFactory idents()
            {
            return this.idents ;
            }

        @Autowired
        protected JdbcResource.LinkFactory links;
        @Override
        public JdbcResource.LinkFactory links()
            {
            return this.links;
            }

        /**
         * The default 'userdate' OGSA-DAI resource id.
         * @todo Make this a configurable property.
         *
         */
        public static final String DEFAULT_USERDATA_OGSA_ID = "userdata" ;

        /**
         * The default 'userdate' JDBC URI.
         * @todo Make this a configurable property.
         *
         */
        public static final String DEFAULT_USERDATA_URI = "spring:FireThornUserData" ;

        /**
         * Select (or create) the default 'userdata' Resource (based on ogsaid).
         * @todo Make the default properties configurable.
         *
         */
        @Override
        @CreateEntityMethod
        public JdbcResource userdata()
            {
            log.debug("userdata()");
            JdbcResource userdata = ogsaid(
                DEFAULT_USERDATA_OGSA_ID
                );
            if (userdata == null)
                {
                log.debug("Userdata resource is null, creating a new one");
                userdata = create(
                    DEFAULT_USERDATA_OGSA_ID,
                    DEFAULT_USERDATA_OGSA_ID,
                    DEFAULT_USERDATA_URI
                    );
                }
            log.debug("Userdata resource [{}][{}]", userdata.ident(), userdata.name());
            return userdata ;
            }

        @SelectEntityMethod
        public JdbcResource ogsaid(final String ogsaid)
            {
            return super.first(
                super.query(
                    "JdbcResource-select-ogsaid"
                    ).setString(
                        "ogsaid",
                        ogsaid
                        )
                );
            }

        @Override
        public JdbcResource select(UUID uuid) throws NotFoundException
            {
            // TODO Auto-generated method stub
            return null;
            }
        }

    protected JdbcResourceEntity()
        {
        super();
        }

    protected JdbcResourceEntity(final String ogsaid, final String catalog, final String name, final  String url)
        {
        super(name);
        this.ogsaid  = ogsaid  ;
        this.catalog = catalog ;
        this.connection = new JdbcConnectionEntity(
            this,
            url
            );
        }

    protected JdbcResourceEntity(final String ogsaid, final String catalog, final String name, final String url, final String user, final String pass)
	    {
	    super(name);
        this.ogsaid  = ogsaid  ;
        this.catalog = catalog ;
	    this.connection = new JdbcConnectionEntity(
	        this,
	        url,
	        user,
	        pass
	        );
	    }

    @Override
    public JdbcResource.Schemas schemas()
        {
        scantest();
        return schemasimpl();
        }

    protected JdbcResource.Schemas schemasimpl()
        {
        return new JdbcResource.Schemas(){

            @Override
            public Iterable<JdbcSchema> select()
                {
                return factories().jdbc().schemas().select(
                    JdbcResourceEntity.this
                    );
                }

            @Override
            public JdbcSchema create(final String catalog, final String schema)
                {
                return factories().jdbc().schemas().create(
                    JdbcResourceEntity.this,
                    catalog,
                    schema
                    );
                }

            @Override
            public JdbcSchema select(final String catalog, final String schema)
                {
                return factories().jdbc().schemas().select(
                    JdbcResourceEntity.this,
                    catalog,
                    schema
                    );
                }

            @Override
            public JdbcSchema select(final String name)
                {
                return factories().jdbc().schemas().select(
                    JdbcResourceEntity.this,
                    name
                    );
                }

            @Override
            public Iterable<JdbcSchema> search(final String text)
                {
                return factories().jdbc().schemas().search(
                    JdbcResourceEntity.this,
                    text
                    );
                }

            @Override
            public JdbcSchema create(final Identity identity)
                {
                return factories().jdbc().schemas().build(
                    JdbcResourceEntity.this,
                    identity
                    );
                }

            @Override
            public Iterable<JdbcSchema> select(final Identity identity)
                {
                return factories().jdbc().schemas().select(
                    JdbcResourceEntity.this,
                    identity
                    );
                }

            @Override
            public void scan()
                {
                JdbcResourceEntity.this.scansync();
                }

            @Override
            public JdbcSchema simple()
                {
                return factories().jdbc().schemas().select(
                    JdbcResourceEntity.this,
                    connection().catalog(),
                    connection().type().schema()
                    );
                }
            };
        }

    @Embedded
    private JdbcConnectionEntity connection;

    @Override
    public JdbcConnection connection()
        {
        return this.connection;
        }

    /**
     * The the OGSA-DAI resource ID.
     *
     */
    @Basic(
        fetch = FetchType.EAGER
        )
    @Column(
        name = DB_JDBC_OGSAID_COL,
        unique = false,
        nullable = true,
        updatable = true
        )
    private String ogsaid;
    @Override
    public String ogsaid()
        {
        return this.ogsaid;
        }
    @Override
    public void ogsaid(final String ogsaid)
        {
        this.ogsaid = ogsaid;
        }

    @Basic(
        fetch = FetchType.EAGER
        )
    @Column(
        name = DB_JDBC_CATALOG_COL,
        unique = false,
        nullable = true,
        updatable = true
        )
    private String catalog ;
    @Override
    public String catalog()
        {
        return this.catalog ;
        }
    @Override
    public void catalog(final String catalog)
        {
        this.catalog = catalog ;
        scansync();
        }

    @Override
    public String link()
        {
        return factories().jdbc().resources().links().link(
            this
            );
        }

    @Override
    protected void scanimpl()
        {
        log.debug("scanimpl()");
        //
        // Default to using the catalog name from the Connection.
        // Explicitly set it to 'ALL_CATALOGS' to get all.
        if (this.catalog == null)
            {
            this.catalog = connection().catalog();
            }
        //
        // Scan all the catalogs.
        if (ALL_CATALOGS.equals(this.catalog))
            {
            for (final String cname : connection().catalogs())
                {
                try {
                    scanimpl(
                        cname
                        );
                    }
                catch (final Exception ouch)
                    {
                    log.debug("Exception in catalog processing loop");
                    log.debug("Exception text   [{}]", ouch.getMessage());
                    log.debug("Exception string [{}]", ouch.toString());
                    log.debug("Exception class  [{}]", ouch.getClass().toString());
                    //
                    // Continue with the rest of the catalogs ...
                    //
                    }
                }
            }
        //
        // Just scan one catalog.
        else {
            scanimpl(
                this.catalog
                );
            }
//
// TODO
// Reprocess the list disable missing ones ...
//

        }

    protected void scanimpl(final String catalog)
        {
        log.debug("scanimpl(String)");
        log.debug("  Catalog [{}]", catalog);
        //
        // Get the database metadata
        try {
            final DatabaseMetaData metadata = connection().metadata();
            final JdbcProductType  product  = JdbcProductType.match(
                metadata
                );
            // TODO - fix connection errors
            if (metadata != null)
                {
                try {
/*
 * getTables() fails if there are no tables
 */
                    final ResultSet schemas = metadata.getTables(
                        catalog,
                        null, // sch
                        null, // tab
                        new String[]
                            {
                            JdbcTypes.JDBC_META_TABLE_TYPE_TABLE,
                            JdbcTypes.JDBC_META_TABLE_TYPE_VIEW
                            }
                        );
/*
 * getSchemas() fails because catalog is ignored
                    final ResultSet schemas = metadata.getSchemas(
                        catalog,
                        null
                        );

// This will get ALL the catalogs.
// But the cname is always null, so we can't distinguish between them.
                    final ResultSet schemas = metadata.getSchemas();
 *
 */



                    String cprev = null ;
                    String sprev = null ;
                    while (schemas.next())
                        {
                        //String cname = schemas.getString(JdbcTypes.JDBC_META_TABLE_CATALOG);
                        String cname = schemas.getString(JdbcTypes.JDBC_META_TABLE_CAT);
                        String sname = schemas.getString(JdbcTypes.JDBC_META_TABLE_SCHEM);
                        log.debug("Found schema [{}][{}]", new Object[]{cname, sname});
/*
                        ResultSetMetaData meta = schemas.getMetaData();
                        for (int i = 1 ; i <= meta.getColumnCount() ; i++)
                            {
                            log.debug("Col [{}][{}]", i, meta.getColumnName(i));
                            }
 */
                        //
                        // In MySQL the schema name is always null, use the catalog name instead.
                        if (product == JdbcProductType.MYSQL)
                            {
                            sname = cname ;
                            cname = null ;
                            }
                        //
                        // In HSQLDB the schema and catalogs overlap, use the catalog name as the schema.
                        if (product == JdbcProductType.HSQLDB)
                            {
                            //log.debug("JdbcProductType.HSQLDB, swapping names");
                            //sname = cname ;
                            //cname = null ;
                            }
                        //
                        // The jTDS driver for SQLServer returns null column.
                        // This fix works for single catalog resources.
                        // This may fail for wildcard resources.
                        // TODO test with wildcard resources.
                        if (product == JdbcProductType.MSSQL)
                            {
                            if (cname == null)
                                {
                                cname = catalog ;
                                }
                            }
                        //
                        // Skip if the schema is on our ignore list.
                        if (product.ignore().contains(sname))
                            {
                            //log.debug("Schema [{}] is on the ignore list for [{}]", sname, product);
                            continue;
                            }
                        //
                        // Check if we have already done this one.
                        if (
                            ((cname == null) ? cprev == null : cname.equals(cprev))
                            &&
                            ((sname == null) ? sprev == null : sname.equals(sprev))
                            ){
                            log.debug("Already done [{}][{}], skipping", cname, sname);
                            continue;
                            }
                        else {
                            cprev = cname;
                            sprev = sname;
                            }

                        log.debug("Processing schema [{}][{}]", new Object[]{cname, sname});

                        //
                        // Check for an existing schema.
                        // If none found, create a new one.
                        JdbcSchema schema = this.schemasimpl().select(
                            cname,
                            sname
                            );
                        if (schema == null)
                            {
//////
                            log.debug("Matching schema not found [{}][{}], creating", new Object[]{cname, sname});
                            schema = this.schemasimpl().create(
                                cname,
                                sname
                                );
                            }
                        else {
                            log.debug("Matching schema found [{}][{}], skipping", new Object[]{cname, sname});
//////
                            }
                        }
                    }
                catch (final SQLInvalidAuthorizationSpecException ouch)
                    {
                    log.debug("Authorization exception reading JDBC metadata for [{}][{}][{}]", connection().uri(), catalog, ouch.getMessage());
                    }
                catch (final SQLException ouch)
                    {
                    log.error("Exception reading JDBC metadata for [{}][{}][{}]", connection().uri(), catalog, ouch.getMessage());
                    log.debug("Exception text   [{}]", ouch.getMessage());
                    log.debug("Exception string [{}]", ouch.toString());
                    log.debug("Exception class  [{}]", ouch.getClass().toString());
                    throw connection().translator().translate(
                        "Reading JDBC catalog schemas",
                        null,
                        ouch
                        );
                    }
                }
            }
        finally {
            connection().close();
            }
        }

    @Override
    public JdbcColumn.Type jdbctype(final AdqlColumn.Type type)
        {
        return connection().type().jdbctype(type);
        }

    @Override
    public Integer jdbcsize(final AdqlColumn.Type type)
        {
        return connection().type().jdbcsize(type);
        }

    @Override
    public Integer jdbcsize(final JdbcColumn.Type type)
        {
        return connection().type().jdbcsize(type);
        }
    }
