package com.example.apptaphoa.model;

import java.util.List;

public class Sanphamnewmodel {
    boolean success;
    String message;
    List<Sanphamnew> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Sanphamnew> getResult() {
        return result;
    }

    public void setResult(List<Sanphamnew> result) {
        this.result = result;
    }
}
