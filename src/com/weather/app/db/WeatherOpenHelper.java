package com.weather.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class WeatherOpenHelper extends SQLiteOpenHelper {

	/** Province建表语句 */
	public static final String CREATE_PROVINCE = "create table province("
			+ "id integer primary key autoincrement,"
			+ "province_name text,"
			+ "province_code text)";

	/** City建表语句 */
	public static final String CREATE_CITY = "create table city("
			+ "in integer primary key autoincrement,"
			+ "city_name text,"
			+ "city_code text,"
			+ "province_id integer)";

	/** County建表语句 */
	public static final String CREATE_COUNTY = "create table county("
			+ "id integer primary key autoincrement,"
			+ "county_name text,"
			+ "county_cody text,"
			+ "city_id integer)";

	
	
	public WeatherOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		/**
		 * 创建省，市，县三张表
		 * */
		db.execSQL(CREATE_CITY);
		db.execSQL(CREATE_COUNTY);
		db.execSQL(CREATE_PROVINCE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
