package info.bpace.caffeine;

import android.app.Activity;
import android.os.Bundle;

import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class CaffeineMainActivity extends Activity implements OnClickListener
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Button all = (Button) findViewById(R.id.all);
		all.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view)
	{
		switch(view.getId())
		{
			case R.id.all:
				Intent i = new Intent(this, ListViewActivity.class);
				startActivity(i);
				return;
		}
	}
}