package com.weather.app.db;
/**
 * 该类封装了一些常用的数据库操作,以方便后期使用
 * 
 * */


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.weather.app.entity.City;
import com.weather.app.entity.County;
import com.weather.app.entity.Province;

public class WeatherDB {
	/** 数据库名*/
	public static final String DB_NAME = "weather_DB";
	
	/** 数据库版本*/
	public static final int VERSION = 1;
	
	private static WeatherDB weatherDB;
	private SQLiteDatabase sdb;
	
	/** 将构造方法私有化*/
	private WeatherDB(Context context){
		WeatherOpenHelper woh = new WeatherOpenHelper(context, DB_NAME, null,
				VERSION);
		sdb = woh.getWritableDatabase();
	}
	
	/** 获取WeatherDB实例*/
	public synchronized static WeatherDB getInstance(Context context){
		if (weatherDB == null) {
			weatherDB = new WeatherDB(context);
		}
		return weatherDB;
	}
	
	/** 将Province实例存储到数据库*/
	public void saveProvince(Province province){
		if (province != null) {
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			sdb.insert("province", null, values);
		}
	}
	
	/** 从数据库读取全国所有的省份信息*/
	public List<Province> loadProvinces(){
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = sdb.query("Province", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}
	
	/** 将city实例存储到数据库*/
	public void saveCity(City city){
		if (city != null) {
			ContentValues values = new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			sdb.insert("city", null, values);
		}
	}
	
	/** 从数据库读取省下所有城市的信息*/
	public List<City> loadCities(int provinceId){
		List<City> list = new ArrayList<City>();
		Cursor cursor = sdb.query("city", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceId(cursor.getInt(cursor.getColumnIndex("provinceId")));
				list.add(city);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}
	
	/** 将county实例存储到数据库*/
	public void saveCounty(County county){
		ContentValues values = new ContentValues();
		values.put("county_name", county.getCountyName());
		values.put("county_code", county.getCountyCode());
		values.put("city_id", county.getCityId());
		sdb.insert("city", null, values);
	}
	
	/** 从数据库读取市下所有县*/
	public List<County> loadCounty(int cityId){
		List<County> list = new ArrayList<County>();
		Cursor cursor = sdb.query("city", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
				county.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
				list.add(county);
			} while (cursor.moveToNext());
		}
		
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}
}
