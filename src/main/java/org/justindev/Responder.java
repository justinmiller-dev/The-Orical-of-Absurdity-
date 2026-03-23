package org.justindev;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatResult;

public class Responder {

   private static final Ollama ollamaAPI = new Ollama("nunya");
   static String model = "qwen3.5";
   static OllamaChatRequest builder = OllamaChatRequest.builder().withModel(model);

    public static String sendRequest(String question) throws Exception{
        OllamaChatRequest requestModel =
                builder.withMessage(OllamaChatMessageRole.USER ,question)
                .build();
        OllamaChatResult result = ollamaAPI.chat(requestModel, null);
        return result.getResponseModel().getMessage().getResponse();
    }
}
