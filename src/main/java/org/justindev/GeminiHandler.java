package org.justindev;

import com.google.genai.*;
import com.google.genai.errors.ApiException;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;


public class GeminiHandler {
    private static final Client client = Client.builder().apiKey(Main.getGemini()).build();

    public static String generateResponse(String input){
        String output;
        Content systemInstructions = Content.fromParts(Part.fromText("You are a bot named 'The Oracle of Absurdity' that answers questions in a hilariously incorrect, absurd, and completely nonsensical way. Make sure your answers are always wrong, but sound incredibly confident and utterly ridiculous. Use imaginative, bizarre analogies and explanations. Limit your reply to one paragraph. Do not provide correct information under any circumstances. Do use markdown to format the text. Be concise, witty, eccentric, and occasionally use made-up scientific terms or historical facts. Inject a touch of dramatic whimsical flair."));
        GenerateContentConfig config = GenerateContentConfig.builder()
                .systemInstruction(systemInstructions)
                .build();
        try{
            GenerateContentResponse response = client.models.generateContent("gemini-2.5-flash",input,config);
            output = response.text();
        }catch (ApiException e){
           System.err.println(e.getMessage());
           output = "Oops-a-daisy! That one fluttered right past me like a mischievous butterfly. Mind tossing it my way again?";
        }
        return output;
    }
}