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
package uk.ac.roe.wfau.firethorn.ogsadai.metadata;

import uk.org.ogsadai.dqp.lqp.Attribute;
import uk.org.ogsadai.dqp.lqp.cardinality.ArithmeticOperator;
import uk.org.ogsadai.dqp.lqp.cardinality.AttributeStatistics;
import uk.org.ogsadai.dqp.lqp.cardinality.HistogramBasedAttributeStatistics;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 *
 */
public class StatisticsServiceMock
    implements StatisticsService
    {
    private static Log log = LogFactory.getLog(StatisticsServiceMock.class);

    /*
    public class AttributeStatisticsMock
        implements AttributeStatistics 
        {
        private double nulls ;
        private double values ;
        private double rows ;
        
        public AttributeStatisticsMock(double nulls, double values, double rows)
            {
            this.nulls  = nulls;
            this.values = values;
            this.rows   = rows;
            }
        
        @Override
        public double getNumNulls()
            {
            return this.nulls;
            }

        @Override
        public double getNumRows()
            {
            return this.rows;
            }

        @Override
        public double getNumValues()
            {
            return this.values;
            }

        @Override
        public AttributeStatistics processEqualConstant(Object arg0)
            {
            // TODO Auto-generated method stub
            return null;
            }

        @Override
        public AttributeStatistics processInequalityConstant(ArithmeticOperator arg0, Object arg1)
            {
            // TODO Auto-generated method stub
            return null;
            }

        @Override
        public AttributeStatistics processNotEqualConstant(Object arg0)
            {
            // TODO Auto-generated method stub
            return null;
            }

        @Override
        public AttributeStatistics rescale(double arg0)
            {
            // TODO Auto-generated method stub
            return null;
            }
        }
    */

    @Override
    public AttributeStatistics getStatistics(Attribute attrib)
        {
        log.debug("getStatistics(Attribute) [" + attrib.getSource() + "][" + attrib.getName() + "]");
        return getStatistics(
            attrib.getSource(),
            attrib.getName()
            )
        }

    @Override
    public AttributeStatistics getStatistics(String table, String column)
        {
        log.debug("getStatistics(String, String) [" + table + "][" + column + "]");
        return new HistogramBasedAttributeStatistics(); 
        }
    }

