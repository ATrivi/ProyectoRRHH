package com.example.proyectoRecursosHumanos.dto;

public class NotificationRequest {

        private String userName;
        private String message;
        private String type;

    public NotificationRequest(String userName, String message, String type) {
        this.userName = userName;
        this.message = message;
        this.type = type;
    }

    public NotificationRequest() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
