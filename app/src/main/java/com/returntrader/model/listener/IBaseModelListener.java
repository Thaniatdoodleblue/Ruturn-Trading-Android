package com.returntrader.model.listener;


import com.returntrader.library.CustomException;


public interface IBaseModelListener<T> {

    public void onSuccessfulApi(long taskId, T response);

    public void onFailureApi(long taskId, CustomException e);
}
