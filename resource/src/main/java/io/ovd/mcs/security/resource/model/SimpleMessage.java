package io.ovd.mcs.security.resource.model;

import java.util.UUID;

/**
 * Created by sergey.ovdienko on 18.07.2016.
 */
public class SimpleMessage {
    private String id = UUID.randomUUID().toString();
    private String content;

    SimpleMessage() {
    }

    public SimpleMessage(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}