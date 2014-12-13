import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import ketai.sensors.KetaiLocation;
import ketai.sensors.Location;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;


public class About extends PApplet {
  PImage img, img2, img3, img4;

  //location variable
  KetaiLocation location;

  //variables to hold your coordinates
  double longitude, latitude;

  float rectX1, rectY1, //position of the first button 
        rectX2, rectY2, //position of the second button
        rectX3, rectY3, //position of the third button
        rectX4, rectY4; //position of the fourth button
        

  //the size of the rectangle for the button
  float rectWidth, rectHeight;

  //these are labels for the buttons
  String labelStudents, labelEngineers, labelVersion;

  //setting the font for labels
  PFont font ;
  PFont labelFont;
     
  int buttonColor; //color of the buttons

  //setup method that gets parameters for the screen ready
  public void setup(){
    size(displayWidth, displayHeight);
      
      //locks the screen orientation in portrait
      orientation(PORTRAIT);
      
      noStroke();
      
      //setting the location 
      location = new KetaiLocation(this);
      
      font = createFont("Century", displayWidth/10, true);
      labelFont = createFont("Century.bold", 54, true);
      
      //setting the dimensions for the rectangle
      rectWidth = (float) (displayWidth/1.5);
      rectHeight = displayHeight/10;
      
      //setting the location for the buttons on the screen beginning with the left most button
      rectX1 = displayWidth/3 - rectHeight;
      rectY1 = displayHeight/4;
      
      rectX2 = displayWidth/3 - rectHeight;
      rectY2 = displayHeight/4 + displayHeight/8;
     
      rectX3 = displayWidth/3 - rectHeight;
      rectY3 = displayHeight/4 + (displayHeight/8*2);
      
      //checking the width and height of the device screen
      println("Width: " + displayWidth + "\nHeight: " + displayHeight);
      
      //background color
      background(255,192,203); //kids can edit this
      
      
      labelStudents = "Students";
      labelEngineers = "Engineers";
      labelVersion = "Version"; 
    }

    public void draw(){
      fill(70,130,180);
      textFont(font);
      textAlign(CENTER);
      text("About Us", displayWidth/2, displayHeight/6);
     
      //setting up the buttons on the screen
      //a rectangle object is used here to set the buttons up
      rect(rectX1,rectY1,rectWidth, rectHeight, 20);  
      rect(rectX2,rectY2,rectWidth, rectHeight, 20);
      rect(rectX3,rectY3,rectWidth, rectHeight, 20);
      
      //placing the labels on the buttons
      fill(230,230,250); //kids can change this
      textFont(labelFont);
      text(labelStudents,(float)(rectX1 + (rectWidth/2.1)),(float)(rectY1 + (rectHeight/2)));
      text(labelEngineers,(float)(rectX2 + (rectWidth/2.1)),(float)(rectY2 + (rectHeight/2)));
      text(labelVersion,(float)(rectX3 + (rectWidth/2.1)),(float)(rectY3 + (rectHeight/2)));
      
      if(location.getProvider() == "none"){
        println("No location available5");
      }
    }
    public void mousePressed(){
        
        //if the user clicks the Students Button, a dialog box will launch
        if(mouseX >= rectX1 && mouseX <= rectX1 + rectWidth && mouseY >= rectY1 && mouseY <= rectY1 + rectHeight){
        
          Intent intent = new Intent(About.this, DialogActivity.class);
            intent.putExtra("latitude", latitude);
            intent.putExtra ("longitude", longitude);
            startActivity(intent);
          
        }
        //if the user clicks the Engineers button, a dialog box will launch
        if(mouseX >= rectX2 && mouseX <= rectX2 + rectWidth && mouseY >= rectY2 && mouseY <= rectY2 + rectHeight){
          Intent intent = new Intent(About.this, DialogActivity2.class);
          intent.putExtra("latitude", latitude);
          intent.putExtra ("longitude", longitude);
          startActivity(intent);
          
        }
        //if the user clicks the version button, a dialog box will launch
        if(mouseX >= rectX3 && mouseX <= rectX3 + rectWidth && mouseY >= rectY3 && mouseY <= rectY3 + rectHeight){
          Intent intent = new Intent(About.this, DialogActivity3.class);
          intent.putExtra("latitude", latitude);
            intent.putExtra ("longitude", longitude);
              startActivity(intent);
        }
        
      }
  /**
   * This method will get the device's latitude and longitude coordinates
   * @param _loc
   */
  public void onLocationEvent(Location _loc){
    
    latitude = _loc.getLatitude();
    longitude = _loc.getLongitude();
    
  }

  public int sketchWidth() { 
      
      return displayWidth; 
      }
    
    public int sketchHeight() { 
      return displayHeight;
      }
  
  }
