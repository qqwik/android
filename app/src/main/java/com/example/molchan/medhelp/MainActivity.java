package com.example.molchan.medhelp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;


public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    Date[] dates;
    String[] s;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ListView lvMain = (ListView) findViewById(R.id.mainList);



        ContentValues cv = new ContentValues();
        if (getIntent().getStringExtra("name") != null)
            cv.put("name", getIntent().getStringExtra("name"));
        if (getIntent().getStringExtra("reminder 1") != null)
            cv.put("reminderOne", getIntent().getStringExtra("reminder 1"));
        if (getIntent().getStringExtra("reminder 2") != null)
            cv.put("reminderTwo", getIntent().getStringExtra("reminder 2"));
        if (getIntent().getStringExtra("reminder 3") != null)
            cv.put("reminderThree", getIntent().getStringExtra("reminder 3"));
        db.insert("mytable", null, cv);
        if (getIntent().getStringExtra("delete") != null)
            db.delete("mytable", null, null);

        Cursor c = db.query("mytable", null, null, null, null, null,"name "+"ASC");
        count = c.getCount();
        s = new String[count];
        dates = new Date[count*3];
        int i = 0;
        if (c.getCount() >= 1) {
            if (c.moveToFirst()) {

                int nameColIndex = c.getColumnIndex("name");

                do {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
                    if (getIntent().getStringExtra("reminder 1") != null) {
                        try {
                            dates[3*i] = dateFormat.parse(getIntent().getStringExtra("reminder 1"));
                        } catch (ParseException p) {
                            p.printStackTrace();
                        }
                    }
                    if (getIntent().getStringExtra("reminder 2") != null) {
                        try {
                            dates[3*i+1] = dateFormat.parse(getIntent().getStringExtra("reminder 2"));
                        } catch (ParseException p) {
                            p.printStackTrace();
                        }


                    }
                    if (getIntent().getStringExtra("reminder 3") != null) {
                        try {
                           dates[3*i+2] = dateFormat.parse(getIntent().getStringExtra("reminder 3"));
                        } catch (ParseException p) {
                            p.printStackTrace();
                        }
                    }
                    s[i] = c.getString(nameColIndex);
                    i++;

                } while (c.moveToNext());
                c.close();
                dbHelper.close();
            }


            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, s);

            // присваиваем адаптер списку
            lvMain.setAdapter(adapter);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewPill.class);
                startActivity(intent);
            }
        });


    }

    private void restartNotify() {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, TimeNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);
// На случай, если мы ранее запускали активити, а потом поменяли время,
// откажемся от уведомления
        am.cancel(pendingIntent);
// Устанавливаем разовое напоминание
        for (int i = 0; i < count * 3; i++) {
            if (dates[i] != null) {
                am.set(AlarmManager.RTC_WAKEUP, dates[i].getTime(), pendingIntent);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.menu_new_pill:
                Intent intent = new Intent(MainActivity.this, NewPill.class);
                startActivity(intent);
                break;
            case R.id.menu_action_settings:
                intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            // конструктор суперкласса
            super(context, "myData", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            // создаем таблицу с полями
            db.execSQL("create table mytable ("
                    + "_id integer primary key autoincrement, "
                    + "name TEXT, "
                    + "reminderOne TEXT, "
                    + "reminderTwo TEXT, "
                    + "reminderThree TEXT" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }


}
