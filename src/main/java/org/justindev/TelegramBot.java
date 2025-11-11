package org.justindev;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;


public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient = new OkHttpTelegramClient(Main.getBotToken());

    @Override
    public void consume(Update message) {
        MessageHandler messageHandler = new MessageHandler();
        messageHandler.messageParse(message);
    }

    public void sendMessage(String messageText, long chatId) {
        SendMessage message = SendMessage
        .builder()
        .chatId(chatId)
        .text(messageText)
        .build();
        message.enableMarkdown(true);
        message.setParseMode("Markdown");
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            System.out.println("Message failed to send with formatting retrying without");
            System.err.println(e.getMessage());
            try{
                message.enableMarkdown(false);
                telegramClient.execute(message);
            } catch (TelegramApiException f){
                System.err.println("Message Failed");
                System.err.println(f.getMessage());
            }
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
}

    
