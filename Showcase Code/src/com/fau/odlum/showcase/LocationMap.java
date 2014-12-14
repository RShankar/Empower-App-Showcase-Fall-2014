package com.fau.odlum.showcase;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

//import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

//import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class LocationMap extends ActionBarActivity {
	
	//private final LatLng LOCATION_HALLANDALE=new LatLng(25.981202, -80.148379);
	private GoogleMap map;
	private LocationManager locMan;
	private Marker userMarker;
	private int userIcon, foodIcon, drinkIcon, shopIcon, otherIcon;
	private Marker[] placeMarkers;
	private final int MAX_PLACES = 200;
	private MarkerOptions[] places;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_map);
		userIcon = R.drawable.yellow_point;
		foodIcon = R.drawable.red_point;
		drinkIcon = R.drawable.blue_point;
		shopIcon = R.drawable.green_point;
		otherIcon = R.drawable.purple_point;
		
		map=((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		returnFromLocationsButton();
		
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		OnInfoWindowClickListener listener = new OnInfoWindowClickListener() {
			@Override
            public void onInfoWindowClick(Marker arg0) {
				locMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
				//get last location
				Location lastLoc = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				double lat = lastLoc.getLatitude();
				double lng = lastLoc.getLongitude();
				//create LatLng
				LatLng lastLatLng = new LatLng(lat, lng);
				
				Intent intent = new Intent(LocationMap.this, LocationInformation.class);
				intent.putExtra("mapAddress", new String[] {arg0.getTitle(), arg0.getSnippet()});
				intent.putExtra("mapLocations", new double[] {lastLatLng.latitude, lastLatLng.longitude, arg0.getPosition().latitude, arg0.getPosition().longitude});
				startActivity(intent);
			}
		};
		map.setOnInfoWindowClickListener(listener);
		
		//CameraUpdate update=CameraUpdateFactory.newLatLngZoom(LOCATION_HALLANDALE,14);
		//map.animateCamera(update);
		placeMarkers = new Marker[MAX_PLACES];
		updatePlaces();
		
	}
	
	private void updatePlaces(){
		//get location manager
		locMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		//get last location
		Location lastLoc = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		double lat = lastLoc.getLatitude();
		double lng = lastLoc.getLongitude();
		//create LatLng
		LatLng lastLatLng = new LatLng(lat, lng);

		//remove any existing marker
		if(userMarker!=null) {userMarker.remove();}
		//create and set marker properties
		userMarker = map.addMarker(new MarkerOptions()
			.position(lastLatLng)
			.title("You are here")
			.icon(BitmapDescriptorFactory.fromResource(userIcon))
			.snippet("Your last recorded location"));
		//move to location
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(lastLatLng,14), 1000, null);
		
		String placesSearchStr = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
				"json?location="+lat+","+lng+
				"&radius=4800&sensor=true" +
				"&types="+getIntent().getExtras().getString("spinnerData")+
				"&key=AIzaSyCR8VcaEVBSyywncgSrDjdGyzkF1qnCRxc";//ADD KEY
		new GetPlaces().execute(placesSearchStr);
	}
	
	private class GetPlaces extends AsyncTask<String, Void, String> {
		//fetch and parse place data
		@Override
		protected String doInBackground(String... placesURL) {
		    //fetch places
			StringBuilder placesBuilder = new StringBuilder();
			//process search parameter string(s)
			for (String placeSearchURL : placesURL) {
				//execute search
				HttpClient placesClient = new DefaultHttpClient();
				try {
				    //try to fetch the data
					HttpGet placesGet = new HttpGet(placeSearchURL);
					HttpResponse placesResponse = placesClient.execute(placesGet);
					StatusLine placeSearchStatus = placesResponse.getStatusLine();
					if (placeSearchStatus.getStatusCode() == 200) {
						//we have an OK response
						System.out.println("got code 200");
						HttpEntity placesEntity = placesResponse.getEntity();
						InputStream placesContent = placesEntity.getContent();
						InputStreamReader placesInput = new InputStreamReader(placesContent);
						BufferedReader placesReader = new BufferedReader(placesInput);
						String lineIn;
						while ((lineIn = placesReader.readLine()) != null) {
						    placesBuilder.append(lineIn);
						}
					}
					else {
						System.out.println("No code 200");
					}
				}
				catch(Exception e){
				    e.printStackTrace();
				}
			}
			return placesBuilder.toString();
			
		}
		protected void onPostExecute(String result) {
		    //parse place data returned from Google Places
			if(placeMarkers!=null){
			    for(int pm=0; pm<placeMarkers.length; pm++){
			        if(placeMarkers[pm]!=null)
			            placeMarkers[pm].remove();
			    }
			}
			try {
			    //parse JSON
				JSONObject resultObject = new JSONObject(result);
				JSONArray placesArray = resultObject.getJSONArray("results");
				places = new MarkerOptions[placesArray.length()];
				//loop through places
				for (int p=0; p<placesArray.length(); p++) {
				    //parse each place
					boolean missingValue=false;
					LatLng placeLL=null;
					String placeName="";
					String vicinity="";
					int currIcon = otherIcon;
					try{
					    //attempt to retrieve place data values
						missingValue=false;
						JSONObject placeObject = placesArray.getJSONObject(p);
						JSONObject loc = placeObject.getJSONObject("geometry").getJSONObject("location");
						placeLL = new LatLng(
							    Double.valueOf(loc.getString("lat")),
							    Double.valueOf(loc.getString("lng")));
						JSONArray types = placeObject.getJSONArray("types");
						for(int t=0; t<types.length(); t++){
						    //what type is it
							String thisType=types.get(t).toString();
							if(thisType.contains("food")){
							    currIcon = foodIcon;
							    break;
							}
							else if(thisType.contains("restaurant")){
							    currIcon = drinkIcon;
							    break;
							}
							else if(thisType.contains("store")){
							    currIcon = shopIcon;
							    break;
							}
						}
						vicinity = placeObject.getString("vicinity");
						placeName = placeObject.getString("name");
					}
					catch(JSONException jse){
					    missingValue=true;
					    jse.printStackTrace();
					}
					if(missingValue)    places[p]=null;
					else
					{
						places[p]=new MarkerOptions()
					    .position(placeLL)
					    .title(placeName)
					    .icon(BitmapDescriptorFactory.fromResource(currIcon))
					    .snippet(vicinity);
					}
				}
			}
			catch (Exception e) {
			    e.printStackTrace();
			}
			if(places!=null && placeMarkers!=null){
			    for(int p=0; p<places.length && p<placeMarkers.length; p++){
			        //will be null if a value was missing
			        if(places[p]!=null)
			            placeMarkers[p]=map.addMarker(places[p]);
			    }
			}
		}
	}

	
	private void returnFromLocationsButton() {
	   	Button schoolBackButton=(Button)findViewById(R.id.buttonMapLocationBack);
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
		getMenuInflater().inflate(R.menu.location_map, menu);
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
