package org.justindev;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Main {

    private static String botToken;

    static void main() {
        Properties prop = new Properties();
        InputStream input;
        try{
             input = Main.class.getResourceAsStream("/.properties");
             prop.load(input);
             botToken = prop.getProperty("api.token");
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
}
