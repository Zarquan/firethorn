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
package uk.ac.roe.wfau.firethorn.blue;

import java.util.concurrent.Future;

import org.hibernate.Session;
import org.joda.time.DateTime;

import uk.ac.roe.wfau.firethorn.entity.Entity;
import uk.ac.roe.wfau.firethorn.entity.Identifier;
import uk.ac.roe.wfau.firethorn.entity.NamedEntity;
import uk.ac.roe.wfau.firethorn.entity.exception.IdentifierNotFoundException;
import uk.ac.roe.wfau.firethorn.hibernate.HibernateConvertException;
import uk.ac.roe.wfau.firethorn.spring.Context;

/**
 * Generic task interface.
 *
 */
public interface BlueTask<TaskType extends BlueTask<?>>
extends NamedEntity
    {

    /**
     * EntityServices interface.
     * 
     */
    public static interface EntityServices<TaskType extends BlueTask<?>>
    extends NamedEntity.EntityServices<TaskType>
        {
        /**
         * Our {@link BlueTask.EntityFactory} instance.
         *
         */
        public BlueTask.EntityFactory<TaskType> entities();

        /**
         * Our {@link BlueTask.TaskRunner} instance.
         * 
         */
        public BlueTask.TaskRunner<TaskType> runner(); 
        
        /**
         * Our {@link Context.Factory} instance.
         * 
         */
        public Context.Factory contexts();        
        }

    /**
     * TaskRunner interface.
     * 
     */
    public static interface TaskRunner<TaskType extends BlueTask<?>>
        {
        /**
         * Public interface for a {@link BlueTask} update.
         *
         */
        public interface Updator
            {
            /**
             * The {@link BlueTask} {@link Identifier}.
             *
             */
            public Identifier ident();

            /**
             * Execute the step.
             *
             */
            public TaskState execute();
            }

        /**
         * Execute an {@link TaskRunner.Updator} in a new {@link Thread}.
         * 
         */
        public TaskState thread(final Updator updator);

        /**
         * Execute an {@link TaskRunner.Updator} in a {@link Future}.
         * 
         */
        public Future<TaskState> future(final Updator updator);
        
        /**
         * Public interface for a {@link BlueTask} creation.
         *
         */
        public interface Creator<TaskType extends BlueTask<?>>
            {
            /**
             * Execute the step.
             * @throws HibernateConvertException
             * @throws InvalidStateTransitionException
             *
             */
            public TaskType create()
            throws InvalidStateTransitionException, HibernateConvertException;

            }

        /**
         * Execute an {@link TaskRunner.Creator} in a new {@link Thread}.
         * <br/> 
         * Running the {@link TaskRunner.Creator} in a new {@link Thread} means that it is run in
         * a new Hibernate {@link Session}, which gets committed to the database when
         * the {@link TaskRunner.Creator} completes its operation.
         * 
         */
        public TaskType thread(final Creator<TaskType> creator)
        throws InvalidStateTransitionException;

        /**
         * Execute an {@link TaskRunner.Creator} in a {@link Future}.
         * 
         */
        public Future<TaskType> future(final Creator<TaskType> creator);

        }

    /**
     * EntityFactory interface.
     * 
     */
    public static interface EntityFactory<TaskType extends BlueTask<?>>
    extends Entity.EntityFactory<TaskType>
        {
        /**
         * Select all the available tasks.
         *
         */
        public Iterable<TaskType> select();

        /**
         * Advance the {@link TaskState} of a {@link BlueTask}.
         * @param ident The {@link BlueTask} {@link Identifier}. 
         * @param prev The current/previous {@link TaskState}. 
         * @param next The next {@link TaskState} to move to. 
         * @param wait The blocking time in milliseconds. 
         * 
         */
        public TaskType advance(final Identifier ident, final TaskState prev, final TaskState next, long wait)
        throws IdentifierNotFoundException, InvalidStateRequestException;
       
        }

    /**
     * The primary task status.
     *
     */
    public enum TaskState
    implements Comparable<TaskState>
        {
        EDITING(false),
        READY(false),
        QUEUED(true),
        RUNNING(true),
        COMPLETED(false),
        CANCELLED(false),
        FAILED(false),
        ERROR(false);
        
        private TaskState(boolean active)
            {
            this.active = active;
            }

        private boolean active ;
        /**
         * Check if this is an active {@link TaskState}. 
         * @return true if this is an active {@link TaskState}.
         * 
         */
        public boolean active()
            {
            return this.active;
            }

        /**
         * Get the next {@link TaskState} in the sequence. 
         * @return The next {@link TaskState} in the sequence.
         * 
         */
        public TaskState next()
        	{
        	return next(this);
        	}

        /**
         * Get the next {@link TaskState} in the sequence. 
         * @param prev The current {@link TaskState}.
         * @return The next {@link TaskState} in the sequence.
         * 
         */
        public static TaskState next(final TaskState prev)
    		{
        	switch(prev)
        		{
        		case EDITING:
        			return READY;
        		case READY:
        			return QUEUED;
        		case QUEUED:
        			return RUNNING;
        		case RUNNING:
        			return COMPLETED;
    			default:
    				return prev;
        		}
    		}
        };

    /**
     * The task {@link TaskState}.
     *
     */
    public TaskState state();

    /**
     * Advance to the next {@link TaskState},
     * called in response to a user action. 
     * @param prev The next {@link TaskState} to move to. 
     * 
    public void advance(final TaskState next)
    throws InvalidStateTransitionException;
     */

    /**
     * Advance to the next {@link TaskState},
     * called in response to a user action.
     * @param next The next {@link TaskState} to move to. 
     * @param wait The blocking time in milliseconds. 
     * 
    public void advance(final TaskState next, long wait)
    throws InvalidStateTransitionException;
     */

    /**
     * Advance to the next {@link TaskState},
     * called in response to a user action.
     * @param prev The current/previous {@link TaskState}. 
     * @param next The next {@link TaskState} to move to. 
     * @param wait The blocking time in milliseconds. 
     * 
     */
    public void advance(final TaskState prev, final TaskState next, final Long wait)
    throws InvalidStateRequestException;

    /**
     * Wait for a state {@link TaskState} change.
     * @param prev The current/previous {@link TaskState}. 
     * @param prev The next {@link TaskState} to move to. 
     * @param wait The blocking time in milliseconds. 
     * 
     */
    public void waitfor(final TaskState prev, final TaskState next, final Long wait);

    /**
     * An event notification handle.
     * Hopefully abstract enough to enable us to use something like Hazelcast if we needed to.
     * http://hazelcast.com/use-cases/application-scaling/
     *
     */
    public static interface Handle
        {

        /**
         * The task identifier.
         *
         */
        public String ident();

        /**
         * Get the {@link Handle} {@link TaskState}.
         *
         */
        public TaskState state();

        /**
         * Update our {@link Handle} with a {@link TaskState} event.
         *
         */
        public void event(final TaskState state);

        /**
         * Event listener interface.
         *
         */
        public static interface Listener
            {
            /**
             * Wait for an event from a {@link Handle}. 
             *
             */
            public void waitfor(final Handle handle);

            }

        /**
         * Handle resolver.
         *
         */
        public static interface Resolver
            {
            public Handle select(final Identifier ident);
            } 
        }

    /**
     * Our {@link BlueTask.Handle}.
     *
     */
    public Handle handle();

    /**
     * Get the {@link Entity} instance linked to the current {@link Thread}.
     * 
     */
    public TaskType rebase()
	throws HibernateConvertException;
    
    /**
     * The date/time the {@link BlueTask} was queued.
     *
     */
    public DateTime queued();

    /**
     * The date/time the {@link BlueTask} was started.
     *
     */
    public DateTime started();

    /**
     * The date/time the {@link BlueTask} was completed.
     *
     */
    public DateTime completed();
    
    }
