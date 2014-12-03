// Copyright (c) The University of Edinburgh, 2009.
//
// LICENCE-START
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// LICENCE-END


package uk.org.ogsadai.dqp.lqp.udf;

import uk.org.ogsadai.dqp.lqp.Heading;
import uk.org.ogsadai.dqp.lqp.exceptions.LQPException;

/**
 * The function interface that is used by the logical query plan builder.
 *
 * @author The OGSA-DAI Project Team.
 */
public interface LogicalFunction extends Function
{

    /**
     * Sets the input heading for the input at the specified index.
     * 
     * @param index
     *            index of the input
     * @param heading
     *            parameter heading
     */
    public void setInputHeading(int index, Heading heading);
    
    /**
     * Returns the heading of the output of this function.
     * 
     * @return output heading
     */
    public Heading getHeading();

    /**
     * Validates the function.
     * 
     * @throws LQPException
     *             if validation fails
     */
    public void validate() throws LQPException;
    
}
