package com.example.root.touristhelpline2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    StateAdapter stateAdapter;
    PlaceAdapter placeAdapter;
    List<Places> placesList;
    String[] states = {"Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chhattisgarh","Delhi","Goa","Gujarat",
    "Haryana","Himachal Pradesh","Jammu & Kashmir","Jharkhand","Karnataka","Kerala","Madhya Pradesh","Maharashtra",
    "Manipur","Meghalaya","Mizoram","Nagaland","Odisha","Punjab","Rajasthan","Sikkim","Tamilnadu","Telangana",
    "Tripura","Uttarakhand","Uttar Pradesh","West Bengal"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!SharedPrefManager.getmInstance(this).isLoggedIn()) {
            finish();
            startActivity (new Intent(MainActivity.this,Login.class));
        }

        recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL,false));
        stateAdapter = new StateAdapter (states,this);
        recyclerView.setAdapter(stateAdapter);

        placesList = new ArrayList<>();
        recyclerView2 = findViewById(R.id.recyclerView2);
        recyclerView2.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this,LinearLayout.VERTICAL,false));
        placeAdapter = new PlaceAdapter(placesList,this);
        recyclerView2.setAdapter(placeAdapter);


        new GetPlaces().execute();
    }

    public class GetPlaces extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            String response = requestHandler.sendPostRequest(URLs.placesURL,new HashMap<String, String>());
            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!TextUtils.isEmpty(s)) {
                try {
                    JSONObject response = new JSONObject(s);
                    JSONArray res = response.getJSONArray("response");
                    for (int i=0; i<res.length(); i++) {
                        JSONObject p = res.getJSONObject(i);
                        int id = Integer.parseInt(p.getString("id"));
                        String imageUrl = p.getString("imageUrl");
                        String Url = p.getString("Url");
                        int upvotes = Integer.parseInt(p.getString("upvotes"));
                        String name = p.getString("name");
                        String location = p.getString("location");
                        Places pl = new Places(id,imageUrl,Url,upvotes,name,location);
                        placesList.add (pl);
                        placeAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
