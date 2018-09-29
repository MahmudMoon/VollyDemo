package com.example.moon.vollydemo;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;

public class MainActivity extends AppCompatActivity {


    TextView tx;
    Button btn;
    String url = "https://androidtutorialpoint.com/api/volleyString";
    String imageUrl = "https://androidtutorialpoint.com/api/lg_nexus_5x";
    String jsonUrl = "https://androidtutorialpoint.com/api/volleyJsonObject";
    StringRequest stringRequest;
    ImageRequest imageRequest;
    ImageView imageView;
    Button btn_imv;
    TextView textView,textView1;
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx = (TextView)findViewById(R.id.tv_text);
        btn = (Button)findViewById(R.id.button);
        imageView = (ImageView)findViewById(R.id.imageView);
        btn_imv = (Button)findViewById(R.id.button2);
        btn2 = (Button)findViewById(R.id.button3);
        textView = (TextView)findViewById(R.id.textView);
        textView1 = (TextView)findViewById(R.id.textView2);

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


        final JsonObjectRequest jsonObjectRequest  = new JsonObjectRequest(Request.Method.POST, jsonUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    textView.setText(response.getString("rom"));
                    textView1.setText(response.getString("screenSize"));
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
               SingleToneClass.getInstance(getApplicationContext()).addToRequest(jsonObjectRequest);
           }
       });

    }
}
