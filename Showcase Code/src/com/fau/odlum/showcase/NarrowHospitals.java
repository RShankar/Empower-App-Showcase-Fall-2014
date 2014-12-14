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

public class NarrowHospitals extends ActionBarActivity {
	
	public static String[] hospitalTypes={"Type","Clinic","Dentist","Hospital", "Pharmacy"};
	public static String[] hospitalConversions={"", "doctor", "dentist", "hospital", "pharmacy"};
	public static Spinner hospitalTypeSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_narrow_hospitals);
		
		hospitalTypeSpinner=(Spinner)findViewById(R.id.hospital_type);
		ArrayAdapter<String> hospitalAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,hospitalTypes);
		hospitalTypeSpinner.setAdapter(hospitalAdapter);
		returnFromHospitalButton();
		findHospitalMapButton();
	}
	
	private void findHospitalMapButton() {
		final Spinner typeSpinner = (Spinner) findViewById(R.id.hospital_type);
    	Button hospitalMapButton=(Button)findViewById(R.id.hospitalFind);
    	hospitalMapButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent passTypes=new Intent(NarrowHospitals.this, LocationMap.class);
				passTypes.putExtra("spinnerData",hospitalConversions[typeSpinner.getSelectedItemPosition()]);
				startActivity(passTypes);
			}
		});
	}
	
	private void returnFromHospitalButton() {
	   	Button schoolBackButton=(Button)findViewById(R.id.hospitalBack);
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
		getMenuInflater().inflate(R.menu.narrow_hospitals, menu);
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
