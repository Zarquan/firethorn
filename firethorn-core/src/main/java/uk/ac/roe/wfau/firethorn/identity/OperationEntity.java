/*
 *  Copyright (C) 2013 Royal Observatory, University of Edinburgh, UK
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
package uk.ac.roe.wfau.firethorn.identity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.ac.roe.wfau.firethorn.entity.AbstractEntity;
import uk.ac.roe.wfau.firethorn.entity.AbstractFactory;

/**
 *
 *
 */
@Slf4j
@Entity()
@Access(
    AccessType.FIELD
    )
@Table(
    name = OperationEntity.DB_TABLE_NAME
    )
@Inheritance(
    strategy = InheritanceType.JOINED
    )
@NamedQueries(
        {
        }
    )
public class OperationEntity
extends AbstractEntity
implements Operation
    {

    /**
     * Hibernate table mapping.
     *
     */
    protected static final String DB_TABLE_NAME = "OperationEntity";

    /**
     * Hibernate column mapping.
     *
     */
    protected static final String DB_METHOD_COL = "method" ;
    protected static final String DB_SOURCE_COL = "source" ;

    @Component
    public static class Factory
    extends AbstractFactory<Operation>
    implements Operation.Factory
        {
        @Override
        public Class<?> etype()
            {
            return OperationEntity.class ;
            }

        @Override
        public Operation create(final String method, final String source)
            {
            return current(
                this.insert(
                    new OperationEntity(
                        method,
                        source
                        )
                    )
                );
            }
        
        @Autowired
        protected Operation.LinkFactory links;
        @Override
        public Operation.LinkFactory links()
            {
            return this.links;
            }

        @Autowired
        protected Operation.IdentFactory idents;
        @Override
        public Operation.IdentFactory idents()
            {
            return this.idents;
            }

        /*
         * ThreadLocal Operation.
         *
         */
        private final ThreadLocal<Operation> local = new ThreadLocal<Operation>();
        
        @Override
        public Operation current()
            {
            return local.get();
            }

        public Operation current(Operation current)
            {
            local.set(
                current
                );
            return current;
            }
        }
    
    /**
     * Protected constructor.
     *
     */
    protected OperationEntity()
        {
        super();
        }
    
    /**
     * Protected constructor.
     * @todo remove name
     *
     */
    protected OperationEntity(final String method, final String source)
        {
        super(method);
        this.method = method ;
        this.source = source ;
        }

    @Basic(
        fetch = FetchType.EAGER
        )
    @Column(
        name = DB_METHOD_COL,
        unique = false,
        nullable = true,
        updatable = true
        )
    private String method ;
    @Override
    public String method()
        {
        return this.method ;
        }
    
    @Basic(
        fetch = FetchType.EAGER
        )
    @Column(
        name = DB_SOURCE_COL,
        unique = false,
        nullable = true,
        updatable = true
        )
    private String source ;
    @Override
    public String source ()
        {
        return this.source ;
        }
    
    /**
     * The set of Authentications for this Operation.
     *
     */
    @OneToMany(
        fetch   = FetchType.LAZY,
        targetEntity = AuthenticationEntity.class
        )
    private final List<Authentication> authentications = new ArrayList<Authentication>();
    @Override
    public Authentications authentications()
        {
        return new Authentications()
            {
            @Override
            public Iterable<Authentication> select()
                {
                return authentications;
                }

            @Override
            public Authentication create(Identity identity, String method)
                {
                Authentication authentication = factories().authentications().create(
                    OperationEntity.this,
                    identity,
                    method
                    );
                authentications.add(
                    authentication
                    );
                return authentication;
                }
            };
        }

    @Override
    public String link()
        {
        // TODO Auto-generated method stub
        return null;
        }
    }
