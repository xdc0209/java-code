package com.xdc.basic.tools.ip.proxy.io.handler;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractProxyIoHandler extends IoHandlerAdapter
{
    private static final Charset CHARSET          = Charset.forName("iso8859-1");

    public static final String   OTHER_IO_SESSION = AbstractProxyIoHandler.class.getName() + ".OtherIoSession";

    private final static Logger  LOGGER           = LoggerFactory.getLogger(AbstractProxyIoHandler.class);

    @Override
    public void sessionCreated(IoSession session) throws Exception
    {
        session.suspendRead();
        session.suspendWrite();
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception
    {
        if (session.getAttribute(AbstractProxyIoHandler.OTHER_IO_SESSION) != null)
        {
            IoSession sess = (IoSession) session.getAttribute(AbstractProxyIoHandler.OTHER_IO_SESSION);
            sess.setAttribute(AbstractProxyIoHandler.OTHER_IO_SESSION, null);
            sess.close(false);
            session.setAttribute(AbstractProxyIoHandler.OTHER_IO_SESSION, null);
        }
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception
    {
        IoBuffer rb = (IoBuffer) message;
        IoBuffer wb = IoBuffer.allocate(rb.remaining());
        rb.mark();
        wb.put(rb);
        wb.flip();
        ((IoSession) session.getAttribute(AbstractProxyIoHandler.OTHER_IO_SESSION)).write(wb);
        rb.reset();
        AbstractProxyIoHandler.LOGGER.info(rb.getString(AbstractProxyIoHandler.CHARSET.newDecoder()));
    }
}
