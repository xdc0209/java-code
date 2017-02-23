//
// ========================================================================
// Copyright (c) 1995-2014 Mort Bay Consulting Pty. Ltd.
// ------------------------------------------------------------------------
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// and Apache License v2.0 which accompanies this distribution.
//
// The Eclipse Public License is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// The Apache License v2.0 is available at
// http://www.opensource.org/licenses/apache2.0.php
//
// You may elect to redistribute this code under either of these licenses.
// ========================================================================
//

package com.xdc.basic.api.jetty;

import org.eclipse.jetty.server.Server;

/**
 * The simplest possible Jetty server.
 * 
 * This runs an HTTP server on port 8080.
 * It is not a very useful server as it has no handlers, and thus returns a 404 error for every request.
 */
public class SimplestServer
{
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);
        server.start();
        server.dumpStdErr();
        server.join();
    }
}