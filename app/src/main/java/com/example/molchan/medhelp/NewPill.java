package com.example.molchan.medhelp;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class NewPill extends AppCompatActivity {
    EditText etName;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    Button btnDone;
    CheckBox cb1;
    CheckBox cb2;
    CheckBox cb3;
    int myHour = 0;
    int myMinute = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pill);
        etName = (EditText) findViewById(R.id.editText);
        tv1 = (TextView) findViewById(R.id.textView2);
        tv2 = (TextView) findViewById(R.id.textView3);
        tv3 = (TextView) findViewById(R.id.textView4);
       cb1 = (CheckBox) findViewById(R.id.checkBox5);
        cb2 = (CheckBox) findViewById(R.id.checkBox6);
        cb3 = (CheckBox) findViewById(R.id.checkBox7);
        btnDone = (Button) findViewById(R.id.btnDone);
    }


        public void onClick(View v) {
            switch(v.getId()){
                case R.id.textView2:
                    showDialog(1);
                    break;
                case R.id.textView3:
                    showDialog(2);
                    break;
                case R.id.textView4:
                    showDialog(3);
                    break;
                case R.id.btnDone:
                    caseBtnDone();
                    break;
            }

        }



void caseBtnDone(){
    String name = etName.getText().toString();
    Intent intent = new Intent(NewPill.this, MainActivity.class);
    intent.putExtra("name",name);
    if (cb1.isChecked()){
        intent.putExtra("reminder 1",tv1.getText());
    }
    if (cb2.isChecked()){
        intent.putExtra("reminder 2",tv2.getText());
    }
    if (cb3.isChecked()){
        intent.putExtra("reminder 3",tv3.getText());
    }
    startActivity(intent);
}



    protected Dialog onCreateDialog(int id) {
        if (id == 1) {
            TimePickerDialog tpd = new TimePickerDialog(this, myCallBack, myHour, myMinute, true);
            return tpd;
        }
        if (id == 2) {
            TimePickerDialog tpd = new TimePickerDialog(this, myCallBack2, myHour, myMinute, true);
            return tpd;
        }
        if (id == 3) {
            TimePickerDialog tpd = new TimePickerDialog(this, myCallBack3, myHour, myMinute, true);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    TimePickerDialog.OnTimeSetListener myCallBack = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;
            if (myMinute/2<=5)
                tv1.setText(myHour+":"+"0"+myMinute);
            else
                tv1.setText(myHour+":"+myMinute);
        }

    };
    TimePickerDialog.OnTimeSetListener myCallBack2 = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;
            if (myMinute/2<=5)
                tv2.setText(myHour+":"+"0"+myMinute);
            else
                tv2.setText(myHour+":"+myMinute);

        }

    };
    TimePickerDialog.OnTimeSetListener myCallBack3 = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;
            if (myMinute/2<=5)
            tv3.setText(myHour+":"+"0"+myMinute);
            else
                tv3.setText(myHour+":"+myMinute);
        }

    };

}
