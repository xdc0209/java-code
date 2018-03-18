package com.xdc.basic.api.google.guava.base;

import java.io.IOException;
import java.sql.SQLException;

import com.google.common.base.Throwables;

/**
 * 参考：http://ifeve.com/google-guava-throwables/
 */
public class ThrowablesTest
{
    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws IOException, SQLException
    {
        try
        {
            someMethodThatCouldThrowAnything();
        }
        catch (IKnowWhatToDoWithThisException e)
        {
            handle(e);
        }
        catch (Throwable t)
        {
            Throwables.throwIfInstanceOf(t, IOException.class);
            Throwables.throwIfInstanceOf(t, SQLException.class);
            throw Throwables.propagate(t);
        }
    }

    private static void handle(IKnowWhatToDoWithThisException e)
    {
        // do something.
    }

    private static void someMethodThatCouldThrowAnything() throws IKnowWhatToDoWithThisException
    {
        // do something.
    }

    class IKnowWhatToDoWithThisException extends Exception
    {
        private static final long serialVersionUID = 6303001122319321413L;
    }
}
