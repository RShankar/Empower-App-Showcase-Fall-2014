package com.fau.odlum.showcase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

public class LocationInformation extends ActionBarActivity {

	private String[] info;
	private double[] locations;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_information);
		
		TextView textLocation = (TextView) findViewById(R.id.editTextLocationInfo);
		TextView textName = (TextView) findViewById(R.id.infoLocationName);
		info = getIntent().getExtras().getStringArray("mapAddress");
		locations = getIntent().getExtras().getDoubleArray("mapLocations");
		String name = info[0];
		textName.setText(name);
		textLocation.setText(info[1]);
		setupViewRatingsButton();
		returnFromFavoritesButton();
		setupDirectionsButton(locations);
		setupMyRatingsButton();
		returnFromAppButton();
	}
	
	private void setupViewRatingsButton() {
		final double[] locationsArray = locations;
		final String[] locationInfo = info;
    	Button schoolButton=(Button)findViewById(R.id.buttonCommentsRatings);
    	schoolButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LocationInformation.this, RatingsScreen.class);
				intent.putExtra("mapLocations", locationsArray);
				intent.putExtra("mapAddress", locationInfo);
				startActivity(intent);
			}
		});
	}
	
	private void setupDirectionsButton(double[] locations) {
		final double[] locationsArray = locations;
    	Button schoolButton=(Button)findViewById(R.id.buttonDirections);
    	schoolButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LocationInformation.this, Directions.class);
				intent.putExtra("mapLocations", locationsArray);
				startActivity(intent);
			}
		});
	}
	
	private void setupMyRatingsButton() {
		final double[] locationsArray = locations;
    	Button schoolButton=(Button)findViewById(R.id.buttonRate);
    	schoolButton.setOnClickListener(new View.OnClickListener() {
		
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LocationInformation.this, MyRatingsComments.class);
				ParseUser currentUser = ParseUser.getCurrentUser();
				intent.putExtra("userName", currentUser.getUsername());
				intent.putExtra("mapAddress", info);
				intent.putExtra("mapLocations", locationsArray);
				startActivity(intent);
//				ParseUser currentUser = ParseUser.getCurrentUser();
//
//				if (currentUser == null || ParseAnonymousUtils.isLinked(currentUser)) {
//		            Intent intent = new Intent(LocationInformation.this,
//		                    Login.class);
//		            startActivity(intent);
//			    } else {
//			    	Intent intent = new Intent(LocationInformation.this, MyRatingsComments.class);
//	            	startActivity(intent);
//				}
			}
		});
	}
	
	private void returnFromAppButton() {
		Button schoolBackButton=(Button)findViewById(R.id.buttonInfoExit);
	   	schoolBackButton.setOnClickListener(new View.OnClickListener() {
				
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private void returnFromFavoritesButton() {
	   	Button schoolBackButton=(Button)findViewById(R.id.buttonFavorite);
	   	schoolBackButton.setOnClickListener(new View.OnClickListener() {
				
			@Override
			public void onClick(View v) {
				ParseUser currentUser=ParseUser.getCurrentUser();
				String tempName=currentUser.getUsername();
				File yourFile=new File(Environment.getExternalStorageDirectory().getPath()+"/ShowcaseFavoritesFiles/"+tempName+".txt");
				try{
					if(!yourFile.exists()){
						yourFile.createNewFile();
					}
					FileInputStream fis = new FileInputStream(yourFile);
				    InputStreamReader isr = new InputStreamReader(fis);
				    BufferedReader bufferedReader = new BufferedReader(isr);
				    StringBuilder sb = new StringBuilder();
				    String line;
				    while ((line = bufferedReader.readLine()) != null) {
				       sb.append(line+"\r\n");
				       
				    }
				    bufferedReader.close();
				    String checkInfo=info[0]+";"+info[1]+";"+locations[2]+";"+locations[3]+"\r\n";
				    if(sb.indexOf(checkInfo)==-1)
				    {
					   sb.append(checkInfo);
				    }
				    FileOutputStream fos=new FileOutputStream(yourFile);
				   
				    OutputStreamWriter outputStreamWriter=new OutputStreamWriter(fos);
				    outputStreamWriter.write(sb.toString());
				    outputStreamWriter.close();
				    Toast.makeText(getApplicationContext(), "Added to Favorites", Toast.LENGTH_LONG).show();
				
				}
				catch(FileNotFoundException f){
					
				}
				catch(IOException i){
					i.printStackTrace();
					
				}
				catch(Exception e){
					e.printStackTrace();
				}
				
				//OutputStreamWriter outputStreamWriter= new OutputStreamWriter(openFileOutput("/sdcard/ShowcaseFavoritesFiles/Test.txt",Context.MODE_PRIVATE));
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.location_information, menu);
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
