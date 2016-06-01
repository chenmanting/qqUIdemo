package com.cmt.qq.tool;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

	public static final String CREATE_USER = "create table User ("
			+ "id integer primary key autoincrement, " 
			+ "username text, "
			+ "password text, "
			+ "sex text, " 
			+ "name text)";

	public MyDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_USER);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists User");
		onCreate(db);
	}

	public Cursor queryUser(SQLiteDatabase db, String username, String password){
		return db.rawQuery("select * from User where 1=1 and username = ? and password =?",
				new String[]{username,password});
	}
	
	public String queryPassword(SQLiteDatabase db, String username){
		Cursor cursor = db.rawQuery("select * from User where 1=1 and username = ?",
				new String[]{username});
		if(cursor.moveToFirst())
		return cursor.getString(2);
		else return "";
	}
	
	public void addUser(SQLiteDatabase db,String username,String password){
		db.execSQL("insert into User values(null,?,?,null,null)", new String[] {
				username, password});
	}
	
	public void updatePassword(SQLiteDatabase db,String username, String newPassword){
		db.execSQL("update User set password=? where username=?", 
				new String[]{newPassword, username});
	}
	
}
