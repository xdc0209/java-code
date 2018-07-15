package com.xdc.basic.tools.statemachine;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.api.jaxb.tool1.XMLConvertor;
import com.xdc.basic.tools.statemachine.xml.Event;
import com.xdc.basic.tools.statemachine.xml.Handler;
import com.xdc.basic.tools.statemachine.xml.Root;
import com.xdc.basic.tools.statemachine.xml.State;

public class StateMachineFrameWork
{
    /**
     * 根据输入xml描述创建一个状态机并返回
     * 
     * @param xml
     *            UTF8编码的XML内容（注意不是xml文件名）
     * @return 状态机
     * @throws SMException
     */
    public static StateMachine createStateMachine(String xml) throws SMException
    {
        final String XML_MODEL_MEMBER = Root.class.getPackage().getName();
        Root root = null;
        try
        {
            XMLConvertor xmlConvertor = new XMLConvertor(XML_MODEL_MEMBER);
            root = (Root) xmlConvertor.xmlString2Java(xml);
        }
        catch (JAXBException e)
        {
            throw new SMException(SMException.XML_ERROR, e.getMessage());
        }

        // 生成状态信息
        StateInfo stateInfo = generateStateInfo(root);

        // 判断目标状态是否存在
        doDestStateValidator(root);

        // 方法参数校验
        doFuncArgValidator(root, stateInfo);

        // 方法返回值校验
        doFuncRetValidator(root, stateInfo);

        StateMachine stateMachine = new StateMachine();
        stateMachine.setRoot(root);
        stateMachine.setStateInfo(stateInfo);
        return stateMachine;
    }

    private static void doFuncRetValidator(Root root, StateInfo stateInfo) throws SMException
    {
        // <function>函数的返回类型，框架应根据此类型及其期望返回值（由succval属性决定）判断<function>函数实际执行的返回值是否满足要求。可能取值：
        // 1、 int
        // 2、 boolean
        // 3、 java.lang.String
        // 4、 IRetValue
        // 5、 careless
        // 其他取值均导致状态机创建异常（错误码5）

        // 1、2、3看函数实际返回值是否等于succval属性取值（1、2转成String后与succval取值比较），相等认为满足条件。
        // 4、说明返回类型实现了IRetValue接口，此接口的isSucceed()方法返回true，认为满足条件，否则认为不满足条件；如果<function>返回null视为不满足条件。如果<function>返回值实际未实现IRetValue接口导致状态机创建异常（错误码5）
        // public interface IRetValue
        // { boolean isSucceed(); }
        // 5、表示不关心<function>函数返回值，只要<function>函数执行不抛异常就认为执行成功，应该迁移状态。
        // 但<rettype>为int、boolean、java.lang.String时，无此属性导致状态机创建异常（错误码5）

        List<State> stateList = root.getStates().getState();
        List<Event> eventList = root.getEvents().getEvent();
        for (State state : stateList)
        {
            List<Handler> handlerList = state.getHandlers().getHandler();
            for (Handler handler : handlerList)
            {
                Event event = StateMachineHelper.queryEventByName(eventList, handler.getName());
                Method method = StateMachineHelper.generateFunc(handler, event, stateInfo);
                if (method == null)
                {
                    continue;
                }
                Class<?> returnType = method.getReturnType();
                String rettype = handler.getRettype().getValue();
                String succval = handler.getRettype().getSuccval();
                if (StringUtils.equals("int", rettype))
                {
                    if (succval == null)
                    {
                        throw new SMException(SMException.ERROR_RETTYPE);
                    }
                    if (!int.class.isAssignableFrom(returnType))
                    {
                        throw new SMException(SMException.ERROR_RETTYPE);
                    }
                }
                else if (StringUtils.equals("boolean", rettype))
                {
                    if (succval == null)
                    {
                        throw new SMException(SMException.ERROR_RETTYPE);
                    }
                    if (!boolean.class.isAssignableFrom(returnType))
                    {
                        throw new SMException(SMException.ERROR_RETTYPE);
                    }
                }
                else if (StringUtils.equals("java.lang.String", rettype))
                {
                    if (succval == null)
                    {
                        throw new SMException(SMException.ERROR_RETTYPE);
                    }
                    if (!String.class.isAssignableFrom(returnType))
                    {
                        throw new SMException(SMException.ERROR_RETTYPE);
                    }
                }

                else if (StringUtils.equals("careless", rettype))
                {
                    // do nothing
                }
                else
                {
                    // 判断是否实现IRetValue接口。
                    Class<?> rettypeClazz = null;
                    try
                    {
                        rettypeClazz = Class.forName(rettype);
                    }
                    catch (ClassNotFoundException e)
                    {
                        throw new SMException(SMException.ERROR_RETTYPE);
                    }
                    if (!IRetValue.class.isAssignableFrom(rettypeClazz))
                    {
                        throw new SMException(SMException.ERROR_RETTYPE);
                    }
                    if (!rettypeClazz.isAssignableFrom(returnType))
                    {
                        throw new SMException(SMException.ERROR_RETTYPE);
                    }
                }
            }
        }
    }

    private static void doFuncArgValidator(Root root, StateInfo stateInfo) throws SMException
    {
        List<State> stateList = root.getStates().getState();
        List<Event> eventList = root.getEvents().getEvent();
        for (State state : stateList)
        {
            List<Handler> handlerList = state.getHandlers().getHandler();
            for (Handler handler : handlerList)
            {
                Event event = StateMachineHelper.queryEventByName(eventList, handler.getName());
                StateMachineHelper.generateFunc(handler, event, stateInfo);
            }
        }
    }

    private static StateInfo generateStateInfo(Root root) throws SMException
    {
        // 初始化初始状态
        State startState = getStartState(root);
        String stateName = startState.getName();

        // 初始化上下文
        Object context = null;
        String contextClassString = root.getContext();
        try
        {
            Class<?> contextClazz = Class.forName(contextClassString);
            context = contextClazz.newInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new SMException(SMException.LOAD_CLASS_ERROR);
        }
        StateInfo stateInfo = new StateInfo(stateName, context);
        return stateInfo;
    }

    private static void doDestStateValidator(Root root) throws SMException
    {
        List<State> stateList = root.getStates().getState();
        List<String> stateNameList = new ArrayList<String>();
        for (State state : stateList)
        {
            stateNameList.add(state.getName());
        }

        for (State state : stateList)
        {
            List<Handler> handlerList = state.getHandlers().getHandler();
            for (Handler handler : handlerList)
            {
                if (!stateNameList.contains(handler.getNxtstate()))
                {
                    throw new SMException(SMException.NO_SUCH_STATE);
                }
            }
        }
    }

    private static State getStartState(Root root) throws SMException
    {
        State startState = null;
        List<State> stateList = root.getStates().getState();
        BigInteger startStateFlag = BigInteger.valueOf(1);
        for (State state : stateList)
        {
            if (startStateFlag.equals(state.getStart()))
            {
                if (startState == null)
                {
                    startState = state;
                }
                else
                {
                    throw new SMException(SMException.TOO_MANY_START_STATE);
                }
            }
        }
        if (startState == null)
        {
            throw new SMException(SMException.NO_START_STATE);
        }
        return startState;
    }
}
