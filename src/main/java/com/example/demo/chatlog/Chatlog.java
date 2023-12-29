package com.example.demo.chatlog;

public class Chatlog implements Comparable<Chatlog> {

    private String messageId;
    private String message;
    private Long timestamp;
    private Boolean isSent;

    public Chatlog() {
    }

    public Chatlog(String message, Long timestamp, Boolean isSent) {
        this.message = message;
        this.timestamp = timestamp;
        this.isSent = isSent;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getIsSent() {
        return isSent;
    }

    public void setIsSent(Boolean sent) {
        isSent = sent;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return "Chatlog{" +
                "messageId='" + messageId + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", isSent=" + isSent +
                '}';
    }

    @Override
    public int compareTo(Chatlog chatlog) {
        return Long.compare(chatlog.timestamp, this.timestamp);
    }
}
