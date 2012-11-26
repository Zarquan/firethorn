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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.NamedQueries;

/**
 *
 *
 */
@Entity()
@Access(
    AccessType.FIELD
    )
@Table(
    name = TuesdayBaseSchemaEntity.DB_TABLE_NAME,
    uniqueConstraints={
        @UniqueConstraint(
            name = TuesdayBaseSchemaEntity.DB_TABLE_NAME + TuesdayBaseEntity.DB_PARENT_NAME_IDX,
            columnNames = {
                TuesdayBaseEntity.DB_NAME_COL,
                TuesdayBaseEntity.DB_PARENT_COL,
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
public abstract class TuesdayBaseSchemaEntity
    extends TuesdayBaseEntity
    implements TuesdayBaseSchema
    {
    protected static final String DB_TABLE_NAME = "TuesdayBaseSchemaEntity";

    protected TuesdayBaseSchemaEntity()
        {
        super();
        }

    protected TuesdayBaseSchemaEntity(TuesdayBaseResource resource, String name)
        {
        super(name);
        this.resource = resource;
        }

    @ManyToOne(
        fetch = FetchType.EAGER,
        targetEntity = TuesdayBaseResourceEntity.class
        )
    @JoinColumn(
        name = DB_PARENT_COL,
        unique = false,
        nullable = false,
        updatable = false
        )
    private TuesdayBaseResource resource;
    @Override
    public TuesdayBaseResource resource()
        {
        return this.resource;
        }

    @Override
    public StringBuilder fullname()
        {
        return new StringBuilder(this.name());
        }
    }
