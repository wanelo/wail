package com.wanelo.wail.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wanelo.wail.Wail;

public class NetworkAdapter extends ArrayAdapter<String> {

    private static final int layoutId = R.layout.grid_item;
    private Wail wail;
    private Picasso picasso;

    public NetworkAdapter(Context context, Wail wail) {
        super(context, layoutId);
        this.wail = wail;
        this.picasso = new Picasso.Builder(context).build();
        addData();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(layoutId, null);
        }

        ImageView imageView = (ImageView) convertView;
//        imageView.setImageResource(R.color.image_placeholder);
        picasso.load(getItem(position)).noFade().placeholder(R.color.image_placeholder).into(imageView);
//        wail.load(getItem(position), imageView);
        return convertView;
    }

    private void addData() {
        setNotifyOnChange(false);
        for(int loop = 0; loop < 10; loop++) {
            int length = ImageUrls.DATA.length;
            for(int index = 0; index < length; index++){
                add(ImageUrls.DATA[index]);
            }
        }

        setNotifyOnChange(false);
        notifyDataSetChanged();
    }
}
