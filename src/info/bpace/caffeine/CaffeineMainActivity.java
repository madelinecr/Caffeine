package info.bpace.caffeine;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.view.Menu;
import android.view.MenuInflater;

public class CaffeineMainActivity extends ListActivity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
		
		ArrayAdapter itemArray = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		itemArray.add("Placeholder");
		setListAdapter(itemArray);
		getListView().setTextFilterEnabled(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.list_menu, menu);
		return true;
	}
}
