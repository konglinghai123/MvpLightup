package com.dawnlightning.ucqa.db;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceDb {

	private Context context;
	
	public SharedPreferenceDb(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
	}
	
	public void setIsFirstInstall(){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_IS_INSTALL, Context.MODE_PRIVATE);
		sp.edit().putBoolean(DbConstants.KEY_IS_INSTALL, true).commit();
	}
	
	public boolean getIsFirstInstall(){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_IS_INSTALL, Context.MODE_PRIVATE);
		return sp.getBoolean(DbConstants.KEY_IS_INSTALL, false);
	}
	
	public void setChangeTheme(int bgColor){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_CHANGE_THEME, Context.MODE_PRIVATE);
		sp.edit().putInt("bgColor", bgColor).commit();
	}
	
	public int getChangeTheme(){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_CHANGE_THEME, Context.MODE_PRIVATE);
		int bgColor=sp.getInt("bgColor", 0);
		return bgColor;
	}
	
	public void setOpenAnimation(){//开
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_ANIMATION, Context.MODE_PRIVATE);
		sp.edit().putBoolean(DbConstants.KEY_ANIMATION, true).commit();
	}
	
	public void setCloseAnimation(){//关
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_ANIMATION, Context.MODE_PRIVATE);
		sp.edit().putBoolean(DbConstants.KEY_ANIMATION, false).commit();
	}
	
	public boolean getAnimation(){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_ANIMATION, Context.MODE_PRIVATE);
		return sp.getBoolean(DbConstants.KEY_ANIMATION, false);
	}
	
	
	public void setUserId(String userId){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_ID, Context.MODE_PRIVATE);
		sp.edit().putString(DbConstants.KEY_USER_ID, userId).commit();
	}
	
	public String getUserId(){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_ID, Context.MODE_PRIVATE);
		return sp.getString(DbConstants.KEY_USER_ID, "");
	}
	
	public String getName(){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
		return sp.getString(DbConstants.KEY_USER_NAME, "");
	}
	public void setName(String name){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
		sp.edit().putString(DbConstants.KEY_USER_NAME, name).commit();
	}
	public void setAVATOR(String url){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_DEFAULT_AVATOR, Context.MODE_PRIVATE);
		sp.edit().putString(DbConstants.KEY_DEFAULT_AVATOR, url).commit();
	}
	public String getAVATOR(){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_DEFAULT_AVATOR, Context.MODE_PRIVATE);
		return sp.getString(DbConstants.KEY_DEFAULT_AVATOR, "");
	}
	
	public void setuserAVATOR(String url){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_AVATOR, Context.MODE_PRIVATE);
		sp.edit().putString(DbConstants.KEY__AVATOR, url).commit();
	}
	public String getuserAVATOR(){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_AVATOR, Context.MODE_PRIVATE);
		return sp.getString(DbConstants.KEY__AVATOR, "");
	}
	
	public void setuserAutoLogin(Boolean IsAutoLogin){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_lOGIN, Context.MODE_PRIVATE);
		sp.edit().putBoolean(DbConstants.KEY__LOGIN, IsAutoLogin).commit();
	}
	public Boolean getAutoLogin(){
		Boolean isAutoLogin =true;
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_lOGIN, Context.MODE_PRIVATE);
		return sp.getBoolean(DbConstants.KEY__LOGIN, isAutoLogin);
	}

	public void setPush(Boolean isOpen){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_PUSH, Context.MODE_PRIVATE);
		sp.edit().putBoolean(DbConstants.KEY__PUSH, isOpen).commit();
	}
	public Boolean getPush(){

		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_PUSH, Context.MODE_PRIVATE);
		return sp.getBoolean(DbConstants.KEY__PUSH,true);
	}
	
	public void setformhash(String formhash){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_lOGIN, Context.MODE_PRIVATE);
		sp.edit().putString(DbConstants.KEY_FORMHASH, formhash).commit();
	}
	public String getformhash(){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_lOGIN, Context.MODE_PRIVATE);
		return sp.getString(DbConstants.KEY_FORMHASH, "");
	}
	public void setage(String age){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_lOGIN, Context.MODE_PRIVATE);
		sp.edit().putString(DbConstants.KEY_AGE, age).commit();
	}
	public String getage(){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_lOGIN, Context.MODE_PRIVATE);
		return sp.getString(DbConstants.KEY_AGE, "");
	}
	
	public void setsex(String sex){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_lOGIN, Context.MODE_PRIVATE);
		sp.edit().putString(DbConstants.KEY_SEX, sex).commit();
	}
	public String getsex(){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_lOGIN, Context.MODE_PRIVATE);
		return sp.getString(DbConstants.KEY_SEX, "");
	}
	public void setname(String name){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_lOGIN, Context.MODE_PRIVATE);
		sp.edit().putString(DbConstants.KEY_NAME, name).commit();
	}
	public String getname(){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_lOGIN, Context.MODE_PRIVATE);
		return sp.getString(DbConstants.KEY_NAME, "");
	}

	public void setm_auth(String m_auth){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_lOGIN, Context.MODE_PRIVATE);
		sp.edit().putString(DbConstants.KEY_M_AUTH, m_auth).commit();
	}
	public String getm_auth(){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_lOGIN, Context.MODE_PRIVATE);
		return sp.getString(DbConstants.KEY_M_AUTH, "");
	}
	public void setIsUpdateShow(Boolean Is){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_IS_INSTALL, Context.MODE_PRIVATE);
		sp.edit().putBoolean(DbConstants.KEY_UPDATESHOW, Is).commit();
	}
	
	public boolean getIsUpdateShow(){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_IS_INSTALL, Context.MODE_PRIVATE);
		return sp.getBoolean(DbConstants.KEY_UPDATESHOW, false);
	}


	public void setPassword(String password){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_lOGIN, Context.MODE_PRIVATE);
		sp.edit().putString(DbConstants.KEY_PASSWORD, password).commit();
	}
	public String getPassword(){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_lOGIN, Context.MODE_PRIVATE);
		return sp.getString(DbConstants.KEY_PASSWORD, "");
	}

	public void setPhone(String phone){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_lOGIN, Context.MODE_PRIVATE);
		sp.edit().putString(DbConstants.KEY_PHONE, phone).commit();
	}
	public String getPhone(){
		SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_lOGIN, Context.MODE_PRIVATE);
		return sp.getString(DbConstants.KEY_PHONE, "");
	}
}
