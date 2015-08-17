package com.weather.app.db;
/**
 * �����װ��һЩ���õ����ݿ����,�Է������ʹ��
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
	/** ���ݿ���*/
	public static final String DB_NAME = "weather_DB";
	
	/** ���ݿ�汾*/
	public static final int VERSION = 1;
	
	private static WeatherDB weatherDB;
	private SQLiteDatabase sdb;
	
	/** �����췽��˽�л�*/
	private WeatherDB(Context context){
		WeatherOpenHelper woh = new WeatherOpenHelper(context, DB_NAME, null,
				VERSION);
		sdb = woh.getWritableDatabase();
	}
	
	/** ��ȡWeatherDBʵ��*/
	public synchronized static WeatherDB getInstance(Context context){
		if (weatherDB == null) {
			weatherDB = new WeatherDB(context);
		}
		return weatherDB;
	}
	
	/** ��Provinceʵ���洢�����ݿ�*/
	public void saveProvince(Province province){
		if (province != null) {
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			sdb.insert("province", null, values);
		}
	}
	
	/** �����ݿ��ȡȫ�����е�ʡ����Ϣ*/
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
	
	/** ��cityʵ���洢�����ݿ�*/
	public void saveCity(City city){
		if (city != null) {
			ContentValues values = new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			sdb.insert("city", null, values);
		}
	}
	
	/** �����ݿ��ȡʡ�����г��е���Ϣ*/
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
	
	/** ��countyʵ���洢�����ݿ�*/
	public void saveCounty(County county){
		ContentValues values = new ContentValues();
		values.put("county_name", county.getCountyName());
		values.put("county_code", county.getCountyCode());
		values.put("city_id", county.getCityId());
		sdb.insert("city", null, values);
	}
	
	/** �����ݿ��ȡ����������*/
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
