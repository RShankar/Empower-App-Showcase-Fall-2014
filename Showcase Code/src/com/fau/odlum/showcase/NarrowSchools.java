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

public class NarrowSchools extends ActionBarActivity {
	
	public static String[] schoolTypes={"Type","School","University"};
	public static String[] schoolConversions={"", "school" ,"university"};
	public static Spinner schoolTypeSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_narrow_schools);
		
		schoolTypeSpinner=(Spinner)findViewById(R.id.school_type);
		ArrayAdapter<String> schoolTypeAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,schoolTypes);
		schoolTypeSpinner.setAdapter(schoolTypeAdapter);
		returnFromSchoolButton();
		findSchoolMapButton();
	
	}
	
	private void findSchoolMapButton() {
		final Spinner typeSpinner = (Spinner) findViewById(R.id.school_type);
    	Button schoolMapButton=(Button)findViewById(R.id.schoolFind);
    	schoolMapButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent passTypes=new Intent(NarrowSchools.this, LocationMap.class);
				passTypes.putExtra("spinnerData",schoolConversions[typeSpinner.getSelectedItemPosition()]);
				startActivity(passTypes);
			}
		});
	}
	
	private void returnFromSchoolButton() {
	   	Button schoolBackButton=(Button)findViewById(R.id.schoolBack);
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
		getMenuInflater().inflate(R.menu.narrow_schools, menu);
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
