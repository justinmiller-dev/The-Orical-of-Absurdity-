package org.justindev;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.time.ZonedDateTime;

public class DailyTasks implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException{
        CommandHandler.resetDailyRequestLimit();
        System.out.println("Daily request limit reset at" + ZonedDateTime.now());
    }



}
