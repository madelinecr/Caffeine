package info.bpace.caffeine;

import android.app.Activity;
import android.os.Bundle;

import android.database.Cursor;

import android.widget.TextView;
import android.content.Intent;

/**
 * Activity for viewing specific items in the database
 * and their details
 */
public class ViewItemActivity extends Activity
{
	DBAdapter mDBAdapter;

// -----------------------------------------------------------------------------
// Public methods
// -----------------------------------------------------------------------------

	/**
	 * Gets passed in an id number as an intent extra
	 * Uses that id to load information from the database
	 * and display it
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_item);
		
		TextView title = (TextView) findViewById(R.id.view_title);
		TextView ingredients = (TextView) findViewById(R.id.view_ingredients);
		TextView body = (TextView) findViewById(R.id.view_body);
		
		mDBAdapter = new DBAdapter(this);
		mDBAdapter.open();

		// figure out what id to load from the database
		Bundle extras = getIntent().getExtras();
		long id = extras.getLong(DBAdapter.KEY_ROWID);

		Cursor c = mDBAdapter.readEntry(id);
		startManagingCursor(c);
		
		int title_index = c.getColumnIndexOrThrow(DBAdapter.KEY_TITLE);
		int ingredients_index = c.getColumnIndexOrThrow(DBAdapter.KEY_INGREDIENTS);
		int body_index = c.getColumnIndexOrThrow(DBAdapter.KEY_BODY);
		
		title.setText( c.getString(title_index) );
		ingredients.setText( c.getString(ingredients_index) );
		body.setText( c.getString(body_index) );
	}
}