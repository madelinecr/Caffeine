package info.bpace.caffeine;

import android.app.ListActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.content.Intent;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;

/**
 * List activity showing every drink in the database
 */
public class CaffeineMainActivity extends ListActivity
{
	private static final int ACTIVITY_CREATE=0;
	private static final int ACTIVITY_EDIT=1;
	private static final int ACTIVITY_VIEW=2;
	
	DBAdapter mDBAdapter;
	
	/**
	 * Connects to the database and fills listView
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mDBAdapter = new DBAdapter(this);
		mDBAdapter.open();
		
		registerForContextMenu( getListView() );
		fillData();
	}
	
	/**
	 * Queries for all rows in database and adds titles to listView
	 */
	private void fillData()
	{
		Cursor c = mDBAdapter.readAll();
		startManagingCursor(c);
		
		int layout = android.R.layout.simple_list_item_1;
		String[] from = new String[] { DBAdapter.KEY_TITLE };
		int[] to = new int[] { android.R.id.text1 };
		
		ListAdapter adapter = new SimpleCursorAdapter(this, layout, c, from, to);
		
		setListAdapter(adapter);
		getListView().setTextFilterEnabled(true);
	}
	
	/**
	 * Options menu callback
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.list_options, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * Options menu selected callback
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.add_item:
				Intent i = new Intent(this, EditItemActivity.class);
				startActivity(i);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	/**
	 * Context menu callback
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.list_context, menu);
	}
	
	/**
	 * List selection callback
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this, ViewItemActivity.class);
		
		i.putExtra(DBAdapter.KEY_ROWID, id);
		startActivityForResult(i, ACTIVITY_VIEW);
	}
	
	/**
	 * Called activity finished callback
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		super.onActivityResult(requestCode, resultCode, intent);
		fillData();
	}
}
