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
package uk.ac.roe.wfau.firethorn.meta.ivoa;

import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import uk.ac.roe.wfau.firethorn.entity.AbstractEntityFactory;
import uk.ac.roe.wfau.firethorn.entity.annotation.CreateEntityMethod;
import uk.ac.roe.wfau.firethorn.entity.annotation.SelectEntityMethod;
import uk.ac.roe.wfau.firethorn.entity.exception.NotFoundException;
import uk.ac.roe.wfau.firethorn.meta.adql.AdqlColumn;
import uk.ac.roe.wfau.firethorn.meta.base.BaseColumnEntity;

/**
 *
 *
 */
@Entity()
@Access(
    AccessType.FIELD
    )
@Table(
    name = IvoaColumnEntity.DB_TABLE_NAME,
    uniqueConstraints={
        }
    )
@NamedQueries(
        {
        @NamedQuery(
            name  = "IvoaColumn-select-parent",
            query = "FROM IvoaColumnEntity WHERE parent = :parent ORDER BY ident desc"
            ),
        @NamedQuery(
            name  = "IvoaColumn-parent.name",
            query = "FROM IvoaColumnEntity WHERE ((parent = :parent) AND (name = :name)) ORDER BY ident desc"
            ),
        @NamedQuery(
            name  = "IvoaColumn-search-parent.text",
            query = "FROM IvoaColumnEntity WHERE ((parent = :parent) AND (name LIKE :text)) ORDER BY ident desc"
            )
        }
    )
public class IvoaColumnEntity
    extends BaseColumnEntity<IvoaColumn>
    implements IvoaColumn
    {
    /**
     * Hibernate table mapping.
     *
     */
    protected static final String DB_TABLE_NAME = "IvoaColumnEntity";

    /**
     * Hibernate column mapping.
     *
     */
    protected static final String DB_IVOA_TYPE_COL = "ivoatype" ;
    protected static final String DB_IVOA_SIZE_COL = "ivoasize" ;

    /**
     * Alias factory implementation.
     * @todo Move to a separate package.
     *
     */
    @Component
    public static class AliasFactory
    implements IvoaColumn.AliasFactory
        {
        @Override
        public String alias(final IvoaColumn column)
            {
            return "IVOA_".concat(
                column.ident().toString()
                );
            }
        }

    /**
     * Column factory implementation.
     *
     */
    @Repository
    public static class Factory
    extends AbstractEntityFactory<IvoaColumn>
    implements IvoaColumn.Factory
        {

        @Override
        public Class<?> etype()
            {
            return IvoaColumnEntity.class ;
            }

        @Override
        @CreateEntityMethod
        public IvoaColumn create(final IvoaTable parent, final String name)
            {
            return this.insert(
                new IvoaColumnEntity(
                    parent,
                    name
                    )
                );
            }

        @Override
        @SelectEntityMethod
        public Iterable<IvoaColumn> select(final IvoaTable parent)
            {
            return super.list(
                super.query(
                    "IvoaColumn-select-parent"
                    ).setEntity(
                        "parent",
                        parent
                        )
                );
            }

        @Override
        @SelectEntityMethod
        public IvoaColumn select(final IvoaTable parent, final String name)
            {
            return super.first(
                super.query(
                    "IvoaColumn-select-parent.name"
                    ).setEntity(
                        "parent",
                        parent
                    ).setString(
                        "name",
                        name
                    )
                );
            }

        @Override
        @SelectEntityMethod
        public Iterable<IvoaColumn> search(final IvoaTable parent, final String text)
            {
            return super.iterable(
                super.query(
                    "IvoaColumn-search-parent.text"
                    ).setEntity(
                        "parent",
                        parent
                    ).setString(
                        "text",
                        searchParam(
                            text
                            )
                        )
                );
            }

        @Autowired
        protected IvoaColumn.IdentFactory idents;
        @Override
        public IvoaColumn.IdentFactory idents()
            {
            return this.idents;
            }

        @Autowired
        protected IvoaColumn.LinkFactory links;
        @Override
        public IvoaColumn.LinkFactory links()
            {
            return this.links;
            }

        @Autowired
        protected IvoaColumn.AliasFactory aliases;
        @Override
        public IvoaColumn.AliasFactory aliases()
            {
            return this.aliases;
            }

        @Override
        public IvoaColumn select(UUID uuid) throws NotFoundException
            {
            // TODO Auto-generated method stub
            return null;
            }
        }

    protected IvoaColumnEntity()
        {
        }

    protected IvoaColumnEntity(final IvoaTable table, final String name)
        {
        super(table, name);
        this.table = table;
        }

    @Override
    public IvoaColumn base()
        {
        return this ;
        }
    @Override
    public IvoaColumn root()
        {
        return this ;
        }

    @ManyToOne(
        fetch = FetchType.EAGER,
        targetEntity = IvoaTableEntity.class
        )
    @JoinColumn(
        name = DB_PARENT_COL,
        unique = false,
        nullable = false,
        updatable = true
        )
    private IvoaTable table;
    @Override
    public IvoaTable table()
        {
        return this.table;
        }
    @Override
    public IvoaSchema schema()
        {
        return this.table().schema();
        }
    @Override
    public IvoaResource resource()
        {
        return this.table().resource();
        }

    @Basic(
        fetch = FetchType.EAGER
        )
    @Column(
        name = DB_IVOA_TYPE_COL,
        unique = false,
        nullable = true,
        updatable = true
        )
    @Enumerated(
        EnumType.STRING
        )
    private AdqlColumn.Type ivoatype ;
    protected AdqlColumn.Type ivoatype()
        {
        return this.ivoatype;
        }
    protected void ivoatype(final AdqlColumn.Type type)
        {
        this.ivoatype = type;
        }
    @Override
    protected AdqlColumn.Type adqltype()
        {
        if (this.adqltype != null)
            {
            return this.adqltype;
            }
        else {
            return this.ivoatype;
            }
        }

    @Basic(
        fetch = FetchType.EAGER
        )
    @Column(
        name = DB_IVOA_SIZE_COL,
        unique = false,
        nullable = true,
        updatable = true
        )
    private Integer ivoasize;
    protected Integer ivoasize()
        {
        return this.ivoasize;
        }
    protected void ivoasize(final Integer size)
        {
        this.ivoasize = size;
        }
    @Override
    protected Integer adqlsize()
        {
        if (this.adqlsize != null)
            {
            return this.adqlsize ;
            }
        else {
            return this.ivoasize ;
            }
        }
    @Override
    public String alias()
        {
        return factories().ivoa().columns().aliases().alias(
            this
            );
        }
    @Override
    public String link()
        {
        return factories().ivoa().columns().links().link(
            this
            );
        }

    @Override
    protected void scanimpl()
        {
        // TODO Auto-generated method stub

        }
    }
