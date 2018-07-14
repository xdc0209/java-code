package com.xdc.basic.api.apache.poi.check;

import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.api.apache.poi.check.result.Result;
import com.xdc.basic.api.apache.poi.check.result.StudentRowObjCheckResult;
import com.xdc.basic.api.apache.poi.model.xls.StudentRowObj;

public class StudentRowObjChecker
{
    public static void check(StudentRowObj studentRowObj, StudentRowObjCheckResult checkResult)
    {
        String number = studentRowObj.getNumber();
        String name = studentRowObj.getName();
        String gender = studentRowObj.getGender();

        String numberCheck = numberCheck(StudentRowObj.nubmerColum, number);
        checkResult.addErrorDetailOnNotEmpty(numberCheck);

        String nameCheck = nameCheck(StudentRowObj.nameColum, name);
        checkResult.addErrorDetailOnNotEmpty(nameCheck);

        String genderCheck = genderCheck(StudentRowObj.genderColum, gender);
        checkResult.addErrorDetailOnNotEmpty(genderCheck);
    }

    /**
     * 字母M/F
     */
    private static String genderCheck(String columName, String gender)
    {
        String msg = String.format(Result.errorFormat, columName, gender, "not valid.");

        if (!StringUtils.equals(gender, "M") && !StringUtils.equals(gender, "F"))
        {
            return msg;
        }

        return null;
    }

    /**
     * 2或3个汉字
     */
    private static String nameCheck(String columName, String name)
    {
        String msg = String.format(Result.errorFormat, columName, name, "not valid.");
        if (StringUtils.isBlank(name))
        {
            return msg;
        }

        if (!name.matches("^[\\u4E00-\\u9FA5]{2,3}$"))
        {
            return msg;
        }

        return null;
    }

    /**
     * 8位长度数字串
     */
    private static String numberCheck(String columName, String number)
    {
        String msg = String.format(Result.errorFormat, columName, number, "not valid.");

        if (number == null || number.length() != 8)
        {
            return msg;
        }

        if (!number.matches("^[0-9]{8}$"))
        {
            return msg;
        }

        return null;
    }
}
