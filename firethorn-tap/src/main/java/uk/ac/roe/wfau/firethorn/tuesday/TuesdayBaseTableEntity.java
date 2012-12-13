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
package uk.ac.roe.wfau.firethorn.tuesday;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.NamedQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import uk.ac.roe.wfau.firethorn.common.entity.AbstractFactory;
import uk.ac.roe.wfau.firethorn.common.entity.exception.NotFoundException;

/**
 *
 *
 */
@Entity()
@Access(
    AccessType.FIELD
    )
@Table(
    name = TuesdayBaseTableEntity.DB_TABLE_NAME,
    uniqueConstraints={
        @UniqueConstraint(
            columnNames = {
                TuesdayBaseComponentEntity.DB_NAME_COL,
                TuesdayBaseComponentEntity.DB_PARENT_COL,
                }
            )
        }
    )
@Inheritance(
    strategy = InheritanceType.JOINED
    )
@NamedQueries(
        {
        }
    )
public abstract class TuesdayBaseTableEntity<TableType extends TuesdayBaseTable<TableType, ColumnType>, ColumnType extends TuesdayBaseColumn<ColumnType>>
extends TuesdayBaseComponentEntity
    implements TuesdayBaseTable<TableType, ColumnType>
    {
    /**
     * Hibernate database table name.
     *
     */
    protected static final String DB_TABLE_NAME = "TuesdayBaseTableEntity";

    /**
     * Alias factory implementation.
     *
    @Repository
    public static class AliasFactory
    implements TuesdayBaseTable.AliasFactory
        {
        protected static final String PREFIX = "BASE_" ;

        @Override
        public String alias(final TuesdayBaseTable<?,?> table)
            {
            return PREFIX + table.ident();
            }
        }
     */

    /**
     * Alias resolver implementation.
     *
     */
    @Repository
    public static class AliasResolver
    extends AbstractFactory<TuesdayBaseTable<?,?>>
    implements TuesdayBaseTable.AliasResolver
        {
        @Override
        public Class<?> etype()
            {
            return TuesdayBaseTableEntity.class;
            }

        @Autowired
        protected TuesdayBaseTable.IdentFactory idents ;
        @Override
        public TuesdayBaseTable.IdentFactory idents()
            {
            return this.idents;
            }

        @Override
        public TuesdayBaseTable.LinkFactory links()
            {
            return null;
            }

        protected static final String PREFIX = "BASE_" ;

        @Override
        public TuesdayBaseTable<?,?> select(final String alias)
        throws NotFoundException
            {
            return this.select(
                this.idents.ident(
                    alias.substring(
                        PREFIX.length()
                        )
                    )
                );
            }
        }

    /**
     * Table factory implementation.
     *
     */
    @Repository
    public static abstract class Factory<SchemaType extends TuesdayBaseSchema<SchemaType, TableType>, TableType extends TuesdayBaseTable<TableType, ?>>
    extends AbstractFactory<TableType>
    implements TuesdayBaseTable.Factory<SchemaType, TableType>
        {
        }

    protected TuesdayBaseTableEntity()
        {
        super();
        }

    protected TuesdayBaseTableEntity(final TuesdayBaseSchema<?,TableType> parent, final String name)
        {
        super(name);
        this.parent = parent;
        }

    @Basic(fetch = FetchType.EAGER)
    @Column(
        name = DB_TYPE_COL,
        unique = false,
        nullable = true,
        updatable = true
        )
    private String type ;
    @Override
    public String type()
        {
        return this.type;
        }
    @Override
    public void type(final String type)
        {
        this.type = type;
        }

    @Basic(fetch = FetchType.EAGER)
    @Column(
        name = DB_SIZE_COL,
        unique = false,
        nullable = true,
        updatable = true
        )
    private Integer size ;
    @Override
    public Integer size()
        {
        return this.size;
        }
    @Override
    public void size(final Integer size)
        {
        this.size = size;
        }

    @Basic(fetch = FetchType.EAGER)
    @Column(
        name = DB_UCD_COL,
        unique = false,
        nullable = true,
        updatable = true
        )
    private String ucd;
    @Override
    public String ucd()
        {
        return this.ucd;
        }
    @Override
    public void ucd(final String ucd)
        {
        this.ucd = ucd;
        }

    @Override
    public StringBuilder fullname()
        {
        return this.schema().fullname().append(".").append(this.name());
        }

    @Override
    public abstract TuesdayOgsaTable<?, ?> ogsa();

    @Index(
        name=DB_TABLE_NAME + "IndexByParent"
        )
    @ManyToOne(
        fetch = FetchType.EAGER,
        targetEntity = TuesdayBaseSchemaEntity.class
        )
    @JoinColumn(
        name = DB_PARENT_COL,
        unique = false,
        nullable = false,
        updatable = false
        )
    private TuesdayBaseSchema<?, TableType> parent;
    @Override
    public TuesdayBaseSchema<?, TableType> schema()
        {
        return this.parent;
        }
    protected void schema(final TuesdayBaseSchema<?, TableType> schema)
        {
        this.parent = schema;
        }

    @Override
    public abstract TuesdayBaseResource<?> resource();

    @Override
    public Linked linked()
        {
        return new Linked()
            {
            @Override
            public Iterable<TuesdayAdqlTable> select()
                {
                //"SELECT FROM TuesdayAdqlTable WHERE base = :base"
                return null;
                }
            };
        }

    @Override
    public abstract String alias();
    }
