package info.bpace.caffeine;

import android.content.Context;
import android.util.Log;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter
{
	private static final String DATABASE_NAME = "caffeine_db";
	private static final String DATABASE_TABLE = "drinks";
	private static final int DATABASE_VERSION = 1;
	
	public static final String KEY_TITLE = "title";
	public static final String KEY_BODY = "body";
	public static final String KEY_ROWID = "_id";
	private static final String TAG = "CaffeineDBAdapter";

	private static final String DATABASE_CREATE =
 		"create table notes (_id integer primary key autoincrement, "
		+ "title text not null, body text not null);";
	
	/**
	 * Private helper class to perform basic database management
	 */
	private class DBHelper extends SQLiteOpenHelper
	{
		DBHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			db.execSQL(DATABASE_CREATE);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS notes");
			onCreate(db);
		}
	}
	
}