package com.peccompany.pec;

import android.content.Context;
import android.content.DialogInterface;
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

import static com.peccompany.pec.RetrieveActivity.retrieveActivity;

public class RetrieveActivity extends AppCompatActivity {
    static TextView toastMessage;
    static Toast toast;
    static EditText email;
    static Button retrieve;
    static ProgressBar progressBar;
    private int height, width;
    static RetrieveActivity retrieveActivity;
    static Helper helper;
    static String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrieve_activity);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#02A9B5"));
        findViewById(android.R.id.content).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Helper.hideSoftKeyboard(RetrieveActivity.this);
                return false;
            }
        });
        retrieveActivity = this;
        helper = new Helper();
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        email = (EditText) findViewById(R.id.email);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.getLayoutParams().height = height * 95 / 100;
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.colorPrimaryDark),
                PorterDuff.Mode.SRC_IN);
        retrieve = (Button) findViewById(R.id.retrieve);
        RelativeLayout.LayoutParams relativeParams;
        toastMessage = new TextView(this);
        toastMessage.setTextColor(Color.WHITE);
        toastMessage.setTypeface(null, Typeface.BOLD);
        toastMessage.setPadding(width * 5 / 100, height * 2 / 100, width * 5 / 100, height * 2 / 100);
        toastMessage.setTextSize((float) (width * 1 / 100));
        toast = Toast.makeText(getApplicationContext(), null,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, height * 3 / 100);

        relativeParams = (RelativeLayout.LayoutParams) email.getLayoutParams();
        relativeParams.setMargins(width * 18 / 100, height * 32 / 100, 0, 0);
        relativeParams.width = width * 62 / 100;
        relativeParams.height = height * 10 / 100;
        email.setLayoutParams(relativeParams);
        email.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        email.setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 5 / 100);

        relativeParams = (RelativeLayout.LayoutParams) retrieve.getLayoutParams();
        relativeParams.setMargins(width * 34 / 100, height * 50 / 100, 0, 0);
        relativeParams.width = width * 30 / 100;
        relativeParams.height = height * 10 / 100;
        retrieve.setLayoutParams(relativeParams);
        retrieve.setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 4 / 100);
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
                } else if (email.getText().toString().trim().length() == 0) {
                    toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                    toastMessage.setText("The field is required!");
                    toast.setView(toastMessage);
                    toast.show();
                } else if (!Helper.checkEmail(email.getText().toString().trim())) {
                    toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                    toastMessage.setText("Invalid email!");
                    toast.setView(toastMessage);
                    toast.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RetrieveActivity.this);
                    builder.setTitle("Confirm");
                    builder.setMessage("Are you sure?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            if (!Helper.isOnline(getApplicationContext())) {
                                toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                                toastMessage.setText("No internet connection!");
                                toast.setView(toastMessage);
                                toast.show();
                            } else
                                new Retrieve().execute();
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

class Retrieve extends AsyncTask<Void, Integer, String> {
    protected void onPreExecute() {
        retrieveActivity.progressBar.setVisibility(View.VISIBLE);
        retrieveActivity.email.setVisibility(View.INVISIBLE);
        retrieveActivity.retrieve.setVisibility(View.INVISIBLE);
    }

    protected String doInBackground(Void... arg0) {
        String urlString = retrieveActivity.helper.getDomainName(retrieveActivity) + "checkClientsByEmail.php?email=" + retrieveActivity.email.getText().toString().trim();
        String response = null;
        try {
            response = retrieveActivity.helper.getMethod(urlString);
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
            retrieveActivity.toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
            retrieveActivity.toastMessage.setText("The email doesn't exist!");
            retrieveActivity.toast.setView(retrieveActivity.toastMessage);
            retrieveActivity.toast.show();
            retrieveActivity.progressBar.setVisibility(View.INVISIBLE);
            retrieveActivity.email.setVisibility(View.VISIBLE);
            retrieveActivity.retrieve.setVisibility(View.VISIBLE);
        } else {
            String[] parts = result.split("\\$");
            retrieveActivity.message = "Dear Sir,<br><br>Username : " + parts[0] + "<br><br>Password : " + parts[1];
            new RetSendEmail().execute();
        }

        super.onPostExecute(result);
    }
}

class RetSendEmail extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "manolatostechnoreply@gmail.com";
        String password = "manolatostech123";

        // outgoing message information
        String mailTo = retrieveActivity.email.getText().toString().trim();
        String subject = "PEC - Retrieve Account";
        RetHtmlEmailSender mailer = new RetHtmlEmailSender();

        try {
            mailer.sendHtmlEmail(host, port, mailFrom, password, mailTo,
                    subject, retrieveActivity.message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "Executed";
    }

    @Override
    protected void onPostExecute(String result) {
        retrieveActivity.toastMessage.setBackgroundColor(Color.parseColor("#038E18"));
        retrieveActivity.toastMessage.setText("An email has been sent to your mail!");
        retrieveActivity.toast.setView(retrieveActivity.toastMessage);
        retrieveActivity.toast.show();
        retrieveActivity.progressBar.setVisibility(View.INVISIBLE);
        retrieveActivity.email.setVisibility(View.VISIBLE);
        retrieveActivity.email.setText("");
        retrieveActivity.retrieve.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
}

class RetHtmlEmailSender {

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