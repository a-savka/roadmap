package ru.savka.demo.dto;

public class AppMessageDto {

    private Long id;
    private String messageKey;
    private String messageText;
    private String messageTextEn;
    private String category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageTextEn() {
        return messageTextEn;
    }

    public void setMessageTextEn(String messageTextEn) {
        this.messageTextEn = messageTextEn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
