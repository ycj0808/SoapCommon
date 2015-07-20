package com.icefire.common.network;

public class Request<T> implements Runnable{

	protected String mUrl = null;
	protected int mConnectTimeout = 30000;
	protected String mNamespace="";
	
	protected RequestResultCallback<T> mCallbackListener;
	@Override
	public void run() {
	}
	
	/**
	 * 设置连接超时时间
	 * @param connectTimeout
	 */
	protected void setConnectTimeout(int connectTimeout) {
		this.mConnectTimeout = connectTimeout;
	}
	
	/**
	 * 设置命名空间
	 * @param nameSpace
	 */
	protected void setNameSpace(String nameSpace){
		this.mNamespace=nameSpace;
	}
	
	protected void setRequestUrl(String url){
		this.mUrl=url;
	}
}
