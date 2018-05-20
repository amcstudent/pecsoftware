package com.peccompany.pec;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;

import static com.peccompany.pec.MainActivity.mainActivity;

public class MainActivity extends AppCompatActivity {
    TextView toastMessage, text;
    Toast toast;
    EditText user, pass;
    Button logIn, register, retrieve;
    ProgressBar progressBar;
    ImageView imageView;
    private int width, height;
    static MainActivity mainActivity;
    static Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(android.R.id.content).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Helper.hideSoftKeyboard(MainActivity.this);
                return false;
            }
        });
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#02A9B5"));
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        helper = new Helper();
        mainActivity = this;
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.getLayoutParams().height = height * 95 / 100;
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.colorPrimaryDark),
                PorterDuff.Mode.SRC_IN);
        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);
        logIn = (Button) findViewById(R.id.logIn);
        register = (Button) findViewById(R.id.register);
        retrieve = (Button) findViewById(R.id.retrieve);
        text = (TextView) findViewById(R.id.myText);
        imageView = (ImageView) findViewById(R.id.settings);
        RelativeLayout.LayoutParams relativeParams;
        toastMessage = new TextView(this);
        toastMessage.setTextColor(Color.WHITE);
        toastMessage.setTypeface(null, Typeface.BOLD);
        toastMessage.setPadding(width * 5 / 100, height * 2 / 100, width * 5 / 100, height * 2 / 100);
        toastMessage.setTextSize((float) (width * 1 / 100));
        toast = Toast.makeText(getApplicationContext(), null,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, height * 3 / 100);
        relativeParams = (RelativeLayout.LayoutParams) text.getLayoutParams();
        relativeParams.setMargins(width * 28 / 100, height * 5 / 100, 0, 0);
        relativeParams.width = width;
        relativeParams.height = height * 10 / 100;
        text.setLayoutParams(relativeParams);
        text.setTypeface(null, Typeface.BOLD_ITALIC);
        text.setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 7 / 100);


        relativeParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        relativeParams.setMargins(width * 85 / 100, height * 4 / 100, 0, 0);
        relativeParams.width = width * 8 / 100;
        relativeParams.height = height * 8 / 100;
        imageView.setLayoutParams(relativeParams);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getCurrentFocus() != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                register.setEnabled(false);
                logIn.setEnabled(false);
                retrieve.setEnabled(false);
                imageView.setEnabled(false);
                Intent myIntent = new Intent(MainActivity.this, ConfConnectionActivity.class);
                startActivity(myIntent);
                register.setEnabled(true);
                logIn.setEnabled(true);
                retrieve.setEnabled(true);
                imageView.setEnabled(true);
                user.setText("");
                pass.setText("");
                user.requestFocus();
            }
        });

        relativeParams = (RelativeLayout.LayoutParams) user.getLayoutParams();
        relativeParams.setMargins(width * 25 / 100, height * 18 / 100, 0, 0);
        relativeParams.width = width * 50 / 100;
        relativeParams.height = height * 10 / 100;
        user.setLayoutParams(relativeParams);
        user.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        user.setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 5 / 100);


        relativeParams = (RelativeLayout.LayoutParams) pass.getLayoutParams();
        relativeParams.setMargins(width * 25 / 100, height * 30 / 100, 0, 0);
        relativeParams.width = width * 50 / 100;
        relativeParams.height = height * 10 / 100;
        pass.setLayoutParams(relativeParams);
        pass.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        pass.setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 5 / 100);


        relativeParams = (RelativeLayout.LayoutParams) logIn.getLayoutParams();
        relativeParams.setMargins(width * 18 / 100, height * 52 / 100, 0, 0);
        relativeParams.width = width * 30 / 100;
        relativeParams.height = height * 10 / 100;
        logIn.setLayoutParams(relativeParams);
        logIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getCurrentFocus() != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                if (!Helper.isOnline(getApplicationContext())) {
                    toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                    toastMessage.setText("No internet connection!");
                    toast.setView(toastMessage);
                    toast.show();
                } else if (user.getText().toString().trim().length() == 0 || pass.getText().toString().trim().length() == 0) {
                    toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                    toastMessage.setText("No empty fields allowed!");
                    toast.setView(toastMessage);
                    toast.show();
                } else {
                    new LogIn().execute();
                }
            }
        });


        relativeParams = (RelativeLayout.LayoutParams) register.getLayoutParams();
        relativeParams.setMargins(width * 52 / 100, height * 52 / 100, 0, 0);
        relativeParams.width = width * 30 / 100;
        relativeParams.height = height * 10 / 100;
        register.setLayoutParams(relativeParams);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getCurrentFocus() != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                if (!Helper.isOnline(getApplicationContext())) {
                    toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                    toastMessage.setText("No internet connection!");
                    toast.setView(toastMessage);
                    toast.show();
                } else {
                    register.setEnabled(false);
                    logIn.setEnabled(false);
                    retrieve.setEnabled(false);
                    imageView.setEnabled(false);
                    Intent myIntent = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(myIntent);
                    register.setEnabled(true);
                    logIn.setEnabled(true);
                    retrieve.setEnabled(true);
                    imageView.setEnabled(true);
                    user.setText("");
                    pass.setText("");
                    user.requestFocus();
                }
            }
        });


        relativeParams = (RelativeLayout.LayoutParams) retrieve.getLayoutParams();
        relativeParams.setMargins(width * 28 / 100, height * 66 / 100, 0, 0);
        relativeParams.width = width * 45 / 100;
        relativeParams.height = height * 10 / 100;
        retrieve.setLayoutParams(relativeParams);
        retrieve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getCurrentFocus() != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                if (!Helper.isOnline(getApplicationContext())) {
                    toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                    toastMessage.setText("No internet connection!");
                    toast.setView(toastMessage);
                    toast.show();
                } else {
                    register.setEnabled(false);
                    logIn.setEnabled(false);
                    retrieve.setEnabled(false);
                    imageView.setEnabled(false);
                    Intent myIntent = new Intent(MainActivity.this, RetrieveActivity.class);
                    startActivity(myIntent);
                    register.setEnabled(true);
                    logIn.setEnabled(true);
                    retrieve.setEnabled(true);
                    imageView.setEnabled(true);
                    user.setText("");
                    pass.setText("");
                    user.requestFocus();
                }
            }
        });
    }
}

class LogIn extends AsyncTask<Void, Integer, String> {
    protected void onPreExecute() {
        mainActivity.progressBar.setVisibility(View.VISIBLE);
        mainActivity.user.setVisibility(View.INVISIBLE);
        mainActivity.pass.setVisibility(View.INVISIBLE);
        mainActivity.logIn.setVisibility(View.INVISIBLE);
        mainActivity.register.setVisibility(View.INVISIBLE);
        mainActivity.retrieve.setVisibility(View.INVISIBLE);
        mainActivity.text.setVisibility(View.INVISIBLE);
        mainActivity.imageView.setVisibility(View.INVISIBLE);
    }

    protected String doInBackground(Void... arg0) {
        String urlString = mainActivity.helper.getDomainName(mainActivity) + "checkClientsCred.php?user=" + mainActivity.user.getText().toString().trim() + "&pass=" + mainActivity.pass.getText().toString().trim();
        String response = null;
        try {
            response = mainActivity.helper.getMethod(urlString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    protected void onProgressUpdate(Integer... a) {
        super.onProgressUpdate(a);
    }

    protected void onPostExecute(String result) {
        if (result.trim().length() == 0) {
            mainActivity.toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
            mainActivity.toastMessage.setText("Username or Password is wrong.Please try again!");
            mainActivity.toast.setView(mainActivity.toastMessage);
            mainActivity.toast.show();
            mainActivity.progressBar.setVisibility(View.INVISIBLE);
            mainActivity.user.setVisibility(View.VISIBLE);
            mainActivity.pass.setVisibility(View.VISIBLE);
            mainActivity.logIn.setVisibility(View.VISIBLE);
            mainActivity.register.setVisibility(View.VISIBLE);
            mainActivity.retrieve.setVisibility(View.VISIBLE);
            mainActivity.text.setVisibility(View.VISIBLE);
            mainActivity.imageView.setVisibility(View.VISIBLE);
        } else {
            String[] parts = result.toString().split("\\$");
            long id = Long.parseLong(parts[0]);
            String name = parts[1];
            String position = parts[2];
            String pass = parts[3];
            String email = parts[4];
            Long maxInvoiceNo = Long.parseLong(parts[5]);
            CompanyModel companyModel = new CompanyModel();
            companyModel.id = id;
            companyModel.position = position;
            companyModel.name = name;
            companyModel.pass = pass;
            companyModel.email = email;
            companyModel.maxInvoiceNo = maxInvoiceNo;
            Intent intent = new Intent(mainActivity, HomeActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("companyModel", companyModel);
            intent.putExtras(b);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mainActivity.startActivity(intent);
        }

        super.onPostExecute(result);
    }
}