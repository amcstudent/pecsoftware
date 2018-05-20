/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pecUsers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 *
 */
public class Helper {

    private BufferedWriter bw = null;
    private FileWriter fw = null;

    public long getMethod(String urlString) throws MalformedURLException, ProtocolException, IOException {
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
            return 0;
        }
        return Integer.parseInt(result.toString());
    }

    public String postMethod(String urlString, String urlParameters) throws MalformedURLException {
        StringBuffer response = null;
        try {
            URL obj = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            // Send post request
            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            wr.write(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response.toString();
    }

    public ArrayList<JobOrderModel> getJobOrders(String urlString, int mode, int plus) throws MalformedURLException, ProtocolException, IOException {
        ArrayList<JobOrderModel> model = new ArrayList<JobOrderModel>();
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
        String[] parts = result.toString().split("\\$");
        for (int i = 1; i < parts.length; i = i + plus) {
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
            if (mode == 2) {
                temp.candidateId = Long.parseLong(parts[i + 27]);
                temp.status = parts[i + 28];
                temp.clientEmail = parts[i + 29];
                temp.clientId = Long.parseLong(parts[i + 30]);
            } else if (mode == 0) {
                temp.clientEmail = parts[i + 27];
                temp.clientId = Long.parseLong(parts[i + 28]);
            } else {
                temp.status = "";
            }
            model.add(temp);
        }
        return model;
    }

    public ArrayList<CandidateModel> getCandidates(String urlString, int mode, int plus) throws MalformedURLException, ProtocolException, IOException {
        ArrayList<CandidateModel> model = new ArrayList<CandidateModel>();
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
        String[] parts = result.toString().split("\\$");
        for (int i = 1; i < parts.length; i = i + plus) {
            CandidateModel temp = new CandidateModel();
            temp.candidateId = Long.parseLong(parts[i]);
            temp.name = parts[i + 1];
            if (mode == 0) {
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
            }
            model.add(temp);
        }
        return model;
    }

    public ArrayList<ResumeModel> getResumes(String urlString) throws MalformedURLException, ProtocolException, IOException {
        ArrayList<ResumeModel> model = new ArrayList<ResumeModel>();
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
        String[] parts = result.toString().split("\\$");
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
        values.add(modelDetails.position);
        values.add(modelDetails.dateInserted);
        values.add(modelDetails.firm);
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

    public ArrayList<String> modelCandidateDetailsValues(CandidateModel modelDetails, int mode) {
        ArrayList<String> values = new ArrayList<String>();
        if (mode == 0) {
            values.add(modelDetails.name);
            values.add(modelDetails.address);
            values.add(modelDetails.birthdate);
            values.add(modelDetails.email);
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
            values.add(modelDetails.leavingReason);
            values.add(modelDetails.interviewImpressions);
            values.add(modelDetails.consultantInitials);
            values.add(String.valueOf(modelDetails.ratings));
            values.add(modelDetails.tenureResponsibilities);
            values.add(modelDetails.maritalStatus);
            values.add(modelDetails.travelPreference);
        } else {
            values.add(modelDetails.name);
            values.add(modelDetails.address);
            values.add(modelDetails.birthdate);
            values.add(modelDetails.email);
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
        }
        return values;
    }

    public ArrayList<String> modelResumeValues(ResumeModel modelDetails, char upsert) {
        ArrayList<String> values = new ArrayList<String>();
        if (upsert == 'I') {
            values.add("");
            values.add("");
            values.add("");
            values.add("");
            values.add("");
        } else {
            values.add(modelDetails.commomDuties);
            values.add(modelDetails.careerOpportunities);
            values.add(modelDetails.educationalRequirements);
            values.add(modelDetails.experienceRequirements);
            values.add(modelDetails.salaryRanges);
        }
        return values;
    }

    public void writeToFile(String data) {
        try {
            File file = new File(String.valueOf("log/log.txt"));
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = new Date();
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(data + " at " + dateFormat.format(date) + System.lineSeparator());
        } catch (IOException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {

                if (bw != null) {
                    bw.close();
                }

                if (fw != null) {
                    fw.close();
                }

            } catch (IOException ex) {

                ex.printStackTrace();

            }
        }
    }

    public void sendEmail(String sendTo, String text, String subject) throws AddressException, javax.mail.MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("kostasorfeasamc@gmail.com", "Amcstudents1");
                    }
                });
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("no-reply@gmail.com"));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(sendTo));
        message.setSubject(subject);
        message.setText(text);
        Transport.send(message);
    }

    public boolean isConnected() {
        try {
            URL url = new URL("https://www.google.gr");
            URLConnection conn = url.openConnection();
            conn.connect();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }

    public int getJobModelIndex(long id, ArrayList<JobOrderModel> model) {
        int index = 0;
        for (JobOrderModel item : model) {
            if (id == item.jobOrderId) {
                break;
            }
            index++;
        }
        return index;
    }

    public int getCandidateModelIndex(long id, ArrayList<CandidateModel> model) {
        int index = 0;
        for (CandidateModel item : model) {
            if (id == item.candidateId) {
                break;
            }
            index++;
        }
        return index;
    }

    public int getResumeModelIndex(long id, ArrayList<ResumeModel> model, int search) {
        int index = 0;
        for (ResumeModel item : model) {
            if (search == 0) {
                if (id == item.id) {
                    break;
                }
            } else {
                if (id == item.candidateId) {
                    break;
                }
            }
            index++;
        }
        return index;
    }

    public ArrayList<JobOrderModel> refreshedJobModel(ArrayList<JobOrderModel> refreshedModel, ArrayList<JobOrderModel> model) {
        if (refreshedModel.size() > model.size()) {
            if (model.size() == 0) {
                for (JobOrderModel item : refreshedModel) {
                    item.status = "New";
                }
            } else {
                int index = 0;
                for (JobOrderModel item : refreshedModel) {
                    if (item.jobOrderId == model.get(index).jobOrderId) {
                        break;
                    }
                    item.status = "New";
                }
            }
        }
        return refreshedModel;
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

    public boolean fieldValidity(String field) {
        if (!field.contains("*") && !field.contains("#") && !field.contains("%") && !field.contains("$") && !field.contains("=") && !field.contains("@")) {
            return true;
        } else {
            return false;
        }
    }

    public String getDomainName() {
        String domain="";
        try (BufferedReader br = new BufferedReader(new FileReader("connection.txt"))) {
            String content;

            while ((content = br.readLine()) != null) {
                domain = content;
            }
        if (domain.charAt(domain.length() - 1) != '/')
            domain += '/';
        } catch (IOException e) {
            e.printStackTrace();
        }
        return domain;
    }
}
