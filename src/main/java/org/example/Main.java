package org.example;


import org.example.deepseek.*;

public class Main {

    public static void main(String[] args) {
        DeepSeekConfig config = new DeepSeekConfig.Builder()
//
                .model(Model.REASONER)
                .stream(true)
                .requestMode(true)
                .build();
        DeepSeekHelper helper = new DeepSeekHelper(config);
        helper.addListener(response -> {
            if (response.getClass() == DeepSeekStreamResponse.class) {
                if (response.isReasoning()) {
                    System.out.print(response.getReasoningContent());
                } else {
                    System.out.print(response.getContent());
                }
            }
        });
        helper.addMessage(Role.USER, "你好! DeepSeek小姐!");
        helper.request();
        while (true);

    }

}
