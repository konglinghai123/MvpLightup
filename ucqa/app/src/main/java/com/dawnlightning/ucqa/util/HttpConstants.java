package com.dawnlightning.ucqa.util;

public class HttpConstants {

	
	public final static String HTTP_HEAD="https://";
	
	
	
	//外网
	public final static String HTTP_IP="ucqa.dawnlightning.com/";
	
	public final static String HTTP_CONTEXT="/capi/";
	
	public final static String HTTP_REQUEST=HTTP_HEAD+HTTP_IP+HTTP_CONTEXT;

	/*未登录用户操作*/
	public final static String HTTP_LOGIN=HTTP_REQUEST+"do.php?";//登陆
	/*用户对咨询的操作*/
	public final static String HTTP_COMMENT=HTTP_REQUEST+"cp.php?";
	/*用户登录后的动作*/
	public final static String HTTP_SELECTION=HTTP_REQUEST+"space.php?";

	public final static String Update="https://ucqa.dawnlightning.com/m/index.php/version.json";

	
}
