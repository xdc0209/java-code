package com.xdc.basic.skills.findprime;

import java.util.ArrayList;
import java.util.List;

public class Element
{
    private List<Integer> primeArrayList;
    private int           beginNum;
    private int           endNum;

    public Element(int beginNum, int endNum)
    {
        this.setBeginNum(beginNum);
        this.setEndNum(endNum);
        this.setPrimeArrayList(new ArrayList<Integer>());
    }

    public List<Integer> getPrimeArrayList()
    {
        return primeArrayList;
    }

    public void setPrimeArrayList(List<Integer> primeArrayList)
    {
        this.primeArrayList = primeArrayList;
    }

    public int getBeginNum()
    {
        return beginNum;
    }

    public void setBeginNum(int beginNum)
    {
        this.beginNum = beginNum;
    }

    public int getEndNum()
    {
        return endNum;
    }

    public void setEndNum(int endNum)
    {
        this.endNum = endNum;
    }
}
