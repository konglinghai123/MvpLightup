package com.dawnlightning.ucqa.base;

/**
 * Created by Administrator on 2016/4/8.
 */
public class Code {
    public static final int PAGESIZE = 10;//页数
    public static final int SERVER_LOAD_FAILURE = -1;//服务器无响应
    public static final int LOAD_FAILURE = 1;//服务器有响应但是失败
    public static final int LOAD_FULL_SUCCESS = 0;//加载成功，可能有下一页
    public static final int LOAD_NOFULL_SUCCESS = 2;//加载成功但是没有下一页
    public static final int LOAD_NODATA = 3;//没有咨询


    public static  final int CHANGE=2;//切换分类刷新
    public static  final int REFRESH=0;//刷新
    public static  final int LOADMORD=1;//加载更多

    public static final  int REPLY=0;//回复
    public static final  int COMMENT=1;//评论

    public static final int LOADMORECOMMENT=0;//加载更多的评论
    public static final int INITCOMMENT=1;//开始加载评论列表

    public static final int ME=0;//我的咨询列表
    public static final int ALL=1;//全部的咨询列表

    public static  final  int NONEXTPAGE=3;//没有下一页
    public static  final  int HAVENEXTPAGE=4;//没有下一页

    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";//推送服务信息
    public static final String KEY_TITLE = "title";//推送标题
    public static final String KEY_MESSAGE = "message";//推送信息
    public static final String KEY_EXTRAS = "extras";//推送实体
    public static final int MSG_SET_ALIAS = 1001;//推送执行命令
    public static final String TAG = "JPush";//推送调试标签

    public static final int DetailForResult=1;//咨询详细的Activity回调
    public static final int DetailForResultDelete=2;//咨询详细的Activity回调删除咨询
    public static final int DetailForResultSolve=3;//咨询详细的Activity回调采纳咨询
    public static final int ConsultForResult=4;//发布咨询成功后的回调
    public static final int LoginoffForResult=5;//注销登陆
    public static final int ModifyPersonaldataForResult=6;//修改用户资料;
    public static final int UPLOADCHANGE=1;//图片正在上传中
    public static final int UPLOADSUCCESS=0;//图片上传成功
    public static final int UPLOADFAILURE=-1;//图片上传失败

}
