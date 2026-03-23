package org.justindev;

public class Prompt {
    String model;
    String prompt;
    String system;
    Boolean stream;
    Boolean think;


    public Prompt(String model, String content, String systemPrompt, Boolean stream, Boolean think) {
        this.model = model;
        this.prompt = content;
        this.system = systemPrompt;
        this.stream = stream;
        this.think = think;
    }


}

class CustomPrompt {
    String role;
    String content;

    public CustomPrompt(String role, String content) {
        this.role = role;
        this.content = content;
    }
}

