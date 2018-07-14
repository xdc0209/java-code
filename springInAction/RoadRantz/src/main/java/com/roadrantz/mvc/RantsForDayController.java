package com.roadrantz.mvc;

import java.util.Date;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.throwaway.ThrowawayController;

import com.roadrantz.domain.Rant;
import com.roadrantz.service.RantService;

public class RantsForDayController implements ThrowawayController
{
    private int month;
    private int day;
    private int year;

    public ModelAndView execute() throws Exception
    {
        Date date = new Date(month, day, year);

        List<Rant> dayRants = rantService.getRantsForDay(date);

        return new ModelAndView("dayRants", "rants", dayRants);
    }

    public void setDay(int day)
    {
        this.day = day;
    }

    public void setMonth(int month)
    {
        this.month = month;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    // injected
    private RantService rantService;

    public void setRantService(RantService rantService)
    {
        this.rantService = rantService;
    }
}
