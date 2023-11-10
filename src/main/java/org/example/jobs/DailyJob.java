package org.example.jobs;

import io.dropwizard.jobs.Job;
import io.dropwizard.jobs.annotations.Every;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
@Every("3s")
public class DailyJob extends Job {

    @Override
    public void doJob(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("This is the Daily job running");
    }
}
