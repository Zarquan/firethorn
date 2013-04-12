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
package uk.ac.roe.wfau.firethorn.adql.query;

import uk.ac.roe.wfau.firethorn.entity.Entity;
import uk.ac.roe.wfau.firethorn.job.Job;
import uk.ac.roe.wfau.firethorn.meta.adql.AdqlColumn;
import uk.ac.roe.wfau.firethorn.meta.adql.AdqlResource;
import uk.ac.roe.wfau.firethorn.meta.adql.AdqlSchema;
import uk.ac.roe.wfau.firethorn.meta.adql.AdqlTable;
import uk.ac.roe.wfau.firethorn.meta.base.BaseResource;
import uk.ac.roe.wfau.firethorn.meta.base.BaseTable;
import uk.ac.roe.wfau.firethorn.meta.jdbc.JdbcResource;
import uk.ac.roe.wfau.firethorn.meta.jdbc.JdbcSchema;
import uk.ac.roe.wfau.firethorn.meta.jdbc.JdbcTable;

/**
 *
 *
 */
public interface AdqlQuery
extends Entity, Job
    {
    /**
     * Our local service implementations.
     *
     */
    public static interface Services
        {
        /**
         * Our Name factory.
         *
         */
        public NameFactory names();

        /**
         * Our Link factory.
         *
         */
        public LinkFactory links();

        /**
         * Our Ident factory.
         *
         */
        public IdentFactory idents();

        /**
         * Our Query resolver.
         *
         */
        public Resolver resolver();

        /**
         * Our Query Factory.
         *
         */
        public Factory factory();

        /**
         * Our Query executor.
         *
         */
        public Job.Executor executor();

        /**
         * Our table builder.
         *
         */
        public Builder builder();

        }

    /**
     * Our local service implementations.
     *
     */
    public Services services();

    /**
     * Name factory interface.
     *
     */
    public static interface NameFactory
    extends Entity.NameFactory
        {
        }

    /**
     * Link factory interface.
     *
     */
    public static interface LinkFactory
    extends Entity.LinkFactory<AdqlQuery>
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
     * Resolver interface.
     *
     */
    public static interface Resolver
    extends Entity.Factory<AdqlQuery>
        {
        }

    /**
     * Factory interface.
     *
     */
    public static interface Factory
    extends Job.Factory<AdqlQuery>
        {
        /**
         * Create a new query.
         *
         */
        public AdqlQuery create(final AdqlSchema schema, final JdbcSchema store, final String input);

        /**
         * Create a new query.
         *
         */
        public AdqlQuery create(final AdqlSchema schema, final JdbcSchema store, final String input, final String name);

        /**
         * Select all the queries from a resource.
         *
         */
        public Iterable<AdqlQuery> select(final AdqlSchema schema);

        /**
         * Text search for queries (name starts with).
         *
         */
        public Iterable<AdqlQuery> search(final AdqlSchema schema, final String text);

        }

    /**
     * Table builder interface.
     *
     */
    public static interface Builder
        {
        /**
         * Build a physical table.
         *
         */
        public JdbcTable create(final JdbcSchema store, final AdqlQuery query);

        //
        // Test config methods ..
        //public JdbcSchema schema();
        //public void schema(JdbcSchema schema);
        //public JdbcResource resource();
        //public void resource(JdbcResource resource);

        }

    /**
     * Get the input text.
     *
     */
    public String input();

    /**
     * Set the input text.
     *
     */
    public void input(final String input);

    /**
     * Query syntax validation status.
     *
     */
    public interface Syntax
        {
        /**
         * The validation status.
         *
         */
        public enum State
            {
            /**
             * The query has been parsed and is valid ADQL.
             *
             */
            VALID(),

            /**
             * A parser error in the ADQL query.
             *
             */
            PARSE_ERROR(),

            /**
             * A translation error processing the query.
             *
             */
            TRANS_ERROR(),

            /**
             * Unknown state - the query hasn't been parsed yet.
             *
             */
            UNKNOWN();
            }

        /**
         * The validation status.
         *
         */
        public State state();

        /**
         * The original parser error message.
         *
         */
        public String message();

        /**
         * A user friendly message.
         *
         */
        public String friendly();

        }

    /**
     * Get the syntax validation status.
     *
     */
    public Syntax syntax();

    /**
     * OGSA-DAI query mode.
     *
     */
    public enum Mode
        {
        /**
         * Direct query to a single resource.
         *
         */
        DIRECT(),

        /**
         * Distributed query handled DQP.
         *
         */
        DISTRIBUTED();

        }

    /**
     * The OGSA-DAI query mode.
     *
     */
    public Mode mode();

    /**
     * The ADQL schema this query applies to.
     *
     */
    public AdqlSchema schema();

    /**
     * The processed ADQL query.
     *
     */
    public String adql();

    /**
     * The processed SQL query.
     *
     */
    public String osql();

    /**
     * A list of the AdqlColumns used by the query.
     *
     */
    public Iterable<AdqlColumn> columns();

    /**
     * A list of the AdqlTables used by the query.
     * The list is only generated in response to a POST request that updates the ADQL query.
     * The list is generated when an input query is parsed and is not saved in the database.
     * On subsequent GET requests the list will be empty. 
     *
     */
    public Iterable<AdqlTable> tables();

    /**
     * A list of the resources used by the query.
     *
     */
    public Iterable<BaseResource<?>> resources();

    /**
     * The primary resource used by the query.
     * @todo rename to resource()
     * 
     */
    public BaseResource<?> primary();

    /**
     * Metadata for a SELECT field.
     *
     */
    public interface SelectField
        {
    
        /**
         * The field name or alias.
         *
         */
        public abstract String name();
    
        /**
         * The column metadata.
         *
         */
        public abstract AdqlColumn.Metadata info();
    
        /**
         * The field size.
         *
         */
        public abstract int length();
    
        /**
         * The field type.
         *
         */
        public abstract AdqlColumn.Type type();
    
        }

    /**
     * A list of the SELECT fields.
     * The list is only generated in response to a POST request that updates the ADQL query.
     * The list is generated when an input query is parsed and is not saved in the database.
     * On subsequent GET requests the list will be empty. 
     *
     */
    public Iterable<SelectField> fields();

    /**
     * Our result tables.
     * 
     */
    public interface Results
        {
        /**
         * The physical JDBC database table.
         * 
         */
        public JdbcTable jdbc();

        /**
         * The physical base table.
         * 
         */
        public BaseTable<?,?> base();

        /**
         * The abstract ADQL table.
         * 
         */
        public AdqlTable adql();
        }

    /**
     * Our result tables.
     * 
     */
    public Results results();

    }
