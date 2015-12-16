package com.example.automobileaccessoiores.automobileaccessoiores;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SingleProductView extends AppCompatActivity {
    NetworkImageView ivSingleImage;
    ImageLoader imageLoader;
    Button call;
    Button sms;

    TextView tvProductName;
    TextView tvProductPrice;
    TextView tvShowroom;
    HashMap<String, ShowRoom> showRooms = new HashMap<>();
    ProductList pt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pt = (ProductList) getIntent().getSerializableExtra("ds");
        imageLoader = new ImageLoader(Volley.newRequestQueue(this), new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
        tvProductName = (TextView) findViewById(R.id.tvName);
        tvProductName.setText("Name: " + pt.getProductName());
        tvProductPrice = (TextView) findViewById(R.id.tvPrice);
        tvProductPrice.setText("Price: " + pt.getPrice());
        tvShowroom = (TextView) findViewById(R.id.tvshowroomName);
        call = (Button) findViewById(R.id.btnCall);
        sms = (Button) findViewById(R.id.btnSMS);
        JsonArrayRequest rs = new JsonArrayRequest("http://studentmanik.tk/showroom.html", jsonArrayListener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(rs);
        ivSingleImage = (NetworkImageView) findViewById(R.id.IVSingleImage);
        ivSingleImage.setImageUrl(pt.getProductImage(), imageLoader);
            }

    Response.ErrorListener errorListener = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError arg0) {
            // TODO Auto-generated method stub

        }
    };
    Response.Listener<JSONArray> jsonArrayListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {

            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject obj = response.getJSONObject(i);
                    ShowRoom showRoom = new ShowRoom();
                    showRoom.setShowroomid(obj.getString("showroomid"));
                    showRoom.setShowRoomName(obj.getString("showroomname"));
                    showRoom.setShowroomNumber(obj.getString("showroomNumber"));
                    showRooms.put(obj.getString("showroomid"), showRoom);


                    //  Toast.makeText(getApplicationContext(), obj.getString("title"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            sms.setOnClickListener(new View.OnClickListener() {

                String sms="Show Room ID: "+pt.getShowroomid()+", Product Name: "+pt.getProductName()+", Product Price: "+pt.getPrice();
                @Override
                public void onClick(View v) {
                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                    smsIntent.setData(Uri.parse("sms:"));
                    smsIntent.setType("vnd.android-dir/mms-sms");
                    smsIntent.putExtra("sms_body",sms);
                    smsIntent.putExtra("address", showRooms.get(pt.getShowroomid()).getShowroomNumber());

                    startActivity(smsIntent);
                }
            });
            tvShowroom.setText("Show Room: " + showRooms.get(pt.getShowroomid()).getShowRoomName());
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + showRooms.get(pt.getShowroomid()).getShowroomNumber()));
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(callIntent);
                }
            });

        }
    };

}
