package com.fau.odlum.showcase;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseObject;

public class MyRatingsComments extends ActionBarActivity {

	EditText editText;
	RatingBar ratingBar1;
	double[] locations;
	String[] info;
	String userName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_ratings_comments);
		
		locations = getIntent().getExtras().getDoubleArray("mapLocations");
		info = getIntent().getExtras().getStringArray("mapAddress");
		userName = getIntent().getExtras().getString("userName");
		
		editText = (EditText) findViewById(R.id.about);
	   	ratingBar1 = (RatingBar)findViewById(R.id.ratingBar1);

		returnFromMyRatingsButton();
		saveMyRatingsButton();
	}
	
	private void returnFromMyRatingsButton() {
	   	Button schoolBackButton=(Button)findViewById(R.id.buttonBackToMap);
	   	schoolBackButton.setOnClickListener(new View.OnClickListener() {
				
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void saveMyRatingsButton() {
		final double[] locationsArray = locations;
		final String[] locationInfo = info;
		final String userID = userName;
	   	Button saveButton=(Button)findViewById(R.id.buttonSave);
	   	saveButton.setOnClickListener(new View.OnClickListener() {
				
			@Override
			public void onClick(View v) {
				String comment = editText.getText().toString();
				float numstars = ratingBar1.getRating();
				String stars = Float.toString(numstars);
				System.out.println(comment);
				System.out.println(stars + " stars");
				ParseObject ratingComments = new ParseObject("RatingComments");
				ratingComments.put("placeName", locationInfo[0]);
				ratingComments.put("placeID", new LatLng(locationsArray[2], locationsArray[3]).toString());
				ratingComments.put("userID", userID);
				ratingComments.put("rating", stars);
				ratingComments.put("comments", comment);
				ratingComments.saveInBackground();
				Toast.makeText(getApplicationContext(),
                        "Successfully Saved",
                        Toast.LENGTH_LONG).show();

			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_ratings_comments, menu);
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
