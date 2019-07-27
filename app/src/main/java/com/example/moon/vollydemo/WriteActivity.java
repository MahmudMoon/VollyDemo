package com.example.moon.vollydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class WriteActivity extends AppCompatActivity {

    EditText et_name,et_age,et_salary;
    Button btn_submit;
    String jsonUrl = "http://myjson.com/vfjts";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        et_name = (EditText)findViewById(R.id.editText);
        et_age = (EditText)findViewById(R.id.editText2);
        et_salary = (EditText)findViewById(R.id.editText3);
        btn_submit = (Button)findViewById(R.id.button5);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String name = et_name.getText().toString();
                final String age = et_age.getText().toString();
                final String salary = et_salary.getText().toString();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, jsonUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),"Response is:" + response,Toast.LENGTH_SHORT).show();
                    }
                },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> myMap = new HashMap<String, String>();
                        myMap.put("name",name);
                        myMap.put("age",age);
                        myMap.put("salary",salary);
                        return myMap;
                    }
                };


                SingleToneClass.getInstance(getApplicationContext()).addToRequest(stringRequest);
            }
        });

    }
}
