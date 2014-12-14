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

public class NarrowMalls extends ActionBarActivity {
	
	public static String[] mallTypes={"Type","Clothing Store","Department Store", "Electronics Store", "Furniture Store", "Home Goods Store", "Jewelry Store", "Shoe Store", "Shopping Mall"};
	public static String[] mallConversions={"", "clothing_store", "department_store", "electronics_store", "furniture_store", "home_goods_store", "jewelry_store", "shoe_store", "shopping_mall"};
	public static Spinner mallTypeSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_narrow_malls);
		
		mallTypeSpinner=(Spinner)findViewById(R.id.mall_type);
		ArrayAdapter<String> mallAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,mallTypes);
		mallTypeSpinner.setAdapter(mallAdapter);
		returnFromMallButton();
		findMallMapButton();
	}
	
	private void findMallMapButton() {
		final Spinner typeSpinner = (Spinner) findViewById(R.id.mall_type);
    	Button mallMapButton=(Button)findViewById(R.id.mallFind);
    	mallMapButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent passTypes=new Intent(NarrowMalls.this, LocationMap.class);
				passTypes.putExtra("spinnerData",mallConversions[typeSpinner.getSelectedItemPosition()]);
				startActivity(passTypes);
			}
		});
	}
	
	private void returnFromMallButton() {
	   	Button schoolBackButton=(Button)findViewById(R.id.mallBack);
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
		getMenuInflater().inflate(R.menu.narrow_malls, menu);
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
