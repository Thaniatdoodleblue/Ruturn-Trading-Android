/**
 * 
 */
package com.returntrader.model.webservice.executor;

public interface OnNetworkCallListener<T> {


	void onSuccess(long taskId, T response);

    /**
     *
     * @param taskId corresponding id of the task
     * @param progress corresponding progress of the task
     */
	void onProgress(long taskId, int progress);

    /**
     *
     * @param taskId corresponding id of the task
     * @param exception corresponding exception of the task
     */
	void onFailure(long taskId, Exception exception);

}