package com.github.niqdev.ipcam.settings;

import java.util.Date;
public class ChatMessage {

    private String text;
    private String name;
    private long timestamp;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public ChatMessage(String text, String name) {
        this.text = text;
        this.name = name;

        // Initialize to current time
        timestamp = new Date().getTime();
    }

    public ChatMessage(){

    }

   /* public String getMessageText() {
        return text;
    }

    public void setMessageText(String text) {
        this.text = text;
    }

    public String getMessageUser() {
        return name;
    }

    public void setMessageUser(String name) {
        this.name = name;
    }

    public long getMessageTime() {
        return timestamp;
    }

    public void setMessageTime(long timestamp) {
        this.timestamp = timestamp;
    }*/
}
