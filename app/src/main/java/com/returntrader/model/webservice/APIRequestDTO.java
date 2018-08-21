package com.returntrader.model.webservice;


import com.returntrader.model.webservice.common.ContentUrl;

/**
 * Created by Guru karthik on 26-11-2016.
 */

public class APIRequestDTO<T> {

    private ContentUrl contentUrl;

    private Object contentRequest;

    private RequestType requestType = RequestType.GET;

    private Class<T> classType;

    private ResponseFormat responseFormat = ResponseFormat.JSON;

    public static enum ResponseFormat {
        JSON, XML
    }

    public static enum RequestType {
        GET, POST
    }

    public APIRequestDTO() {}

    public APIRequestDTO(ContentUrl url, Object contentRequest, Class<T> classType) {
        this.contentRequest = contentRequest;
        this.contentUrl = url;
        this.classType = classType;
    }

    public APIRequestDTO(ContentUrl url, Object contentRequest, RequestType requestType, Class<T> classType, ResponseFormat responseFormat) {
        this.contentUrl = url;
        this.contentRequest = contentRequest;
        this.requestType = requestType;
        this.classType = classType;
        this.responseFormat = responseFormat;
    }

    public APIRequestDTO(ContentUrl url, Object contentRequest, RequestType requestType, Class<T> classType) {
        this.contentUrl = url;
        this.contentRequest = contentRequest;
        this.requestType = requestType;
        this.classType = classType;
    }

    /**
     * @return the url
     */
    public ContentUrl getContentUrl() {
        return contentUrl;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setContentUrl(ContentUrl url) {
        this.contentUrl = url;
    }

    public Object getContentRequest() {
        return contentRequest;
    }

    public void setContentRequest(Object contentRequest) {
        this.contentRequest = contentRequest;
    }

    /**
     * @return the requestType
     */
    public RequestType getRequestType() {
        return requestType;
    }

    /**
     * @param requestType
     *            the requestType to set
     */
    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    /**
     * @return the classType
     */
    public Class<T> getClassType() {
        return classType;
    }

    /**
     * @param classType
     *            the classType to set
     */
    public void setClassType(Class<T> classType) {
        this.classType = classType;
    }

    public ResponseFormat getResponseFormat() {
        return responseFormat;
    }

    public void setResponseFormat(ResponseFormat responseFormat) {
        this.responseFormat = responseFormat;
    }

}
