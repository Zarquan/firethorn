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
package uk.ac.roe.wfau.firethorn.common.entity ;

import java.util.Iterator;

import lombok.extern.slf4j.Slf4j;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import uk.ac.roe.wfau.firethorn.common.entity.annotation.CreateEntityMethod;
import uk.ac.roe.wfau.firethorn.common.entity.annotation.DeleteEntityMethod;
import uk.ac.roe.wfau.firethorn.common.entity.annotation.SelectEntityMethod;
import uk.ac.roe.wfau.firethorn.common.entity.annotation.UpdateEntityMethod;
import uk.ac.roe.wfau.firethorn.common.entity.exception.NotFoundException;
import uk.ac.roe.wfau.firethorn.common.entity.exception.IdentifierNotFoundException;
import uk.ac.roe.wfau.firethorn.common.womble.Womble;
import uk.ac.roe.wfau.firethorn.mallard.AdqlService;
import uk.ac.roe.wfau.firethorn.widgeon.adql.AdqlResource;

/**
 * Generic base class for a persistent Entity Factory.
 *
 */
@Slf4j
@Repository
public abstract class AbstractFactory<EntityType extends Entity>
implements Entity.Factory<EntityType>
    {

    /**
     * Get the class of Entity we manage.
     * Required because we can't get this from the generics at runtime, because ....
     * http://stackoverflow.com/questions/3403909/get-generic-type-of-class-at-runtime
     * http://stackoverflow.com/questions/2225979/getting-t-class-despite-javas-type-erasure
     *
     */
    public abstract Class<?> etype();

    /**
     * Our autowired reference to the global Womble.
     *
     */
    @Autowired
    private Womble womble ;
    public Womble womble()
        {
        return womble;
        }

    /**
     * Get a named query.
     *
     */
    public Query query(final String name)
        {
        return womble.hibernate().query(
            name
            );
        }

    /**
     * Insert a new Entity into the database.
     *
     */
    @CreateEntityMethod
    @SuppressWarnings("unchecked")
    public EntityType insert(final EntityType entity)
        {
        log.debug("insert [{}]", entity);
        return (EntityType) womble.hibernate().insert(
            entity
            );
        }

    /**
     * Select a specific Entity by Identifier.
     *
     */
    @Override
    @SelectEntityMethod
    public EntityType select(final Identifier ident)
    throws NotFoundException
        {
        log.debug("select [{}]", (ident != null) ? ident.value() : null);
        @SuppressWarnings("unchecked")
        final
        EntityType result = (EntityType) womble.hibernate().select(
            etype(),
            ident
            );
        if (result != null)
            {
            return result ;
            }
        else {
            throw new IdentifierNotFoundException(
                ident
                );
            }
        }

    /**
     * Update an Entity.
     *
     */
    @UpdateEntityMethod
    @SuppressWarnings("unchecked")
    public EntityType update(final EntityType entity)
        {
        log.debug("update [{}]", entity);
        if (etype().isInstance(entity))
            {
            return (EntityType) womble.hibernate().update(
                entity
                );
            }
        else {
            log.error(
                "Update not supported for [" + entity.getClass().getName() + "]"
                );
            throw new IllegalArgumentException(
                "Update not supported for [" + entity.getClass().getName() + "]"
                );
            }
        }

    /**
     * Delete an Entity.
     *
     */
    @DeleteEntityMethod
    public void delete(final EntityType entity)
        {
        log.debug("delete [{}]", entity);
        if (etype().isInstance(entity))
            {
            womble.hibernate().delete(
                entity
                );
            }
        else {
            log.error(
                "Delete not supported for [" + entity.getClass().getName() + "]"
                );
            throw new IllegalArgumentException(
                "Delete not supported for [" + entity.getClass().getName() + "]"
                );
            }
        }

    /**
     * Flush changes to the database.
     *
     */
    protected void flush()
        {
        womble.hibernate().flush();
        }

    /**
     * Clear the session state, discarding unsaved changes.
     *
     */
    protected void clear()
        {
        womble.hibernate().clear();
        }

    /**
     * Select a single object from a query.
     *
     */
    @SelectEntityMethod
    public EntityType single(final Query query)
    throws NotFoundException
        {
        @SuppressWarnings("unchecked")
        final EntityType result = (EntityType) womble.hibernate().single(
            query
            );
        if (result != null)
            {
            return result ;
            }
        else {
            throw new NotFoundException();
            }
        }

    /**
     * Return the first result of a query, or null if the results are empty.
     *
     */
    @SuppressWarnings("unchecked")
    @SelectEntityMethod
    public EntityType first(final Query query)
        {
        return (EntityType) womble.hibernate().first(
            query
            );
        }

    /**
     * Select an Iterable set of objects.
     *
     */
    @SelectEntityMethod
    public Iterable<EntityType> iterable(final Query query)
        {
        return new Iterable<EntityType>()
            {
            @Override
            @SelectEntityMethod
            @SuppressWarnings("unchecked")
            public Iterator<EntityType> iterator()
                {
                try {
                    return query.iterate();
                    }
                catch (final HibernateException ouch)
                    {
                    throw womble.hibernate().convert(
                        ouch
                        );
                    }
                }
            };
        }
    /**
     * Create a text search string.
     * TODO .. lots !!
     *
     */
    public String searchParam(final String text)
        {
        //
        // Using wildcards in a HQL query with named parameters.
        // http://www.stpe.se/2008/07/hibernate-hql-like-query-named-parameters/
        return new StringBuilder(text).append("%").toString();
        }

    /**
     * Our corresponding Identifier factory.
     *
     */
    public abstract Entity.IdentFactory<EntityType> identifiers();

    @Override
    public String link(final EntityType entity)
        {
        return identifiers().link(
            entity
            );
        }
    /*
     *
    @Override
    public String link(Identifier ident)
        {
        return identifiers().link(
            ident
            );
        }
*/

    @Override
    public Identifier ident(final String string)
        {
        return identifiers().ident(
            string
            );
        }

    }


