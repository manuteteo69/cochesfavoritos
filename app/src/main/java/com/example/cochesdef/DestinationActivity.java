package com.example.cochesdef;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DestinationActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "extra_title";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        String title = getIntent().getStringExtra(EXTRA_TITLE);
        if (title == null) title = "Detail";

        ((TextView) findViewById(R.id.tvScreenTitle)).setText(title);
    }
}
