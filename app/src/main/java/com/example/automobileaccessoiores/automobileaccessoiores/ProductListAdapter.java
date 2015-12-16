package com.example.automobileaccessoiores.automobileaccessoiores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Parcelable;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import static android.support.v4.app.ActivityCompat.startActivity;


public class ProductListAdapter extends ArrayAdapter<ProductList> {
    Context acontext;
    LayoutInflater inflater;

    ImageLoader imageLoader;
    public ProductListAdapter(Context context, List<ProductList> objects) {
        super(context,0, objects);
        inflater = (LayoutInflater)
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        acontext=context;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        //imageLoader = new ImageLoader(Volley.newRequestQueue(acontext), imageCache);

        final String m;
        imageLoader = new ImageLoader(Volley.newRequestQueue(acontext), new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });

        ViewHolder holder;
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.product_list_view, null);
            holder = new ViewHolder();
            holder.IVContactImage=(NetworkImageView)convertView.findViewById(R.id.IVProductImage);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
            holder.btnMore=(Button) convertView.findViewById(R.id.btnMore);
            convertView.setTag(holder);

           
      } else {
            holder = (ViewHolder) convertView.getTag();

       }
            final ProductList productList = getItem(position);
            holder.tvName.setText(productList.getProductName());
            holder.tvPrice.setText(productList.getPrice() + " TK");
            holder.IVContactImage.setImageUrl(productList.getProductImage(), imageLoader);
            holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(parent.getContext(),SingleProductView.class);
                i.putExtra("ds", productList);
                parent.getContext().startActivity(i);

            }
        });
         return convertView;
    }

    static public class ViewHolder {
        public NetworkImageView IVContactImage;
        public TextView tvName;
        public TextView tvPrice;
        public Button btnMore;

    }
}
