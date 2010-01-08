package info.bpace.caffeine;

import android.content.Context;
import android.database.Cursor;
import android.content.ContentValues;
import android.util.Log;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;

/**
 * Adapter class for interfacing with the database
 */
public class DBAdapter
{
	// static block
	public static final String DATABASE_NAME = "caffeine_db";
	public static final String DATABASE_TABLE = "drinks";
	public static final int DATABASE_VERSION = 1;
	
	public static final String DATABASE_PATH = "/data/data/info.bpace.caffeine/databases/";
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_INGREDIENTS = "ingredients";
	public static final String KEY_BODY = "body";
	public static final String[] COLUMNS = { KEY_ROWID, KEY_TITLE, KEY_INGREDIENTS, KEY_BODY };
	
	public static final String TAG = "CaffeineDBAdapter";

	public static final String DATABASE_CREATE =
 		"create table drinks (_id integer primary key autoincrement, "
		+ "title text not null, ingredients text not null, body text not null);";
	
	private final Context mContext;
	private DBHelper mDBHelper;
	private SQLiteDatabase mDB;
	
// -----------------------------------------------------------------------------
// Public methods
// -----------------------------------------------------------------------------
	
	/**
	 * Constructor - takes the context to allow the database to be
	 * created.
	 * @param context the context within which to work
	 */
	public DBAdapter(Context context)
	{
		mContext = context;
	}
	
	/**
	 * Opens connection to database, creating or upgrading it if
	 * necessary
	 * 
	 * @param this (self reference)
	 * @throws SQLException if the database couldn't be opened or created
	 */
	public DBAdapter open() throws SQLException
	{
		mDBHelper = new DBHelper(mContext);
		mDB = mDBHelper.getWritableDatabase();
		try
		{
			if(mDBHelper.checkDatabase() == true)
			{ // copy database if it doesn't exist
				close();
				mDBHelper.copyDatabase();
				mDB = mDBHelper.getWritableDatabase();
			}
			
		}
		catch(IOException e)
		{
			Log.e(TAG, "Couldn't copy database");
		}
		return this;
	}
	
	/**
	 * Closes database connection
	 */
	public void close()
	{
		mDBHelper.close();
	}
	
	/**
	 * Adds an entry to the database
	 * @param title The title of the drink
	 * @param ingredients the ingredients the drink needs
	 * @param body instructions for making the drink
	 * @return rowId or -1 if failed
	 */
	public long createEntry(String title, String ingredients, String body)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TITLE, title);
		initialValues.put(KEY_INGREDIENTS, ingredients);
		initialValues.put(KEY_BODY, body);
		
		return mDB.insert(DATABASE_TABLE, null, initialValues);
	}
	
	/**
	 * Returns a cursor containing every item in the database
	 * @return Cursor every row in the database
	 */
	public Cursor readAll()
	{
		return mDB.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE,
			KEY_INGREDIENTS, KEY_BODY}, null, null, null, null, KEY_TITLE);
	}
	
	/**
	 * Returns a cursor containing the specific row requested
	 * @param rowId the row queried
	 * @return Cursor containing the row contents
	 */
	public Cursor readEntry(long rowId) throws SQLException
	{
		
		Cursor mCursor = mDB.query(true, DATABASE_TABLE, COLUMNS, KEY_ROWID + "=" + rowId, 
			null, null, null, null, null);
		
		if(mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	/**
	 * Updates an entry in the database
	 */
	public boolean updateEntry(long id, String title, String ingredients, String body)
	{
		ContentValues args = new ContentValues();
		args.put(KEY_TITLE, title);
		args.put(KEY_INGREDIENTS, ingredients);
		args.put(KEY_BODY, body);
		
		return mDB.update(DATABASE_TABLE, args, KEY_ROWID + "=" + id, null) > 0;
	}
	
	public boolean deleteEntry(long id)
	{
		return mDB.delete(DATABASE_TABLE, KEY_ROWID + "=" + id, null) > 0;
	}
	
// -----------------------------------------------------------------------------
// Private helper class
// -----------------------------------------------------------------------------

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
			db.execSQL("DROP TABLE IF EXISTS drinks");
			onCreate(db);
		}
		
		/**
		 * @return true if db exists, false if it doesn't
		 */
		public boolean checkDatabase()
		{
			SQLiteDatabase checkDB = null;
			try
			{
				String path = DATABASE_PATH + DATABASE_NAME;
				checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
			}
			catch(SQLiteException e)
			{
				// database doesn't exist yet
			}
			
			if(checkDB != null)
			{
				checkDB.close();
			}
			
			return checkDB != null ? true : false;
		}
		
		private void copyDatabase() throws IOException
		{
			InputStream input = mContext.getAssets().open(DATABASE_NAME);
			String filename = DATABASE_PATH + DATABASE_NAME;
			OutputStream output = new FileOutputStream(filename);
			
			Log.v(TAG, "Copying database to " + filename);
			
			byte[] buffer = new byte[1024];
			int length;
		
			// transfer asset db to real db
			while( (length = input.read(buffer) ) > 0)
			{
				Log.v(TAG, "Copying data");
				output.write(buffer, 0, length);
			}
			output.flush();
			output.close();
			input.close();
		}
	}
}