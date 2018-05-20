package com.peccompany.pec;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static com.peccompany.pec.RegisterActivity.registerActivity;


public class RegisterActivity extends AppCompatActivity {
    EditText email, user, pass, companyName, position, retypePass;
    TextView toastMessage;
    Button register;
    Toast toast;
    ProgressBar progressBar;
    int height, width;
    RelativeLayout relativeLayout;
    static RegisterActivity registerActivity;
    static Helper helper;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        findViewById(android.R.id.content).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Helper.hideSoftKeyboard(RegisterActivity.this);
                return false;
            }
        });
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#02A9B5"));
        registerActivity = this;
        helper = new Helper();
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.getLayoutParams().height = height * 95 / 100;
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.colorPrimaryDark),
                PorterDuff.Mode.SRC_IN);
        relativeLayout = (RelativeLayout) findViewById(R.id.rl);
        companyName = (EditText) findViewById(R.id.companyName);
        position = (EditText) findViewById(R.id.position);
        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);
        retypePass = (EditText) findViewById(R.id.retypePass);
        email = (EditText) findViewById(R.id.email);
        register = (Button) findViewById(R.id.register);
        RelativeLayout.LayoutParams relativeParams;
        toastMessage = new TextView(this);
        toastMessage.setTextColor(Color.WHITE);
        toastMessage.setTypeface(null, Typeface.BOLD);
        toastMessage.setPadding(width * 5 / 100, height * 2 / 100, width * 5 / 100, height * 2 / 100);
        toastMessage.setTextSize((float) (width * 1 / 100));
        toast = Toast.makeText(getApplicationContext(), null,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, height * 3 / 100);
        relativeParams = (RelativeLayout.LayoutParams) companyName.getLayoutParams();
        relativeParams.setMargins(width * 25 / 100, height * 2 / 100, 0, 0);
        relativeParams.width = width * 50 / 100;
        relativeParams.height = height * 10 / 100;
        companyName.setLayoutParams(relativeParams);
        companyName.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        companyName.setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 4 / 100);


        relativeParams = (RelativeLayout.LayoutParams) position.getLayoutParams();
        relativeParams.setMargins(width * 25 / 100, height * 14 / 100, 0, 0);
        relativeParams.width = width * 50 / 100;
        relativeParams.height = height * 10 / 100;
        position.setLayoutParams(relativeParams);
        position.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        position.setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 4 / 100);


        relativeParams = (RelativeLayout.LayoutParams) user.getLayoutParams();
        relativeParams.setMargins(width * 25 / 100,height * 26 / 100, 0, 0);
        relativeParams.width = width * 50 / 100;
        relativeParams.height = height * 10 / 100;
        user.setLayoutParams(relativeParams);
        user.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        user.setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 4 / 100);


        relativeParams = (RelativeLayout.LayoutParams) pass.getLayoutParams();
        relativeParams.setMargins(width * 25 / 100, height * 38 / 100, 0, 0);
        relativeParams.width = width * 50 / 100;
        relativeParams.height = height * 10 / 100;
        pass.setLayoutParams(relativeParams);
        pass.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        pass.setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 4 / 100);


        relativeParams = (RelativeLayout.LayoutParams) retypePass.getLayoutParams();
        relativeParams.setMargins(width * 25 / 100,height * 50 / 100, 0, 0);
        relativeParams.width = width * 50 / 100;
        relativeParams.height = height * 10 / 100;
        retypePass.setLayoutParams(relativeParams);
        retypePass.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        retypePass.setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 4 / 100);


        relativeParams = (RelativeLayout.LayoutParams) email.getLayoutParams();
        relativeParams.setMargins(width * 25 / 100, height * 62 / 100, 0, 0);
        relativeParams.width = width * 50 / 100;
        relativeParams.height = height * 10 / 100;
        email.setLayoutParams(relativeParams);
        email.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        email.setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 4 / 100);
        relativeParams = (RelativeLayout.LayoutParams) register.getLayoutParams();
        relativeParams.setMargins(width * 36 / 100, height * 75 / 100, 0, 0);
        relativeParams.width = width * 30 / 100;
        relativeParams.height = height * 10 / 100;
        register.setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 4 / 100);
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
                } else if (companyName.getText().toString().trim().length() == 0 || position.getText().toString().trim().length() == 0 || user.getText().toString().trim().length() == 0 || pass.getText().toString().trim().length() == 0 || retypePass.getText().toString().trim().length() == 0 || email.getText().toString().trim().length() == 0) {
                    toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                    toastMessage.setText("No empty fields allowed!");
                    toast.setView(toastMessage);
                    toast.show();
                } else if (!Helper.fieldValidity(companyName.getText().toString().trim()) || !Helper.fieldValidity(position.getText().toString().trim()) || !Helper.fieldValidity(user.getText().toString().trim()) || !Helper.fieldValidity(pass.getText().toString().trim()) || !Helper.fieldValidity(retypePass.getText().toString().trim()) || !Helper.checkEmail(email.getText().toString().trim())) {
                    toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                    toastMessage.setText("Invalid fields!");
                    toast.setView(toastMessage);
                    toast.show();
                } else if (!pass.getText().toString().equals(retypePass.getText().toString())) {
                    toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                    toastMessage.setText("Passwords don't match!");
                    toast.setView(toastMessage);
                    toast.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Are you sure you want to register?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            if (!Helper.isOnline(getApplicationContext())) {
                                toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                                toastMessage.setText("No internet connection!");
                                toast.setView(toastMessage);
                                toast.show();
                            }
                            else
                            {
                                new Register().execute();
                            }
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                    alert.getButton(alert.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    alert.getButton(alert.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }
        });
    }
}

class Register extends AsyncTask<Void, Integer, String> {
    protected void onPreExecute() {
        registerActivity.progressBar.setVisibility(View.VISIBLE);
        registerActivity.companyName.setVisibility(View.INVISIBLE);
        registerActivity.position.setVisibility(View.INVISIBLE);
        registerActivity.user.setVisibility(View.INVISIBLE);
        registerActivity.pass.setVisibility(View.INVISIBLE);
        registerActivity.retypePass.setVisibility(View.INVISIBLE);
        registerActivity.email.setVisibility(View.INVISIBLE);
        registerActivity.register.setVisibility(View.INVISIBLE);
    }

    protected String doInBackground(Void... arg0) {
        String urlString = registerActivity.helper.getDomainName(registerActivity) + "checkClientsRegistration.php?company=" + registerActivity.companyName.getText().toString().trim() + "&user=" + registerActivity.user.getText().toString().trim() + "&email=" + registerActivity.email.getText().toString().trim();
        String response = null;
        try {
            response = registerActivity.helper.getMethod(urlString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    protected void onProgressUpdate(Integer... a) {
        super.onProgressUpdate(a);
    }

    protected void onPostExecute(String result) {
        if (result.trim().equals("Ok")) {
            new RegSendEmail().execute();
        }
        else {
            registerActivity.toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
            registerActivity.toastMessage.setText(result);
            registerActivity.toast.setView(registerActivity.toastMessage);
            registerActivity.toast.show();
            registerActivity.progressBar.setVisibility(View.INVISIBLE);
            registerActivity.companyName.setVisibility(View.VISIBLE);
            registerActivity.position.setVisibility(View.VISIBLE);
            registerActivity.user.setVisibility(View.VISIBLE);
            registerActivity.pass.setVisibility(View.VISIBLE);
            registerActivity.retypePass.setVisibility(View.VISIBLE);
            registerActivity.email.setVisibility(View.VISIBLE);
            registerActivity.register.setVisibility(View.VISIBLE);
        }
        super.onPostExecute(result);
    }
}

class RegSendEmail extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "manolatostechnoreply@gmail.com";
        String password = "manolatostech123";

        // outgoing message information
        String mailTo = registerActivity.email.getText().toString().trim();
        String subject = "PEC - Account Activation";
        String urlString = registerActivity.helper.getDomainName(registerActivity) + "registerClient.php?company=" + registerActivity.companyName.getText().toString().trim() + "&position=" + registerActivity.position.getText().toString().trim() + "&user=" + registerActivity.user.getText().toString().trim() + "&pass=" + registerActivity.pass.getText().toString().trim() + "&email=" + registerActivity.email.getText().toString().trim();
        String message = "Dear Sir,<br><br> Click <a href = '" + urlString + "' >here</a> for activation";
        RegHtmlEmailSender mailer = new RegHtmlEmailSender();

        try {
            mailer.sendHtmlEmail(host, port, mailFrom, password, mailTo,
                    subject, message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "Executed";
    }

    @Override
    protected void onPostExecute(String result) {
        registerActivity.toastMessage.setBackgroundColor(Color.parseColor("#038E18"));
        registerActivity.toastMessage.setText("Check your email for activation!");
        registerActivity.toast.setView(registerActivity.toastMessage);
        registerActivity.toast.show();
        registerActivity.progressBar.setVisibility(View.INVISIBLE);
        registerActivity.companyName.setVisibility(View.VISIBLE);
        registerActivity.position.setVisibility(View.VISIBLE);
        registerActivity.user.setVisibility(View.VISIBLE);
        registerActivity.pass.setVisibility(View.VISIBLE);
        registerActivity.retypePass.setVisibility(View.VISIBLE);
        registerActivity.email.setVisibility(View.VISIBLE);
        registerActivity.register.setVisibility(View.VISIBLE);
        Intent intent = new Intent(registerActivity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        registerActivity.startActivity(intent);
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
}

class RegHtmlEmailSender {

    public void sendHtmlEmail(String host, String port,
                              final String userName, final String password, String toAddress,
                              String subject, String message) throws AddressException,
            MessagingException {

        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };

        Session session = Session.getInstance(properties, auth);

        // creates a new e-mail message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress("no-reply@gmail.com"));
        InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setContent(message, "text/html; charset=utf-8");

        // sends the e-mail
        Transport.send(msg);

    }
}