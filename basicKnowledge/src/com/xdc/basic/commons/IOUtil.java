package com.xdc.basic.commons;

import java.io.Closeable;
import java.io.IOException;

public class IOUtil
{
    /**
     * @see org.apache.commons.io.IOUtils.closeQuietly(Closeable)
     */
    public static void closeQuietly(Closeable closeable)
    {
        try
        {
            if (closeable != null)
            {
                closeable.close();
            }
        }
        catch (IOException ioe)
        {
            // ignore
        }
    }
}
