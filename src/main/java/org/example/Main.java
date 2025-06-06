package org.example;


import org.example.deepseek.DeepSeekConfig;
import org.example.deepseek.DeepSeekHelper;
import org.example.deepseek.Role;

public class Main {

    public static void main(String[] args) {
        DeepSeekConfig config = new DeepSeekConfig.Builder()
                .apiKey("sk-00a32fc9ca8049a999369ba8ad404065")
                .stream(true)
                .requestMode(true)
                .build();
        DeepSeekHelper helper = new DeepSeekHelper(config);
        helper.addMessage(Role.USER, "你好! DeepSeek小姐!");
        helper.request();
        while (true);

    }

}
