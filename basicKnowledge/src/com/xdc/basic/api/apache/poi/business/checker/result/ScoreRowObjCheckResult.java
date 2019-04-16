package com.xdc.basic.api.apache.poi.business.checker.result;

import com.xdc.basic.api.apache.poi.business.model.bean.Score;
import com.xdc.basic.api.apache.poi.core.checker.result.Result;

public class ScoreRowObjCheckResult extends Result
{
    private String studentNubmer;

    private String courseNumber;

    /**
     * 校验结果为成功时，生成对应的bean
     */
    private Score  score;

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

    public Score getScore()
    {
        return score;
    }

    public void setScore(Score score)
    {
        this.score = score;
    }
}
