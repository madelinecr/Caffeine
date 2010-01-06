package info.bpace.caffeine;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter
{
	private static final String DATABASE_NAME = "caffeine_db";
	private static final int DATABASE_VERSION = 2;
	
	private class DBHelper extends SQLiteOpenHelper
	{
		DBHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			
		}
	}
	
}