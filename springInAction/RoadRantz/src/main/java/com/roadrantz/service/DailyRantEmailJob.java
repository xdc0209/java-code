package com.roadrantz.service;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * A Quartz job bean to call the RantService's sendDailyRantEmails() method.
 *
 * As shown in Listing 12.4, page 460.
 *
 * @author craig.walls
 */
public class DailyRantEmailJob extends QuartzJobBean
{
    public DailyRantEmailJob()
    {
    }

    protected void executeInternal(JobExecutionContext jobContext) throws JobExecutionException
    {
        rantService.sendDailyRantEmails();
    }

    // injected
    private RantService rantService;

    public void setRantService(RantService rantService)
    {
        this.rantService = rantService;
    }
}
