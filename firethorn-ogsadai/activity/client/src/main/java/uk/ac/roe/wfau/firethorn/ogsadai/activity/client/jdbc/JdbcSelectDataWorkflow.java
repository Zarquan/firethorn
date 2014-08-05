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
package uk.ac.roe.wfau.firethorn.ogsadai.activity.client.jdbc;

import java.net.MalformedURLException;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;
import uk.ac.roe.wfau.firethorn.ogsadai.activity.client.SimpleWorkflowResult;
import uk.ac.roe.wfau.firethorn.ogsadai.activity.client.WorkflowResult;
import uk.ac.roe.wfau.firethorn.ogsadai.activity.client.data.DelaysClient;
import uk.ac.roe.wfau.firethorn.ogsadai.activity.client.data.LimitsClient;
import uk.ac.roe.wfau.firethorn.ogsadai.activity.client.jdbc.JdbcCreateResourceWorkflow.Result;
import uk.ac.roe.wfau.firethorn.ogsadai.activity.client.ogsa.OgsaServiceClient;
import uk.org.ogsadai.client.toolkit.PipelineWorkflow;
import uk.org.ogsadai.client.toolkit.RequestExecutionType;
import uk.org.ogsadai.client.toolkit.RequestResource;
import uk.org.ogsadai.client.toolkit.activities.delivery.DeliverToRequestStatus;
import uk.org.ogsadai.client.toolkit.activities.sql.SQLQuery;
import uk.org.ogsadai.client.toolkit.exception.ClientException;
import uk.org.ogsadai.client.toolkit.exception.RequestException;
import uk.org.ogsadai.client.toolkit.exception.ResourceUnknownException;
import uk.org.ogsadai.resource.ResourceID;

/**
 * Workflow for the JdbcSelectData Activity.
 *
 */
@Slf4j
public class JdbcSelectDataWorkflow
    {
    /**
     * Public interface for the workflow parameters.
     *
     */
    public interface Param
        {
        /**
         * The {@link JdbcCreateTable} params.
         *
         */
        public JdbcCreateTableClient.Param create();
        
        /**
         * The {@link JdbcSelectData} params.
         *
         */
        public JdbcSelectDataClient.Param select();

        /**
         * The {@link JdbcInsertData} params.
         *
         */
        public JdbcInsertDataClient.Param insert();
        
        /**
         * The {@link DelaysActivity} params.
         *
         */
        public DelaysClient.Param  delays();

        /**
         * The {@link LimitsActivity} params.
         *
         */
        public LimitsClient.Param limits();

        }

    /**
     * Workflow results.
     * 
     */
    public static class Result
    extends SimpleWorkflowResult
        {
        /**
         * Public constructor.
         * 
         */
        public Result(final RequestResource request)
            {
            super(
                request
                );
            }

        /**
         * Public constructor.
         * 
         */
        public Result(final Throwable cause)
            {
            super(
                cause
                );
            }

        /**
         * Public constructor.
         * 
         */
        public Result(final String message, final Throwable cause)
            {
            super(
                message,
                cause
                );
            }
        }

    /**
     * Public constructor.
     * @param endpoint The OGSA-DAI service endpoint URL.
     * @throws MalformedURLException 
     *
     */
    public JdbcSelectDataWorkflow(final String endpoint)
    throws MalformedURLException
        {
        this(
            new OgsaServiceClient(
                new URL(
                    endpoint
                    )
                )
            );
        }

    /**
     * Public constructor.
     * @param endpoint The OGSA-DAI service endpoint URL.
     *
     */
    public JdbcSelectDataWorkflow(final URL endpoint)
        {
        this(
            new OgsaServiceClient(
                endpoint
                )
            );
        }

    /**
     * Public constructor.
     * @param service Our {@link OgsaServiceClient} service client.
     *
     */
    public JdbcSelectDataWorkflow(final OgsaServiceClient service)
        {
        this.service = service ;
        }

    /**
     * Our {@link OgsaServiceClient} service client.
     * 
     */
    private OgsaServiceClient service ;  

    /**
     * Execute our workflow.
     * @param param The workflow params.
     * @return A workflow {@link Result} containing the results.
     * 
     */
    public Result execute(final ResourceID source, final ResourceID target, final Param param)
        {
        final JdbcSelectDataClient select = new JdbcSelectDataClient(
            source,
            param.select()
            );

        final DelaysClient delay = new DelaysClient(
            select.results(),
            param.delays()
            );

        final LimitsClient limits = new LimitsClient(
            delay.output(),
            param.limits()
            );

        final JdbcCreateTableClient create = new JdbcCreateTableClient(
            limits.output(),
            target,
            param.create()
            );
        
        final JdbcInsertDataClient insert = new JdbcInsertDataClient(
            create.results(),
            target,
            param.insert()
            );
        
        final DeliverToRequestStatus delivery = new DeliverToRequestStatus();
        delivery.connectInput(
            insert.results()
            );

        final PipelineWorkflow workflow = new PipelineWorkflow();
        workflow.add(
            select
            );
        workflow.add(
            delay
            );
        workflow.add(
            limits
            );
        workflow.add(
            create
            );
        workflow.add(
            insert
            );
        workflow.add(
            delivery
            );
        
        try {
            return new Result(
                service.drer().execute(
                    workflow,
                    RequestExecutionType.SYNCHRONOUS
                    )
                );
            }
        catch (ResourceUnknownException ouch)
            {
            log.debug("Exception processing query [{}][{}]", ouch.getClass().getName(), ouch.getMessage());
            return new Result(
                ouch
                );
            }
        catch (RequestException ouch)
            {
            log.debug("Exception processing query [{}][{}]", ouch.getClass().getName(), ouch.getMessage());
            return new Result(
                ouch
                );
            }
        catch (ClientException ouch)
            {
            log.debug("Exception processing query [{}][{}]", ouch.getClass().getName(), ouch.getMessage());
            return new Result(
                ouch
                );
            }
        }
    }
