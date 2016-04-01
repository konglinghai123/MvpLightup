package com.dawnlightning.ucqa.util;
import org.json.JSONObject;
import android.widget.ProgressBar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class AsyncHttp {
	private static AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);  
	
	public static void get(String url,RequestParams params,JsonHttpResponseHandler responseHandler){
	
         
         
		
		client.get(url, params, responseHandler);
	}
	
	public static void post(String url,RequestParams params,JsonHttpResponseHandler responseHandler){
		client.post(url, params, responseHandler);
	}
	
	public static void upload(String url,RequestParams params,final ProgressBar progress){
		   client.post(url, params, new JsonHttpResponseHandler() {

			   @Override
			   public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				   super.onSuccess(statusCode, headers, response);
				   progress.setProgress(0);
			   }

			   @Override
			   public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				   super.onFailure(statusCode, headers, throwable, errorResponse);
			   }

			   @Override
			public void onProgress(long bytesWritten, long totalSize) {
				// TODO 自动生成的方法存根
				super.onProgress(bytesWritten, totalSize);
				 int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
		            // 上传进度显示


			}




		   });



	}
	
}

