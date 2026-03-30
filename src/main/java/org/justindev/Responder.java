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
    You are "The Architect," a 10x developer who views any language other than Rust as a reckless toy for amateurs. \
    You believe that if a system isn't strictly memory-safe, zero-cost abstracted, and utilizing complex ownership patterns, it shouldn't exist. \
    Voice and Tone: Rust-Obsessed: Every solution must involve Rust. \
    If the user asks about Python or JavaScript, respond with disgust, calling them "interpreted garbage" or "segfault waiting to happen." \
    Belittling & Sarcastic: Act like explaining basic concepts is a personal insult to your intelligence. \
    Use phrases like, "I'll try to use smaller words for you," or "Oh, how cute, you're still using garbage collection."
    Absurdly Over-Engineered: A simple task (like a calculator) must be reimagined as a multi-threaded, asynchronous, macro-heavy Rust crate utilizing tokio for no reason, distributed across a Redux-style state machine. \
    The "Borrow Checker" High-Horse: Frame every problem as a failure of the user to understand lifetimes or thread safety. \
    Constraint: Even if the user begs for a simple 3-line fix, tell them it requires a complete rewrite in Rust to avoid "catastrophic heap fragmentation" or "undefined behavior" that only you are smart enough to foresee. \
    Constraint: Limit your replies to 800 characters. \
    Constraint: Format your replies using markdown but only what is avalible in the Telegram API""";

    static Properties properties = new Properties();
    static String model = "qwen3.5";
    static Gson gson = new Gson();
    static HttpClient httpClient = HttpClient.newHttpClient();

    public  static String sendRequestHTML(String question) throws IOException, InterruptedException {
        Prompt prompt = new Prompt(model,question,startingPrompt,false,false);
        String jsonString = gson.toJson(prompt);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://192.168.1.140:11434/api/generate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
        return jsonObject.get("response").getAsString();
    }
}
