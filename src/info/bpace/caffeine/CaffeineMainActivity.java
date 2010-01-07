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

public class CaffeineMainActivity extends ListActivity
{
	private static final int ACTIVITY_CREATE=0;
	private static final int ACTIVITY_EDIT=1;
	private static final int ACTIVITY_VIEW=2;
	
	DBAdapter mDBAdapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mDBAdapter = new DBAdapter(this);
		mDBAdapter.open();
		
		fillData();
	}
	
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.list_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.add_item:
				Intent i = new Intent(this, EditItemActivity.class);
				startActivity(i);
		}
		return true;
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this, ViewItemActivity.class);
		
		i.putExtra(DBAdapter.KEY_ROWID, id);
		startActivityForResult(i, ACTIVITY_VIEW);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		super.onActivityResult(requestCode, resultCode, intent);
		fillData();
	}
}
