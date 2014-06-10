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
package uk.ac.roe.wfau.firethorn.meta.adql;

import uk.ac.roe.wfau.firethorn.adql.query.AdqlQuery;
import uk.ac.roe.wfau.firethorn.adql.query.AdqlQuery.QueryParam;
import uk.ac.roe.wfau.firethorn.adql.query.QueryProcessingException;
import uk.ac.roe.wfau.firethorn.entity.Entity;
import uk.ac.roe.wfau.firethorn.entity.exception.NameNotFoundException;
import uk.ac.roe.wfau.firethorn.meta.base.BaseColumn;
import uk.ac.roe.wfau.firethorn.meta.base.BaseSchema;
import uk.ac.roe.wfau.firethorn.meta.base.BaseTable;

/**
 *
 *
 */
public interface AdqlSchema
extends BaseSchema<AdqlSchema, AdqlTable>
    {
    /**
     * {@link BaseSchema.IdentFactory} interface.
     *
     */
    public static interface IdentFactory
    extends BaseSchema.IdentFactory
        {
        }

    /**
     * {@link BaseSchema.NameFactory} interface.
     *
     */
    public static interface NameFactory
    extends BaseSchema.NameFactory<AdqlSchema>
        {
        }

    /**
     * {@link BaseSchema.LinkFactory} interface.
     *
     */
    public static interface LinkFactory
    extends BaseSchema.LinkFactory<AdqlSchema>
        {
        }
    /**
     * {@link BaseSchema.EntityResolver} interface.
     *
     */
    public static interface EntityResolver
    extends BaseSchema.EntityResolver<AdqlResource, AdqlSchema>
        {
        }

    /**
     * {@link BaseSchema.EntityFactory} interface.
     *
     */
    public static interface EntityFactory
    extends BaseSchema.EntityFactory<AdqlResource, AdqlSchema>
        {
        /**
         * Create an empty schema.
         *
         */
        public AdqlSchema create(final AdqlResource parent, final String name);

        /**
         * Create a new schema, importing a base table.
         *
         */
        public AdqlSchema create(final AdqlResource parent, final String name, final BaseTable<?,?> base);

        /**
         * Create a new schema, importing a base table.
         *
         */
        public AdqlSchema create(final CopyDepth depth, final AdqlResource parent, final String name, final BaseTable<?,?> base);

        /**
         * Create a new schema, importing the tables from a base schema.
         *
         */
        public AdqlSchema create(final AdqlResource parent, final BaseSchema<?,?> base);

        /**
         * Create a new schema, importing the tables from a base schema.
         *
         */
        public AdqlSchema create(final CopyDepth depth, final AdqlResource parent, final BaseSchema<?,?> base);

        /**
         * Create a new schema, importing the tables from a base schema.
         *
         */
        public AdqlSchema create(final AdqlResource parent, final String name, final BaseSchema<?,?> base);

        /**
         * Create a new schema, importing the tables from a base schema.
         *
         */
        public AdqlSchema create(final CopyDepth depth, final AdqlResource parent, final String name, final BaseSchema<?,?> base);

        /**
         * Our local {@link AdqlTable.EntityFactory} implementation.
         * @todo - move to services
         *
         */
        public AdqlTable.EntityFactory tables();

        //TODO - move to services
        @Override
        public AdqlSchema.IdentFactory idents();

        //TODO - move to services
        //@Override
        //public AdqlSchema.NameFactory names();

        //TODO - move to services
        @Override
        public AdqlSchema.LinkFactory links();
        
        }

    @Override
    public AdqlResource resource();

    /**
     * Our table {@link AdqlTable tables}.
     *
     */
    public interface Tables extends BaseSchema.Tables<AdqlTable>
        {
        /**
         * Create a new {@link AdqlTable table}, importing the columns from a {@link BaseTable base table}.
         *
         */
        public AdqlTable create(final BaseTable<?,?> base);

        /**
         * Create a new {@link AdqlTable table}, importing the columns from a {@link BaseTable base table}.
         *
         */
        public AdqlTable create(final CopyDepth depth, final BaseTable<?,?> base);

        /**
         * Create a new {@link AdqlTable table}, importing the columns from a {@link BaseTable base table}.
         *
         */
        public AdqlTable create(final BaseTable<?,?> base, final String name);

        /**
         * Create a new {@link AdqlTable table}, importing the columns from a {@link BaseTable base table}.
         *
         */
        public AdqlTable create(final CopyDepth depth, final BaseTable<?,?> base, final String name);

        /**
         * Create a new {@link AdqlTable table}, importing the columns from a {@link AdqlQuery query}.
         *
         */
        public AdqlTable create(final AdqlQuery query);

        /**
         * Import a {@link AdqlTable table} from our base schema..
         *
         */
        public AdqlTable inport(final String name)
        throws NameNotFoundException;

        }
    @Override
    public Tables tables();

    /**
     * Access to the schema {@link AdqlQuery queries}.
     * @todo Does this make sense ?
     * @todo Does this depend on who is asking ?
     *
     */
    public interface Queries
        {
        /**
         * Create a new {@link AdqlQuery}.
         *
         */
        public AdqlQuery create(final QueryParam param, final String query)
        throws QueryProcessingException;

        /**
         * Create a new {@link AdqlQuery}.
         *
         */
        public AdqlQuery create(final QueryParam param, final String query, final String name)
        throws QueryProcessingException;

        /**
         * Select all the {@link AdqlQuery} for this schema.
         * @todo Does this make sense ?
         *
         */
        public Iterable<AdqlQuery> select();

        }

    /**
     * Access to the schema {@link AdqlQuery queries}.
     * @todo Does this make sense ?
     * @todo Does this depend on who is asking ?
     *
     */
    public Queries queries();

    /**
     * The {@link AdqlSchema} metadata.
     *
     */
    public interface Metadata
    extends BaseSchema.Metadata
        {
        /**
         * The ADQL metadata.
         * 
         */
        public interface Adql
            {
            }

        /**
         * The ADQL metadata.
         * 
         */
        public Adql adql();
        }

    @Override
    public AdqlSchema.Metadata meta();

    }
