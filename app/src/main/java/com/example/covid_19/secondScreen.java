package com.example.covid_19;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class secondScreen extends AppCompatActivity {


    ArrayList<Util> mArrayList = new ArrayList<Util>();
    private Util mUtil;
    private TextView mConfrimed;
    private TextView mLocation;
    private TextView mInNigeria;
    private TextView mTextOutbreak;
    private TextView mActive;
    private TextView mRecovered;
    private TextView mDeath;
    private TextView mToday;
    private TextView mTomorrow;
    private TextView mAgoTwoDays;
    private TextView mIn7days;
    private CardView location;
    public static TextView countryname;
    public static Dialog sDialog;
    private String mUrl = "https://corona.lmao.ninja/v2/countries";
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mProgressBar = findViewById(R.id.progressbar);
        countryname = findViewById(R.id.countryname);
        mLocation = findViewById(R.id.location);
        mTextOutbreak = findViewById(R.id.textViewoutbreak);
        mInNigeria = findViewById(R.id.innigeria);
        mConfrimed = findViewById(R.id.confirmedcasesnumber);
        mActive = findViewById(R.id.activecasesnumber);
        mRecovered = findViewById(R.id.recoveredcasesnumber);
        mDeath = findViewById(R.id.deathcasesnumbers);
        mToday = findViewById(R.id.outbreaktoday);
        mTomorrow = findViewById(R.id.outbreaktomorrow);
        mAgoTwoDays = findViewById(R.id.ago2days);
        mIn7days = findViewById(R.id.in7days);
        location = findViewById(R.id.locationcardView);
        mProgressBar.setVisibility(View.GONE);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();


            }
        });

        TextView callNcdc =  findViewById(R.id.callncdc);
        callNcdc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getData();
               if(!countryname.getText().toString().equals("Select country")){
                   showResult();
               }
            }
        });

    }

    public void showResult() {
        for(int i =0;i<mArrayList.size()-1;i++){
            if(mArrayList.get(i).getCountry().equals(countryname.getText().toString())){
                mInNigeria.setText(mArrayList.get(i).getCountry());
                mTextOutbreak.setText(mArrayList.get(i).getCountry());
                mLocation.setText(mArrayList.get(i).getCountry());
                mConfrimed.setText(mArrayList.get(i).getConfirmed());
                mActive.setText(mArrayList.get(i).getActive());
                mRecovered.setText(mArrayList.get(i).getRecovered());
                mDeath.setText(mArrayList.get(i).getDeath());
                mToday.setText(mArrayList.get(i).getTwodaysAgo());
                int to = Integer.parseInt(mArrayList.get(i).getConfirmed()) + 20;
                mTomorrow.setText(Integer.toString(to));
                mAgoTwoDays.setText("0");
            }
        }
    }

    private void displaynation() {
        Log.i("Display", "Displayed");
        sDialog = new Dialog(this);
        sDialog.setContentView(R.layout.countrys);
        Collections.sort(mArrayList, new Comparator<Util>() {
            @Override
            public int compare(Util util, Util t1) {
                 return util.getCountry().compareTo(t1.getCountry());
            }
        });
        nationAdapter nationAdapterr = new nationAdapter(this,mArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = sDialog.findViewById(R.id.recyclernation);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(nationAdapterr);
        WindowManager.LayoutParams ip = new WindowManager.LayoutParams();
        ip.copyFrom(sDialog.getWindow().getAttributes());
        ip.width = WindowManager.LayoutParams.MATCH_PARENT;
        ip.height = WindowManager.LayoutParams.MATCH_PARENT;
        sDialog.getWindow().setAttributes(ip);
        sDialog.setCanceledOnTouchOutside(false);
        sDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sDialog.show();
        sDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                showResult();
            }
        });

    }

    private void getData(){
        mProgressBar.setVisibility(View.VISIBLE);
        mArrayList.clear();
        RequestQueue mrequestQueue = Volley.newRequestQueue(this);
        StringRequest mStringrequest = new StringRequest(Request.Method.GET, mUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("RESPONSE", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0;i < jsonArray.length();i++){

                        JSONObject countires  = jsonArray.getJSONObject(i);
                        JSONObject detailsoFeach = countires.getJSONObject("countryInfo");
                        Util newUtil = new Util(countires.isNull("country")?"":countires.getString("country"),Integer.toString(countires.getInt("cases")),Integer.toString(countires.getInt("active")),Integer.toString(countires.getInt("recovered")),Integer.toString(countires.getInt("deaths")),Integer.toString(countires.getInt("todayCases")),detailsoFeach.getString("flag"));
                        mArrayList.add(newUtil);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mProgressBar.setVisibility(View.GONE);
                displaynation();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(secondScreen.this,"Oops something went wrong.",Toast.LENGTH_SHORT).show();

            }
        });

        RetryPolicy retryPolicy = new DefaultRetryPolicy(10000,2,1);
        mStringrequest.setRetryPolicy(retryPolicy);
        mrequestQueue.add(mStringrequest);

    }


}
