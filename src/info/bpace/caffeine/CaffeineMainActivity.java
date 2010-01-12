package info.bpace.caffeine;

import android.app.Activity;
import android.os.Bundle;

import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class CaffeineMainActivity extends Activity implements OnClickListener
{
	public static final String TAG = "CAFFEINE_ACTIVITY";
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Button espresso = (Button) findViewById(R.id.espresso);
		Button coffee = (Button) findViewById(R.id.coffee);
		Button hot = (Button) findViewById(R.id.hot);
		Button cold = (Button) findViewById(R.id.cold);
		Button all = (Button) findViewById(R.id.all);
		
		espresso.setOnClickListener(this);
		coffee.setOnClickListener(this);
		hot.setOnClickListener(this);
		cold.setOnClickListener(this);
		all.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view)
	{
		Intent i = new Intent(this, ListViewActivity.class);
		switch(view.getId())
		{
			case R.id.espresso:
				i.setAction(ListViewActivity.LIST_ESPRESSO);
				startActivity(i);
				return;
			case R.id.coffee:
				i.setAction(ListViewActivity.LIST_COFFEE);
				startActivity(i);
				return;
			case R.id.hot:
				i.setAction(ListViewActivity.LIST_HOT);
				startActivity(i);
				return;
			case R.id.cold:
				i.setAction(ListViewActivity.LIST_COLD);
				startActivity(i);
				return;
			case R.id.all:
				startActivity(i);
				return;
		}
	}
}