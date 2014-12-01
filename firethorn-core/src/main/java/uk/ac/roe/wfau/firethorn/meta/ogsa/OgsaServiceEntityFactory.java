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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import uk.ac.roe.wfau.firethorn.entity.AbstractEntityFactory;
import uk.ac.roe.wfau.firethorn.entity.annotation.CreateMethod;
import uk.ac.roe.wfau.firethorn.entity.annotation.SelectMethod;

/**
 * {@link OgsaService.EntityFactory} implementation.
 *
 */
@Repository
public class OgsaServiceEntityFactory
extends AbstractEntityFactory<OgsaService>
implements OgsaService.EntityFactory
    {
    @Override
    public Class<?> etype()
        {
        return OgsaServiceEntity.class ;
        }

    @Autowired
    private OgsaService.IdentFactory idents;
    @Override
    public OgsaService.IdentFactory idents()
        {
        return this.idents;
        }

    @Autowired
    private OgsaService.LinkFactory links;
    @Override
    public OgsaService.LinkFactory links()
        {
        return this.links;
        }

    @Autowired
    private OgsaService.NameFactory names;
    public OgsaService.NameFactory names()
        {
        return this.names;
        }

    @Override
    @SelectMethod
    public Iterable<OgsaService> select()
        {
        return super.iterable(
            super.query(
                "OgsaService-select-all"
                )
            );
        }

    @Override
    @CreateMethod
    public OgsaService create()
        {
        return create(
            null,
            names.name()
            );
        }

    @Override
    @CreateMethod
    public OgsaService create(final String endpoint)
        {
        return create(
            endpoint,
            names.name()
            );
        }

    @Override
    @CreateMethod
    public OgsaService create(final String endpoint, final String name)
        {
        return super.insert(
            new OgsaServiceEntity(
                endpoint,
                name
                )
            );
        }
    }