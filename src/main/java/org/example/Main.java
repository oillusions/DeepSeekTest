package org.example;


import org.example.deepseek.*;

public class Main {

    public static void main(String[] args) {
        DeepSeekConfig config = new DeepSeekConfig.Builder()
                .apiKey("sk-b6e064276dd54c91ace1278b66a3d273")
                .model(Model.REASONER)
                .stream(true)
                .requestMode(true)
                .build();
        DeepSeekContext helper = new DeepSeekContext(config);
        helper.addListener(response -> {
                if (response.isReasoning()) {
                    System.out.print(response.getReasoningContent()+ '\r');
                } else {
                    System.out.print(response.getContent()+ '\r');
                }
        });
        helper.addMessage(Role.USER, "你好! DeepSeek小姐!");
        helper.request();
        while (true);

    }

}
