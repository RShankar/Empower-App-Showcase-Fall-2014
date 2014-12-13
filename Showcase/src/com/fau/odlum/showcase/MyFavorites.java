package com.fau.odlum.showcase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseUser;

public class MyFavorites extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_favorites);
		readFavoritesData();
		returnFromMyFavoritesButton();
		//setupfavoritesAButton();
	}
	
	private void readFavoritesData(){
		LinearLayout ll=(LinearLayout)findViewById(R.id.favoritesLayout);
		ParseUser currentUser=ParseUser.getCurrentUser();
		String tempName=currentUser.getUsername();
		File yourFile=new File(Environment.getExternalStorageDirectory().getPath()+"/ShowcaseFavoritesFiles/"+tempName+".txt");
		try{
			if(yourFile.exists()){
				FileInputStream fis = new FileInputStream(yourFile);
			    InputStreamReader isr = new InputStreamReader(fis);
			    BufferedReader bufferedReader = new BufferedReader(isr);
			    String line;
			    while ((line = bufferedReader.readLine()) != null) {
			        final String[] favoriteData=line.split(";");
			        final LinearLayout llCopy = ll;
			        final String userName = tempName;
			    	TextView tv=new TextView(this);
			    	tv.setTag(new LatLng(Double.parseDouble(favoriteData[2]), Double.parseDouble(favoriteData[3])).toString());
			    	tv.setLongClickable(true);
			    	tv.setOnLongClickListener(new View.OnLongClickListener() {
			    		
			    		@Override
			    		public boolean onLongClick(View v) {
			    			TextView textView = (TextView)v;
			    			deleteFavorite(textView, userName);
			    			llCopy.removeView(v);
			    			Toast.makeText(getApplicationContext(), "Deleted " + textView.getText(), Toast.LENGTH_LONG).show();
			    			return true;
			    		}
			    	});
			        tv.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent=new Intent(MyFavorites.this,LocationInformation.class);
						intent.putExtra("mapAddress",new String[]{favoriteData[0],favoriteData[1]});
						LocationManager locMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
						//get last location
						Location lastLoc = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						intent.putExtra("mapLocations",new double[]{lastLoc.getLatitude(),lastLoc.getLongitude(),Double.parseDouble(favoriteData[2]),Double.parseDouble(favoriteData[3])});
						startActivity(intent);
						}
					});
			        
			        tv.setTextSize(30);
			        
			        tv.setText(favoriteData[0]);
			        ll.addView(tv);
			    }
			    bufferedReader.close();
			}
		}
		catch(FileNotFoundException f){
			
		}
		catch(IOException i){
			i.printStackTrace();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void deleteFavorite(TextView v, String tempName) {
		String tag = v.getTag().toString();
		File yourFile=new File(Environment.getExternalStorageDirectory().getPath()+"/ShowcaseFavoritesFiles/"+tempName+".txt");
		try {
			if(yourFile.exists()){
				FileInputStream fis = new FileInputStream(yourFile);
			    InputStreamReader isr = new InputStreamReader(fis);
			    BufferedReader bufferedReader = new BufferedReader(isr);
			    StringBuilder sb = new StringBuilder();
			    String line;
			    while ((line = bufferedReader.readLine()) != null) {
			    	boolean removeEntry = true;
			    	String[] lineData = line.split(";");
			    	
			    	if (!(tag.contains(lineData[2]) && tag.contains(lineData[3]))){
			    		removeEntry = false;
			    	}
			    	
			    	if (!removeEntry) {
			    		sb.append(line + "\r\n");
			    	}
			    }
			    
			    bufferedReader.close();
			    FileOutputStream fos=new FileOutputStream(yourFile);
				   
			    OutputStreamWriter outputStreamWriter=new OutputStreamWriter(fos);
			    outputStreamWriter.write(sb.toString());
			    outputStreamWriter.close();
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	private void returnFromMyFavoritesButton() {
	   	Button schoolBackButton=(Button)findViewById(R.id.myFavoritesBack);
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
		getMenuInflater().inflate(R.menu.my_favorites, menu);
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
