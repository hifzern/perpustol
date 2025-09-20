package com.example.test_weather;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button btn_getCityID, btn_getWaetherByID,  btn_getwaetherByCityName;
    EditText et_dataInput;
    ListView lv_waetherReaports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //Assign value to each control in the lay out
        btn_getCityID = findViewById(R.id.btn_getCityID);
        btn_getWaetherByID = findViewById(R.id.btn_getWaetherByID);
        btn_getwaetherByCityName = findViewById(R.id.btn_getwaetherByCityName);

        et_dataInput = findViewById(R.id.et_dataInput);
        lv_waetherReaports = findViewById(R.id.lv_waetherReaports);

        //click Listener
        btn_getCityID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestQueue requestQueue;

// Instantiate the cache
                Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

// Set up the network to use HttpURLConnection as the HTTP client.
                Network network = new BasicNetwork(new HurlStack());

// Instantiate the RequestQueue with the cache and network.
                requestQueue = new RequestQueue(cache, network);

// Start the queue
                requestQueue.start();

                String url = "https://api.projectidek.dev/book/" + et_dataInput.getText().toString();

// Formulate the request and handle the response.

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject book_data = null;
                        String title = "";
                        try {
                            book_data = response.getJSONObject("data");
                            title = book_data.getString("title");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

//                        JSONObject data = null;
//                        String title = "";
//                        try {
//                            data = book.getJSONObject("data");
//                            title = data.getString("title");
//                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
//                        }




//                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
//                        JSONArray Books = null;
//                        try {
//                            Books = response.getJSONArray("data");
//                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
//                        }
//
//                        JSONObject book_1 = null;
//                        String title = "";
//
//                        try {
//                            book_1 = Books.getJSONObject(6);
//                            title = book_1.getString("title");
//                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
//                        }



                        Toast.makeText(MainActivity.this, "title: " + title, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });

//                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                // Do something with the response
//                                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                // Handle error
//                                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
//
//                            }
//                        });

                requestQueue.add(request);
//                requestQueue.add(stringRequest);
                //Toast.makeText(MainActivity.this, "You typed: " + et_dataInput.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        btn_getWaetherByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "test_btn_02", Toast.LENGTH_SHORT).show();

            }
        });

        btn_getwaetherByCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "test_btn_03", Toast.LENGTH_SHORT).show();

            }
        });
    }
}