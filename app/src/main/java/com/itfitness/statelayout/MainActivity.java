package com.itfitness.statelayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.itfitness.statelayout.widget.StateLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StateLayout stateLayout=findViewById(R.id.statelayout);
        stateLayout.showLoading();
    }
}
