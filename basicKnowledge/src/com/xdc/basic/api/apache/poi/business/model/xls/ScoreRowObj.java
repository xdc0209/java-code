package com.xdc.basic.api.apache.poi.business.model.xls;

import com.xdc.basic.api.apache.poi.core.annotation.XlsColum;
import com.xdc.basic.api.apache.poi.core.annotation.XlsSheet;
import com.xdc.basic.api.apache.poi.core.model.RowObj;

@XlsSheet(name = ScoreRowObj.sheetName)
public class ScoreRowObj extends RowObj
{
    protected static final String sheetName          = "成绩";

    public static final String    studentNubmerColum = "学生编号";
    public static final String    courseNumberColum  = "课程编号";
    public static final String    scoreColum         = "分数";

    @XlsColum(name = studentNubmerColum)
    private String                studentNubmer;

    @XlsColum(name = courseNumberColum)
    private String                courseNumber;

    @XlsColum(name = scoreColum)
    private String                score;

    public String getStudentNubmer()
    {
        return studentNubmer;
    }

    public void setStudentNubmer(String studentNubmer)
    {
        this.studentNubmer = studentNubmer;
    }

    public String getCourseNumber()
    {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber)
    {
        this.courseNumber = courseNumber;
    }

    public String getScore()
    {
        return score;
    }

    public void setScore(String score)
    {
        this.score = score;
    }
}
