/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class JobDetails {

    static private JFrame frame;
    private JobOrderModel modelDetails;
    ArrayList<ResumeModel> resumesModel;
    private Helper helper;
    private JComboBox newPositionComboBox;
    private JTextField[] textFields;
    private JTextArea[] textAreas;
    private JScrollPane jsp;
    private int fieldsLength;
    private JButton cancelButton, updateButton;
    private ProgressCancelTask progressCancelTask;
    private ProgressUpdateTask progressUpdateTask;
    private ArrayList<JobOrderModel> jobOrderModel;
    private ArrayList<CandidateModel> candidatesModel;
    private CompanyModel companyModel;
    private boolean waiting = false;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public JobDetails(CompanyModel companyModel, ArrayList<JobOrderModel> jobOrderModel, ArrayList<CandidateModel> candidatesModel, ArrayList<ResumeModel> resumesModel, JobOrderModel modelDetails) {
        this.companyModel = companyModel;
        this.candidatesModel = candidatesModel;
        this.modelDetails = modelDetails;
        this.jobOrderModel = jobOrderModel;
        this.resumesModel = resumesModel;
        initialize();
    }

    JobDetails() {

    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        int x = 4, y = 12, count = 1;
        String fieldsName[] = {"*Contact Name", "*Department", "*Billing Contact", "*Phone", "Salary", "*Starting Date", "Address", "*Business type",
            "Placement Fee", "*Placement Date", "*Actual Starting Date", "New Position", "*Educational Requirements", "*Experience Requirements", "Duties", "Bonuses", "*Travel Requirements", "Car",
            "Career Opportunities", "Interview", "Order Taker", "Counselor Ultimate"};
        helper = new Helper();

        //save modelDetails values into an arraylist
        ArrayList<String> values = helper.modelJobDetailsValues(modelDetails);
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.GRAY);
        frame.setTitle("Details - InvoieNo: " + modelDetails.invoiceNo);
        frame.setResizable(false);

        //size and location of window opened
        frame.setSize(width, height * 92 / 100);
        frame.setLocationRelativeTo(null);

        //initialize components      
        JPanel container = new JPanel();
        jsp = new JScrollPane(container);
        jsp.setLocation(width * 1 / 100, height * 3 / 100);
        jsp.setSize(width * 98 / 100, height * 82 / 100);
        container.setLayout(null);
        container.setPreferredSize(new Dimension(width, height * 162 / 100));
        container.setBackground(Color.GRAY);
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/img/back.png"));
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(width * 3 / 100, height * 2 / 100, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);

        JLabel backLabel = new JLabel();
        backLabel.setSize(width * 3 / 100, height * 2 / 100);
        backLabel.setLocation(width * 2 / 100, height * 4 / 100);
        backLabel.setIcon(imageIcon);
        backLabel.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (!waiting) {
                    new Home(companyModel, jobOrderModel, candidatesModel, resumesModel).setVisible(true);
                    frame.dispose();
                }
            }
        });
        container.add(backLabel);
        JLabel[] labels = new JLabel[fieldsName.length];
        JScrollPane[] scrollArray = new JScrollPane[10];
        textFields = new JTextField[11];
        textAreas = new JTextArea[10];
        fieldsLength = fieldsName.length;
        Font currentFont;
        for (int i = 0; i < fieldsName.length; i++) {
            String value = "";

            labels[i] = new JLabel(fieldsName[i] + " :");
            currentFont = labels[i].getFont();
            labels[i].setFont(currentFont.deriveFont(Font.BOLD, width * 1.1f / 100));
            labels[i].setSize(width, height * 5 / 100);
            labels[i].setLocation(width * x / 100, height * y / 100);
            container.add(labels[i]);
            if (i < 11) {
                if (!values.get(i).equals("0.0")) {
                    value = values.get(i);
                }
                textFields[i] = new JTextField(value);
                textFields[i].setSize(width * 15 / 100, height * 5 / 100);
                textFields[i].setLocation(width * x / 100, height * (y + 5) / 100);
                if (i == 6)
                {
                    textFields[i].setEnabled(false);
                    textFields[i].setText(companyModel.position);
                }
                if (i == 5 || i == 9 || i == 10) {
                    textFields[i].setEnabled(false);
                    textFields[i].setDisabledTextColor(Color.BLACK);
                    textFields[i].setName("name" + i);
                    textFields[i].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (!waiting) {
                                JTextField textField;
                                textField = (JTextField) e.getSource();
                                String date = new DatePicker(frame, textField.getText()).setPickedDate();
                                textField.setText(date);
                            }
                        }
                    });
                }
                container.add(textFields[i]);
            } else if (i == 11) {
                String position[] = {"Yes", "No"};
                newPositionComboBox = new JComboBox(position);
                newPositionComboBox.setSize(width * 10 / 100, height * 5 / 100);
                newPositionComboBox.setLocation(width * x / 100, height * (y + 5) / 100);
                newPositionComboBox.setSelectedItem(values.get(i));
                container.add(newPositionComboBox);
            } else {
                value = values.get(i).replaceAll("@", "\n");
                textAreas[i - 12] = new JTextArea(value);
                scrollArray[i - 12] = new JScrollPane(textAreas[i - 12]);
                scrollArray[i - 12].setSize(width * 15 / 100, height * 15 / 100);
                scrollArray[i - 12].setLocation(width * x / 100, height * (y + 5) / 100);
                container.add(scrollArray[i - 12]);
            }
            if ((count % 4) == 0) {
                x = 4;
                if (i < 12) {
                    y += 15;
                } else {
                    y += 25;
                }
            } else {
                x += 25;
            }
            count++;
        }

        cancelButton = new JButton("Cancel Job Request");
        currentFont = cancelButton.getFont();
        cancelButton.setSize(width * 13 / 100, height * 6 / 100);
        cancelButton.setLocation(width * 33 / 100, height * (y + 35) / 100);
        cancelButton.setFont(currentFont.deriveFont(Font.PLAIN, width * 0.8f / 100));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (cancelButton.isEnabled()) {
                    if (!helper.isConnected()) {
                        JOptionPane.showMessageDialog(frame,
                                "Please check your internet connection!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel the job request?", "Confirmation", dialogButton);
                        if (dialogResult == JOptionPane.YES_OPTION) {
                            progressCancelTask = new ProgressCancelTask();
                            progressCancelTask.execute();
                        }
                    }
                }
            }
        });
        container.add(cancelButton);

        updateButton = new JButton("Update");
        currentFont = cancelButton.getFont();
        updateButton.setSize(width * 13 / 100, height * 6 / 100);
        updateButton.setLocation(width * 52 / 100, height * (y + 35) / 100);
        updateButton.setFont(currentFont.deriveFont(Font.PLAIN, width * 0.8f / 100));
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (updateButton.isEnabled()) {
                    if (!helper.isConnected()) {
                        JOptionPane.showMessageDialog(frame,
                                "Please check your internet connection!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        int error = 0;
                        if (textAreas[0].getText().trim().length() == 0 || textAreas[1].getText().trim().length() == 0 || textAreas[4].getText().trim().length() == 0) {
                            error = 1;
                        }
                        for (int i = 0; i < fieldsLength; i++) {
                            if (error == 1 || error == 2) {
                                break;
                            }
                            if (i < 11) {
                                if (i != 4 && i != 8) {
                                    if (textFields[i].getText().trim().length() == 0) {
                                        error = 1;
                                    } else if (!helper.fieldValidity(textFields[i].getText())) {
                                        error = 2;
                                    }
                                }
                                else if (i == 4 || i == 8) {
                                    if (textFields[i].getText().trim().length() > 0) {
                                        if (!textFields[i].getText().trim().replace(",", ".").matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                                            error = 2;
                                        }
                                    }
                                }
                            } else if (i > 11) {
                                if (!helper.fieldValidity(textAreas[i - 12].getText())) {
                                    error = 2;
                                }
                            }
                        }
                        if (error == 1) {
                            JOptionPane.showMessageDialog(frame,
                                    "The requirement fields cannot be blank!",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else if (error == 2) {
                            JOptionPane.showMessageDialog(frame,
                                    "Invalid fields!",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            int dialogButton = JOptionPane.YES_NO_OPTION;
                            int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to update the job request?", "Confirmation", dialogButton);
                            if (dialogResult == JOptionPane.YES_OPTION) {
                                progressUpdateTask = new ProgressUpdateTask();
                                progressUpdateTask.execute();
                            }
                        }
                    }
                }
            }
        });
        container.add(updateButton);
        frame.getContentPane().add(jsp);
    }

    class ProgressCancelTask extends SwingWorker<Void, Void> {

        @Override
        public Void doInBackground() throws ClassNotFoundException, SQLException, IOException {
            waiting = true;
            frame.setCursor(Cursor.WAIT_CURSOR);
            cancelButton.setEnabled(false);
            updateButton.setEnabled(false);
            for (int i = 0; i < fieldsLength; i++) {
                if (i < 11) {
                    textFields[i].setEnabled(false);
                } else if (i == 11) {
                    newPositionComboBox.setEnabled(false);
                } else {
                    textAreas[i - 12].setEnabled(false);
                }
            }
            String url = helper.getDomainName() + "cancelJob.php";
            String urlParameters = "jobId=" + modelDetails.jobOrderId;
            String response = helper.postMethod(url, urlParameters);
            if (response.charAt(0) == 'A') {
                JOptionPane.showMessageDialog(frame,
                        response,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                int index = helper.getJobModelIndex(modelDetails.jobOrderId, jobOrderModel);
                jobOrderModel.remove(index);
                JOptionPane.showMessageDialog(frame,
                        response);
                new Home(companyModel, jobOrderModel, candidatesModel, resumesModel).setVisible(true);
                frame.dispose();
            }
            waiting = false;
            cancelButton.setEnabled(true);
            updateButton.setEnabled(true);
            for (int i = 0; i < fieldsLength; i++) {
                if (i < 11) {
                    textFields[i].setEnabled(true);
                } else if (i == 11) {
                    newPositionComboBox.setEnabled(true);
                } else {
                    textAreas[i - 12].setEnabled(true);
                }
            }
            frame.setCursor(Cursor.DEFAULT_CURSOR);
            return null;
        }

        /*
     * Executed in event dispatching thread
         */
        @Override
        public void done() {

        }
    }

    class ProgressUpdateTask extends SwingWorker<Void, Void> {

        @Override
        public Void doInBackground() throws ClassNotFoundException, SQLException, IOException {
            double salary, placementFee;
            int i;
            if (textFields[4].getText().trim().length() == 0) {
                salary = 0;
            } else {
                salary = Double.parseDouble(textFields[4].getText().trim().replace(",", "."));
            }
            if (textFields[8].getText().trim().length() == 0) {
                placementFee = 0;
            } else {
                placementFee = Double.parseDouble(textFields[8].getText().trim().replace(",", "."));
            }
            frame.setCursor(Cursor.WAIT_CURSOR);
            cancelButton.setEnabled(false);
            updateButton.setEnabled(false);
            waiting = true;
            String parameterFields[] = {"contactName", "department", "billingContact", "phone", "salary", "startingDate", "address", "businessType",
                "placementFee", "placementDate", "actualStartingDate", "newPosition", "educationalRequirements", "experienceRequirements", "duties", "bonuses", "travelRequirements", "car",
                "careerOpportunities", "interview", "orderTaker", "counselorUltimatePlacement"};
            String url = helper.getDomainName() + "updateJob.php";
            String urlParameters = "jobId=" + modelDetails.jobOrderId;
            for (i = 0; i < fieldsLength; i++) {
                if (i < 11) {
                    textFields[i].setEnabled(false);
                    if (i == 4 || i == 8) {
                        urlParameters += "&" + parameterFields[i] + "=" + textFields[i].getText().trim().replace(",", ".");
                    } else {
                        urlParameters += "&" + parameterFields[i] + "=" + textFields[i].getText().trim();
                    }
                } else if (i == 11) {
                    newPositionComboBox.setEnabled(false);
                    urlParameters += "&" + parameterFields[i] + "=" + newPositionComboBox.getSelectedItem();
                } else {
                    textAreas[i - 12].setEnabled(false);
                    urlParameters += "&" + parameterFields[i] + "=" + textAreas[i - 12].getText().trim().replaceAll("\\n", "@");
                }
            }
            String response = helper.postMethod(url, urlParameters);
            if (response.charAt(0) == 'A') {
                JOptionPane.showMessageDialog(frame,
                        response,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JobOrderModel temp = new JobOrderModel();
                temp.jobOrderId = modelDetails.jobOrderId;
                temp.position = modelDetails.position;
                temp.dateInserted = modelDetails.dateInserted;
                temp.contactName = textFields[0].getText().trim();
                temp.department = textFields[1].getText().trim();
                temp.billingContact = textFields[2].getText().trim();
                temp.phone = textFields[3].getText().trim();
                temp.salary = salary;
                temp.startingDate = textFields[5].getText().trim();
                temp.address = textFields[6].getText().trim();
                temp.businessType = textFields[7].getText().trim();
                temp.placementFee = placementFee;
                temp.placementDate = textFields[9].getText().trim();
                temp.actualStartingDate = textFields[10].getText().trim();
                temp.newPosition = newPositionComboBox.getSelectedItem().toString();
                temp.educationalRequirements = textAreas[0].getText().trim().replaceAll("\\n", "@");
                temp.experienceRequirements = textAreas[1].getText().trim().replaceAll("\\n", "@");
                temp.duties = textAreas[2].getText().trim().replaceAll("\\n", "@");
                temp.bonuses = textAreas[3].getText().trim().replaceAll("\\n", "@");
                temp.travelRequirements = textAreas[4].getText().replaceAll("\\n", "@");
                temp.car = textAreas[5].getText().trim().replaceAll("\\n", "@");
                temp.careerOpportunities = textAreas[6].getText().trim().replaceAll("\\n", "@");
                temp.interview = textAreas[7].getText().trim().replaceAll("\\n", "@");
                temp.orderTaker = textAreas[8].getText().trim().replaceAll("\\n", "@");
                temp.counselorUltimate = textAreas[9].getText().trim().replaceAll("\\n", "@");
                temp.invoiceNo = modelDetails.invoiceNo;
                int index = helper.getJobModelIndex(modelDetails.jobOrderId, jobOrderModel);
                jobOrderModel.set(index, temp);
                JOptionPane.showMessageDialog(frame,
                        response);
            }
            waiting = false;
            cancelButton.setEnabled(true);
            updateButton.setEnabled(true);
            for (i = 0; i < fieldsLength; i++) {
                if (i < 11) {
                    textFields[i].setEnabled(true);
                    if (i == 5 || i == 6 || i == 9 || i == 10) {
                        textFields[i].setEnabled(false);
                    }
                } else if (i == 11) {
                    newPositionComboBox.setEnabled(true);
                } else {
                    textAreas[i - 12].setEnabled(true);
                }
            }
            frame.setCursor(Cursor.DEFAULT_CURSOR);
            return null;
        }

        /*
     * Executed in event dispatching thread
         */
        @Override
        public void done() {

        }
    }
}
