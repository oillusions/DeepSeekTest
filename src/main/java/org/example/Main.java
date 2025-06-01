package org.example;

import org.example.deepseek.DeepSeekHelper;
import org.example.deepseek.Model;
import org.example.deepseek.Role;



public class Main {
    public enum GameRole {
        PLAYER(Role.USER, "Player"),
        SYSTEM(Role.USER, "System"),
        THIS(Role.USER, "This")
        ;
        private final Role dsRole;
        private final String gameRole;

        GameRole(Role role, String gameRole) {
            this.dsRole = role;
            this.gameRole = gameRole;
        }

        public Role getDsRole() {
            return dsRole;
        }

        public String getGameRole() {
            return gameRole;
        }
    }

    public static void addMsg(DeepSeekHelper deepSeek, GameRole gameRole, String Name, String messageContent) {
        switch (gameRole) {
            case PLAYER -> {
                deepSeek.addMsg(gameRole.getDsRole(), gameRole.getGameRole() + '[' + Name + ']' + "说:" + messageContent);
                break;
            }
            case THIS -> {
                deepSeek.addMsg(gameRole.getDsRole(), gameRole.getGameRole() + "说:" + messageContent);
                break;
            }
            case SYSTEM -> {
                deepSeek.addMsg(gameRole.getDsRole(), gameRole.getGameRole() + ": " + messageContent);
            }
        }

    }

    public static void main(String[] args) {
        String apiKey = "sk-a205090a098c4968b72c4c332ef4c76d";
        String apiUrl = "https://api.deepseek.com/chat/completions";
        String cueWord = "你好Deepseek!. " +
                "我希望你现在作为我[ID: O_Illusions, 或者叫'无言']在Minecraft的对话助手[你可以自称为我]," +
                " 在你接收到消息[其他玩家或系统发送的]后," +
                " 帮助我生成2-6个从幽默到正常的回复," +
                "长度不得超过20字" +
                "直接返回其内容," +
                "回复已JsonArray的格式[直接返回文本就行， 不需要富文本格式]" +
                "请注意, 这是在一个多人服务器中," +
                "不要认错人[随意接话]" +
                "还有一件事, 我是摆烂玩家, 经常被人说是人机， 所以你的回复可以有一种超绝人机感" +
                "以下是我的聊天习惯: 动作描述词使用'[]'包裹, 比如: [看],[盯],[逃],[宕机], 事件描述词使用'()'包裹, 比如: (想了想), 这两个还能这样组合: '[看]在干嘛', (想着什么)阿巴...." +
                "请不要把'[]’包裹体和'()'包裹体弄混, '[]'包裹体是用来包裹当前状态, 动作的, '()'包裹体是用来包裹描述心里话的";

        DeepSeekHelper deepSeek = new DeepSeekHelper(apiUrl, apiKey, Model.CHAT, 100, cueWord);
        addMsg(deepSeek, GameRole.SYSTEM, null, "Player1加入了游戏");
        addMsg(deepSeek, GameRole.PLAYER, "Player1", "早！");
        addMsg(deepSeek, GameRole.THIS, null, "(困)早...");
        addMsg(deepSeek, GameRole.PLAYER, "Player1", "emmmm....你再睡会吧..");
        addMsg(deepSeek, GameRole.PLAYER, "Player1", "那我先不打扰了, 拜拜~");
        addMsg(deepSeek, GameRole.SYSTEM, null, "Player1退出了游戏");
        addMsg(deepSeek, GameRole.THIS, null, "[呆]啊...走了...");
        addMsg(deepSeek, GameRole.PLAYER, "Player2", "? 你真的是人机啊");
        addMsg(deepSeek, GameRole.THIS, null, "[逃]被发现了!");
        addMsg(deepSeek, GameRole.PLAYER, "Player2", "? a...你是谁来着?");
        addMsg(deepSeek, GameRole.THIS, null, "(思考中)ID是O_Illusions...");
        addMsg(deepSeek, GameRole.PLAYER, "Player2", "你的ID[O_Illusions]的含义是什么a[翻译]");
        addMsg(deepSeek, GameRole.SYSTEM, null, "Player2退出了游戏");

        System.out.println(deepSeek.request());
    }
}