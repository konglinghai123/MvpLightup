package com.dawnlightning.ucqa.util;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;

import com.dawnlightning.ucqa.base.Code;
import com.dawnlightning.ucqa.base.MyApp;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.ParseException;

public class AsyncHttp {
	private static AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
	private static  ACache mCache=ACache.get(MyApp.getApp());
	public static void get(String url,RequestParams params,JsonHttpResponseHandler responseHandler){

		client.get(url, params, responseHandler);
	}

	public static void post(String url,RequestParams params,JsonHttpResponseHandler responseHandler){
		client.post(url, params, responseHandler);
	}
	public static void stopresquest(){
		client.cancelAllRequests(true);
	}
	public static ACache getmCache(){
		return mCache;
	}
	public static void savecache(String url,String object,int duetime){
		mCache.put(url,object,duetime);
	}
	public static String getcache(String url){
		return mCache.getAsString(url);
	}
	public static void removecache(String url){
		mCache.remove(url);
	}
}

