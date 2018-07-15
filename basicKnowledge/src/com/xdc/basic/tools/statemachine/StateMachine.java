package com.xdc.basic.tools.statemachine;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.tools.statemachine.xml.Event;
import com.xdc.basic.tools.statemachine.xml.Handler;
import com.xdc.basic.tools.statemachine.xml.Root;
import com.xdc.basic.tools.statemachine.xml.State;

public class StateMachine
{
    private Root      root;
    private StateInfo stateInfo;

    public Root getRoot()
    {
        return root;
    }

    public void setRoot(Root root)
    {
        this.root = root;
    }

    public StateInfo getStateInfo()
    {
        return stateInfo;
    }

    public void setStateInfo(StateInfo stateInfo)
    {
        this.stateInfo = stateInfo;
    }

    /**
     * 静态查询状态机信息，假设状态机当前处于stateName指定的状态（与当前状态机的实际状态无关），返回收到一个事件后，状态机可能处于的状态列表
     * 
     * @param stateName
     *            假设状态机当前所处状态的名称
     * @return 状态列表
     * @throws SMException
     */
    public List<String> queryNextState(String stateName) throws SMException
    {
        List<String> nextStateList = new ArrayList<String>();
        List<State> stateList = root.getStates().getState();
        State state = StateMachineHelper.queryStateByName(stateList, stateName);
        List<Handler> handlers = state.getHandlers().getHandler();
        for (Handler handler : handlers)
        {
            nextStateList.add(handler.getNxtstate());
        }
        return nextStateList;
    }

    /**
     * 查询状态机当前所处状态.
     * 
     * @return 状态信息
     */
    public StateInfo queryState()
    {
        return this.stateInfo;
    }

    /**
     * 状态机处理事件.
     * 
     * @param event
     *            事件对象
     * @return 处理完事件后状态机所处状态的名称
     * @throws SMException
     */
    public String dealEvent(Event event) throws SMException
    {
        // 当前状态
        String stateName = stateInfo.getStateName();
        List<State> stateList = root.getStates().getState();
        State state = StateMachineHelper.queryStateByName(stateList, stateName);
        Handler handler = StateMachineHelper.queryHandlerByName(state.getHandlers().getHandler(), event.getName());

        // 某状态收到未描述的事件，保持原状态，不做任何操作。
        if (handler == null)
        {
            return stateName;
        }

        if (exeFunc(handler, event))
        {
            String nextStateName = handler.getNxtstate();
            stateInfo.setStateName(nextStateName);
            return nextStateName;
        }
        else
        {
            return stateName;
        }
    }

    private boolean exeFunc(Handler handler, Event event) throws SMException
    {
        Event xmlEvent = StateMachineHelper.queryEventByName(root.getEvents().getEvent(), event.getName());
        Method method = StateMachineHelper.generateFunc(handler, xmlEvent, stateInfo);
        if (method == null)
        {
            return true;
        }
        try
        {
            // 由于调用的是静态方法，对象传空就可以了，不用生成个对象。
            Object restult = method.invoke(null, stateInfo.getContext(), event.getArg());
            String rettype = handler.getRettype().getValue();
            String succval = handler.getRettype().getSuccval();
            if (StringUtils.equals("int", rettype))
            {
                return StringUtils.equals(restult.toString(), succval);
            }
            else if (StringUtils.equals("boolean", rettype))
            {
                return StringUtils.equals(restult.toString(), succval);
            }
            else if (StringUtils.equals("java.lang.String", rettype))
            {
                return StringUtils.equals(restult.toString(), succval);
            }
            else if (StringUtils.equals("careless", rettype))
            {
                return true;
            }
            else
            {
                // 判断是否实现IRetValue接口。
                if (restult instanceof IRetValue)
                {
                    IRetValue r = (IRetValue) restult;
                    return r.isSucceed();
                }
                else
                {
                    return false;
                }
            }
        }
        catch (Throwable e)
        {
            return false;
        }
    }
}
