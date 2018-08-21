package com.returntrader.model.listener;


import com.returntrader.library.CustomException;

/**
 * Created by guru on 1/6/2017.
 */

public interface IModelListener<T> {

public void onSuccessfulApi(long taskId, T response);

   public void onFailureApi(long taskId, CustomException e);
}
