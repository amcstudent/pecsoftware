package com.peccompany.pec;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import static com.peccompany.pec.ConfConnectionActivity.confConnectionActivity;

public class ConfConnectionActivity extends AppCompatActivity {
    static TextView toastMessage;
    static Toast toast;
    static EditText url;
    static Button save;
    private int height, width;
    static ConfConnectionActivity confConnectionActivity;
    static Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conf_connection_activity);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#02A9B5"));
        findViewById(android.R.id.content).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Helper.hideSoftKeyboard(ConfConnectionActivity.this);
                return false;
            }
        });
        confConnectionActivity = this;
        helper = new Helper();
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        url = (EditText) findViewById(R.id.url);
        save = (Button) findViewById(R.id.save);
        RelativeLayout.LayoutParams relativeParams;
        toastMessage = new TextView(this);
        toastMessage.setTextColor(Color.WHITE);
        toastMessage.setTypeface(null, Typeface.BOLD);
        toastMessage.setPadding(width * 5 / 100, height * 2 / 100, width * 5 / 100, height * 2 / 100);
        toastMessage.setTextSize((float) (width * 1 / 100));
        toast = Toast.makeText(getApplicationContext(), null,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, height * 3 / 100);

        relativeParams = (RelativeLayout.LayoutParams) url.getLayoutParams();
        relativeParams.setMargins(width * 18 / 100, height * 32 / 100, 0, 0);
        relativeParams.width = width * 62 / 100;
        relativeParams.height = height * 10 / 100;
        url.setLayoutParams(relativeParams);
        url.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        url.setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 5 / 100);
        SharedPreferences prefs = getSharedPreferences("connection", MODE_PRIVATE);
        url.setText(prefs.getString("conn", ""));

        relativeParams = (RelativeLayout.LayoutParams) save.getLayoutParams();
        relativeParams.setMargins(width * 34 / 100, height * 50 / 100, 0, 0);
        relativeParams.width = width * 30 / 100;
        relativeParams.height = height * 10 / 100;
        save.setLayoutParams(relativeParams);
        save.setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 4 / 100);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getCurrentFocus() != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                new Task().execute();
            }
        });
    }
}

class Task extends AsyncTask<Void, Integer, String> {
    protected void onPreExecute() {

    }

    protected String doInBackground(Void... arg0) {
        int conn = confConnectionActivity.helper.isConnectedToServer(confConnectionActivity.url.getText().toString().trim());
        return String.valueOf(conn);
    }

    protected void onProgressUpdate(Integer... a) {
        super.onProgressUpdate(a);
    }

    protected void onPostExecute(String result) {
        if (result.charAt(0) == '0') {
            confConnectionActivity.toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
            confConnectionActivity.toastMessage.setText("No URL connection!");
            confConnectionActivity.toast.setView(confConnectionActivity.toastMessage);
            confConnectionActivity.toast.show();
        } else {
            confConnectionActivity.toastMessage.setBackgroundColor(Color.parseColor("#038E18"));
            confConnectionActivity.toastMessage.setText("URL saved successfully!");
            confConnectionActivity.toast.setView(confConnectionActivity.toastMessage);
            confConnectionActivity.toast.show();
            String connectionStr = confConnectionActivity.url.getText().toString().trim();
            if (confConnectionActivity.url.getText().toString().trim().charAt(confConnectionActivity.url.getText().toString().trim().length() - 1) != '/')
            {
                connectionStr += '/';
            }
            SharedPreferences.Editor connection = confConnectionActivity.getSharedPreferences("connection", confConnectionActivity.MODE_PRIVATE).edit();
            connection.putString("conn", connectionStr);
            connection.commit();
        }

        super.onPostExecute(result);
    }
}
