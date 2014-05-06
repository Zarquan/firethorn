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
package uk.ac.roe.wfau.firethorn.adql.query.atlas;

import org.junit.Test;

import uk.ac.roe.wfau.firethorn.adql.query.AdqlQuery.Syntax.Level;
import uk.ac.roe.wfau.firethorn.adql.query.AdqlQuery.Syntax.State;
import uk.ac.roe.wfau.firethorn.meta.adql.AdqlColumn;

/**
 *
 *
 */
public class ArithmeticExpressionTestCase
    extends AtlasQueryTestBase
    {

    @Test
    public void test010S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    geNum + reNum" +
            " FROM" +
            "    atlasMergeLog",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasmergelog.genum + {ATLAS_VERSION}.dbo.atlasmergelog.renum AS SUM" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasmergelog",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.BYTE, 0)
                }
            );
        }

    @Test
    public void test011S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    (geNum + reNum)" +
            " FROM" +
            "    atlasMergeLog",

            " SELECT TOP 10" +
            "    ({ATLAS_VERSION}.dbo.atlasmergelog.genum + {ATLAS_VERSION}.dbo.atlasmergelog.renum) AS SUM" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasmergelog",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.BYTE, 0)
                }
            );
        }

    @Test
    public void test012S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    geNum + reNum AS mysum" +
            " FROM" +
            "    atlasMergeLog",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasmergelog.genum + {ATLAS_VERSION}.dbo.atlasmergelog.renum AS mysum" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasmergelog",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.BYTE, 0)
                }
            );
        }

    @Test
    public void test013S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    (geNum + reNum) AS mysum" +
            " FROM" +
            "    atlasMergeLog",

            " SELECT TOP 10" +
            "    ({ATLAS_VERSION}.dbo.atlasmergelog.genum + {ATLAS_VERSION}.dbo.atlasmergelog.renum) AS mysum" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasmergelog",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.BYTE, 0)
                }
            );
        }

    @Test
    public void test014S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    geNum + reNum + 1" +
            " FROM" +
            "    atlasMergeLog",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasmergelog.genum + {ATLAS_VERSION}.dbo.atlasmergelog.renum + 1 AS SUM" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasmergelog",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.INTEGER, 0)
                }
            );
        }

    @Test
    public void test015S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    geNum + reNum + 1 AS mysum" +
            " FROM" +
            "    atlasMergeLog",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasmergelog.genum + {ATLAS_VERSION}.dbo.atlasmergelog.renum + 1 AS mysum" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasmergelog",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.INTEGER, 0)
                }
            );
        }

    @Test
    public void test016S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    geNum + reNum + 1.0" +
            " FROM" +
            "    atlasMergeLog",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasmergelog.genum + {ATLAS_VERSION}.dbo.atlasmergelog.renum + 1.0 AS SUM" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasmergelog",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.FLOAT, 0)
                }
            );
        }

    @Test
    public void test017S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    geNum + reNum + 1.0 AS mysum" +
            " FROM" +
            "    atlasMergeLog",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasmergelog.genum + {ATLAS_VERSION}.dbo.atlasmergelog.renum + 1.0 AS mysum" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasmergelog",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.FLOAT, 0)
                }
            );
        }

    @Test
    public void test018S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    geNum + reNum + 0x01" +
            " FROM" +
            "    atlasMergeLog",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasmergelog.genum + {ATLAS_VERSION}.dbo.atlasmergelog.renum + 0x01 AS SUM" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasmergelog",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.INTEGER, 0)
                }
            );
        }

    @Test
    public void test019S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    geNum + reNum + 0x01 AS mysum" +
            " FROM" +
            "    atlasMergeLog",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasmergelog.genum + {ATLAS_VERSION}.dbo.atlasmergelog.renum + 0x01 AS mysum" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasmergelog",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.INTEGER, 0)
                }
            );
        }

// --------------

    @Test
    public void test020S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    extNum + class" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.extnum + {ATLAS_VERSION}.dbo.atlasdetection.class AS SUM" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.SHORT, 0)
                }
            );
        }

    @Test
    public void test021S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    (extNum + class)" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    ({ATLAS_VERSION}.dbo.atlasdetection.extnum + {ATLAS_VERSION}.dbo.atlasdetection.class) AS SUM" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.SHORT, 0)
                }
            );
        }

    @Test
    public void test022S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    extNum + class AS mysum" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.extnum + {ATLAS_VERSION}.dbo.atlasdetection.class AS mysum" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.SHORT, 0)
                }
            );
        }

    @Test
    public void test023S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    (extNum + class) AS mysum" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    ({ATLAS_VERSION}.dbo.atlasdetection.extnum + {ATLAS_VERSION}.dbo.atlasdetection.class) AS mysum" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.SHORT, 0)
                }
            );
        }

    @Test
    public void test024S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    extNum + class + 1" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.extnum + {ATLAS_VERSION}.dbo.atlasdetection.class + 1 AS SUM" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.INTEGER, 0)
                }
            );
        }

    @Test
    public void test025S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    extNum + class + 1 AS mysum" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.extnum + {ATLAS_VERSION}.dbo.atlasdetection.class + 1 AS mysum" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.INTEGER, 0)
                }
            );
        }

    @Test
    public void test026S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    extNum + class + 1.0" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.extnum + {ATLAS_VERSION}.dbo.atlasdetection.class + 1.0 AS SUM" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.FLOAT, 0)
                }
            );
        }

    @Test
    public void test027S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    extNum + class + 1.0 AS mysum" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.extnum + {ATLAS_VERSION}.dbo.atlasdetection.class + 1.0 AS mysum" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.FLOAT, 0)
                }
            );
        }

    @Test
    public void test028S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    extNum + class + 0x01" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.extnum + {ATLAS_VERSION}.dbo.atlasdetection.class + 0x01 AS SUM" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.INTEGER, 0)
                }
            );
        }

    @Test
    public void test029S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    extNum + class + 0x01 AS mysum" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.extnum + {ATLAS_VERSION}.dbo.atlasdetection.class + 0x01 AS mysum" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.INTEGER, 0)
                }
            );
        }

    // --------------

    @Test
    public void test030S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    aProf2 + aProf3" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.aprof2 + {ATLAS_VERSION}.dbo.atlasdetection.aprof3 AS SUM" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.INTEGER, 0)
                }
            );
        }

    @Test
    public void test031S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    (aProf2 + aProf3)" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    ({ATLAS_VERSION}.dbo.atlasdetection.aprof2 + {ATLAS_VERSION}.dbo.atlasdetection.aprof3) AS SUM" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.INTEGER, 0)
                }
            );
        }

    @Test
    public void test032S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    aProf2 + aProf3 AS mysum" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.aprof2 + {ATLAS_VERSION}.dbo.atlasdetection.aprof3 AS mysum" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.INTEGER, 0)
                }
            );
        }

    @Test
    public void test033S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    (aProf2 + aProf3) AS mysum" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    ({ATLAS_VERSION}.dbo.atlasdetection.aprof2 + {ATLAS_VERSION}.dbo.atlasdetection.aprof3) AS mysum" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.INTEGER, 0)
                }
            );
        }

    @Test
    public void test034S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    aProf2 + aProf3 + 1" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.aprof2 + {ATLAS_VERSION}.dbo.atlasdetection.aprof3 + 1 AS SUM" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.INTEGER, 0)
                }
            );
        }

    @Test
    public void test035S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    aProf2 + aProf3 + 1 AS mysum" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.aprof2 + {ATLAS_VERSION}.dbo.atlasdetection.aprof3 + 1 AS mysum" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.INTEGER, 0)
                }
            );
        }

    @Test
    public void test036S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    aProf2 + aProf3 + 1.0" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.aprof2 + {ATLAS_VERSION}.dbo.atlasdetection.aprof3 + 1.0 AS SUM" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.FLOAT, 0)
                }
            );
        }

    @Test
    public void test037S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    aProf2 + aProf3 + 1.0 AS mysum" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.aprof2 + {ATLAS_VERSION}.dbo.atlasdetection.aprof3 + 1.0 AS mysum" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.FLOAT, 0)
                }
            );
        }

    @Test
    public void test038S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    aProf2 + aProf3 + 0x01" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.aprof2 + {ATLAS_VERSION}.dbo.atlasdetection.aprof3 + 0x01 AS SUM" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.INTEGER, 0)
                }
            );
        }

    @Test
    public void test039S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    aProf2 + aProf3 + 0x01 AS mysum" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.aprof2 + {ATLAS_VERSION}.dbo.atlasdetection.aprof3 + 0x01 AS mysum" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.INTEGER, 0)
                }
            );
        }

// --------------

    @Test
    public void test040S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    objID + multiframeID" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.objID  + {ATLAS_VERSION}.dbo.atlasdetection.multiframeID AS SUM" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.LONG, 0)
                }
            );
        }

    @Test
    public void test041S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    (objID + multiframeID)" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    ({ATLAS_VERSION}.dbo.atlasdetection.objID  + {ATLAS_VERSION}.dbo.atlasdetection.multiframeID) AS SUM" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.LONG, 0)
                }
            );
        }

    @Test
    public void test042S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    objID + multiframeID AS mysum" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.objID  + {ATLAS_VERSION}.dbo.atlasdetection.multiframeID AS mysum" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.LONG, 0)
                }
            );
        }

    @Test
    public void test043S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    (objID + multiframeID) AS mysum" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    ({ATLAS_VERSION}.dbo.atlasdetection.objID  + {ATLAS_VERSION}.dbo.atlasdetection.multiframeID) AS mysum" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.LONG, 0)
                }
            );
        }

    @Test
    public void test044S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    objID + multiframeID + 1" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.objID  + {ATLAS_VERSION}.dbo.atlasdetection.multiframeID + 1 AS SUM" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.LONG, 0)
                }
            );
        }

    @Test
    public void test045S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    objID + multiframeID + 1 AS mysum" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.objID  + {ATLAS_VERSION}.dbo.atlasdetection.multiframeID + 1 AS mysum" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.LONG, 0)
                }
            );
        }

    @Test
    public void test046S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    objID + multiframeID + 1.0" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.objID  + {ATLAS_VERSION}.dbo.atlasdetection.multiframeID + 1.0 AS SUM" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.FLOAT, 0)
                }
            );
        }

    @Test
    public void test047S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    objID + multiframeID + 1.0 AS mysum" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.objID  + {ATLAS_VERSION}.dbo.atlasdetection.multiframeID + 1.0 AS mysum" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.FLOAT, 0)
                }
            );
        }

    @Test
    public void test048S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    objID + multiframeID + 0x01" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.objID  + {ATLAS_VERSION}.dbo.atlasdetection.multiframeID + 0x01 AS SUM" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.LONG, 0)
                }
            );
        }

    @Test
    public void test049S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    objID + multiframeID + 0x01 AS mysum" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.objID  + {ATLAS_VERSION}.dbo.atlasdetection.multiframeID + 0x01 AS mysum" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.LONG, 0)
                }
            );
        }

// --------------

    @Test
    public void test050S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    isoMag + isoFlux" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.isoMag + {ATLAS_VERSION}.dbo.atlasdetection.isoFlux AS SUM" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.FLOAT, 0)
                }
            );
        }

    @Test
    public void test051S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    (isoMag + isoFlux)" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    ({ATLAS_VERSION}.dbo.atlasdetection.isoMag + {ATLAS_VERSION}.dbo.atlasdetection.isoFlux) AS SUM" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.FLOAT, 0)
                }
            );
        }

    @Test
    public void test052S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    isoMag + isoFlux AS mysum" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.isoMag + {ATLAS_VERSION}.dbo.atlasdetection.isoFlux AS mysum" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.FLOAT, 0)
                }
            );
        }

    @Test
    public void test054S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    isoMag + isoFlux + 1" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.isoMag + {ATLAS_VERSION}.dbo.atlasdetection.isoFlux + 1 AS SUM" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.FLOAT, 0)
                }
            );
        }

    @Test
    public void test055S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    isoMag + isoFlux + 1 AS mysum" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.isoMag + {ATLAS_VERSION}.dbo.atlasdetection.isoFlux + 1 AS mysum" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.FLOAT, 0)
                }
            );
        }

    @Test
    public void test056S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    isoMag + isoFlux + 1.0" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.isoMag + {ATLAS_VERSION}.dbo.atlasdetection.isoFlux + 1.0 AS SUM" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.FLOAT, 0)
                }
            );
        }

    @Test
    public void test057S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    isoMag + isoFlux + 1.0 AS mysum" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.isoMag + {ATLAS_VERSION}.dbo.atlasdetection.isoFlux + 1.0 AS mysum" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.FLOAT, 0)
                }
            );
        }

    @Test
    public void test058S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    isoMag + isoFlux + 1.0" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.isoMag + {ATLAS_VERSION}.dbo.atlasdetection.isoFlux + 1.0 AS SUM" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.FLOAT, 0)
                }
            );
        }

    @Test
    public void test059S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    isoMag + isoFlux + 1.0 AS mysum" +
            " FROM" +
            "    atlasDetection",

            " SELECT TOP 10" +
            "    {ATLAS_VERSION}.dbo.atlasdetection.isoMag + {ATLAS_VERSION}.dbo.atlasdetection.isoFlux + 1.0 AS mysum" +
            " FROM" +
            "    {ATLAS_VERSION}.dbo.atlasdetection",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.FLOAT, 0)
                }
            );
        }

// --------------

    @Test
    public void test060S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    ra + dec" +
            " FROM" +
            "    twomass_psc",

            " SELECT TOP 10" +
            "    TWOMASS.dbo.twomass_psc.ra + TWOMASS.dbo.twomass_psc.dec AS SUM" +
            " FROM" +
            "    TWOMASS.dbo.twomass_psc",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.DOUBLE, 0)
                }
            );
        }

    @Test
    public void test061S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    (ra + dec)" +
            " FROM" +
            "    twomass_psc",

            " SELECT TOP 10" +
            "    (TWOMASS.dbo.twomass_psc.ra + TWOMASS.dbo.twomass_psc.dec) AS SUM" +
            " FROM" +
            "    TWOMASS.dbo.twomass_psc",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.DOUBLE, 0)
                }
            );
        }

    @Test
    public void test062S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    ra + dec AS mysum" +
            " FROM" +
            "    twomass_psc",

            " SELECT TOP 10" +
            "    TWOMASS.dbo.twomass_psc.ra + TWOMASS.dbo.twomass_psc.dec AS mysum" +
            " FROM" +
            "    TWOMASS.dbo.twomass_psc",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.DOUBLE, 0)
                }
            );
        }

    @Test
    public void test063S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    (ra + dec) AS mysum" +
            " FROM" +
            "    twomass_psc",

            " SELECT TOP 10" +
            "    (TWOMASS.dbo.twomass_psc.ra + TWOMASS.dbo.twomass_psc.dec) AS mysum" +
            " FROM" +
            "    TWOMASS.dbo.twomass_psc",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.DOUBLE, 0)
                }
            );
        }

    @Test
    public void test064S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    ra + dec + 1" +
            " FROM" +
            "    twomass_psc",

            " SELECT TOP 10" +
            "    TWOMASS.dbo.twomass_psc.ra + TWOMASS.dbo.twomass_psc.dec + 1 AS SUM" +
            " FROM" +
            "    TWOMASS.dbo.twomass_psc",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.DOUBLE, 0)
                }
            );
        }

    @Test
    public void test065S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    ra + dec + 1 AS mysum" +
            " FROM" +
            "    twomass_psc",

            " SELECT TOP 10" +
            "    TWOMASS.dbo.twomass_psc.ra + TWOMASS.dbo.twomass_psc.dec + 1 AS mysum" +
            " FROM" +
            "    TWOMASS.dbo.twomass_psc",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.DOUBLE, 0)
                }
            );
        }

    @Test
    public void test066S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    ra + dec + 1.0" +
            " FROM" +
            "    twomass_psc",

            " SELECT TOP 10" +
            "    TWOMASS.dbo.twomass_psc.ra + TWOMASS.dbo.twomass_psc.dec + 1.0 AS SUM" +
            " FROM" +
            "    TWOMASS.dbo.twomass_psc",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.DOUBLE, 0)
                }
            );
        }

    @Test
    public void test067S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    ra + dec + 1.0 AS mysum" +
            " FROM" +
            "    twomass_psc",

            " SELECT TOP 10" +
            "    TWOMASS.dbo.twomass_psc.ra + TWOMASS.dbo.twomass_psc.dec + 1.0 AS mysum" +
            " FROM" +
            "    TWOMASS.dbo.twomass_psc",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.DOUBLE, 0)
                }
            );
        }

    @Test
    public void test068S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    ra + dec + 0x01" +
            " FROM" +
            "    twomass_psc",

            " SELECT TOP 10" +
            "    TWOMASS.dbo.twomass_psc.ra + TWOMASS.dbo.twomass_psc.dec + 0x01 AS SUM" +
            " FROM" +
            "    TWOMASS.dbo.twomass_psc",

            new ExpectedField[] {
                new ExpectedField("SUM", AdqlColumn.Type.DOUBLE, 0)
                }
            );
        }

    @Test
    public void test069S()
        {
        validate(
            Level.STRICT,
            State.VALID,

            " SELECT TOP 10" +
            "    ra + dec + 0x01 AS mysum" +
            " FROM" +
            "    twomass_psc",

            " SELECT TOP 10" +
            "    TWOMASS.dbo.twomass_psc.ra + TWOMASS.dbo.twomass_psc.dec + 0x01 AS mysum" +
            " FROM" +
            "    TWOMASS.dbo.twomass_psc",

            new ExpectedField[] {
                new ExpectedField("mysum", AdqlColumn.Type.DOUBLE, 0)
                }
            );
        }
    }
