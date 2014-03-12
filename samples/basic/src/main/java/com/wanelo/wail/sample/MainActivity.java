package com.wanelo.wail.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.GridView;

import com.squareup.picasso.Picasso;
import com.wanelo.wail.Wail;

public class MainActivity extends ActionBarActivity {

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.gridView);
        Wail wail = new Wail.Builder(this).build();
        NetworkAdapter adapter = new NetworkAdapter(this, wail);
        gridView.setAdapter(adapter);
    }


}
