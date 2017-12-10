package com.xdc.basic.api.apache.poi.check.result;

import com.xdc.basic.api.apache.poi.model.bean.Score;

public class ScoreRowObjCheckResult extends Result
{
    private String studentNubmer;

    private String courseNumber;
    /**
     * 校验结果为成功时，生成对应的bean
     */
    private Score  score;

    public Score getScore()
    {
        return score;
    }

    public void setScore(Score score)
    {
        this.score = score;
    }

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
}
