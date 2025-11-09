import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Main {

    private static String botToken;
    private static String gemini;

    static void main() {

        try {
            SchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = stdSchedulerFactory.getScheduler();
            scheduler.start();
            JobDetail job1 = JobBuilder.newJob(DailyTasks.class)
                    .withIdentity("dailyTask","group1")
                    .build();
            Trigger trigger1 = TriggerBuilder.newTrigger()
                    .withIdentity("dailyTask","group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 2 * * ?"))
                    .build();
            scheduler.scheduleJob(job1,trigger1);
            System.out.println("Resetting daily request limit at 2:00 AM");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        try {
            SchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = stdSchedulerFactory.getScheduler();
            scheduler.start();
            JobDetail job2 = JobBuilder.newJob(MinuteTasks.class)
                    .withIdentity("minuteTask","group1")
                    .build();
            Trigger trigger2 = TriggerBuilder.newTrigger()
                    .withIdentity("minuteTask","group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 * * ? * *"))
                    .build();
            scheduler.scheduleJob(job2,trigger2);
            System.out.println("Resetting RPM limit every minute");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        Properties prop = new Properties();
        InputStream input;
        try{
             input = Main.class.getResourceAsStream("/.properties");
             prop.load(input);
             botToken = prop.getProperty("api.token");
             gemini = prop.getProperty("gem.apitoken");
        } catch (IOException e) {
            System.err.println("Error reading resource: " + e.getMessage());
        }
        try {
            @SuppressWarnings("resource")
            TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
            botsApplication.registerBot(botToken, new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public static String getBotToken() {
        return botToken;
    }
    public static String getGemini(){
        return gemini;
    }
}
