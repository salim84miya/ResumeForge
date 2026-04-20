package com.springai.resumax.ai.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;

@Slf4j
public class CustomLoggingAdvisor implements CallAdvisor {

    private Logger logger = LoggerFactory.getLogger(CustomLoggingAdvisor.class.getName());

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {

        logger.info("prompt {}",chatClientRequest.prompt().getContents());

        ChatClientResponse response = callAdvisorChain.nextCall(chatClientRequest);

        return response;
    }

    @Override
    public String getName() {
        return CustomLoggingAdvisor.class.getName();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
