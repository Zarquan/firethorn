/*
 *  Copyright (C) 2015 Royal Observatory, University of Edinburgh, UK
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
package uk.ac.roe.wfau.firethorn.adql.query.blue;

import java.net.URI;

import uk.ac.roe.wfau.firethorn.adql.query.AdqlQueryBase;
import uk.ac.roe.wfau.firethorn.entity.Entity;
import uk.ac.roe.wfau.firethorn.entity.Identifier;
import uk.ac.roe.wfau.firethorn.entity.NamedEntity;
import uk.ac.roe.wfau.firethorn.entity.exception.IdentifierNotFoundException;
import uk.ac.roe.wfau.firethorn.meta.adql.AdqlColumn;
import uk.ac.roe.wfau.firethorn.meta.adql.AdqlResource;
import uk.ac.roe.wfau.firethorn.meta.adql.AdqlTable;
import uk.ac.roe.wfau.firethorn.meta.base.BaseResource;
import uk.ac.roe.wfau.firethorn.meta.jdbc.JdbcTable;

/**
 * ADQL query job.
 *
 */
public interface BlueQuery
extends AdqlQueryBase, BlueTask<BlueQuery>
    {
    /**
     * The type URI for this type.
     * @todo Use PURLs.
     *
     */
    public static final URI TYPE_URI = URI.create(
        "http://data.metagrid.co.uk/wfau/firethorn/types/entity/blue-query-1.0.json"
        );

    /**
     * EntityServices interface.
     * 
     */
    public static interface EntityServices
    extends BlueTask.EntityServices<BlueQuery>
        {
        @Override
        public BlueQuery.LinkFactory links();

        /**
         * Our {@link BlueQuery.EntityFactory} instance.
         *
         */
        public BlueQuery.EntityFactory entities();
        
        /**
         * Our {@link AdqlQueryBase.Limits.Factory } instance.
         * 
         */
        public AdqlQueryBase.Limits.Factory limits();

        /**
         * Our {@link AdqlQueryBase.Delays.Factory } instance.
         * 
         */
        public AdqlQueryBase.Delays.Factory delays();

        }
    
    /**
     * {@link NamedEntity.NameFactory} interface.
     *
     */
    @Deprecated
    public static interface NameFactory
    extends NamedEntity.NameFactory<BlueQuery>
        {
        }

    /**
     * Link factory interface.
     *
     */
    public static interface LinkFactory
    extends Entity.LinkFactory<BlueQuery>
        {
        /**
         * Create a callback link (as a string).
         *
         */
        public String callback(final BlueQuery query);

        }
    
    /**
     * {@link Identifier} factory interface.
     *
     */
    public static interface IdentFactory
    extends Entity.IdentFactory<BlueQuery>
        {
        }
    
    /**
     * EntityFactory interface.
     * 
     */
    public static interface EntityFactory
    extends BlueTask.EntityFactory<BlueQuery>
        {

        /*
         *
        public BlueQuery create(final AdqlResource source, final String input, final BlueTask.TaskState next, final Long wait)
        throws InvalidRequestException, InternalServerErrorException;

        public BlueQuery create(final AdqlResource source, final String input, final AdqlQueryBase.Limits limits, final BlueTask.TaskState next, final Long wait)
        throws InvalidRequestException, InternalServerErrorException;

        public BlueQuery create(final AdqlResource source, final String input, final AdqlQueryBase.Limits limits, final AdqlQueryBase.Delays delays, final BlueTask.TaskState next, final Long wait)
        throws InvalidRequestException, InternalServerErrorException;
         *
         */

        /**
         * Create a new {@link BlueQuery}.
         * 
         * @param source The {@link AdqlResource} to query.
         * @param input  The ADQL query.
         * @param mode   The {@link AdqlQueryBase.Mode}.
         * @param syntax The {@link AdqlQueryBase.Syntax.Level}.
         * @param limits The {@link AdqlQueryBase.Limits}.
         * @param delays The {@link AdqlQueryBase.Delays}.
         * @param next   The next {@link BlueTask.TaskState} to wait for. 
         * @param wait   How long to wait for the next {@link BlueTask.TaskState}.
         *  
         */
        public BlueQuery create(final AdqlResource source, final String input, final AdqlQueryBase.Mode mode, final AdqlQueryBase.Syntax.Level syntax, final AdqlQueryBase.Limits limits, final AdqlQueryBase.Delays delays, final BlueTask.TaskState next, final Long wait)
        throws InvalidRequestException, InternalServerErrorException;

        /**
         * Update a {@link BlueQuery} with an ADQL string, prev and next {@link BlueQuery.TaskState}, and a wait timeout.
         *
         */
        public BlueQuery update(final Identifier ident, final String input, final BlueTask.TaskState prev, final BlueTask.TaskState next, Long wait)
        throws IdentifierNotFoundException, InvalidStateRequestException;

        /**
         * Update a {@link BlueQuery} with an ADQL string, {@link AdqlQueryBase.Limits}, prev and next {@link BlueQuery.TaskState}, and a wait timeout.
         *
         */
        public BlueQuery update(final Identifier ident, final String input, final AdqlQueryBase.Limits limits, final BlueTask.TaskState prev, final BlueTask.TaskState next, Long wait)
        throws IdentifierNotFoundException, InvalidStateRequestException;

        /**
         * Update a new {@link BlueQuery} with an ADQL string, {@link AdqlQueryBase.Limits}, {@link AdqlQueryBase.Delays}, prev and next {@link BlueQuery.TaskState}, and a wait timeout.
         *
         */
        public BlueQuery update(final Identifier ident, final String input, final AdqlQueryBase.Limits limits, final AdqlQueryBase.Delays delays, final BlueTask.TaskState prev, final BlueTask.TaskState next, Long wait)
        throws IdentifierNotFoundException, InvalidStateRequestException;

        /**
         * Select a {@link BlueQuery} with a state and wait limit.
         *
         */
        public BlueQuery select(final Identifier ident, final TaskState prev, final TaskState next, Long wait)
        throws IdentifierNotFoundException;
        
        /**
         * Select all the {@link BlueQuery}s for an {@link AdqlResource}.
         *
         */
        public Iterable<BlueQuery> select(final AdqlResource resource);

        /**
         * Handle a {@link CallbackEvent} for a {@link BlueQuery}. 
         * 
         */
        public BlueQuery callback(final Identifier ident, final CallbackEvent message)
        throws IdentifierNotFoundException, InvalidStateRequestException;

        }

    /**
     * {@link BlueTask.TaskRunner} interface.
     *
     */
    public static interface TaskRunner
    extends BlueTask.TaskRunner<BlueQuery>
        {
        public static interface Creator
        extends BlueTask.TaskRunner.Creator<BlueQuery>
            {}

        public static interface Updator
        extends BlueTask.TaskRunner.Updator<BlueQuery>
            {}
        }

    /**
     * Public interface for a callback event.
     *  
     */
    public static interface CallbackEvent
        {
        /**
         * The next {@link TaskState}.
         *
         */
        public TaskState state();
        
        /**
         * The result status.
         * 
         */
        public interface Results
            {
            /**
             * The row count processed so far.
             *
             */
            public Long count();
    
            /**
             * The results state.
             * 
             */
            public ResultState state();

            }

        /**
         * The result status.
         * 
         */
        public Results results();

        }
    
    /**
     * Handle a {@link CallbackEvent} message. 
     * 
     */
    public void callback(final BlueQuery.CallbackEvent message)
    throws InvalidStateRequestException;

    /**
     * The {@link CallbackEvent} URL (as a string).
     *
     */
    public String callback();
    
    /**
     * The source {@link AdqlResource} to query.
     *
     */
    public AdqlResource source();
    
    /**
     * Get our input query.
     *
     */
    public String input();

    /**
     * Update our input query.
     * 
     */
    public void update(final String input)
    throws InvalidStateRequestException;

    /**
     * Update our input query and {@link AdqlQueryBase.Limits}.
     * 
     */
    public void update(final String input, final AdqlQueryBase.Limits limits)
    throws InvalidStateRequestException;

    /**
     * Update our input query and {@link AdqlQueryBase.Limits} and {@link AdqlQueryBase.Delays}.
     * 
     */
    public void update(final String input, final AdqlQueryBase.Limits limits, final AdqlQueryBase.Delays delays)
    throws InvalidStateRequestException;
    
    /**
     * Our ADQL query.
     *
     */
    public String adql();

    /**
     * The OGSA-DAI SQL query.
     *
     */
    public String osql();

    /**
     * The query results status.
     * 
     */
    public enum ResultState
    implements Comparable<ResultState>
        {
        NONE(true),
        EMPTY(true),
        PARTIAL(true),
        COMPLETED(false),
        TRUNCATED(false);

        private ResultState(boolean active)
            {
            this.active = active;
            }

        private boolean active ;

        /**
         * Check if this is an active state. 
         * @return true if this is an active state.
         * 
         */
        public boolean active()
            {
            return this.active;
            }
        
        /**
         * Null friendly String parser.
         * 
         */
        public static ResultState parse(final String string)
            {
            if (string == null)
                {
                return null ;
                }
            else {
                return ResultState.valueOf(
                    string
                    );
                }
            }
        }
    
    /**
     * Our results.
     *
     */
    public interface Results
        {
        /**
         * The physical JDBC table.
         *
         */
        public JdbcTable jdbc();

        /**
         * The abstract ADQL table.
         *
         */
        public AdqlTable adql();
        
        /**
         * The number of rows returned.
         * 
         */
        public Long rowcount();
        
        /**
         * The results status.
         * 
         */
        public ResultState state();

        }

    /**
     * Our results.
     *
     */
    public Results results();

    /**
     * The {@link AdqlQueryBase.SelectField}s used by the query.
     *
     */
    public interface Fields
        {
        public Iterable<AdqlQueryBase.SelectField> select();
        }

    /**
     * The {@link AdqlQueryBase.SelectField}s used by the query.
     *
     */
    public Fields fields();

    /**
     * The {@link AdqlColumn}s used by the query.
     *
     */
    public interface Columns
        {
        /**
         * List the {@link AdqlColumn}s used by the query.
         *
         */
        public Iterable<AdqlColumn> select();

        }
    /**
     * The {@link AdqlColumn}s used by the query.
     *
     */
    public Columns columns();

    /**
     * The {@link AdqlTable}s used by this query.
     *
     */
    public interface Tables
        {
        /**
         * List the {@link AdqlTable}s used by the query.
         *
         */
        public Iterable<AdqlTable> select();
        }

    /**
     * The {@link AdqlTable}s used by this query.
     *
     */
    public Tables tables();

    /**
     * The {@link BaseResource}s used by this query.
     *
     */
    public interface Resources
        {
        /**
         * List the {@link BaseResource}s used by the query.
         *
         */
        public Iterable<BaseResource<?>> select();

        /**
         * Select the primary {@link BaseResource} used by the query.
         *
         */
        public BaseResource<?> primary();
        
        }
    
    /**
     * The {@link BaseResource}s used by this query.
     *
     */
    public Resources resources();

    /**
     * Event notification handle.
     *
     */
    public static interface Handle
    extends BlueTask.Handle
        {
        }

    /**
     * Our {@link BlueQuery.Handle}.
     *
    public Handle handle();
     */

    }