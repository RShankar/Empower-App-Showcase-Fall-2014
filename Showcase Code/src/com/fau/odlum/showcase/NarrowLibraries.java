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

public class NarrowLibraries extends ActionBarActivity {
	
	public static String[] libraryTypes={"Type","Bookstore","Library"};
	public static String[] libraryConversions={"", "book_store", "library"};
	public static Spinner libraryTypeSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_narrow_libraries);
		
		libraryTypeSpinner=(Spinner)findViewById(R.id.library_type);
		ArrayAdapter<String> libraryAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,libraryTypes);
		libraryTypeSpinner.setAdapter(libraryAdapter);
		returnFromLibraryButton();
		findLibraryMapButton();
	}
	
	private void findLibraryMapButton() {
		final Spinner typeSpinner = (Spinner) findViewById(R.id.library_type);
    	Button libraryMapButton=(Button)findViewById(R.id.findLibrary);
    	libraryMapButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent passTypes=new Intent(NarrowLibraries.this, LocationMap.class);
				passTypes.putExtra("spinnerData",libraryConversions[typeSpinner.getSelectedItemPosition()]);
				startActivity(passTypes);
			}
		});
	}
	
	private void returnFromLibraryButton() {
	   	Button schoolBackButton=(Button)findViewById(R.id.libraryBack);
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
		getMenuInflater().inflate(R.menu.narrow_libraries, menu);
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
