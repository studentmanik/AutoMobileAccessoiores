package com.example.automobileaccessoiores.automobileaccessoiores;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import java.util.List;

public class MainProduct extends AppCompatActivity implements AdapterView.OnItemClickListener {
    List<ProductList> productLists=new ArrayList<ProductList>();
   // private static final String API_URL = "http://studentmanik.tk/available.html";
    ListView lv;
    ProductListAdapter adapter;
    final Context context = this;
    Dialog dialog;
    Spinner spinner;
    Spinner spinner1;
    Button btnAvailable;
    Button dialogButtonOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv=(ListView)findViewById(R.id.ProductList);

        final String API_URL = "http://studentmanik.tk/available.html";

        btnAvailable=(Button)findViewById(R.id.btnAvailble);

        btnAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productLists.clear();

                JsonArrayRequest rs = new JsonArrayRequest(API_URL, jsonArrayListener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(rs);

            }
        });

       JsonArrayRequest rs=new JsonArrayRequest(API_URL,jsonArrayListener,errorListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(rs);

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.search);
        spinner=(Spinner)dialog.findViewById(R.id.spinner);
       /// spinner.setOnItemSelectedListener(this);
        spinner1=(Spinner)dialog.findViewById(R.id.spinner1);
        dialogButtonOK=(Button)dialog.findViewById(R.id.dialogButtonOK);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.setTitle("Search ");
                dialogButtonOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productLists.clear();
                        String st = spinner.getSelectedItem().toString().toLowerCase().replace(" ", "");
                        String st1 = spinner1.getSelectedItem().toString().toLowerCase().replace(" ", "");
                        String api_url_search = "http://studentmanik.tk/" + st + st1 + ".html";
                        JsonArrayRequest rs = new JsonArrayRequest(api_url_search, jsonArrayListener, errorListener);
                        RequestQueue queue = Volley.newRequestQueue(context);
                        queue.add(rs);
                        dialog.dismiss();
                       // Toast.makeText(getApplicationContext(), api_url_search, Toast.LENGTH_SHORT).show();

                   /*     spinner.getSelectedItem();
                        spinner1.getSelectedItem();
                        Toast.makeText(getApplicationContext(), spinner.getSelectedItem().toString()+" "+spinner1.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                   */
                    }
                });
               dialog.show();

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        lv.setOnItemClickListener(this);
    }
    ErrorListener errorListener = new ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError arg0) {
            // TODO Auto-generated method stub

        }
    };
    Listener<JSONArray> jsonArrayListener=new Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {

            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject obj = response.getJSONObject(i);
                    ProductList pl=new ProductList();
                    pl.setProductImage(obj.getString("productimage"));
                    pl.setProductName(obj.getString("productname"));
                    pl.setPrice(obj.getString("productprice"));
                    pl.setShowroomid(obj.getString("showroomid"));
                    productLists.add(pl);



                   //  Toast.makeText(getApplicationContext(), obj.getString("title"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            adapter= new ProductListAdapter(getApplicationContext(),productLists);

            lv.setAdapter(adapter);

            adapter.notifyDataSetChanged();

        }
    };

 @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       // Toast.makeText(getApplicationContext(), position+"", Toast.LENGTH_SHORT).show();

    }


}
