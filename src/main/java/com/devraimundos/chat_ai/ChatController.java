package com.devraimundos.chat_ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(@NotNull ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/ai")
    public Object generation(@RequestParam String userInput) {
        Map<String, Object> response = new HashMap<>();
        try {
            String result = this.chatClient.prompt()
                    .user(userInput)
                    .call()
                    .content();
            response.put("status", "ok");
            response.put("input", userInput);
            response.put("output", result);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("errorType", e.getClass().getName());
            response.put("message", e.getMessage());
            response.put("cause", e.getCause() != null ? e.getCause().toString() : null);
        }
        return response;
    }
}
