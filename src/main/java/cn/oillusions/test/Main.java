package cn.oillusions.test;


import cn.oillusions.test.deepseek.*;

public class Main {

    public static void main(String[] args) {
        DeepSeekConfig config = new DeepSeekConfig.Builder()
                .model(Model.REASONER)
                .apiKey("sk-b6e064276dd54c91ace1278b66a3d273")
                .stream(true)
                .requestMode(true)
                .build();
        DeepSeekContext helper = new DeepSeekContext(config);
        helper.addListener(response -> {
            if (response.getClass() == DeepSeekStreamResponse.class) {
                if (response.isReasoning()) {
                    System.out.print(response.getReasoningContent()+ "\r");
                } else {
                    System.out.print(response.getContent()+ "\r");
                }
            }
        });
        helper.addMessage(Role.USER, "你好! DeepSeek小姐!");
        helper.request();
        while (true);

    }

}
