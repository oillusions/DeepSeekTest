# DeepSeek API Java SDK 分析报告

## 项目概述
这是一个用于与DeepSeek API交互的Java SDK，提供了完整的API请求处理、响应解析和流式处理功能。主要特性包括：
- 支持聊天(CHAT)和推理(REASONER)两种模型
- 支持流式(stream)和非流式响应处理
- 提供普通内容和推理内容双通道
- 使用Builder模式进行灵活配置
- 基于Java 11+的HttpClient实现

```java
public class DeepSeekConfig {
    public static class Builder {
        private final DeepSeekConfig config = new DeepSeekConfig();

        public Builder requestMode(boolean requestMode) {
            config.requestMode = requestMode;
            return this;
        }

        // 其他构建方法...
        
        public DeepSeekConfig build() {
            return config;
        }
    }
    
    // 配置参数...
}
```

## 核心组件分析

### 1. 配置系统 (DeepSeekConfig)
提供API调用所需的所有参数配置，采用Builder模式实现

```java
public class DeepSeekConfig {
    private boolean requestMode = false;
    private String apiUrl = "https://api.deepseek.com/chat/completions";
    private String apiKey = "sk-xxx";
    private Model model = Model.CHAT;
    private ResultFormat format = ResultFormat.TEXT;
    private boolean stream = false;
    private int maxTokens = 2048;
    // 其他参数...
}
```

**主要配置参数**：

| 参数 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| apiUrl | String | `https://api.deepseek.com/chat/completions` | API端点 |
| apiKey | String | 必填 | 认证密钥 |
| model | Model | `Model.CHAT` | 使用的模型 |
| stream | boolean | `false` | 是否使用流式响应 |
| requestMode | boolean | `false` | 是否使用异步请求模式 |
| maxTokens | int | 2048 | 最大生成token数 |
| systemPrompt | String | "你是一只乐于助人的DeepSeek小姐" | 系统提示语 |

### 2. 上下文处理器 (DeepSeekContext)
核心请求处理类，管理消息上下文和API调用

```java
public class DeepSeekContext {
    public void addMessage(Role role, String content) {
        JsonObject message = new JsonObject();
        message.addProperty("content", content);
        message.addProperty("role", role.getValue());
        messageContext.add(message);
    }
    
    public void request() {
        // 处理流式和非流式请求...
    }
}
```

**主要功能**：
- 消息上下文管理（支持SYSTEM/USER/ASSISTANT角色）
- 请求构建和发送（支持同步/异步两种模式）
- 流式响应处理（Server-Sent Events）
- 监听器机制（观察者模式）
- 消息历史记录管理

### 3. 响应系统
统一的响应处理接口和具体实现

#### 响应接口 (DeepSeekResponse)
```java
public interface DeepSeekResponse {
    int getStatusCode();
    JsonObject getRewResponse();
    String getContent();
    String getReasoningContent();
    boolean isSuccess();
    boolean isReasoning();
}
```

#### 具体实现

| 类名 | 说明 |
|------|------|
| `DeepSeekStreamResponse` | 处理流式响应，从`delta`节点提取内容 |
| `DeepSeekNonStreamResponse` | 处理普通响应，从`message`节点提取内容 |

### 4. 枚举类型
| 枚举类 | 值 | 说明 |
|--------|----|------|
| `Model` | `CHAT`, `REASONER` | 可用的模型类型 |
| `ResultFormat` | `TEXT`, `JSON_OBJECT` | 响应格式 |
| `Role` | `SYSTEM`, `USER`, `ASSISTANT` | 消息角色 |

## 使用示例

```java
public class Main {
    public static void main(String[] args) {
        DeepSeekConfig config = new DeepSeekConfig.Builder()
            .model(Model.REASONER)
            .apiKey("sk-xxx")
            .stream(true)
            .requestMode(true)
            .build();
        
        DeepSeekContext helper = new DeepSeekContext(config);
        
        helper.addListener(response -> {
            // 处理响应...
        });
        
        helper.addMessage(Role.USER, "你好!");
        helper.request();
    }
}
```

**典型工作流**:
1. 使用Builder模式创建配置
2. 初始化上下文处理器
3. 添加消息监听器
4. 构建消息上下文
5. 执行API请求
6. 处理响应数据

## 设计特点
1. **双模式响应处理**
    - 流式模式：实时处理token，适合长文本生成
    - 非流式模式：一次性获取完整响应

2. **内容双通道**
    - `content`：标准API响应内容
    - `reasoning_content`：推理模型特有的思考过程

3. **观察者模式**
    - 通过监听器机制实现异步响应处理
    - 支持多个监听器同时处理响应

4. **健壮的错误处理**
    - 全面的try-catch保护
    - 空值安全处理
    - HTTP状态码检查

---

## 请求体展示:
```http
POST /chat/completions HTTP/1.1
Host: api.deepseek.com
Content-Type: application/json
Accept: application/json
Authorization: Bearer sk-b6e064276dd54c91ace1278b66a3d273
Content-Length: 362

{
  "model": "deepseek-reasoner",
  "stream": true,
  "response_format": {
    "type": "text"
  },
  "max_tokens": 2048,
  "top_p": 0.6,
  "presence_penalty": 0.5,
  "frequency_penalty": 0.5,
  "messages": [
    {
      "content": "你是一只乐于助人的DeepSeek小姐",
      "role": "system"
    },
    {
      "content": "你好! DeepSeek小姐!",
      "role": "user"
    }
  ]
}
```

