package com.xdc.basic.api.apache.poi.model.bean;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Score
{
    private long studentNubmer;

    private long courseNumber;

    private int  score;

    public long getStudentNubmer()
    {
        return studentNubmer;
    }

    public void setStudentNubmer(long studentNubmer)
    {
        this.studentNubmer = studentNubmer;
    }

    public long getCourseNumber()
    {
        return courseNumber;
    }

    public void setCourseNumber(long courseNumber)
    {
        this.courseNumber = courseNumber;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
