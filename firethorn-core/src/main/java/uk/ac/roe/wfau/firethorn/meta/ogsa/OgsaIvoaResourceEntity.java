/*
 *  Copyright (C) 2014 Royal Observatory, University of Edinburgh, UK
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
package uk.ac.roe.wfau.firethorn.meta.ogsa;

import java.net.MalformedURLException;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import uk.ac.roe.wfau.firethorn.entity.AbstractEntityFactory;
import uk.ac.roe.wfau.firethorn.entity.annotation.CreateAtomicMethod;
import uk.ac.roe.wfau.firethorn.entity.annotation.CreateMethod;
import uk.ac.roe.wfau.firethorn.entity.annotation.SelectMethod;
import uk.ac.roe.wfau.firethorn.meta.ivoa.IvoaResource;
import uk.ac.roe.wfau.firethorn.meta.ivoa.IvoaResourceEntity;
import uk.ac.roe.wfau.firethorn.ogsadai.activity.client.SimpleResourceWorkflowResult;
import uk.ac.roe.wfau.firethorn.ogsadai.activity.client.WorkflowResult;
import uk.ac.roe.wfau.firethorn.ogsadai.activity.client.ivoa.IvoaCreateResourceWorkflow;

/**
 * {@link OgsaIvoaResource} implementation.
 *
 */
@Slf4j
@Entity
@Access(
    AccessType.FIELD
    )
@Table(
    name = OgsaIvoaResourceEntity.DB_TABLE_NAME
    )
@NamedQueries(
        {
        @NamedQuery(
            name  = "OgsaIvoaResource-select-all",
            query = "FROM OgsaIvoaResourceEntity ORDER BY ident desc"
            ),
        @NamedQuery(
            name  = "OgsaIvoaResource-select-service",
            query = "FROM OgsaIvoaResourceEntity WHERE service = :service ORDER BY ident desc"
            ),
        @NamedQuery(
            name  = "OgsaIvoaResource-select-resource",
            query = "FROM OgsaIvoaResourceEntity WHERE resource = :resource ORDER BY ident desc"
            ),
        @NamedQuery(
            name  = "OgsaIvoaResource-select-service-resource",
            query = "FROM OgsaIvoaResourceEntity WHERE service = :service AND resource = :resource ORDER BY ident desc"
            ),
        }
    )
public class OgsaIvoaResourceEntity
    extends OgsaBaseResourceEntity<OgsaIvoaResource>
    implements OgsaIvoaResource
    {
    /**
     * Hibernate table mapping, {@value}.
     *
     */
    protected static final String DB_TABLE_NAME = DB_TABLE_PREFIX + "OgsaIvoaResourceEntity";

    /**
     * Hibernate column mapping, {@value}.
     *
     */
    protected static final String DB_RESOURCE_RESOURCE_COL = "resource";
    
    /**
     * {@link OgsaIvoaResource.EntityFactory} implementation.
     *
     */
    @Component
    @Repository
    public static class OgsaIvoaResourceEntityFactory
    extends AbstractEntityFactory<OgsaIvoaResource>
    implements OgsaIvoaResource.EntityFactory
        {

        @Override
        public Class<?> etype()
            {
            return OgsaIvoaResourceEntity.class;
            }

        @Autowired
        private OgsaIvoaResource.IdentFactory idents;
        @Override
        public OgsaIvoaResource.IdentFactory idents()
            {
            return this.idents;
            }

        private OgsaIvoaResource.LinkFactory links;
        @Override
        public OgsaIvoaResource.LinkFactory links()
            {
            return this.links;
            }
        
        @Override
        @SelectMethod
        public Iterable<OgsaIvoaResource> select()
            {
            return super.iterable(
                super.query(
                    "OgsaIvoaResource-select-all"
                    )
                );
            }
        
        @Override
        @SelectMethod
        public Iterable<OgsaIvoaResource> select(final OgsaService service)
            {
            return super.iterable(
                super.query(
                    "OgsaIvoaResource-select-service"
                    ).setEntity(
                        "service",
                        service
                        )
                );
            }

        @Override
        public Iterable<OgsaIvoaResource> select(final IvoaResource resource)
            {
            return super.iterable(
                super.query(
                    "OgsaIvoaResource-select-resource"
                    ).setEntity(
                        "resource",
                        resource
                    )
                );
            }

        @Override
        @SelectMethod
        public Iterable<OgsaIvoaResource> select(final OgsaService service, final IvoaResource resource)
            {
            return super.iterable(
                super.query(
                    "OgsaIvoaResource-select-service-resource"
                    ).setEntity(
                        "service",
                        service
                    ).setEntity(
                        "resource",
                        resource
                    )
                );
            }

        @Override
        @CreateMethod
        public OgsaIvoaResource create(final OgsaService service, final IvoaResource resource)
            {
            return super.insert(
                new OgsaIvoaResourceEntity(
                    service,
                    resource
                    )
                );
            }

        @Override
        @CreateMethod
        public OgsaIvoaResource primary(IvoaResource resource)
            {
            return this.primary(
                factories().ogsa().services().primary(),
                resource
                );
            }

        @Override
        @CreateMethod
        public OgsaIvoaResource primary(OgsaService service, IvoaResource resource)
            {
            // Really really simple - just get the first. 
            OgsaIvoaResource found = super.first(
                super.query(
                    "OgsaIvoaResource-select-service-resource"
                    ).setEntity(
                        "service",
                        service
                    ).setEntity(
                        "resource",
                        resource
                    )
                );
            // If we don't have one, create one.
            if (found == null)
                {
                found = create(
                    service,
                    resource
                    );
                }
            return found ;
            }
        }
    
    /**
     * Protected constructor. 
     *
     */
    protected OgsaIvoaResourceEntity()
        {
        super();
        }

   /**
     *
     * Public constructor.
     * @param service The parent {@link OgsaService}
     * @param resource  The source {@link IvoaResource}
     *
     */
    public OgsaIvoaResourceEntity(final OgsaService service, final IvoaResource resource)
        {
        super(
            service
            );
        this.resource = resource  ;
        }

    @ManyToOne(
        fetch = FetchType.LAZY,
        targetEntity = IvoaResourceEntity.class
        )
    @JoinColumn(
        name = DB_RESOURCE_RESOURCE_COL,
        unique = false,
        nullable = false,
        updatable = false
        )
    private IvoaResource resource;
    @Override
    public IvoaResource resource()
        {
        return this.resource;
        }

    @Override
    public String link()
        {
        return factories().ogsa().factories().ivoa().links().link(
            this
            );
        }

    @Override
    public OgStatus init()
        {
        //
        // If we already have an ODSA-DAI resource ID.
        if (ogsaid() != null)
            {
            return ogStatus() ;
            }
        //
        // If we don't have an ODSA-DAI resource ID.
        else {
            IvoaCreateResourceWorkflow workflow = null;
            try {
                workflow = new IvoaCreateResourceWorkflow(
                    service().endpoint()
                    );
                }
            catch (MalformedURLException ouch)
                {
                return ogStatus(
                    OgStatus.ERROR
                    );
                }

            final SimpleResourceWorkflowResult response = workflow.execute(
                new IvoaCreateResourceWorkflow.Param()
                    {
                    @Override
                    public String endpoint()
                        {
                        // Just use the first endpoint.
                        return resource().endpoints().select().iterator().next().endpoint();
                        }

                    @Override
                    public Boolean quickstart()
                        {
                        return Boolean.FALSE;
                        }

                    @Override
                    public Integer interval()
                        {
                        return new Integer(10);
                        }

                    @Override
                    public Integer timeout()
                        {
                        return new Integer(300);
                        }
                    }
                );

            log.debug("Status  [{}]", response.status());
            log.debug("Created [{}]", response.result());
    
            if (response.status() == WorkflowResult.Status.COMPLETED)
                {
                return ogsaid(
                    OgStatus.ACTIVE,
                    response.result().toString()
                    );
                }
    
            else {
                return ogStatus(
                    OgStatus.ERROR
                    );
                }
            }
        }

	@Override
	protected void scanimpl()
		{
		// TODO Auto-generated method stub
		}
    }
