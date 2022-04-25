package com.example.mentorplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Dish> list;
    static String JSON_URL = "https://demo3755793.mockable.io/dish";
    Adapter adapter;
    TextView totalPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        totalPrice = findViewById(R.id.totalPrice);
        recyclerView = findViewById(R.id.dataRecycler);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list= new ArrayList<>();
        extractData();
   //     createDish();


        LocalBroadcastManager.getInstance(this).registerReceiver(priceReceiver,
                new IntentFilter("message"));
    }
    private void createDish(){
        ArrayList<String> custom = new ArrayList<>();
        custom.add(0, "Customize");
        custom.add("Falafel-E-Khass (12pcs), with a specially prepared chutney");
        Dish dish= new Dish("Zaitooni Paneer Dum Biryani (Veg Paneer) specially made with Fresh Paneer",
                custom, "477");
        list.add(dish);
        ArrayList<String> custom1 = new ArrayList<>();
        custom1.add("Beetroot and Peanut Kebab");
        custom1.add(0, "Customize");
        Dish dish1= new Dish("Zaikedaar Paneer [Paneer Dum Biryani - (Veg Paneer) specially made with Fresh Paneer",
                custom, "514");
        list.add(dish1);
    }

    private void extractData() {
        RequestQueue queue= Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject dishObject = response.getJSONObject(i);
                    Dish dish = new Dish();
                    dish.setDish_name(dishObject.getString("dish_name").toString());
                    JSONArray jsonArray = dishObject.getJSONArray("customisation");
                    ArrayList<String> customList = new ArrayList<>();
                    for (int j = 0; j < jsonArray.length(); j++) {
                        customList.add(jsonArray.getString(j).toString());
                    }
                    customList.add(0, "Customize");
                    dish.setCustomisation(customList);
                    dish.setPrice(dishObject.getString("price").toString());
                    list.add(dish);


                } catch (JSONException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            adapter = new Adapter(this, list);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        }, error -> Log.d("tag", "onErrorResponse"+error.getMessage()));
        queue.add(jsonArrayRequest);
        queue.start();
    }
    public BroadcastReceiver priceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int price = Integer.parseInt(intent.getStringExtra("price"));
            int prevPrice = Integer.parseInt(totalPrice.getText().toString());
            int totalP;
            if(!intent.getBooleanExtra("operation",false)){
                totalP= prevPrice-price;
            } else
                totalP=prevPrice+price;
            String newPrice= String.valueOf(totalP);
            totalPrice.setText(newPrice);
        }
    };
}