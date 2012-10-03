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
package uk.ac.roe.wfau.firethorn.widgeon.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.List;

import javax.sql.DataSource;

import uk.ac.roe.wfau.firethorn.common.entity.Entity;
import uk.ac.roe.wfau.firethorn.widgeon.base.BaseResource;


/**
 *
 *
 */
public interface JdbcResource
extends BaseResource
    {

    /**
     * Factory interface for identifiers.
     *
     */
    public static interface IdentFactory
    extends Entity.IdentFactory<JdbcResource>
        {
        }
    
    /**
     * Factory interface for accessing resources.
     *
     */
    public static interface Factory
    extends BaseResource.FactoryTemplate<JdbcResource>
        {
        /**
         * Create a new resource.
         *
         */
        public JdbcResource create(final String name);

        /**
         * Create a new resource.
         *
         */
        public JdbcResource create(final String name, final DataSource source);

        /**
         * Access to our catalog factory.
         *
         */
        public JdbcCatalog.Factory catalogs();

        }

    /**
     * Public interface for accessing a resource's catalogs.
     *
     */
    public interface Catalogs
    extends BaseResource.Catalogs<JdbcCatalog>
        {
        /**
         * Create a new catalog.
         *
         */
        public JdbcCatalog create(final String name);

        /**
         * Compare our data with DatabaseMetaData from our DataSource.
         * @param pull Update our metadata to match the DatabaseMetaData.
         * @param push Update our database to match our metadata.
         *
         */
        public List<JdbcDiference> diff(final boolean push, final boolean pull);

        /**
         * Compare our data with DatabaseMetaData from our DataSource.
         * @param metadata The DatabaseMetaData to compare against.
         * @param pull Update our metadata to match the DatabaseMetaData.
         * @param push Update our database to match our metadata.
         *
         */
        public List<JdbcDiference> diff(final DatabaseMetaData metadata, final List<JdbcDiference> results, final boolean push, final boolean pull);

        }

    @Override
    public Catalogs catalogs();

    /**
     * JDBC DatabaseMetaData column names.
     * @see DatabaseMetaData
     * @todo Move this to a local sub-interface.
     *
     */
    public static final String JDBC_META_TABLE_CAT   = "TABLE_CAT" ;
    public static final String JDBC_META_TABLE_TYPE  = "TABLE_TYPE" ;
    public static final String JDBC_META_TABLE_NAME  = "TABLE_NAME" ;
    public static final String JDBC_META_TABLE_SCHEM = "TABLE_SCHEM" ;

    public static final String JDBC_META_TABLE_TYPE_VIEW  = "VIEW" ;
    public static final String JDBC_META_TABLE_TYPE_TABLE = "TABLE" ;

    public static final String JDBC_META_COLUMN_NAME      = "COLUMN_NAME" ;
    public static final String JDBC_META_COLUMN_TYPE_TYPE = "DATA_TYPE";
    public static final String JDBC_META_COLUMN_TYPE_NAME = "TYPE_NAME";
    public static final String JDBC_META_COLUMN_SIZE      = "COLUMN_SIZE";

    /**
     * Access to our underlying DataSource.
     * @todo Move this to a local sub-interface.
     *
     */
    public DataSource source();

    /**
     * Open a connection to ourDataSource.
     * @todo Move this to a local sub-interface.
     *
     */
    public Connection connect();

    /**
     * Get the DatabaseMetaData from our DataSource.
     * @todo Move this to a local sub-interface.
     *
     */
    public DatabaseMetaData metadata();

    /**
     * Compare our data with DatabaseMetaData from our DataSource.
     * @param pull Update our metadata to match the DatabaseMetaData.
     * @param push Update our database to match our metadata.
     *
     */
    public List<JdbcDiference> diff(final boolean push, final boolean pull);

    /**
     * Compare our data with DatabaseMetaData from our DataSource.
     * @param metadata The DatabaseMetaData to compare against.
     * @param pull Update our metadata to match the DatabaseMetaData.
     * @param push Update our database to match our metadata.
     *
     */
    public List<JdbcDiference> diff(final DatabaseMetaData metadata, final List<JdbcDiference> results, final boolean push, final boolean pull);
    }
