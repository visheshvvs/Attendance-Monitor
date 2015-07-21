package com.darkdroider.attendancetracker;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.darkdroider.models.Subject;
import com.darkdroider.models.Timing;

public class DatabaseHandler extends SQLiteOpenHelper
{
	private static final int DATABASE_VERSION = 1;
	//database name
	private static final String DATABASE_NAME = "attendanceManager";
	//tables name
	private static final String TABLE_MASTER = "masterTable";
	private static final String TABLE_TIME = "_TIME";
	private static final String TABLE_HISTORY = "_HISTORY";
	
	//COLUMN NAMES
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_PRESENTS = "presents";
	private static final String KEY_ABSENTS = "absents";
	private static final String KEY_TIME = "time";
	private static final String KEY_HISTORY = "history";
	
	private static final String CREATE_TABLE_MASTER = "CREATE TABLE "+TABLE_MASTER+"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
			+KEY_NAME+" TEXT,"+KEY_PRESENTS+" INTEGER,"+KEY_ABSENTS+" INTEGER"+")";
	public DatabaseHandler(Context context)
	{
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL(CREATE_TABLE_MASTER);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		db.execSQL("DROP TABLE IF EXISTS "+CREATE_TABLE_MASTER);
		onCreate(db);
	}
	public void setPresent(String name)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(TABLE_MASTER, new String[] {KEY_PRESENTS},
				KEY_NAME+"=?", new String[] {name}, null,null,null,null);
		if(cursor!=null)
			cursor.moveToFirst();
		int p=Integer.parseInt(cursor.getString(0));
		p++;
		String query = "UPDATE "+TABLE_MASTER+" SET "+KEY_PRESENTS+" = "+ Integer.toString(p)+" WHERE "+KEY_NAME+" = '"+ name+"'";
		db.execSQL(query);
		db.close();
		
	}
	
	public void setAbsent(String name)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(TABLE_MASTER, new String[] {KEY_ABSENTS},
				KEY_NAME+"=?", new String[] {name}, null,null,null,null);
		if(cursor!=null)
			cursor.moveToFirst();
		int a=Integer.parseInt(cursor.getString(0));
		a++;
		String query = "UPDATE "+TABLE_MASTER+" SET "+KEY_ABSENTS+" = "+ Integer.toString(a)+" WHERE "+KEY_NAME+" = '"+ name+"'";
		db.execSQL(query);
		db.close();
		
	}
	public int getPresent(String name)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(TABLE_MASTER, new String[] {KEY_PRESENTS},
				KEY_NAME+"=?", new String[] {name}, null,null,null,null);
		if(cursor!=null)
			cursor.moveToFirst();
		int p=Integer.parseInt(cursor.getString(0));
		return p;
	}
	public int getAbsent(String name)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(TABLE_MASTER, new String[] {KEY_ABSENTS},
				KEY_NAME+"=?", new String[] {name}, null,null,null,null);
		if(cursor!=null)
			cursor.moveToFirst();
		int a=Integer.parseInt(cursor.getString(0));
		return a;
	}
	public void addSubject(Subject subject)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, subject.getName());
		values.put(KEY_PRESENTS, 0);
		values.put(KEY_ABSENTS, 0);
		db.insert(TABLE_MASTER,null,values);
		final String CREATE_TABLE_TIME = "CREATE TABLE "+subject.getName()+TABLE_TIME+"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
				+KEY_TIME+" INTEGER"+")";
		final String CREATE_TABLE_HISTORY = "CREATE TABLE "+subject.getName()+TABLE_HISTORY+"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
				+KEY_HISTORY+" TEXT"+")";
		db.execSQL(CREATE_TABLE_TIME);
		db.execSQL(CREATE_TABLE_HISTORY);
		db.close();
	}
	
	public void addHistory(String name, String status)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_HISTORY, status);
		db.insert(name+TABLE_HISTORY, null, values);
		db.close();
	}
	
	public ArrayList<String> getAllHistory(String name)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ArrayList<String> list = new ArrayList<String>();
		String selectQuery = "SELECT * FROM "+name+TABLE_HISTORY;
		Cursor cursor = db.rawQuery(selectQuery, null);
		if(cursor.moveToFirst())
		{
			do
			{
				list.add(cursor.getString(1));
			}while(cursor.moveToNext());
		}
		return list;
	}
	public void addTiming(String name,Timing timing)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_TIME,timing.getTiming());
		db.insert(name+TABLE_TIME,null,values);
		db.close();
	}
	public Subject getSubject(int id)
	{
		Subject subject=null;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_MASTER, new String[] {KEY_ID,  KEY_NAME,  KEY_PRESENTS, KEY_ABSENTS},
		KEY_ID+"=?", new String[] {String.valueOf(id)}, null,null,null,null);
		if(cursor!=null)
		{
		cursor.moveToFirst();
		subject = new Subject();
		subject.setId(Integer.parseInt(cursor.getString(0)));
		subject.setName(cursor.getString(1));
		subject.setPresents(Integer.parseInt(cursor.getString(2)));
		subject.setAbsents(Integer.parseInt(cursor.getString(3)));
		}
		return subject;
	}
	public ArrayList<Subject> getAllSubjects()
	{
		ArrayList<Subject> subs = new ArrayList<Subject>();
		String selectQuery = "SELECT * FROM "+TABLE_MASTER;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if(cursor.moveToFirst())
		{
			do
			{
				Subject subject = new Subject();
				subject.setId(Integer.parseInt(cursor.getString(0)));
				subject.setName(cursor.getString(1));
				subject.setPresents(Integer.parseInt(cursor.getString(2)));
				subject.setAbsents(Integer.parseInt(cursor.getString(3)));
				subs.add(subject);
			}while(cursor.moveToNext());
			
		}
		
		return subs;
	}
	public ArrayList<Timing> getAllTimings(String name)
	{
		ArrayList<Timing> timings = new ArrayList<Timing>();
		String selectQuery = "SELECT * FROM "+name+TABLE_TIME;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if(cursor.moveToFirst())
		{
			do
			{
				Timing timing = new Timing(Integer.parseInt(cursor.getString(1)));
				timings.add(timing);
			}while(cursor.moveToNext());
			
		}
		
		return timings;
	}
	public int getSubjectsCount()
	{
		String countQuery = "SELECT * FROM "+TABLE_MASTER;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		
		return cursor.getCount();
	}
	
	public void deleteAllTables()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ArrayList<Subject> subjects = new ArrayList<Subject>();
		subjects = getAllSubjects();
		for(int i=0;i<subjects.size();i++)
		{
			db.execSQL("DROP TABLE IF EXISTS "+subjects.get(i).getName()+TABLE_TIME);
			db.execSQL("DROP TABLE IF EXISTS "+subjects.get(i).getName()+TABLE_HISTORY);
			db.delete(TABLE_MASTER, KEY_NAME + " = ?",
			            new String[] { subjects.get(i).getName() });
		}
		db.close();
		
		
	}
	public void deletesubject(String string) {
		// TODO Auto-generated method stub
		SQLiteDatabase db=this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS "+string+TABLE_HISTORY);
		db.execSQL("DROP TABLE IF EXISTS "+string+TABLE_TIME);
		db.delete(TABLE_MASTER, KEY_NAME + " = ?", new String[] { string });
		
	}


}
