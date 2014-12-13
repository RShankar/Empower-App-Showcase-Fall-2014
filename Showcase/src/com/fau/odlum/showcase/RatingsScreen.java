package com.fau.odlum.showcase;

import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class RatingsScreen extends ActionBarActivity {

	private String[] info;
	private double[] locations;
	TextView textComments;
	RatingBar ratingBar;
	
	//String commStr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ratings_screen);

		TextView textLocation = (TextView) findViewById(R.id.ratingsLocationInfo);
		TextView textName = (TextView) findViewById(R.id.locationName);
		info = getIntent().getExtras().getStringArray("mapAddress");
		locations = getIntent().getExtras().getDoubleArray("mapLocations");
		System.out.println("Retrieving info[0]: " + info[0]);
		System.out.println("Retrieving info[1]: " + info[1]);
		textName.setText(info[0]);
		textLocation.setText(info[1]);

		textComments = (TextView) findViewById(R.id.commentsList);
		ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
//		ParseObject parseObj = new ParseObject("parseObj");
//		FindCallback<ParseObject> fcb.FindCallback<T>;
		ParseQuery<ParseObject> query = ParseQuery.getQuery("RatingComments");
		query.whereEqualTo("placeID", new LatLng(locations[2], locations[3]).toString());
//		query.findInBackground(fcb);
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> commList, ParseException e) {
		        if (e == null) {
		        	float avgRating = 0.0f;
		        	String comments = "";
		            Log.d("comments", "Retrieved " + commList.size() + " comments");
		            if (commList.size() == 0){
		            	comments = "There are no comments to display";
		            }
		            for(int i = 0; i < commList.size(); i++)
		            {
		            	ParseObject userComments = commList.get(i);
			            String commStr = userComments.getString("comments");
			            String userID = userComments.getString("userID");
			            comments += userID + ": " + commStr + "\n";
			            avgRating += Float.parseFloat(userComments.getString("rating"));
		            }

		            RatingsScreen.this.ratingBar.setRating(avgRating / commList.size());
		    		RatingsScreen.this.textComments.setText(comments);
		            //Log.d("comments","Retrieved comment: " + commStr);
		            
		        } else {
		            Log.d("comments", "Error: " + e.getMessage());
		        }
		    }
		});

//        Log.d("comments","Comment again: " + RatingsScreen.this.commStr);
//		textComments.setText(RatingsScreen.this.commStr);

		returnFromRatingsButton();
	}

//	public class FindCallback<T> {
//	    public void done(List<T> commList, ParseException e) {
//	    	String commStr;
//	        if (e == null) {
//	            Log.d("comments", "Retrieved " + commList.size() + " comments");
//	            T oneComment = commList.get(0);
//	            commStr = ((ParseObject) oneComment).getString("comments");
//	            Log.d("comments","Retrieved comment: " + commStr);
//	        } else {
//	            Log.d("comments", "Error: " + e.getMessage());
//	        }
//	    }
//	}	
	
	private void returnFromRatingsButton() {
	   	Button schoolBackButton=(Button)findViewById(R.id.ratingBack);
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
		getMenuInflater().inflate(R.menu.ratings_screen, menu);
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
