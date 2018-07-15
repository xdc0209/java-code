package com.xdc.basic.tools.statemachine;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.tools.statemachine.xml.Event;
import com.xdc.basic.tools.statemachine.xml.Handler;
import com.xdc.basic.tools.statemachine.xml.State;

public class StateMachineHelper
{
    public static State queryStateByName(List<State> stateList, String stateName) throws SMException
    {
        for (State state : stateList)
        {
            if (StringUtils.equals(state.getName(), stateName))
            {
                return state;
            }
        }
        throw new SMException(SMException.NO_SUCH_STATE);
    }

    public static Handler queryHandlerByName(List<Handler> handlerlList, String handlerName)
    {
        for (Handler handler : handlerlList)
        {
            if (StringUtils.equals(handler.getName(), handlerName))
            {
                return handler;
            }
        }
        return null;
    }

    public static Event queryEventByName(List<Event> eventList, String eventName) throws SMException
    {
        for (Event event : eventList)
        {
            if (StringUtils.equals(event.getName(), eventName))
            {
                return event;
            }
        }
        throw new SMException(SMException.NO_SUCH_EVENT);
    }

    public static Method generateFunc(Handler handler, Event event, StateInfo stateInfo) throws SMException
    {
        // 方法必须是公有静态方法，且第一个参数为root/context指定的类，第二个参数为root/events/event/arg指定的类。
        // 不满足要求将导致状态机创建异常,其错误码为6,
        // 方法所在类不存在为错误码7

        String fullFncString = handler.getFunction();
        if (StringUtils.isEmpty(fullFncString))
        {
            return null;
        }

        String funcClassString = StringUtils.substringBeforeLast(fullFncString, ".");
        String funcString = StringUtils.substringAfterLast(fullFncString, ".");
        Class<?> funcClazz = null;
        try
        {
            funcClazz = Class.forName(funcClassString);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            throw new SMException(SMException.LOAD_CLASS_ERROR);
        }

        Class<?> argClazz = null;
        try
        {
            argClazz = Class.forName(event.getArgtype());
        }
        catch (ClassNotFoundException e)
        {
            throw new SMException(SMException.ERROR_FUNCTION);
        }

        Method method = null;
        try
        {
            method = funcClazz.getMethod(funcString, new Class[] { stateInfo.getContext().getClass(), argClazz });
        }
        catch (Exception e)
        {
            throw new SMException(SMException.ERROR_FUNCTION);
        }

        int modifiers = method.getModifiers();
        if (!Modifier.isStatic(modifiers))
        {
            throw new SMException(SMException.ERROR_FUNCTION);
        }
        return method;
    }
}
