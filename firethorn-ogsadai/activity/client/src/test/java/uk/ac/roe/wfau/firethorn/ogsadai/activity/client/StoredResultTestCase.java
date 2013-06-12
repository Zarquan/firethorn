/**
 *
 */
package uk.ac.roe.wfau.firethorn.ogsadai.activity.client;

import java.net.URL;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 *
 */
public class StoredResultTestCase
    {
    @Value("${firethon.ogsadai.dqp}")
    private String dqp ;

    @Value("${firethon.ogsadai.store}")
    private String store ;

    @Value("${firethon.ogsadai.endpoint}")
    private String endpoint ;

    /**
     * The test class load time.
     *
     */
    protected static long start = System.currentTimeMillis() ;

    /**
     * A shared counter for unique names.
     *
     */
    protected static long uniques = 0 ;

    /**
     * Glue for generated names.
     *
     */
    public static final String UNIQUE_NAME_GLUE = "_" ;

    /**
     * Generate a unique string.
     *
     */
    public String unique()
        {
        final StringBuilder builder = new StringBuilder();
        builder.append(
            String.valueOf(
                start
                )
            );
        builder.append(
            UNIQUE_NAME_GLUE
            );
        builder.append(
            String.valueOf(
                uniques++
                )
            );
        return builder.toString();
        }

    /**
     * Generate a unique string.
     *
     */
    public String unique(final String prefix)
        {
        final StringBuilder builder = new StringBuilder();
        builder.append(
            prefix
            );
        builder.append(
            UNIQUE_NAME_GLUE
            );
        builder.append(
            unique()
            );
        return builder.toString();
        }

    /**
     * Single catalog, TWOMASS.
     *
     */
    @Test
    public void test001()
    throws Exception
        {
        final StoredResultPipeline pipeline = new StoredResultPipeline(
            new URL(
                endpoint
                )
            );
        pipeline.execute(
            "twomass",
            "userdata",
            unique("table"),
            " SELECT" +
            "    twomass.ra," +
            "    twomass.dec" +
            " FROM" +
            "    twomass_psc AS twomass" +
            " WHERE" +
            "    twomass.ra  BETWEEN '55.0' AND '55.9'" +
            " AND" +
            "    twomass.dec BETWEEN '20.0' AND '22.9'"
            );
        }
    }
