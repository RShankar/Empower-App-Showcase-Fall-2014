package com.fau.odlum.showcase;

import java.util.ArrayList;

import org.w3c.dom.Document;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class Directions extends ActionBarActivity {
	public GMapV2Direction md;
	public GoogleMap map;
	public LatLng fromPosition;
	public LatLng toPosition;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_directions);
		
		double[] locations = getIntent().getExtras().getDoubleArray("mapLocations");
		fromPosition = new LatLng(locations[0], locations[1]);
		toPosition = new LatLng(locations[2], locations[3]);
		
		md = new GMapV2Direction();
		map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map_directions)).getMap();
		
		LatLng coordinates = new LatLng((fromPosition.latitude + toPosition.latitude) / 2, (fromPosition.longitude + toPosition.longitude) / 2);		
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 13));

		map.addMarker(new MarkerOptions().position(fromPosition).title("Start").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
		map.addMarker(new MarkerOptions().position(toPosition).title("End"));

		Document doc = md.getDocument(fromPosition, toPosition, GMapV2Direction.MODE_DRIVING);
		//int duration = md.getDurationValue(doc);
		//String distance = md.getDistanceText(doc);
		//String start_address = md.getStartAddress(doc);
		//String copy_right = md.getCopyRights(doc);

		ArrayList<String> directions = md.getReadableDirections(doc);
		ArrayList<LatLng> directionPoint = md.getDirection(doc);
		PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.RED);

		for(int i = 0 ; i < directionPoint.size() ; i++) {			
			rectLine.add(directionPoint.get(i));
		}
		
		String dir = "";
		for(int j = 0; j < directions.size(); j++) {
			String currentLine = directions.get(j);
			if(currentLine.indexOf("<b>") != -1) {
				currentLine = currentLine.replace("<b>", "");
			} 
			
			if(currentLine.indexOf("</b>") != -1) {
				currentLine = currentLine.replace("</b>", "");
			} 

			if(currentLine.indexOf("</div>") != -1) {
				currentLine = currentLine.replace("</div>", "");
			}
			
			while(currentLine.indexOf("<div") != -1) {
				int index = currentLine.indexOf("<div");
				int closingIndex = currentLine.indexOf(">", index);
				currentLine = currentLine.substring(0, index) + "\n" + currentLine.substring(closingIndex + 1);
			}
			dir = dir + currentLine + "\n";
		}
		
		EditText editText = (EditText) findViewById(R.id.about);
		editText.setText(dir);

		map.addPolyline(rectLine);
		
		returnFromDirectionsButton();
	}
	
	private void returnFromDirectionsButton() {
	   	Button schoolBackButton=(Button)findViewById(R.id.buttonDirectionsBack);
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
		getMenuInflater().inflate(R.menu.directions, menu);
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


