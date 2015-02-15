package com.xdc.basic.api.apache.poi.check.result;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class Result
{
    public static final int    success        = 0;
    public static final int    warning        = 1;
    public static final int    failure        = 2;

    public static final String warningFormat  = "Warn:<%s>:<%s> %s";
    public static final String errorFormat    = "Error:<%s>:<%s> %s";

    private final List<String> warningDetails = new ArrayList<String>();
    private final List<String> errorDetails   = new ArrayList<String>();

    /**
     * 0：成功 1：警告 2：失败
     */
    private int                result;

    private int                excelRowNum;

    public int getResult()
    {
        return result;
    }

    public List<String> getSummeryDetails()
    {
        List<String> summeryDetails = new ArrayList<String>();

        summeryDetails.addAll(errorDetails);
        summeryDetails.addAll(warningDetails);

        return summeryDetails;
    }

    public void addWarningDetailOnNotEmpty(String warningDetail)
    {
        if (StringUtils.isEmpty(warningDetail))
        {
            return;
        }

        if (this.result == Result.success)
        {
            this.result = Result.warning;
        }
        warningDetails.add(warningDetail);
    }

    public void addErrorDetailOnNotEmpty(String errorDetail)
    {
        if (StringUtils.isEmpty(errorDetail))
        {
            return;
        }

        this.result = Result.failure;
        errorDetails.add(errorDetail);
    }

    public int getExcelRowNum()
    {
        return excelRowNum;
    }

    public void setExcelRowNum(int excelRowNum)
    {
        this.excelRowNum = excelRowNum;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
