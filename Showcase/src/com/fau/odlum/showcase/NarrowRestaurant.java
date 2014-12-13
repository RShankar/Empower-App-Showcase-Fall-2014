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

public class NarrowRestaurant extends ActionBarActivity {
	
	public static String[] restaurantTypes={"Type","Bakery","Cafe","Restaurant","Take Out","Delivery"};
	public static String[] restaurantConversions={"","bakery","cafe","restaurant","meal_takeaway","meal_delivery"};
	public static Spinner restaurantTypeSpinner;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_narrow_restaurant);
		
		restaurantTypeSpinner=(Spinner)findViewById(R.id.restaurant_type);
		ArrayAdapter<String> restaurantTypeAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,restaurantTypes);
		restaurantTypeSpinner.setAdapter(restaurantTypeAdapter);
		returnFromRestaurantButton();
		findRestaurantMapButton();
	}
	
	private void findRestaurantMapButton() {
		final Spinner typeSpinner=(Spinner) findViewById(R.id.restaurant_type);
    	Button restaurantMapButton=(Button)findViewById(R.id.restaurantFind);
    	restaurantMapButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent passTypes=new Intent(NarrowRestaurant.this, LocationMap.class);
				passTypes.putExtra("spinnerData",restaurantConversions[typeSpinner.getSelectedItemPosition()]);
				startActivity(passTypes);
			}
		});
	}
	
	private void returnFromRestaurantButton() {
	   	Button schoolBackButton=(Button)findViewById(R.id.restaurantBack);
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
		getMenuInflater().inflate(R.menu.narrow_restaurant, menu);
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
