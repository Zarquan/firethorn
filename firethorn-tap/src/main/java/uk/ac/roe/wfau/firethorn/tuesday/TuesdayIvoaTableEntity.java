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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import uk.ac.roe.wfau.firethorn.common.entity.annotation.CreateEntityMethod;
import uk.ac.roe.wfau.firethorn.common.entity.annotation.SelectEntityMethod;

/**
 *
 *
 */
@Entity()
@Access(
    AccessType.FIELD
    )
@Table(
    name = TuesdayIvoaTableEntity.DB_TABLE_NAME,
    uniqueConstraints={
        }
    )
@NamedQueries(
        {
        @NamedQuery(
            name  = "TuesdayIvoaTable-select-parent",
            query = "FROM TuesdayIvoaTableEntity WHERE parent = :parent ORDER BY ident desc"
            ),
        @NamedQuery(
            name  = "TuesdayIvoaTable-select-parent.name",
            query = "FROM TuesdayIvoaTableEntity WHERE ((parent = :parent) AND (name = :name)) ORDER BY ident desc"
            ),
        @NamedQuery(
            name  = "TuesdayIvoaTable-search-parent.text",
            query = "FROM TuesdayIvoaTableEntity WHERE ((parent = :parent) AND (name LIKE :text)) ORDER BY ident desc"
            )
        }
    )
public class TuesdayIvoaTableEntity
    extends TuesdayBaseTableEntity<TuesdayIvoaTable, TuesdayIvoaColumn>
    implements TuesdayIvoaTable
    {
    /**
     * Hibernate database table name.
     *
     */
    protected static final String DB_TABLE_NAME = "TuesdayIvoaTableEntity";

    /**
     * Alias factory implementation.
     *
     */
    @Repository
    public static class AliasFactory
    implements TuesdayIvoaTable.AliasFactory
        {
        /**
         * The alias prefix for this type.
         *
         */
        protected static final String PREFIX = "IVOA_" ;

        @Override
        public String alias(final TuesdayIvoaTable table)
            {
            return PREFIX + table.ident();
            }
        }

    /**
     * Table factory implementation.
     *
     */
    @Repository
    public static class Factory
    extends TuesdayBaseTableEntity.Factory<TuesdayIvoaSchema, TuesdayIvoaTable>
    implements TuesdayIvoaTable.Factory
        {

        @Override
        public Class<?> etype()
            {
            return TuesdayIvoaTableEntity.class ;
            }

        @Override
        @CreateEntityMethod
        public TuesdayIvoaTable create(final TuesdayIvoaSchema parent, final String name)
            {
            return this.insert(
                new TuesdayIvoaTableEntity(
                    parent,
                    name
                    )
                );
            }

        @Override
        @SelectEntityMethod
        public Iterable<TuesdayIvoaTable> select(final TuesdayIvoaSchema parent)
            {
            return super.list(
                super.query(
                    "TuesdayIvoaTable-select-parent"
                    ).setEntity(
                        "parent",
                        parent
                        )
                );
            }

        @Override
        @SelectEntityMethod
        public TuesdayIvoaTable select(final TuesdayIvoaSchema parent, final String name)
            {
            return super.first(
                super.query(
                    "TuesdayIvoaTable-select-parent.name"
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
        public Iterable<TuesdayIvoaTable> search(final TuesdayIvoaSchema parent, final String text)
            {
            return super.iterable(
                super.query(
                    "TuesdayIvoaTable-search-parent.text"
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
        protected TuesdayIvoaColumn.Factory columns;
        @Override
        public TuesdayIvoaColumn.Factory columns()
            {
            return this.columns;
            }

        @Autowired
        protected TuesdayIvoaTable.IdentFactory idents;
        @Override
        public TuesdayIvoaTable.IdentFactory idents()
            {
            return this.idents;
            }

        @Autowired
        protected TuesdayIvoaTable.LinkFactory links;
        @Override
        public TuesdayIvoaTable.LinkFactory links()
            {
            return this.links;
            }

        @Autowired
        protected TuesdayIvoaTable.AliasFactory aliases;
        @Override
        public TuesdayIvoaTable.AliasFactory aliases()
            {
            return this.aliases;
            }
        }

    protected TuesdayIvoaTableEntity()
        {
        super();
        }

    protected TuesdayIvoaTableEntity(final TuesdayIvoaSchema schema, final String name)
        {
        super(schema, name);
        this.schema = schema;
        }

    @ManyToOne(
        fetch = FetchType.EAGER,
        targetEntity = TuesdayIvoaSchemaEntity.class
        )
    @JoinColumn(
        name = DB_PARENT_COL,
        unique = false,
        nullable = false,
        updatable = false
        )
    private TuesdayIvoaSchema schema;
    @Override
    public TuesdayIvoaSchema schema()
        {
        return this.schema;
        }
    @Override
    public TuesdayIvoaResource resource()
        {
        return this.schema().resource();
        }

    @Override
    public TuesdayOgsaTable<TuesdayIvoaTable, TuesdayIvoaColumn> ogsa()
        {
        return this;
        }

    @Override
    public TuesdayIvoaTable.Columns columns()
        {
        return new TuesdayIvoaTable.Columns()
            {
            @Override
            public Iterable<TuesdayIvoaColumn> select()
                {
                return factories().ivoa().columns().select(
                    TuesdayIvoaTableEntity.this
                    );
                }

            @Override
            public TuesdayIvoaColumn select(final String name)
                {
                return factories().ivoa().columns().select(
                    TuesdayIvoaTableEntity.this,
                    name
                    );
                }

            @Override
            public Iterable<TuesdayIvoaColumn> search(final String text)
                {
                return factories().ivoa().columns().search(
                    TuesdayIvoaTableEntity.this,
                    text
                    );
                }
            };
        }

    @Override
    public String link()
        {
        return factories().ivoa().tables().links().link(
            this
            );
        }

    @Override
    public String alias()
        {
        return factories().ivoa().tables().aliases().alias(
            this
            );
        }
    }
