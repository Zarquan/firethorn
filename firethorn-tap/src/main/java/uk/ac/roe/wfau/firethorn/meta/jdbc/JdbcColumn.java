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

import java.sql.Types;

import uk.ac.roe.wfau.firethorn.entity.Entity;
import uk.ac.roe.wfau.firethorn.meta.adql.AdqlColumn;
import uk.ac.roe.wfau.firethorn.meta.base.BaseColumn;

/**
 *
 *
 */
public interface JdbcColumn
extends BaseColumn<JdbcColumn>
    {
    /**
     * Identifier factory interface.
     *
     */
    public static interface LinkFactory
    extends Entity.LinkFactory<JdbcColumn>
        {
        }

    /**
     * Identifier factory interface.
     *
     */
    public static interface IdentFactory
    extends Entity.IdentFactory
        {
        }

    /**
     * Column factory interface.
     *
     */
    public static interface Factory
    extends BaseColumn.Factory<JdbcTable, JdbcColumn>
        {
        /**
         * Create a new column.
         *
         */
        @Deprecated
        public JdbcColumn create(final JdbcTable parent, final String name);

        /**
         * Create a new column.
         *
         */
        public JdbcColumn create(final JdbcTable parent, final String name, final int type, final int size);

        }

    @Override
    public JdbcTable table();
    @Override
    public JdbcSchema schema();
    @Override
    public JdbcResource resource();

    /**
     * Update the column metadata.
     *
     */
    public void scan();

    public enum Type
        {
        ARRAY(          Types.ARRAY),
        BIGINT(         Types.BIGINT),
        BINARY(         Types.BINARY),
        BIT(            Types.BIT),
        BLOB(           Types.BLOB),
        BOOLEAN(        Types.BOOLEAN),
        CHAR(           Types.CHAR),
        CLOB(           Types.CLOB),
        DATALINK(       Types.DATALINK),
        DATE(           Types.DATE),
        DECIMAL(        Types.DECIMAL),
        DISTINCT(       Types.DISTINCT),
        DOUBLE(         Types.DOUBLE),
        FLOAT(          Types.FLOAT),
        INTEGER(        Types.INTEGER),
        JAVA_OBJECT(    Types.JAVA_OBJECT),
        LONGNVARCHAR(   Types.LONGNVARCHAR),
        LONGVARBINARY(  Types.LONGVARBINARY),
        LONGVARCHAR(    Types.LONGVARCHAR),
        NCHAR(          Types.NCHAR),
        NCLOB(          Types.NCLOB),
        NULL(           Types.NULL),
        NUMERIC(        Types.NUMERIC),
        NVARCHAR(       Types.NVARCHAR),
        OTHER(          Types.OTHER),
        REAL(           Types.REAL),
        REF(            Types.REF),
        ROWID(          Types.ROWID),
        SMALLINT(       Types.SMALLINT),
        SQLXML(         Types.SQLXML),
        STRUCT(         Types.STRUCT),
        TIME(           Types.TIME),
        TIMESTAMP(      Types.TIMESTAMP),
        TINYINT(        Types.TINYINT),
        VARBINARY(      Types.VARBINARY),
        VARCHAR(        Types.VARCHAR),
        UNKNOWN(        Types.OTHER);

        private int code ;
        public int code()
            {
            return this.code;
            }

        public AdqlColumn.Type adql()
            {
            return AdqlColumn.Type.adql(
                this
                );
            }
    
        Type(int code)
            {
            this.code = code ;
            }
    
        /**
         * Mapping from java.sql.Types to JdbcColumn.Type.
         * @see java.sql.Types
         *
         */
        public static Type jdbc(final int sql)
            {
            switch(sql)
                {
                case Types.BIGINT :
                    return BIGINT ;
    
                case Types.BIT :
                    return BIT ;
    
                case Types.BOOLEAN :
                    return BOOLEAN ;
    
                case Types.LONGNVARCHAR :
                    return LONGNVARCHAR ;
    
                case Types.LONGVARCHAR :
                    return LONGVARCHAR ;
    
                case Types.NVARCHAR :
                    return NVARCHAR ;
    
                case Types.VARCHAR :
                    return VARCHAR ;
    
                case Types.NCHAR :
                    return NCHAR ;
    
                case Types.CHAR :
                    return CHAR ;
    
                case Types.DOUBLE :
                    return DOUBLE ;
    
                case Types.REAL  :
                    return REAL  ;
    
                case Types.FLOAT :
                    return FLOAT ;
    
                case Types.INTEGER :
                    return INTEGER ;
    
                case Types.TINYINT :
                    return TINYINT ;
    
                case Types.SMALLINT :
                    return SMALLINT ;
    
                case Types.ARRAY :
                    return ARRAY ;
    
                case Types.BINARY :
                    return BINARY ;
    
                case Types.BLOB :
                    return BLOB ;
    
                case Types.CLOB :
                    return CLOB ;
    
                case Types.DATALINK :
                    return DATALINK ;
    
                case Types.DATE :
                    return DATE ;
    
                case Types.DECIMAL :
                    return DECIMAL ;
    
                case Types.DISTINCT :
                    return DISTINCT ;
    
                case Types.JAVA_OBJECT :
                    return JAVA_OBJECT ;
    
                case Types.LONGVARBINARY :
                    return LONGVARBINARY ;
    
                case Types.NCLOB :
                    return NCLOB ;
    
                case Types.NULL :
                    return NULL ;
    
                case Types.NUMERIC :
                    return NUMERIC ;
    
                case Types.OTHER :
                    return OTHER ;
    
                case Types.REF :
                    return REF ;
    
                case Types.ROWID :
                    return ROWID ;
    
                case Types.SQLXML :
                    return SQLXML ;
    
                case Types.STRUCT :
                    return STRUCT ;
    
                case Types.TIME :
                    return TIME ;
    
                case Types.TIMESTAMP :
                    return TIMESTAMP ;
    
                case Types.VARBINARY :
                    return VARBINARY ;
    
                default :
                    return UNKNOWN ;
                }
            }
        }

    
    /**
     * Access to the column metadata.
     *
     */
    public interface Metadata
    extends AdqlColumn.Metadata
        {
        /**
         *
         *
         */
        public interface JdbcMeta
            {

            public Integer size();

            public void size(final Integer size);

            public Type type();

            public void type(final int type);

            public void type(final Type type);

            }

        /**
         * The JDBC column metadata.
         *
         */
        public JdbcMeta jdbc();

        }

    @Override
    public JdbcColumn.Metadata info();

    }
