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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.extern.slf4j.Slf4j;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import uk.ac.roe.wfau.firethorn.entity.AbstractEntityFactory;
import uk.ac.roe.wfau.firethorn.entity.AbstractEntityTracker;
import uk.ac.roe.wfau.firethorn.entity.EntityTracker;
import uk.ac.roe.wfau.firethorn.entity.annotation.CreateMethod;
import uk.ac.roe.wfau.firethorn.entity.annotation.SelectMethod;
import uk.ac.roe.wfau.firethorn.entity.exception.DuplicateEntityException;
import uk.ac.roe.wfau.firethorn.entity.exception.NameNotFoundException;
import uk.ac.roe.wfau.firethorn.meta.base.BaseResourceEntity;
import uk.ac.roe.wfau.firethorn.meta.jdbc.JdbcTableEntity;
import uk.ac.roe.wfau.firethorn.util.GenericIterable;

/**
 *
 *
 */
@Slf4j
@Entity
@Access(
    AccessType.FIELD
    )
@Table(
    name = IvoaResourceEntity.DB_TABLE_NAME
    )
@NamedQueries(
        {
        @NamedQuery(
            name  = "IvoaResource-select-all",
            query = "FROM IvoaResourceEntity ORDER BY name asc, ident desc"
            ),
        @NamedQuery(
            name  = "IvoaResource-select-ivoaid",
            query = "FROM IvoaResourceEntity WHERE ivoaid = :ivoaid ORDER BY name asc, ident desc"
            )
        }
    )
public class IvoaResourceEntity
    extends BaseResourceEntity<IvoaResource, IvoaSchema>
    implements IvoaResource
    {
    protected static final String DB_TABLE_NAME = DB_TABLE_PREFIX + "IvoaResourceEntity";

    protected static final String DB_IVOAID_COL = "ivoaid";
    protected static final String DB_OGSAID_COL = "ogsaid";

    /**
     * Our Entity Factory implementation.
     *
     */
    @Repository
    public static class EntityFactory
    extends AbstractEntityFactory<IvoaResource>
    implements IvoaResource.EntityFactory
        {

        @Override
        public Class<?> etype()
            {
            return IvoaResourceEntity.class ;
            }

        @Override
        @SelectMethod
        public Iterable<IvoaResource> select()
            {
            return super.iterable(
                super.query(
                    "IvoaResource-select-all"
                    )
                );
            }

        @Override
        @CreateMethod
        public IvoaResource create(final String ivoid)
            {
            return super.insert(
                new IvoaResourceEntity(
                    ivoid
                    )
                );
            }
        
        @Override
        @CreateMethod
        public IvoaResource create(final String ivoid, final String name)
            {
            return super.insert(
                new IvoaResourceEntity(
                    ivoid,
                    name
                    )
                );
            }

        @Autowired
        protected IvoaSchema.EntityFactory schemas;
        @Override
        public IvoaSchema.EntityFactory schemas()
            {
            return this.schemas;
            }

        @Autowired
        protected IvoaResource.IdentFactory idents;
        @Override
        public IvoaResource.IdentFactory idents()
            {
            return this.idents;
            }

        @Autowired
        protected IvoaResource.LinkFactory links;
        @Override
        public IvoaResource.LinkFactory links()
            {
            return this.links;
            }
        }

    protected IvoaResourceEntity()
        {
        super();
        }

    protected IvoaResourceEntity(final String ivoaid)
        {
        this(
            ivoaid,
            ivoaid
            );
        }

    protected IvoaResourceEntity(final String ivoaid, final String name)
        {
        super(
            name
            );
        this.ivoaid = ivoaid;

        /*
         * 
        endpoints.add(
            new IvoaEndpointEntity(
                this,
                ivoaid
                )
            );
         *
         */
        }

    @Basic(fetch = FetchType.EAGER)
    @Column(
        name = DB_IVOAID_COL,
        unique = false,
        nullable = true,
        updatable = true
        )
    private String ivoaid;
    @Override
    public String ivoaid()
        {
        return this.ivoaid;
        }
    @Override
    public void ivoaid(final String ivoaid)
        {
        this.ivoaid = ivoaid ;
        }

    @Override
    public IvoaResource.Schemas schemas()
        {
        return new IvoaResource.Schemas()
            {
            @Override
            public Iterable<IvoaSchema> select()
                {
                return factories().ivoa().schemas().select(
                    IvoaResourceEntity.this
                    );
                }

            @Override
            public IvoaSchema select(String name) throws NameNotFoundException
                {
                return factories().ivoa().schemas().select(
                    IvoaResourceEntity.this,
                    name
                    );
                }

            @Override
            public IvoaSchema search(final String name)
                {
                return factories().ivoa().schemas().search(
                    IvoaResourceEntity.this,
                    name
                    );
                }

            @Override
            public IvoaSchema.Tracker tracker()
                {
                return new IvoaSchemaEntity.Tracker(this.select())
                    {
                    @Override
                    protected void finish(final IvoaSchema schema)
                        {
                        log.debug("Archive inactive schema [{}]", schema.name());
                        }

                    @Override
                    protected IvoaSchema create(String name)
                        throws DuplicateEntityException
                        {
                        log.debug("Create a new schema [{}]", name);
                        return factories().ivoa().schemas().create(
                            IvoaResourceEntity.this,
                            name
                            );
                        }
                    };
                }
            };
        }

	/**
	 * The the OGSA-DAI resource ID.
	 * @todo Move to a common base class.
	 *
	 */
    @Column(
        name = DB_OGSAID_COL,
        unique = false,
        nullable = true,
        updatable = true
        )
	private String ogsaid;
	@Override
	public String ogsaid()
		{
		return this.ogsaid;
		}
	@Override
	public void ogsaid(final String ogsaid)
		{
		this.ogsaid = ogsaid;
		}

    @Override
    public String link()
        {
        return factories().ivoa().resources().links().link(
            this
            );
        }

    @Override
    protected void scanimpl()
        {
        // TODO Auto-generated method stub
        }

    @OneToMany(
        fetch   = FetchType.LAZY,
        mappedBy = "resource",
        targetEntity = IvoaEndpointEntity.class
        )
    private Set<Endpoint> endpoints = new HashSet<Endpoint>();
    
    @Override
    public Endpoints endpoints()
        {
        return new Endpoints()
            {
            @Override
            public Endpoint create(String url)
                {
                Endpoint endpoint = new IvoaEndpointEntity(
                    IvoaResourceEntity.this,
                    url
                    );
                 endpoints.add(
                     endpoint
                    );
                return endpoint;
                }
            @Override
            public Iterable<IvoaResource.Endpoint> select()
                {
                return new GenericIterable<IvoaResource.Endpoint, Endpoint>(
                    endpoints
                    ); 
                }
            };
        }
    }

