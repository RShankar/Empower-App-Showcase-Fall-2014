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

public class NarrowBuses extends ActionBarActivity {
	
	public static String[] busTypes={"Type","Bus Stop","Car Rental","Taxi","Train Station"};
	public static String[] busConversions={"","bus_station","car_rental","taxi_stand","train_station"};
	public static Spinner busTypeSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_narrow_buses);
		
		busTypeSpinner=(Spinner)findViewById(R.id.bus_line);
		ArrayAdapter<String> busAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,busTypes);
		busTypeSpinner.setAdapter(busAdapter);
		returnFromBusButton();
		findBusMapButton();
		
	}
	
	private void findBusMapButton() {
		final Spinner typeSpinner=(Spinner) findViewById(R.id.bus_line);
    	Button busMapButton=(Button)findViewById(R.id.findBus);
    	busMapButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent passTypes=new Intent(NarrowBuses.this, LocationMap.class);
				passTypes.putExtra("spinnerData",busConversions[typeSpinner.getSelectedItemPosition()]);
				startActivity(passTypes);
			}
		});
	}
	
	private void returnFromBusButton() {
	   	Button schoolBackButton=(Button)findViewById(R.id.busBack);
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
		getMenuInflater().inflate(R.menu.narrow_buses, menu);
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
