package com.xdc.basic.api.apache.poi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.xdc.basic.api.apache.poi.check.StudentRowObjChecker;
import com.xdc.basic.api.apache.poi.check.result.StudentRowObjCheckResult;
import com.xdc.basic.api.apache.poi.model.bean.Student;
import com.xdc.basic.api.apache.poi.model.xls.CourseRowObj;
import com.xdc.basic.api.apache.poi.model.xls.ScoreRowObj;
import com.xdc.basic.api.apache.poi.model.xls.StudentRowObj;
import com.xdc.basic.api.apache.poi.utils.XlsToBeanBuilder;
import com.xdc.basic.api.apache.poi.utils.XlsUtils;
import com.xdc.basic.skills.GetPath;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        String curPath = GetPath.getRelativePath();
        String xlsFilePath = curPath + "school.xlsx";

        List<StudentRowObj> studentRowObjs = XlsUtils.parseSheet(xlsFilePath, StudentRowObj.class);
        List<CourseRowObj> courseRowObjs = XlsUtils.parseSheet(xlsFilePath, CourseRowObj.class);
        List<ScoreRowObj> scoreRowObjs = XlsUtils.parseSheet(xlsFilePath, ScoreRowObj.class);

        System.out.println(studentRowObjs);
        System.out.println();

        System.out.println(courseRowObjs);
        System.out.println();

        System.out.println(scoreRowObjs);
        System.out.println();

        // 本例只是演示，对student写具体业务逻辑，course和score就不写了
        List<StudentRowObjCheckResult> studentRowObjCheckResults = new ArrayList<StudentRowObjCheckResult>();
        for (StudentRowObj studentRowObj : studentRowObjs)
        {
            StudentRowObjCheckResult studentRowObjCheckResult = new StudentRowObjCheckResult();
            studentRowObjCheckResult.setExcelRowNum(studentRowObj.getRowNum());
            studentRowObjCheckResult.setNumber(studentRowObj.getNumber());
            studentRowObjCheckResult.setName(studentRowObj.getName());
            studentRowObjCheckResults.add(studentRowObjCheckResult);

            StudentRowObjChecker.check(studentRowObj, studentRowObjCheckResult);

            if (CollectionUtils.isNotEmpty(studentRowObjCheckResult.getSummeryDetails()))
            {
                // 有告警或错误就不生成bean对象了
                continue;
            }

            Student student = XlsToBeanBuilder.build(studentRowObj);
            studentRowObjCheckResult.setStudent(student);
        }
        System.out.println(studentRowObjCheckResults);
    }
}
