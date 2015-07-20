package com.icefire.common.network;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;

import com.icefire.common.exception.SoapError;

public class JsonRequest extends Request<JSONObject> {

	Map<String, Object> mParam = null;
	String mMethod = null;
	
	public JsonRequest(String url, String namespace, String method,
			Map<String, Object> param, RequestResultCallback<JSONObject> listener){
		this.mUrl = url;
		this.mNamespace = namespace;
		this.mMethod = method;
		this.mParam = param;
		this.mCallbackListener = listener;
	}
	
	public JsonRequest(String method, Map<String, Object> param,
			RequestResultCallback<JSONObject> listener){
		this.mMethod = method;
		this.mParam = param;
		this.mCallbackListener = listener;
	}
	
	@Override
	public void run() {
		SoapObject rpc = new SoapObject(mNamespace, mMethod);
		if (mParam != null) {
			for (String key : mParam.keySet()) {
				rpc.addProperty(key, mParam.get(key));
			}
		}
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(rpc);
		// http对象
		HttpTransportSE ht = new HttpTransportSE(mUrl, mConnectTimeout);
		try {
			ht.call(null, envelope);
		} catch (HttpResponseException e) {
			mCallbackListener.onFail(404,new SoapError("Time out", e));
			return;
		} catch (Exception e) {
			mCallbackListener.onFail(403,new SoapError("Other exception", e));
		}
		if (envelope.bodyIn != null && envelope.bodyIn instanceof SoapObject) {
			SoapObject response = (SoapObject) envelope.bodyIn;
			String str = response.getProperty(0).toString();
			JSONObject json;
			try {
				json = new JSONObject(str);
			} catch (JSONException e) {
				mCallbackListener.onFail(601,new SoapError("Data parse exception", e));
				return;
			}
			mCallbackListener.onSuccess(json);
		} else {
			mCallbackListener.onFail(505,new SoapError("Respone error"));
		}
	}
}
