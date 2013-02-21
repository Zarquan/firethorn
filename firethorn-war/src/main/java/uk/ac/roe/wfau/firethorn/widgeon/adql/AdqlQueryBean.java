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
package uk.ac.roe.wfau.firethorn.widgeon.adql;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import uk.ac.roe.wfau.firethorn.job.Job.Status;
import uk.ac.roe.wfau.firethorn.meta.base.BaseResource;
import uk.ac.roe.wfau.firethorn.adql.query.AdqlQuery;
import uk.ac.roe.wfau.firethorn.adql.query.AdqlQuery.Mode;
import uk.ac.roe.wfau.firethorn.webapp.control.AbstractEntityBeanImpl;
import uk.ac.roe.wfau.firethorn.webapp.control.AbstractEntityBeanIter;
import uk.ac.roe.wfau.firethorn.webapp.control.EntityBean;

/**
 * Bean wrapper for <code>AdqlSchema</code>.
 *
 */
public class AdqlQueryBean
extends AbstractEntityBeanImpl<AdqlQuery>
implements EntityBean<AdqlQuery>
    {
    public static class Iter
    extends AbstractEntityBeanIter<AdqlQuery>
        {
        /**
         * Public constructor.
         *
         */
        public Iter(final Iterable<AdqlQuery> iterable)
            {
            super(
                iterable
                );
            }
        @Override
        public EntityBean<AdqlQuery> bean(final AdqlQuery entity)
            {
            return new AdqlQueryBean(
                entity
                );
            }
        }

    /**
     * Public constructor.
     *
     */
    public AdqlQueryBean(final AdqlQuery entity)
        {
        super(
            AdqlQueryIdentFactory.TYPE_URI,
            entity
            );
        }

    public URI getResource()
        {
        try {
            return new URI(
                entity().resource().link()
                );
            }
        catch (final URISyntaxException ouch)
            {
            throw new RuntimeException(
                ouch
                );
            }
        }

    public String getInput()
        {
        return entity().input();
        }

    public Mode getMode()
        {
        return entity().mode();
        }

    public Status getStatus()
        {
        return entity().status();
        }

    public String getAdql()
        {
        return entity().adql();
        }

    public String getOsql()
        {
        return entity().osql();
        }

    public Iterable<URI> getTargets()
        {
        return new Iterable<URI>()
            {
            @Override
            public Iterator<URI> iterator()
                {
                return new Iterator<URI>()
                    {
                    Iterator<BaseResource<?>> iter = entity().targets().iterator();
                    @Override
                    public boolean hasNext()
                        {
                        return this.iter.hasNext();
                        }
                    @Override
                    public URI next()
                        {
                        try {
                            return new URI(
                                this.iter.next().link()
                                );
                            }
                        catch (final URISyntaxException ouch)
                            {
                            throw new RuntimeException(
                                ouch
                                );
                            }
                        }
                    @Override
                    public void remove()
                        {
                        }
                    };
                }
            };
        }
    }
