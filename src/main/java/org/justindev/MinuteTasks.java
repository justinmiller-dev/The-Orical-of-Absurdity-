package org.justindev;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class MinuteTasks implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        CommandHandler.resetMinuteRequestLimit();
    }



}
