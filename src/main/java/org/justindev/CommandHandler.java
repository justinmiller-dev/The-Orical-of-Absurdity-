package org.justindev;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.CompletableFuture;

public class CommandHandler extends TelegramBot {

    private final String botUsername = getBotUsername();

    public void commandParse(Update update) {

        long chatId;
        String chatType;
        String message = "";

        if (update.getMessage() != null){
            chatId = update.getMessage().getChatId();
            chatType = update.getMessage().getChat().getType();
            if (update.getMessage().hasText()){
                message = update.getMessage().getText();
            }

            if (chatType.matches("group|supergroup")){
                if (message.contains("/start@" + botUsername)){
                    sendWelcomeMessage(chatId);
                }
                if (message.startsWith("@" + botUsername + " /question")) {
                    askTheOracle(message,chatId);
                }

                if (message.startsWith("/about@" + botUsername)){
                    sendAboutMessage(chatId);
                }
            }
            else if (message.startsWith("/start")) {
                sendWelcomeMessage(chatId);
            }
            if (message.startsWith("/question")) {
                askTheOracle(message,chatId);
            }
            if (message.startsWith("/about")) {
                sendAboutMessage(chatId);
            }
            if (message.startsWith("/help")) {
                sendMessage("Simply type /question and then your question to ask me anything",chatId);
            }
        }
    }

    private void sendWelcomeMessage(long chatId){
        TelegramBot.sendMessage("""
                        Greetings, curious traveler! I am the Oracle of Absurdity, \
                        conjurer of unexpected truths. \
                        Dare to ask me a question, and I shall answer but beware! \
                        My wisdom dances on the edge of reason.
                        
                        Simply use /Question to ask me anything""",chatId);
    }

    private void askTheOracle(String input, long chatId){
        String regex = "^(@\\S+\\s+)?/question(@\\S+)?\\s*";
        input = input.replaceAll(regex,"");
        if (input.isEmpty()){
            sendMessage("Beg pardon, but was that a question or merely a ghost of one? It seems to have arrived dressed in silence!",chatId);
        } else  {
            sendChatAction(chatId);
            String finalInput = input;
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                try {
                    return Responder.sendRequestHTML(finalInput);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return  "I'm sorry. That question seems to have fluttered right past me. Can you please ask me again?";
                }
            });
            String response = future.join();
            sendMessage(response,chatId);
            System.out.println(response);
        }
    }

    private void sendAboutMessage(long chatId){
        sendMessage("""
                        The Oracle of Absurdity Bot
                        Created by: Justin Miller
                        GitHub: justinmiller-dev
                        Created with
                        Google Gen AI Java SDK
                        https://github.com/googleapis/java-genai?tab=readme-ov-file
                        Telegram Bot Java Library
                        https://github.com/rubenlagus/TelegramBots""",chatId);
    }
}







