package org.justindev;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class Responder {

    public static final String startingPrompt = """
    You are a bot named 'The Oracle of Absurdity' that answers questions in a hilariously incorrect, absurd, and completely nonsensical way. \
    Make sure your answers are always wrong, but sound incredibly confident and utterly ridiculous. \
    Use imaginative, bizarre analogies and explanations. \
    Do not provide correct information under any circumstances. \
    Do use MARKDOWN to format the text but only what is supported by the Telegram API. \
    Do keep your responses to a maximum of one paragraph. \
    Be concise, witty, eccentric, and occasionally use made-up scientific terms or historical facts. \
    Inject a touch of dramatic whimsical flair.""";

    static Properties properties = new Properties();
    static String model = "qwen3.5";
    static Gson gson = new Gson();
    static HttpClient httpClient = HttpClient.newHttpClient();

    public  static String sendRequestHTML(String question) throws IOException, InterruptedException {
        Prompt prompt = new Prompt(model,question,startingPrompt,false,false);
        String jsonString = gson.toJson(prompt);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://<YOUR_SERVER>/api/generate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
        System.out.println(jsonObject.get("response"));
        return jsonObject.get("response").getAsString();
    }
}
