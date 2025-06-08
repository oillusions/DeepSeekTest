package cn.oillusions.test;


import cn.oillusions.test.deepseek.*;

public class Main {

    public static void main(String[] args) {
        DeepSeekConfig config = new DeepSeekConfig.Builder()
                .apiKey("sk-cd9ac9b9636b42ca956e4b81b31dc0af")
                .stream(true)
                .requestMode(true)
                .build();
        DeepSeekContext context = new DeepSeekContext(config);
        context.addListener(response -> {
            if (response.getClass() != DeepSeekNonStreamResponse.class) {
                System.out.print("\r流式内容输入: " + response.getContent());
            } else {
                System.out.println("\n完整内容输入: " + response.getContent());
            }
        });
        context.addMessage(Role.USER, "你好! DeepSeek小姐!");
        context.request();
        while (true);

    }

}
