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

public class NarrowGrocery extends ActionBarActivity {
	
	public static String[] groceryTypes={"Type","Convenience","Supermarket"};
	public static String[] groceryConversions={"", "convenience_store", "grocery_or_supermarket"};
	public static Spinner groceryTypeSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_narrow_grocery);
		
		groceryTypeSpinner=(Spinner)findViewById(R.id.store_type);
		ArrayAdapter<String> groceryAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,groceryTypes);
		groceryTypeSpinner.setAdapter(groceryAdapter);
		returnFromGroceryButton();
		findGroceryMapButton();
	}
	
	private void findGroceryMapButton() {
		final Spinner typeSpinner = (Spinner) findViewById(R.id.store_type);
    	Button groceryMapButton=(Button)findViewById(R.id.groceryFind);
    	groceryMapButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent passTypes=new Intent(NarrowGrocery.this, LocationMap.class);
				passTypes.putExtra("spinnerData",groceryConversions[typeSpinner.getSelectedItemPosition()]);
				startActivity(passTypes);
			}
		});
	}
	
	private void returnFromGroceryButton() {
	   	Button schoolBackButton=(Button)findViewById(R.id.groceryBack);
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
		getMenuInflater().inflate(R.menu.narrow_grocery, menu);
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
