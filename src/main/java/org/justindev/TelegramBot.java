package org.justindev;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.GetMe;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;


public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {
    private static final TelegramClient telegramClient = new OkHttpTelegramClient(Main.getBotToken());

    @Override
    public void consume(Update message) {
        MessageHandler messageHandler = new MessageHandler();
        messageHandler.messageParse(message);
    }

    public static void sendMessage(String messageText, long chatId) {

        int chunkSize = 800;
        int currentPosition = 0;

        while (currentPosition < messageText.length()) {
            int nextPosition = Math.min(currentPosition + chunkSize, messageText.length());

            if (nextPosition < messageText.length()) {
                int lastNewLine = messageText.lastIndexOf('\n', nextPosition);

                if (lastNewLine > currentPosition){
                    nextPosition = lastNewLine +1;
                }
            }
            String chunk = messageText.substring(currentPosition, nextPosition).trim();
            if(!chunk.isEmpty()){
                sendSingleChunk(chunk, chatId);

                if (nextPosition < messageText.length()) {
                    waitForMessage();
                }
            }
            currentPosition = nextPosition;
        }
    }


    public void sendChatAction(long chatId){
        String stringChatId = String.valueOf(chatId);
        SendChatAction chatAction = new SendChatAction(stringChatId,"typing");
        try{
            telegramClient.execute(chatAction);
        } catch (TelegramApiException e){
            System.out.println("Chat Action Failed");
            System.err.println(e.getMessage());
        }
    }

    public String getBotUsername(){
        try{
            GetMe getMe = new GetMe();
            User bot = telegramClient.execute(getMe);
            return bot.getUserName();
        } catch (TelegramApiException e){
            System.out.println("Get Me Failed");
            System.err.println(e.getMessage());
            return  null;
        }
    }

    private static void waitForMessage(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted!");
        }
    }

    private static void sendSingleChunk(String chunk, long chatId) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(chunk)
                .parseMode("Markdown")
                .build();

        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            try {
                message.setParseMode(null);
                telegramClient.execute(message);
            } catch (TelegramApiException f) {
                System.err.println("Zoiks! Even plain text failed: " + f.getMessage());
            }
        }
    }
}

    
