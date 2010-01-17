package info.bpace.caffeine;

import android.app.Activity;
import android.os.Bundle;
import android.database.Cursor;
import android.widget.EditText;
import android.widget.Button;

import android.view.View;
import android.view.View.OnClickListener;

import android.app.Dialog;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;

/**
 * Activity for adding and editting items
 * in the database
 */
public class EditItemActivity extends Activity implements OnClickListener
{
	public static final int DIALOG_CATEGORIES = 0;
	
	private EditText mTitle;
	private EditText mIngredients;
	private EditText mBody;
	private Long mId;
	
	private DBAdapter mDBAdapter;

// -----------------------------------------------------------------------------
// Public methods
// -----------------------------------------------------------------------------

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
		
		mTitle = (EditText) findViewById(R.id.item_title);
		mIngredients = (EditText) findViewById(R.id.item_ingredients);
		mBody = (EditText) findViewById(R.id.item_body);
		Button categories = (Button) findViewById(R.id.categories);
		Button submit = (Button) findViewById(R.id.submit);
		
		// check if activity is resuming, and pull id from saved state
		mId = savedInstanceState != null ? savedInstanceState.getLong(DBAdapter.KEY_ROWID)
		                                 : null;
		
		boolean resuming = false;
		if(mId == null)
		{
			// activity isn't resuming, try to pull id from intent
			Bundle extras = getIntent().getExtras();
			mId = extras != null ? extras.getLong(DBAdapter.KEY_ROWID)
			                     : null;
		}
		else
		{
			resuming = true;
		}
		
		populateFields(resuming);
		categories.setOnClickListener(this);
		submit.setOnClickListener(this);
	}
	
	/**
	 * Submits form information and quits activity
	 */
	@Override
	public void onClick(View view)
	{
		switch(view.getId())
		{
			case R.id.categories:
				showDialog(DIALOG_CATEGORIES);
				return;
			case R.id.submit:
				setResult(RESULT_OK);
				saveState();
				finish();
				return;
			default:
				return;
		}
	}
	
// -----------------------------------------------------------------------------
// Private methods
// -----------------------------------------------------------------------------

	protected Dialog onCreateDialog(int id)
	{
		switch(id)
		{
			case DIALOG_CATEGORIES:
				final CharSequence[] options = {"Espresso", "Coffee", "Cold"};
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Drink Categories");
				
				builder.setMultiChoiceItems(options, null, new DialogInterface.OnMultiChoiceClickListener()
				{
					public void onClick(DialogInterface dialog, int which, boolean isChecked)
					{
						
					}
				});
				return builder.create();
			default:
				return super.onCreateDialog(id);
		}
	}

// -----------------------------------------------------------------------------
// Private methods
// -----------------------------------------------------------------------------

	/**
	 */
	private void populateFields(boolean isResuming)
	{
		if(mId != null && isResuming == false)
		{
			Cursor c = mDBAdapter.readEntry(mId);
			startManagingCursor(c);
			
			int title_index = c.getColumnIndexOrThrow(DBAdapter.KEY_TITLE);
			int ingredients_index = c.getColumnIndexOrThrow(DBAdapter.KEY_INGREDIENTS);
			int body_index = c.getColumnIndexOrThrow(DBAdapter.KEY_BODY);

			mTitle.setText( c.getString(title_index) );
			mIngredients.setText( c.getString(ingredients_index) );
			mBody.setText( c.getString(body_index) );
		}
	}

	/**
	 * Write EditText fields out to database
	 */
	private void saveState()
	{
		String title = mTitle.getText().toString();
		String ingredients = mIngredients.getText().toString();
		String body = mBody.getText().toString();
		
		if(mId == null)
		{
			mDBAdapter.createEntry(title, ingredients, body);
		}
		else
		{
			mDBAdapter.updateEntry(mId, title, ingredients, body);
		}
	}
}