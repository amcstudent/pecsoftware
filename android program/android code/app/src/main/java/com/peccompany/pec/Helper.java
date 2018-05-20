package com.peccompany.pec;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.Assert.assertEquals;


public class Helper {

    public String getMethod(String urlString) throws IOException {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        } catch (Exception e) {
            return null;
        }
        return result.toString();
    }

    public String postMethod(String urlString, String urlParameters){
        String response = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL(urlString);

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            wr.write(urlParameters);
            wr.flush();
            wr.close();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line);
            }


            response = sb.toString();
        }
        catch(Exception ex)
        {

        }
        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {}
        }

        // Show response on activity
        return response;
    }

    public ArrayList<JobOrderModel> getJobOrders(String urlString) throws MalformedURLException, ProtocolException, IOException {
        ArrayList<JobOrderModel> model = new ArrayList<JobOrderModel>();
        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        urlConnection.setRequestMethod("GET");
        int statusCode = urlConnection.getResponseCode();
        StringBuilder data = new StringBuilder();
        if (statusCode ==  200) {
            InputStream it = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader read = new InputStreamReader(it);
            BufferedReader buff = new BufferedReader(read);
            String line ;
            while((line = buff.readLine()) != null)
            {
                data.append(line);
            }
        }
        String[] parts = data.toString().split("\\$");
        for (int i = 1; i < parts.length; i = i + 27) {
            JobOrderModel temp = new JobOrderModel();
            temp.jobOrderId = Long.parseLong(parts[i]);
            temp.position = parts[i + 1];
            temp.dateInserted = parts[i + 2];
            temp.firm = parts[i + 3];
            temp.department = parts[i + 4];
            temp.contactName = parts[i + 5];
            temp.address = parts[i + 6];
            temp.billingContact = parts[i + 7];
            temp.businessType = parts[i + 8];
            temp.phone = parts[i + 9];
            temp.educationalRequirements = parts[i + 10];
            temp.salary = Double.parseDouble(parts[i + 11]);
            temp.startingDate = parts[i + 12];
            temp.experienceRequirements = parts[i + 13];
            temp.newPosition = parts[i + 14];
            temp.duties = parts[i + 15];
            temp.bonuses = parts[i + 16];
            temp.travelRequirements = parts[i + 17];
            temp.car = parts[i + 18];
            temp.careerOpportunities = parts[i + 19];
            temp.interview = parts[i + 20];
            temp.orderTaker = parts[i + 21];
            temp.placementFee = Double.parseDouble(parts[i + 22]);
            temp.counselorUltimate = parts[i + 23];
            temp.invoiceNo = Long.parseLong(parts[i + 24]);
            temp.placementDate = parts[i + 25];
            temp.actualStartingDate = parts[i + 26];
            model.add(temp);
        }
        return model;
    }

    public ArrayList<CandidateModel> getCandidates(String urlString) throws IOException {
        ArrayList<CandidateModel> model = new ArrayList<CandidateModel>();
        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        urlConnection.setRequestMethod("GET");
        int statusCode = urlConnection.getResponseCode();
        StringBuilder data = new StringBuilder();
        if (statusCode ==  200) {
            InputStream it = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader read = new InputStreamReader(it);
            BufferedReader buff = new BufferedReader(read);
            String line ;
            while((line = buff.readLine()) != null)
            {
                data.append(line);
            }
        }
        String[] parts = data.toString().split("\\$");
        for (int i = 1; i < parts.length; i = i + 23) {
            CandidateModel temp = new CandidateModel();
            temp.candidateId = Long.parseLong(parts[i]);
            temp.name = parts[i + 1];
            temp.address = parts[i + 2];
            temp.birthdate = parts[i + 3];
            temp.email = parts[i + 4];
            temp.residenceNumber = parts[i + 5];
            temp.businessNumber = parts[i + 6];
            temp.maritalStatus = parts[i + 7];
            temp.driverInformation = parts[i + 8];
            temp.degree = parts[i + 9];
            temp.positionDesired = parts[i + 10];
            temp.salaryDesired = Double.parseDouble(parts[i + 11]);
            temp.geographicPreference = parts[i + 12];
            temp.travelPreference = parts[i + 13];
            temp.currentPositionSalary = Double.parseDouble(parts[i + 14]);
            temp.onePreviousPositionSalary = Double.parseDouble(parts[i + 15]);
            temp.twoPreviousPositionSalary = Double.parseDouble(parts[i + 16]);
            temp.threePreviousPositionSalary = Double.parseDouble(parts[i + 17]);
            temp.tenureResponsibilities = parts[i + 18];
            temp.leavingReason = parts[i + 19];
            temp.interviewImpressions = parts[i + 20];
            temp.ratings = Integer.valueOf(parts[i + 21]);
            if (parts[i + 22].equals("-")) {
                temp.consultantInitials = "";
            } else {
                temp.consultantInitials = parts[i + 22];
            }
            model.add(temp);
        }
        return model;
    }

    public ArrayList<ResumeModel> getResumes(String urlString) throws MalformedURLException, ProtocolException, IOException {
        ArrayList<ResumeModel> model = new ArrayList<ResumeModel>();
        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        urlConnection.setRequestMethod("GET");
        int statusCode = urlConnection.getResponseCode();
        StringBuilder data = new StringBuilder();
        if (statusCode ==  200) {
            InputStream it = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader read = new InputStreamReader(it);
            BufferedReader buff = new BufferedReader(read);
            String line ;
            while((line = buff.readLine()) != null)
            {
                data.append(line);
            }
        }
        String[] parts = data.toString().split("\\$");
        for (int i = 1; i < parts.length; i = i + 7) {
            ResumeModel temp = new ResumeModel();
            temp.id = Long.parseLong(parts[i]);
            temp.commomDuties = parts[i + 1];
            temp.careerOpportunities = parts[i + 2];
            temp.educationalRequirements = parts[i + 3];
            temp.salaryRanges = parts[i + 4];
            temp.experienceRequirements = parts[i + 5];
            temp.candidateId = Long.parseLong(parts[i + 6]);
            model.add(temp);
        }
        return model;
    }

    public ArrayList<String> modelJobDetailsValues(JobOrderModel modelDetails) {
        ArrayList<String> values = new ArrayList<String>();
        values.add(modelDetails.contactName);
        values.add(modelDetails.department);
        values.add(modelDetails.billingContact);
        values.add(modelDetails.phone);
        values.add(String.valueOf(modelDetails.salary));
        values.add(modelDetails.startingDate);
        values.add(modelDetails.address);
        values.add(modelDetails.businessType);
        values.add(String.valueOf(modelDetails.placementFee));
        values.add(modelDetails.placementDate);
        values.add(modelDetails.actualStartingDate);
        values.add(modelDetails.newPosition);
        values.add(modelDetails.educationalRequirements);
        values.add(String.valueOf(modelDetails.experienceRequirements));
        values.add(modelDetails.duties);
        values.add(modelDetails.bonuses);
        values.add(modelDetails.travelRequirements);
        values.add(modelDetails.car);
        values.add(modelDetails.careerOpportunities);
        values.add(modelDetails.interview);
        values.add(modelDetails.orderTaker);
        values.add(modelDetails.counselorUltimate);
        return values;
    }

    public ArrayList<String> modelCandidateDetailsValues(CandidateModel modelDetails) {
        ArrayList<String> values = new ArrayList<String>();
        values.add(modelDetails.name);
        values.add(modelDetails.address);
        values.add(modelDetails.birthdate);
        values.add(modelDetails.residenceNumber);
        values.add(modelDetails.businessNumber);
        values.add(modelDetails.driverInformation);
        values.add(modelDetails.degree);
        values.add(modelDetails.positionDesired);
        values.add(modelDetails.geographicPreference);
        values.add(String.valueOf(modelDetails.salaryDesired));
        values.add(String.valueOf(modelDetails.currentPositionSalary));
        values.add(String.valueOf(modelDetails.onePreviousPositionSalary));
        values.add(String.valueOf(modelDetails.twoPreviousPositionSalary));
        values.add(String.valueOf(modelDetails.threePreviousPositionSalary));
        values.add(String.valueOf(modelDetails.ratings));
        values.add(modelDetails.tenureResponsibilities);
        values.add(modelDetails.maritalStatus);
        values.add(modelDetails.travelPreference);
        values.add(modelDetails.leavingReason);
        values.add(modelDetails.interviewImpressions);
        values.add(modelDetails.consultantInitials);
        return values;
    }

    public int getCandidateModelIndex(long id, ArrayList<CandidateModel> candidateModel) {
        int index = 0;
        for (CandidateModel item : candidateModel) {
            if (id == item.candidateId) {
                break;
            }
            index++;
        }
        return index;
    }

    public int getJobModelIndex(long id, ArrayList<JobOrderModel> jobOrderModel) {
        int index = 0;
        for (JobOrderModel item : jobOrderModel) {
            if (id == item.jobOrderId) {
                break;
            }
            index++;
        }
        return index;
    }

    public int isConnectedToServer(String strUrl) {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.connect();
            assertEquals(HttpURLConnection.HTTP_OK, urlConn.getResponseCode());
            return 1;
        } catch (IOException e) {
            return 0;
        }
    }

    static public boolean isOnline(Context context) {
        boolean connected = false;
        try {
            ConnectivityManager connectivityManager;
            NetworkInfo wifiInfo, mobileInfo;
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;

        } catch (Exception e) {

        }
        return connected;
    }

    public boolean emailValidity(String email) {
        Pattern p = Pattern.compile("^[A-Za-z0-9-]+(\\-[A-Za-z0-9])*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9])");
        Matcher m = p.matcher(email);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean fieldValidity(String field) {
        if (!field.contains("*") && !field.contains("#") && !field.contains("%") && !field.contains("$") && !field.contains("=") && !field.contains("@")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkEmail(String email) {
        Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+"
        );
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public String getDomainName(Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences("connection", activity.MODE_PRIVATE);
        String domain = prefs.getString("conn", "");
        return domain;
    }
}
