package com.example.moon.vollydemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;

public class MainActivity extends AppCompatActivity {


    TextView tx;
    Button btn;
    String url = "https://androidtutorialpoint.com/api/volleyString";
    String imageUrl = "https://androidtutorialpoint.com/api/lg_nexus_5x";
    String jsonUrl = "https://androidtutorialpoint.com/api/volleyJsonObject";
    //String jsonUrl = "https://rest.soilgrids.org/query?lon=88.6228466&lat=24.3665815";
    String jsonArrayUrl = "https://androidtutorialpoint.com/api/volleyJsonArray";
    StringRequest stringRequest;
    ImageRequest imageRequest;
    JsonObjectRequest jsonObjectRequest;
    JsonArrayRequest jsonArrayRequest;
    ImageView imageView;
    Button btn_imv;
    TextView textView,textView1;
    Button btn2;
    Button btn4;
    TextView textView3;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPlayProtect();




        tx = (TextView)findViewById(R.id.tv_text);
        btn = (Button)findViewById(R.id.button);
        imageView = (ImageView)findViewById(R.id.imageView);
        btn_imv = (Button)findViewById(R.id.button2);
        btn2 = (Button)findViewById(R.id.button3);
        textView = (TextView)findViewById(R.id.textView);
        textView1 = (TextView)findViewById(R.id.textView2);
        textView3 = (TextView)findViewById(R.id.textView3);
        btn4 = (Button)findViewById(R.id.button4);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        stringRequest = new StringRequest(Request.Method.POST, url,
               new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                        tx.setText(response);
                   }
               },

               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       Toast.makeText(getApplicationContext(),"Error in fetching data",Toast.LENGTH_SHORT).show();
                       error.printStackTrace();
                   }
               }
       );


       imageRequest = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
           @Override
           public void onResponse(Bitmap response) {
                    imageView.setImageBitmap(response);
           }
       },0,0, ImageView.ScaleType.FIT_CENTER,null,
               new Response.ErrorListener(){

                   @Override
                   public void onErrorResponse(VolleyError error) {
                          error.printStackTrace();
                   }
               }
       );


        jsonObjectRequest  = new JsonObjectRequest(Request.Method.POST, jsonUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.INVISIBLE);
                try {
                   textView.setText(String.valueOf(response.getBoolean("raw")));
                   // textView1.setText(response.getString("screenSize"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               error.printStackTrace();
            }
        });

        jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, jsonArrayUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String finalStrings = "";
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String ROM  = jsonObject.getString("rom");
                            finalStrings+=ROM;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    textView3.setText(finalStrings);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );


       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               SingleToneClass.getInstance(getApplicationContext()).addToRequest(stringRequest);
           }
       });

       btn_imv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               SingleToneClass.getInstance(getApplicationContext()).addToRequest(imageRequest);
           }
       });


       btn2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               progressBar.setVisibility(View.VISIBLE);
               SingleToneClass.getInstance(getApplicationContext()).addToRequest(jsonObjectRequest);
           }
       });

       btn4.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               SingleToneClass.getInstance(getApplicationContext()).addToRequest(jsonArrayRequest);
           }
       });

    }

    private void checkPlayProtect() {
        SafetyNet.getClient(this)
                .isVerifyAppsEnabled()
                .addOnCompleteListener(new OnCompleteListener<SafetyNetApi.VerifyAppsUserResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<SafetyNetApi.VerifyAppsUserResponse> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().isVerifyAppsEnabled()){
                                Toast.makeText(getApplicationContext(),"Varified app Enable",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"Varified app disabled",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(),"Failed to open task",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.writeJson){
            Intent intent = new Intent(MainActivity.this,WriteActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
