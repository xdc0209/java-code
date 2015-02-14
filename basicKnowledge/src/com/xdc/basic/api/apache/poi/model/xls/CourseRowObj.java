package com.xdc.basic.api.apache.poi.model.xls;

import com.xdc.basic.api.apache.poi.utils.XlsColum;
import com.xdc.basic.api.apache.poi.utils.XlsSheet;

@XlsSheet(name = CourseRowObj.sheetName)
public class CourseRowObj extends RowObj
{
    protected static final String sheetName   = "课程";

    public static final String    nubmerColum = "编号";
    public static final String    nameColum   = "名称";
    public static final String    creditColum = "学分";

    @XlsColum(name = nubmerColum)
    private String                number;

    @XlsColum(name = nameColum)
    private String                name;

    @XlsColum(name = creditColum)
    private String                credit;

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCredit()
    {
        return credit;
    }

    public void setCredit(String credit)
    {
        this.credit = credit;
    }
}
