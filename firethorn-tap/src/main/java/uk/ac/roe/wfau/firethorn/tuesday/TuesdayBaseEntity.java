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
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;

import uk.ac.roe.wfau.firethorn.common.entity.AbstractEntity;

/**
 *
 *
 */
@MappedSuperclass
@Access(
    AccessType.FIELD
    )
public class TuesdayBaseEntity
extends AbstractEntity
    implements TuesdayBase
    {
    //protected static final String DB_NAME_COL = "tuename";
    protected static final String DB_TEXT_COL = "text";
    protected static final String DB_BASE_COL = "base";
    protected static final String DB_PARENT_COL = "parent";

    protected static final String DB_ALIAS_COL  = "alias";
    protected static final String DB_TYPE_COL = "datatype" ;
    protected static final String DB_SIZE_COL = "datasize" ;
    protected static final String DB_UCD_COL  = "ucd"  ;

    protected static final String DB_NAME_IDX        = "IndexByName";
    protected static final String DB_PARENT_IDX      = "IndexByParent";
    protected static final String DB_PARENT_NAME_IDX = "IndexByParentAndName";

    protected TuesdayBaseEntity()
        {
        }

    protected TuesdayBaseEntity(String name)
        {
        super(name);
        }

    /*
    @Basic(fetch = FetchType.EAGER)
    @Column(
        name = DB_NAME_COL,
        unique = false,
        nullable = true,
        updatable = true
        )
    private String tuename ;
    @Override
    public String name()
        {
        return this.tuename;
        }
    @Override
    public void name(String name)
        {
        this.tuename = name;
        }
     */
    
    @Basic(fetch = FetchType.LAZY)
    @Column(
        name = DB_TEXT_COL,
        unique = false,
        nullable = true,
        updatable = true
        )
    private String text ;
    @Override
    public String text()
        {
        return this.text;
        }
    @Override
    public void text(String text)
        {
        this.text = text;
        }

    @Override
    public String link()
        {
        // TODO Auto-generated method stub
        return null;
        }
    }
