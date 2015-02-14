package com.xdc.basic.api.apache.poi.model.xls;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

// Excel中的概念：
// Workbook 工作簿（对应xls文件）
// Sheet    工作表
// Row      行
// Colum    列
// Cell     单元格
public abstract class RowObj
{
    /**
     * excel行号
     */
    protected int rowNum;

    public int getRowNum()
    {
        return rowNum;
    }

    public void setRowNum(int rowNum)
    {
        this.rowNum = rowNum;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}