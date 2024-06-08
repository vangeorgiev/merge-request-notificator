package com.mergerequestnotificator.transport.data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "slack")
public class TeamChannel {

    private Map<String, String> channel;

    public Map<String, String> getChannel(){
        return channel;
    }

    public void setChannel(Map<String, String> channel) {
        this.channel = channel;
    }
}
