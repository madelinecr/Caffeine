package info.bpace.caffeine;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

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
}
