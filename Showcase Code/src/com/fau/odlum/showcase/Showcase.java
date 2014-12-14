package com.fau.odlum.showcase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;



import com.parse.ParseUser;

public class Showcase extends ActionBarActivity {
	
	//static public boolean loggedIn=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    	setContentView(R.layout.activity_showcase);
        setupSchoolButton();
        setupTheaterButton();
        setupLibraryButton();
        setupHospitalButton();
        setupBusButton();
        setupRestaurantButton();
        setupMallButton();
        setupGroceryButton();
        setupMyFavoritesButton();
        setupAboutButton();
        exitFromAppButton();
    }
    
    private void setupAboutButton(){
    	Button aboutButton=(Button)findViewById(R.id.About);
    	aboutButton.setOnClickListener(new View.OnClickListener() {
    		
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Showcase.this, About.class));
			}
		});
    }
    private void setupSchoolButton() {
    	Button schoolButton=(Button)findViewById(R.id.schools);
    	schoolButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Showcase.this, NarrowSchools.class));
			}
		});
	}
    
    private void setupTheaterButton() {
    	Button schoolButton=(Button)findViewById(R.id.theaters);
    	schoolButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Showcase.this, NarrowTheaters.class));
			}
		});
	}
    
    private void setupLibraryButton() {
    	Button schoolButton=(Button)findViewById(R.id.libraries);
    	schoolButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Showcase.this, NarrowLibraries.class));
			}
		});
	}
    
    private void setupHospitalButton() {
    	Button schoolButton=(Button)findViewById(R.id.hospitals);
    	schoolButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Showcase.this, NarrowHospitals.class));
			}
		});
	}
    
    private void setupBusButton() {
    	Button schoolButton=(Button)findViewById(R.id.bus);
    	schoolButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Showcase.this, NarrowBuses.class));
			}
		});
	}
    
    private void setupRestaurantButton() {
    	Button schoolButton=(Button)findViewById(R.id.restaurants);
    	schoolButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Showcase.this, NarrowRestaurant.class));
			}
		});
	}
    
    private void setupMallButton() {
    	Button schoolButton=(Button)findViewById(R.id.mall);
    	schoolButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Showcase.this, NarrowMalls.class));
			}
		});
	}
    
    private void setupGroceryButton() {
    	Button schoolButton=(Button)findViewById(R.id.grocery);
    	schoolButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Showcase.this, NarrowGrocery.class));
			}
		});
	}
    
    private void setupMyFavoritesButton() {
    	Button schoolButton=(Button)findViewById(R.id.favorites);
    	schoolButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Showcase.this, MyFavorites.class));
			}
		});
	}
    
    private void exitFromAppButton() {
	   	Button schoolBackButton=(Button)findViewById(R.id.exitButton);
	   	schoolBackButton.setOnClickListener(new View.OnClickListener() {
				
			@Override
			public void onClick(View v) {
				ParseUser.logOut();
				finish();
			}
		});
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.showcase, menu);
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
   
    