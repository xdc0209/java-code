/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011-2014 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package com.xdc.basic.api.restserver.jersey.application.resource.asyncdemo;

import java.text.MessageFormat;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import jersey.repackaged.com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * Example of a simple fire&forget point-to-point messaging resource.
 * 
 * This version of the messaging resource does not block when POSTing a new message.
 * 
 * @author Marek Potociar (marek.potociar at oracle.com)
 */
@Path("async/messaging/fireAndForget")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class FireAndForgetChatResource
{
    private static final ExecutorService              QUEUE_EXECUTOR             = Executors
                                                                                         .newCachedThreadPool(new ThreadFactoryBuilder()
                                                                                                 .setNameFormat(
                                                                                                         "fire&forget-chat-resource-executor-%d")
                                                                                                 .build());

    private static final BlockingQueue<AsyncResponse> suspended                  = new ArrayBlockingQueue<AsyncResponse>(
                                                                                         5);

    public static final String                        POST_NOTIFICATION_RESPONSE = "Message sent.";

    @GET
    public void pickUpMessage(@Suspended final AsyncResponse ar, @QueryParam("id") final String messageId)
            throws InterruptedException
    {
        System.out.println(MessageFormat.format("Received GET <{0}> with context {1} on thread {2}.", new Object[] {
                messageId, ar.toString(), Thread.currentThread().getName() }));
        QUEUE_EXECUTOR.submit(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    suspended.put(ar);
                    System.out.println(MessageFormat.format("GET <{0}> context {1} scheduled for resume.",
                            new Object[] { messageId, ar.toString() }));
                }
                catch (InterruptedException ex)
                {
                    System.out.println(MessageFormat.format(
                            "Waiting for a message pick-up interrupted. Cancelling context" + ar.toString(), ex));
                    ar.cancel(); // close the open connection
                }
            }
        });
    }

    @POST
    public String postMessage(final String message) throws InterruptedException
    {
        System.out.println(MessageFormat.format("Received POSTed message <{0}> on thread {1}.", new Object[] { message,
                Thread.currentThread().getName() }));
        QUEUE_EXECUTOR.submit(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    final AsyncResponse suspendedResponse = suspended.take();
                    System.out.println(MessageFormat.format(
                            "Resuming GET context {0} with a message <{1}> on thread {2}.", new Object[] {
                                    suspendedResponse.toString(), message, Thread.currentThread().getName() }));
                    suspendedResponse.resume(message);
                }
                catch (InterruptedException ex)
                {
                    System.out.println(MessageFormat.format("Waiting for a sending a message <" + message
                            + "> has been interrupted.", ex));
                }
            }
        });

        return POST_NOTIFICATION_RESPONSE;
    }
}
