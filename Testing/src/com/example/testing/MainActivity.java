package com.example.testing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


public class MainActivity extends ActionBarActivity {

	// Declare the UI components
		private ListView songsListView;

		// Declare an ArrayAdapter that we use to join the data set and the ListView
		// is the way of type safe, means you only can pass Strings to this array
		//Anyway ArrayAdapter supports only TextView
		private ArrayAdapter arrayAdapter;

		private Firebase ref;
		private MainActivity activ;
		private ArrayList<String> dishes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	activ = this;
    	dishes = new ArrayList<String>(20);
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
     // Initialize the UI components
        songsListView = (ListView)findViewById(R.id.listView1);

        // Create a reference to a Firebase location
        ref = new Firebase("https://uqtez5y2bki.firebaseio-demo.com/Restaurants/Restaurant 2/Tables/Table 2/Orders");

        // Read data and react to changes
        ref.addChildEventListener(new ChildEventListener() {
        	  @Override
        	  public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
        	    Map s = snapshot.getValue(Map.class);
        	    dishes.add(snapshot.getName() + "  " + ((Map)snapshot.getValue(Map.class)).get("Price"));
        	    Log.i("Test", dishes.toString());
        	    arrayAdapter = new ArrayAdapter(activ, android.R.layout.simple_list_item_1, dishes.toArray());

                // By using setAdapter method, you plugged the ListView with adapter
                songsListView.setAdapter(arrayAdapter);
        	  }

        	  @Override public void onChildChanged(DataSnapshot snapshot, String previousChildName) { }

        	  @Override public void onChildRemoved(DataSnapshot snapshot) {
        		Map s = snapshot.getValue(Map.class);
          	    dishes.remove(snapshot.getName() + "  " + ((Map)snapshot.getValue(Map.class)).get("Price"));
          	    Log.i("Test", dishes.toString());
          	    arrayAdapter = new ArrayAdapter(activ, android.R.layout.simple_list_item_1, dishes.toArray());

                // By using setAdapter method, you plugged the ListView with adapter
                songsListView.setAdapter(arrayAdapter);
        	  }

        	  @Override public void onChildMoved(DataSnapshot snapshot, String previousChildName) { }

        	  @Override public void onCancelled(FirebaseError error) { }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
