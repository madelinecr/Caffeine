package info.bpace.caffeine;

import android.content.Context;
import android.database.Cursor;
import android.content.ContentValues;
import android.util.Log;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;

public class DBAdapter
{
	// static block
	private static final String DATABASE_NAME = "caffeine_db";
	private static final String DATABASE_TABLE = "drinks";
	private static final int DATABASE_VERSION = 1;
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_INGREDIENTS = "ingredients";
	public static final String KEY_BODY = "body";
	private static final String TAG = "CaffeineDBAdapter";

	private static final String DATABASE_CREATE =
 		"create table drinks (_id integer primary key autoincrement, "
		+ "title text not null, ingredients text not null, body text not null);";
	
	private final Context mContext;
	private DBHelper mDBHelper;
	private SQLiteDatabase mDB;
	
	public DBAdapter(Context context)
	{
		mContext = context;
	}
	
	public DBAdapter open() throws SQLException
	{
		mDBHelper = new DBHelper(mContext);
		mDB = mDBHelper.getWritableDatabase();
		return this;
	}
	
	public void close()
	{
		mDBHelper.close();
	}
	
	public long createEntry(String title, String ingredients, String body)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TITLE, title);
		initialValues.put(KEY_INGREDIENTS, ingredients);
		initialValues.put(KEY_BODY, body);
		
		return mDB.insert(DATABASE_TABLE, null, initialValues);
	}
	
	public Cursor readAll()
	{
		return mDB.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE,
			KEY_INGREDIENTS, KEY_BODY}, null, null, null, null, null);
	}
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
			Log.w(TAG, "Creating new database");
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