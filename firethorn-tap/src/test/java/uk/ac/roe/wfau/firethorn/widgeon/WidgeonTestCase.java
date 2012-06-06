/*
 *
 */
package uk.ac.roe.wfau.firethorn.widgeon ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URL;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional; 

import uk.ac.roe.wfau.firethorn.test.TestBase;

/**
 *
 */
public class WidgeonTestCase
extends TestBase
    {
    /**
     * Our debug logger.
     * 
     */
    private static Logger logger = LoggerFactory.getLogger(
        WidgeonTestCase.class
        );

    @Before
    public void before()
        {
        logger.debug("before()");
        super.before();
        }

    @After
    public void after()
        {
        logger.debug("after()");
        super.after();
        }

    //@Test
    public void simple()
        {
        Widgeon one = womble().widgeons().create(
            "albert",
            URI.create("ivo://org.astrogrid.test/0001")
            );
        assertNotNull(
            one
            );
        assertNotNull(
            one.name()
            );
        logger.debug("One [{}][{}]", one.ident(), one.name());

        Widgeon two = womble().widgeons().create(
            "albert",
            URI.create("ivo://org.astrogrid.test/0001")
            );
        assertNotNull(
            two
            );
        assertNotNull(
            two.name()
            );
        logger.debug("Two [{}][{}]", two.ident(), two.name());

        //womble().hibernate().statefull().session().flush();

        for (Widgeon widgeon : womble().widgeons().select())
            {
            logger.debug("Widgeon [{}]", widgeon);
            }

        }

    @Test
    public void nested()
        {
        nested(
            womble().widgeons().create(
                "widgeon-0001",
                URI.create("ivo://org.astrogrid.test/0001")
                )
            );
        nested(
            womble().widgeons().create(
                "widgeon-0002",
                URI.create("ivo://org.astrogrid.test/0002")
                )
            );

        for (Widgeon widgeon : womble().widgeons().select())
            {
            logger.debug("-------");
            logger.debug("Widgeon [{}]", widgeon);
            for (Widgeon.Schema schema : widgeon.schemas().select())
                {
                logger.debug("  Schema [{}]", schema);
                for (Widgeon.Schema.Catalog catalog : schema.catalogs().select())
                    {
                    logger.debug("  Catalog [{}]", catalog);
                    }
                }
            }
        }

    public void nested(Widgeon widgeon)
        {
        nested(
            widgeon.schemas().create(
                "schema-0001"
                )
            );
        nested(
            widgeon.schemas().create(
                "schema-0002"
                )
            );
        }

    public void nested(Widgeon.Schema schema)
        {
        schema.catalogs().create(
            "catalog-0001"
            );
        schema.catalogs().create(
            "catalog-0002"
            );
        }
    }

