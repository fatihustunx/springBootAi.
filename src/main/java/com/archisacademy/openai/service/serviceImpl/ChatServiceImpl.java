package com.archisacademy.openai.service.serviceImpl;

import com.archisacademy.openai.dto.ChatRequest;
import com.archisacademy.openai.dto.ChatResponse;
import com.archisacademy.openai.service.ChatService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatServiceImpl implements ChatService {

    @Qualifier("openAiRestTemplate")
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;
    @Value("${openai.api.url}")
    private String url;

    public ChatServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String sendMessage(String message) {
        ChatRequest chatRequest = new ChatRequest(model,message);
        ChatResponse chatResponse = restTemplate.postForObject(url,chatRequest, ChatResponse.class);

        if (chatResponse != null) {
            return chatResponse.getChoices().get(0).getMessage().getContent();
        }else {
            throw new NullPointerException("There is no message present...");
        }
    }
}
