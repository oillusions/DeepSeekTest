package org.example.deepseek;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DeepSeekHelper {
    private final Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
    private final DeepSeekConfig config;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private List<String> streamBuffer;
    private JsonArray messageContext = new JsonArray();

    private final List<Consumer<HttpResponse<InputStream>>> listeners = new ArrayList<>();


    public DeepSeekHelper(DeepSeekConfig config) {
        this.config = config;
    }

    public void addMessage(Role role, String content) {
        addMessage(role, null, content);
    }

    public void addMessage(Role role, String name, String content) {
        JsonObject message = new JsonObject();

        message.addProperty("content", content);
        message.addProperty("role", role.getValue());
        if (name != null && !name.isBlank()) {
            message.addProperty("name", name);
        }

        messageContext.add(message);
    }

    protected JsonObject buildRequestBody() {
        JsonObject requestBody = new JsonObject();
        JsonObject systemMessage = new JsonObject();
        JsonObject resultFormat = new JsonObject();
        JsonArray messages = new JsonArray();


        systemMessage.addProperty("content", config.getSystemPrompt());
        systemMessage.addProperty("role", Role.SYSTEM.getValue());

        messages.add(systemMessage);
        messages.addAll(messageContext);

        resultFormat.addProperty("type", config.getFormat().getValue());

        requestBody.addProperty("model", config.getModel().getValue());
        requestBody.addProperty("stream", config.isStream());
        requestBody.add("response_format", resultFormat);

        requestBody.addProperty("max_tokens", config.getMaxTokens());
        requestBody.addProperty("top_p", config.getTopP());
        requestBody.addProperty("presence_penalty", config.getPresencePenalty());
        requestBody.addProperty("frequency_penalty", config.getFrequencyPenalty());

        requestBody.add("messages", messages);

        return requestBody;
    }

    protected HttpRequest buildRequest(JsonObject requestBody) {

        return HttpRequest.newBuilder()
                .uri(URI.create(config.getApiUrl()))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + config.getApiKey())
                .method("POST", HttpRequest.BodyPublishers.ofString(gson.toJson(requestBody)))
                .build();
    }

    public DeepSeekResponse request() {
        HttpRequest request = buildRequest(buildRequestBody());
        streamBuffer = new ArrayList<>();
        System.out.println(gson.toJson(buildRequestBody()));

        if (config.isRequestMode()) {
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
                    .thenAccept(response -> {
//                        notifyListener(response);
                        System.out.println(response.statusCode());
                        try (InputStream stream = response.body()) {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                streamBuffer.add(line);
                                System.out.println(line);
                            }
                        } catch (Exception e) {

                        }
                    });
        } else {
            try {
                httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
            } catch (Exception e) {

            }

        }

        return null;
    }



    public void addListener(Consumer<HttpResponse<InputStream>> listener) {
        listeners.add(listener);
    }

    private void notifyListener(HttpResponse<InputStream> response) {
        for (Consumer<HttpResponse<InputStream>> listener : listeners) {
            listener.accept(response);
        }
    }

    public void resetConversation() {
        messageContext = new JsonArray();
    }

    public DeepSeekConfig getConfig() {
        return config;
    }

    public JsonArray getMessageContext() {
        return messageContext;
    }
}
