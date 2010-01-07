package info.bpace.caffeine;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * Activity for adding and editting items
 * in the database
 */
public class EditItemActivity extends Activity implements OnClickListener
{
	private EditText mTitleText;
	private EditText mIngredientsText;
	private EditText mBodyText;
	private DBAdapter mDBAdapter;
	
	/**
	 * Starts rendering main view, opens database connection
	 * and sets mouse callback for submit button
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_item);
		
		mDBAdapter = new DBAdapter(this);
		mDBAdapter.open();
		mTitleText = (EditText) findViewById(R.id.item_title);
		mIngredientsText = (EditText) findViewById(R.id.item_ingredients);
		mBodyText = (EditText) findViewById(R.id.item_body);
		
		Button submit = (Button) findViewById(R.id.submit);
		
		submit.setOnClickListener(this);
	}
	
	/**
	 * Submits form information and quits activity
	 */
	@Override
	public void onClick(View view)
	{
		setResult(RESULT_OK);
		saveState();
		finish();
	}
	
	/**
	 * Write EditText fields out to database
	 */
	private void saveState()
	{
		String title = mTitleText.getText().toString();
		String ingredients = mIngredientsText.getText().toString();
		String body = mBodyText.getText().toString();
		
		mDBAdapter.createEntry(title, ingredients, body);
	}
}