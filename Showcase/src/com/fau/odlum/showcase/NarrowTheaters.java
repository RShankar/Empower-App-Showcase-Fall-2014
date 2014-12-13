package com.fau.odlum.showcase;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class NarrowTheaters extends ActionBarActivity {
	
	public static String[] theaterTypes={"Type","Movie Rental","Movie Theater"};
	public static String[] theaterConversions={"", "movie_rental", "movie_theater"};
	public static Spinner theaterTypeSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_narrow_theaters);
		
		theaterTypeSpinner=(Spinner)findViewById(R.id.theater_type);
		ArrayAdapter<String> theaterTypeAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,theaterTypes);
		theaterTypeSpinner.setAdapter(theaterTypeAdapter);
		returnFromTheaterButton();
		findTheaterMapButton();
	}
	
	private void findTheaterMapButton() {
		final Spinner typeSpinner = (Spinner) findViewById(R.id.theater_type);
    	Button theaterMapButton=(Button)findViewById(R.id.theaterFind);
    	theaterMapButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent passTypes=new Intent(NarrowTheaters.this, LocationMap.class);
				passTypes.putExtra("spinnerData",theaterConversions[typeSpinner.getSelectedItemPosition()]);
				startActivity(passTypes);
			}
		});
	}
	
	private void returnFromTheaterButton() {
	   	Button schoolBackButton=(Button)findViewById(R.id.theaterBack);
	   	schoolBackButton.setOnClickListener(new View.OnClickListener() {
				
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.narrow_theaters, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
