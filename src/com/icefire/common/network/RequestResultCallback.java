package com.icefire.common.network;

import com.icefire.common.exception.SoapError;

public interface RequestResultCallback<T> {
	public void onSuccess(T success);
	public void onFail(int errorCode,SoapError e);
}
