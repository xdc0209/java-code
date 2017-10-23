package com.xdc.basic.api.rmi.sbus.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

public class SerializationUtil
{
    /**
     * 使用java原始的序列化方法序列化（Apache最终使用的java接口）
     */
    public static byte[] serialize(Serializable obj)
    {
        return SerializationUtils.serialize(obj);
    }

    /**
     * 使用java原始的序列化方法反序列化（Apache最终使用的java接口）
     */
    public static Object deserialize(byte[] objectData)
    {
        return SerializationUtils.deserialize(objectData);
    }

    /**
     * 使用Hessian2的序列化方法序列化（效率比java的高）
     */
    public static byte[] serializeWithHessian2(Serializable obj)
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Hessian2Output ho = new Hessian2Output(os);
        try
        {
            ho.writeObject(obj);
            ho.flush();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (os != null)
                {
                    os.close();
                }
            }
            catch (IOException ex)
            {
                // ignore close exception
            }
        }

        return os.toByteArray();
    }

    /**
     * 使用Hessian2的序列化方法反序列化（效率比java的高）
     */
    public static Object deserializeWithHessian2(byte[] objectData)
    {
        if (objectData == null)
        {
            throw new IllegalArgumentException("The byte[] must not be null.");
        }

        ByteArrayInputStream is = new ByteArrayInputStream(objectData);
        Hessian2Input hi = new Hessian2Input(is);
        try
        {
            return hi.readObject();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (is != null)
                {
                    is.close();
                }
            }
            catch (IOException ex)
            {
                // ignore close exception
            }
        }
    }
}
