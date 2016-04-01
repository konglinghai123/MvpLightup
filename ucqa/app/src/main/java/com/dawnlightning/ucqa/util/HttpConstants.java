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

	/*上传用户头像*/
	public final static String HTTP_UPLOAD_AVATAR=HTTP_HEAD+HTTP_IP+"ucenter/index.php?a=uploadavatar4m&m=user&agent=!&avatartype=@&input=#&appid=$&inajax=1";
	
	/*修改用户资料*/
	public final static String HTTP_EDIT_USEINFO=HTTP_REQUEST+"cp.php?ac=profile&m_auth=!&op=base";
	
	/*改变咨询状态*/
	public final static String HTTP_EDIT_CONSULT_STAUTS=HTTP_REQUEST+"cp.php?ac=bwzt&bwztid=!&m_auth=@&bwztsubmit=true&status=1&op=alterstatus";
	
	/*获取用户头像*/
	public final static String HTTP_GET_USERICON=HTTP_REQUEST+"cp.php?ac=avatar&m_auth=!&get_avatar=true&avatar_size=middle";
	

	
	public final static String Update="https://ucqa.dawnlightning.com/m/version.xml";
	
}
