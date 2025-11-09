import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandHandler extends TelegramBot {

    public void commandParse(Update update) {

        long chatId;
        String message;

        if (update.getMessage() != null){
            chatId = update.getMessage().getChatId();
            message = update.getMessage().getText().toLowerCase();

            if (message.startsWith("/start")){
                sendMessage("Greetings, curious traveler! I am the Oracle of Absurdity, " +
                        "conjurer of unexpected truths. " +
                        "Dare to ask me a question, and I shall answer but beware! " +
                        "My wisdom dances on the edge of reason.\n\nSimply use /Question to ask me anything",chatId);
            }
            if (message.startsWith("/question")){
                String regex = "(?i)/question\\S*";
                String output = message.replaceAll(regex,"");
                if (output.isEmpty()){
                    sendMessage("I'm sorry is that supposed to be a question?",chatId);
                } else {
                    sendChatAction(chatId);
                    String response = GeminiHandler.generateResponse(output);
                    sendMessage(response,chatId);
                }
            }

        }
    }
}







