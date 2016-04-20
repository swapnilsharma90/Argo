package com.dd.argo.model;

/**
 * Created by jkarkhanis on 30/03/16.
 */
public class ChatItem {

    private String chatText;
    private boolean isUsermessage;

    public ChatItem(String chatText,boolean isUsermessage) {
        this.chatText = chatText;
        this.isUsermessage=isUsermessage;
    }

    public String getChatText() {
        return chatText;
    }

    public void setChatText(String chatText) {
        this.chatText = chatText;
    }

    public boolean isUsermessage() {
        return isUsermessage;
    }

    public void setUsermessage(boolean usermessage) {
        isUsermessage = usermessage;
    }
}
