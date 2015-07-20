package com.icefire.common.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@SuppressWarnings("serial")
public class SoapError extends Exception {

	public SoapError() {
	}
	
	public SoapError(String error){
		super(error);
	}
	
	public SoapError(Throwable e){
		super(e);
	}
	
	public SoapError(String error,Throwable e){
		super(error, e);
	}
	
	public String printErrorDetail(){
		String res="--";
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);  
		this.printStackTrace(pw);
		res=sw.toString();
		try {
			sw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pw.close();
		return res;
	}
}
