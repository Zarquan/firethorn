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
package uk.ac.roe.wfau.firethorn.widgeon.entity.view ;

import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URL;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.NamedQueries;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;  

import uk.ac.roe.wfau.firethorn.common.entity.Identifier;
import uk.ac.roe.wfau.firethorn.common.entity.AbstractEntity;
import uk.ac.roe.wfau.firethorn.common.entity.AbstractFactory;

import uk.ac.roe.wfau.firethorn.common.entity.annotation.CreateEntityMethod;
import uk.ac.roe.wfau.firethorn.common.entity.annotation.SelectEntityMethod;

import uk.ac.roe.wfau.firethorn.widgeon.Widgeon;
import uk.ac.roe.wfau.firethorn.widgeon.WidgeonStatus;
import uk.ac.roe.wfau.firethorn.widgeon.entity.WidgeonStatusEntity;
import uk.ac.roe.wfau.firethorn.widgeon.entity.base.SchemaBaseEntity;

/**
 * Schema View implementation.
 *
 */
@Slf4j
@Entity()
@Access(
    AccessType.FIELD
    )
@Table(
    name = SchemaViewEntity.DB_TABLE_NAME,
    uniqueConstraints=
        @UniqueConstraint(
            columnNames = {
                AbstractEntity.DB_NAME_COL,
                SchemaViewEntity.DB_PARENT_COL,
                }
            )
    )
@NamedQueries(
        {
        @NamedQuery(
            name  = "widgeon.view.schema-select-parent",
            query = "FROM SchemaViewEntity WHERE parent = :parent ORDER BY ident desc"
            ),
        @NamedQuery(
            name  = "widgeon.view.schema-select-parent.name",
            query = "FROM SchemaViewEntity WHERE parent = :parent AND name = :name ORDER BY ident desc"
            )
        }
    )
public class SchemaViewEntity
extends WidgeonStatusEntity
implements Widgeon.View.Schema
    {

    /**
     * Our persistence table name.
     * 
     */
    public static final String DB_TABLE_NAME = "widgeon_view_schema" ;

    /**
     * The persistence column name for our parent Widgeon.
     * 
     */
    public static final String DB_PARENT_COL = "parent" ;

    /**
     * The persistence column name for our base Schema.
     * 
     */
    public static final String DB_BASE_COL = "base" ;

    /**
     * Our Entity Factory implementation.
     *
     */
    @Repository
    public static class Factory
    extends AbstractFactory<Widgeon.View.Schema>
    implements Widgeon.View.Schema.Factory
        {

        @Override
        public Class etype()
            {
            return SchemaViewEntity.class ;
            }

        @Override
        @CreateEntityMethod
        public Widgeon.View.Schema create(final Widgeon.Base.Schema base, final Widgeon.View parent, final String name)
            {
            return super.insert(
                new SchemaViewEntity(
                    base,
                    parent,
                    name
                    )
                );
            }

        @Override
        @SelectEntityMethod
        public Iterable<Widgeon.View.Schema> select(final Widgeon.View parent)
            {
            return super.iterable(
                super.query(
                    "widgeon.view.schema-select-parent"
                    ).setEntity(
                        "parent",
                        parent
                        )
                );
            }

        @Override
        @SelectEntityMethod
        public Widgeon.View.Schema select(final Widgeon.View parent, final String name)
            {
            return super.single(
                super.query(
                    "widgeon.view.schema-select-parent.name"
                    ).setEntity(
                        "parent",
                        parent
                    ).setString(
                        "name",
                        name
                    )
                );
            }

        /**
         * Our Autowired Catalog factory.
         * 
         */
        @Autowired
        protected Widgeon.View.Schema.Catalog.Factory catalogs ;

        @Override
        public Widgeon.View.Schema.Catalog.Factory catalogs()
            {
            return this.catalogs ;
            }

        }

    @Override
    public Widgeon.View.Schema.Catalogs catalogs()
        {
        return new Widgeon.View.Schema.Catalogs()
            {

            @Override
            public Widgeon.View.Schema.Catalog select(String name)
                {
                return null ;
                }

            @Override
            public Iterable<Widgeon.View.Schema.Catalog> select()
                {
                return null ;
                }

            };
        }

    /**
     * Check a view name, using the base name if the given name is null or empty.
     *
     */
    private static String name(final Widgeon.Base.Schema base, final String name)
        {
        if ((name == null) || (name.trim().length() == 0))
            {
            return base.name();
            }
        else {
            return name.trim() ;
            }
        }

    /**
     * Default constructor needs to be protected not private.
     * http://kristian-domagala.blogspot.co.uk/2008/10/proxy-instantiation-problem-from.html
     *
     */
    protected SchemaViewEntity()
        {
        super();
        }

    /**
     * Create a new view.
     *
     */
    protected SchemaViewEntity(final Widgeon.Base.Schema base, final Widgeon.View parent)
        {
        this(
            base,
            parent,
            null
            );
        }

    /**
     * Create a new view.
     *
     */
    protected SchemaViewEntity(final Widgeon.Base.Schema base, final Widgeon.View parent, final String name)
        {
        super(
            name(
                base,
                name
                )
            );
        this.base = base ;
        this.parent = parent ;
        }

    /**
     * Our parent Widgeon.
     *
     */
    @ManyToOne(
        fetch = FetchType.EAGER,
        targetEntity = WidgeonViewEntity.class
        )
    @JoinColumn(
        name = DB_PARENT_COL,
        unique = false,
        nullable = false,
        updatable = false
        )
    private Widgeon.View parent ;

    @Override
    public Widgeon.View parent()
        {
        return this.parent ;
        }

    /**
     * Our underlying Schema.
     *
     */
    @ManyToOne(
        fetch = FetchType.EAGER,
        targetEntity = SchemaBaseEntity.class
        )
    @JoinColumn(
        name = DB_BASE_COL,
        unique = false,
        nullable = false,
        updatable = false
        )
    private Widgeon.Base.Schema base ;

    @Override
    public Widgeon.Base.Schema base()
        {
        return this.base ;
        }

    @Override
    public Widgeon.Status status()
        {
        if (this.parent().status() == Widgeon.Status.ENABLED)
            {
            if (this.base().status() == Widgeon.Status.ENABLED)
                {
                return super.status() ;
                }
            else {
                return this.base().status();
                }
            }
        else {
            return this.parent().status();
            }
        }
    }

