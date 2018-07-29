package com.outsource.model;

import java.io.Serializable;

/**
 * @author chuanchen
 */
public class JsonResponse<T> implements Serializable{
    private static final long serialVersionUID = -1L;

    private Integer statusCode;
    private String errorMessage;
    private T response;

    public JsonResponse(Integer statusCode, String errorMessage){
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    public JsonResponse(T response, Integer statusCode){
        this.statusCode = statusCode;
        this.response = response;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "JsonResponse{" +
                "statusCode=" + statusCode +
                ", errorMessage='" + errorMessage + '\'' +
                ", response=" + response +
                '}';
    }
}
