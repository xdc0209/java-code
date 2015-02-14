package com.xdc.basic.api.apache.poi.model.xls;

import com.xdc.basic.api.apache.poi.utils.XlsColum;
import com.xdc.basic.api.apache.poi.utils.XlsSheet;

@XlsSheet(name = StudentRowObj.sheetName)
public class StudentRowObj extends RowObj
{
    protected static final String sheetName   = "学生";

    public static final String    nubmerColum = "编号";
    public static final String    nameColum   = "姓名";
    public static final String    genderColum = "性别";

    @XlsColum(name = nubmerColum)
    private String                number;

    @XlsColum(name = nameColum)
    private String                name;

    @XlsColum(name = genderColum)
    private String                gender;

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

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }
}
