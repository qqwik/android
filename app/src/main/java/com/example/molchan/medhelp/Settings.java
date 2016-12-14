package com.example.molchan.medhelp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Settings extends AppCompatActivity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        tv = (TextView)findViewById(R.id.textView);

        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(view.getId()){
                    case R.id.textView:
                        Intent intent = new Intent(Settings.this, MainActivity.class);
                        intent.putExtra("delete","yes");
                        startActivity(intent);
                }
            }
        };
        tv.setOnClickListener(ocl);

    }
}
