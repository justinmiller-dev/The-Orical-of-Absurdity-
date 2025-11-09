import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandHandler extends TelegramBot {

    static int requests = 0;
    static int totalRequestsToday = 0;
    static int minuteRequestLimit = 9;
    static int dailyRequestLimit = 250;

    public void commandParse(Update update) {

        long chatId;
        String message;

        if (update.getMessage() != null){
            chatId = update.getMessage().getChatId();
            message = update.getMessage().getText().toLowerCase();

            if (message.startsWith("/start")){
                sendMessage("""
                        Greetings, curious traveler! I am the Oracle of Absurdity, \
                        conjurer of unexpected truths. \
                        Dare to ask me a question, and I shall answer but beware! \
                        My wisdom dances on the edge of reason.
                        
                        Simply use /Question to ask me anything""",chatId);
            }
            if (message.startsWith("/question")){
                String regex = "(?i)/question\\S*";
                String output = message.replaceAll(regex,"");
                if (output.isEmpty()){
                    sendMessage("Beg pardon, but was that a question or merely a ghost of one? It seems to have arrived dressed in silence!",chatId);
                } else if (requests <= minuteRequestLimit && totalRequestsToday <= dailyRequestLimit) {
                    sendChatAction(chatId);
                    String response = GeminiHandler.generateResponse(output);
                    sendMessage(response,chatId);
                    requests++;
                    totalRequestsToday++;
                } else {
                    sendMessage("Hold that thought. I must consult the stars and gather my scattered musings. Do ask again in a tickle of time!",chatId);
                }
            }
            if (message.startsWith("/about")){
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
            if (message.startsWith("/help")){
                sendMessage("Simply type /question and then your question to ask me anything",chatId);
            }
        }
    }
    public static void resetDailyRequestLimit(){
        totalRequestsToday = 0;
    }
    public static void resetMinuteRequestLimit (){
        requests = 0;
    }
}







