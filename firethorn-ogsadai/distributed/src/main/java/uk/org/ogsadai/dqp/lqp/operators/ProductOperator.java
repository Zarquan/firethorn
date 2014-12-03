// Copyright (c) The University of Edinburgh, 2008.
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

package uk.org.ogsadai.dqp.lqp.operators;

import uk.org.ogsadai.dqp.lqp.Operator;
import uk.org.ogsadai.dqp.lqp.OperatorID;
import uk.org.ogsadai.dqp.lqp.OperatorVisitor;
import uk.org.ogsadai.dqp.lqp.exceptions.LQPException;

/**
 * Operator PRODUCT.
 * 
 * @author The OGSA-DAI Project Team.
 */
public class ProductOperator extends BinaryOperator
{
    /** Copyright notice. */
    private static final String COPYRIGHT_NOTICE = 
        "Copyright (c) The University of Edinburgh, 2008";

    /**
     * Create a disconnected PRODUCT operator.
     */
    public ProductOperator()
    {
        mID = OperatorID.PRODUCT;
    }

    /**
     * Create a connected PRODUCT operator.
     * 
     * @param leftChild
     *            left child operator
     * @param rightChild
     *            right child operator
     */
    public ProductOperator(Operator leftChild, Operator rightChild)
    {
        this();
        setChild(0, leftChild);
        setChild(1, rightChild);
    }

    /**
     * {@inheritDoc}
     */
    public void update() throws LQPException
    {
        super.update();

        mOperatorHeading = mLeftChildOperator.getHeading().createMerged(
            mRightChildOperator.getHeading());
        mOperatorHeading.invalidateKeys();
    }

    /**
     * {@inheritDoc}
     */
    public void accept(OperatorVisitor visitor)
    {
        visitor.visit(this);
    }

}
