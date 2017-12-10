package com.xdc.basic.tools.alarm;

import java.util.Calendar;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.xdc.basic.tools.alarm.repeat.Once;
import com.xdc.basic.tools.alarm.repeat.Repeat;

public class Alarm
{
    /**
     * 内部id，识别唯一性，用于删除、更新等场景
     */
    private String  id      = UUID.randomUUID().toString();

    /**
     * 响铃的小时
     */
    private int     hour    = 0;

    /**
     * 响铃的分钟
     */
    private int     minute  = 0;

    /**
     * 是否激活
     */
    private boolean enabled = true;

    /**
     * 重复设置
     */
    private Repeat  repeat  = new Once();

    /**
     * 铃声
     */
    private String  ring    = "义勇军进行曲";

    /**
     * 备注
     */
    private String  remark  = "闹铃";

    public boolean isMatchedTime(Calendar now)
    {
        return repeat.isMatchedDay(now) && hour == now.get(Calendar.HOUR_OF_DAY) && minute == now.get(Calendar.MINUTE);
    }

    public boolean disableAfterRing()
    {
        return repeat.disableAfterRing();
    }

    public void setTime(int hour, int minute)
    {
        this.hour = hour;
        this.minute = minute;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public int getHour()
    {
        return hour;
    }

    public void setHour(int hour)
    {
        this.hour = hour;
    }

    public int getMinute()
    {
        return minute;
    }

    public void setMinute(int minute)
    {
        this.minute = minute;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public Repeat getRepeat()
    {
        return repeat;
    }

    public void setRepeat(Repeat repeat)
    {
        this.repeat = repeat;
    }

    public String getRing()
    {
        return ring;
    }

    public void setRing(String ring)
    {
        this.ring = ring;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    /**
     * 使用id识别唯一性
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }

    /**
     * 使用id识别唯一性
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (obj == this)
        {
            return true;
        }
        if (obj.getClass() != getClass())
        {
            return false;
        }
        Alarm that = (Alarm) obj;
        return new EqualsBuilder().append(id, that.getId()).isEquals();
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
