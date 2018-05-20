/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pecUsers;

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;

public class CandidateDetails {

    static private JFrame frame;
    private CandidateModel modelDetails;
    private Helper helper;
    private JTextField[] textFields;
    private JTextArea[] textAreas;
    private JScrollPane jsp;
    private int fieldsLength, index, mode;
    private JButton deleteButton, updateButton;
    private ProgressDeleteTask progressDeleteTask;
    private ProgressUpdateTask progressUpdateTask;
    private ArrayList<JobOrderModel> currentModel, pendingModel, cancelledModel, waitingModel;
    private ArrayList<ResumeModel> resumesModel;
    private ArrayList<CandidateModel> candidatesModel, candidatesFoundModel;
    private long id, maxInvoiceNo;
    private String company, email, pass;
    private boolean waiting = false;
    private String username;
    private JComboBox[] comboBoxes;
    private JSpinner spinner;
    private JSpinner.DefaultEditor spinnerEditor;

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
    public CandidateDetails(String username, CandidateModel modelDetails, ArrayList<JobOrderModel> currentModel, ArrayList<JobOrderModel> pendingModel, ArrayList<JobOrderModel> cancelledModel, ArrayList<JobOrderModel> waitingModel, ArrayList<CandidateModel> candidatesModel, ArrayList<CandidateModel> candidatesFoundModel, ArrayList<ResumeModel> resumesModel, int mode) {
        this.username = username;
        this.pendingModel = pendingModel;
        this.cancelledModel = cancelledModel;
        this.candidatesModel = candidatesModel;
        this.waitingModel = waitingModel;
        this.modelDetails = modelDetails;
        this.currentModel = currentModel;
        this.resumesModel = resumesModel;
        this.candidatesFoundModel = candidatesFoundModel;
        this.mode = mode;
        initialize();
    }

    CandidateDetails() {

    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        int x = 4, y = 12, count = 1;

        String comboBoxValues[][] = {{"Yes", "Pending", "No"}, {"Married", "Unmarried"}, {"Yes", "No"}};
        helper = new Helper();

        //save modelDetails values into an arraylist
        ArrayList<String> values = helper.modelCandidateDetailsValues(modelDetails, mode);
        frame = new JFrame();
        frame.getContentPane().setBackground(Color.GRAY);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                helper.writeToFile(username + " logged out");
            }
        });
        //size and location of window opened
        frame.setSize(width, height * 92 / 100);
        frame.setLocationRelativeTo(null);

        //initialize components      
        JPanel container = new JPanel();
        jsp = new JScrollPane(container);
        jsp.setLocation(width * 1 / 100, height * 3 / 100);
        jsp.setSize(width * 98 / 100, height * 82 / 100);
        container.setLayout(null);
        container.setBackground(Color.GRAY);
        Font currentFont;
        JLabel[] labels = new JLabel[22];
        if (mode == 0) {
            String fieldsName[] = {"*Name", "*Address", "*Birthdate", "Email", "Residence Number", "Business Number", "*Driver Information", "*Degree",
                "*Position Desired", "Geographic Preference", "Salary Desired", "Current Position Salary", "One Previous Position Salary",
                "Two Previous Position Salary", "Three Previous Position Salary", "Leaving Reason", "Interview Impressions", "Consultant Initials",
                "Ratings", "Tenure Responsibilities", "Marital Status", "Travel Preference"};
            container.setPreferredSize(new Dimension(width, height * 152 / 100));
            frame.setTitle("Details");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                        new Home(username, currentModel, pendingModel, cancelledModel, waitingModel, candidatesModel, candidatesFoundModel, resumesModel).setVisible(true);
                        frame.dispose();
                    }
                }
            });
            container.add(backLabel);

            JScrollPane[] scrollArray = new JScrollPane[3];
            textFields = new JTextField[15];
            textAreas = new JTextArea[3];
            comboBoxes = new JComboBox[4];
            fieldsLength = fieldsName.length;
            for (int i = 0; i < fieldsName.length; i++) {
                String value = "";
                labels[i] = new JLabel(fieldsName[i] + " :");
                currentFont = labels[i].getFont();
                labels[i].setFont(currentFont.deriveFont(Font.BOLD, width * 1.1f / 100));
                labels[i].setSize(width, height * 5 / 100);
                labels[i].setLocation(width * x / 100, height * y / 100);
                container.add(labels[i]);
                if (i < 15) {
                    if (values.get(i).equals("0.0")) {
                        value = "";
                    } else {
                        value = values.get(i);
                    }
                    textFields[i] = new JTextField(value);
                    textFields[i].setSize(width * 15 / 100, height * 5 / 100);
                    textFields[i].setLocation(width * x / 100, height * (y + 5) / 100);
                    if (i == 2) {
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
                } else if (i >= 15 && i < 18) {
                    value = values.get(i).replaceAll("@", "\n");
                    textAreas[i - 15] = new JTextArea();
                    scrollArray[i - 15] = new JScrollPane(textAreas[i - 15]);
                    scrollArray[i - 15].setSize(width * 15 / 100, height * 15 / 100);
                    scrollArray[i - 15].setLocation(width * x / 100, height * (y + 5) / 100);
                    container.add(scrollArray[i - 15]);
                } else if (i == 18) {
                    SpinnerModel spinnerValue = new SpinnerNumberModel(Integer.parseInt(values.get(i)), 0, 5, 1);
                    spinner = new JSpinner(spinnerValue);
                    spinner.setSize(width * 8 / 100, height * 5 / 100);
                    spinner.setLocation(width * x / 100, height * (y + 5) / 100);
                    currentFont = spinner.getFont();
                    spinner.setFont(currentFont.deriveFont(Font.BOLD, width * 0.9f / 100));
                    JComponent editor = spinner.getEditor();
                    if (editor instanceof JSpinner.DefaultEditor) {
                        spinnerEditor = (JSpinner.DefaultEditor) editor;
                        spinnerEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
                        spinnerEditor.getTextField().setEnabled(false);
                        spinnerEditor.getTextField().setDisabledTextColor(Color.BLACK);
                    }
                    container.add(spinner);
                } else {
                    comboBoxes[i - 19] = new JComboBox(comboBoxValues[i - 19]);
                    comboBoxes[i - 19].setSize(width * 10 / 100, height * 5 / 100);
                    comboBoxes[i - 19].setLocation(width * x / 100, height * (y + 5) / 100);
                    comboBoxes[i - 19].setSelectedItem(values.get(i));
                    container.add(comboBoxes[i - 19]);
                }
                if ((count % 4) == 0) {
                    x = 4;
                    if (i < 15) {
                        y += 15;
                    } else {
                        y += 25;
                    }
                } else {
                    x += 25;
                }
                count++;
            }

            deleteButton = new JButton("Delete");
            currentFont = deleteButton.getFont();
            deleteButton.setSize(width * 13 / 100, height * 6 / 100);
            deleteButton.setLocation(width * 33 / 100, height * (y + 25) / 100);
            deleteButton.setFont(currentFont.deriveFont(Font.PLAIN, width * 0.8f / 100));
            deleteButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (deleteButton.isEnabled()) {
                        if (!helper.isConnected()) {
                            JOptionPane.showMessageDialog(frame,
                                    "Please check your internet connection!",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            int dialogButton = JOptionPane.YES_NO_OPTION;
                            int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the candidate?", "Confirmation", dialogButton);
                            if (dialogResult == JOptionPane.YES_OPTION) {
                                progressDeleteTask = new ProgressDeleteTask();
                                progressDeleteTask.execute();
                            }
                        }
                    }
                }
            });
            container.add(deleteButton);

            updateButton = new JButton("Update");
            currentFont = updateButton.getFont();
            updateButton.setSize(width * 13 / 100, height * 6 / 100);
            updateButton.setLocation(width * 52 / 100, height * (y + 25) / 100);
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
                            if (textFields[0].getText().trim().length() == 0 || textFields[1].getText().trim().length() == 0 || textFields[2].getText().trim().length() == 0 || textFields[6].getText().trim().length() == 0 || textFields[7].getText().trim().length() == 0 || textFields[8].getText().trim().length() == 0) {
                                error = 1;
                            }
                            if (!helper.emailValidity(textFields[3].getText().trim()) && textFields[3].getText().trim().length() > 0) {
                                error = 2;
                            }
                            for (int i = 0; i < 18; i++) {
                                if (error != 0) {
                                    break;
                                }
                                if (i < 15) {
                                    if (!helper.fieldValidity(textFields[i].getText())) {
                                        error = 2;
                                    } else if (i == 9 || i == 10 || i == 11 || i == 12 || i == 13) {
                                        if (textFields[i].getText().trim().length() > 0) {
                                            if (!textFields[i].getText().trim().replace(",", ".").matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                                                error = 2;
                                            }
                                        }
                                    }
                                } else {
                                    if (!helper.fieldValidity(textAreas[i - 15].getText())) {
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
                                int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to update the candidate?", "Confirmation", dialogButton);
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
        } else {
            String fieldsName[] = {"Name", "Address", "Birthdate", "Email", "Residence Number", "Business Number", "Driver Information", "Degree",
                "Position Desired", "Geographic Preference", "Salary Desired", "Current Position Salary", "One Previous Position Salary",
                "Two Previous Position Salary", "Three Previous Position Salary", "Ratings", "Tenure Responsibilities", "Marital Status", "Travel Preference",
                "Leaving Reason", "Interview Impressions", "Consultant Initials"};
            container.setPreferredSize(new Dimension(width, height * 102 / 100));
            frame.setTitle("Candidate Details");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JScrollPane[] scrollArray = new JScrollPane[3];
            textAreas = new JTextArea[3];
            for (int i = 0; i < fieldsName.length; i++) {
                String value = "";

                labels[i] = new JLabel(fieldsName[i] + " :");
                currentFont = labels[i].getFont();
                labels[i].setFont(currentFont.deriveFont(Font.BOLD, width * 1.1f / 100));
                labels[i].setSize(width, height * 5 / 100);
                labels[i].setLocation(width * x / 100, height * y / 100);
                container.add(labels[i]);

                if (i >= 19) {
                    labels[i - 19] = new JLabel(fieldsName[i] + " :");
                    currentFont = labels[i - 19].getFont();
                    labels[i - 19].setFont(currentFont.deriveFont(Font.BOLD, width * 1.1f / 100));
                    labels[i - 19].setSize(width, height * 5 / 100);
                    labels[i - 19].setLocation(width * x / 100, height * y / 100);
                    container.add(labels[i - 19]);
                    value = values.get(i).replaceAll("@", "\n");
                    textAreas[i - 19] = new JTextArea(value);
                    textAreas[i - 19].setEnabled(false);
                    textAreas[i - 19].setDisabledTextColor(Color.BLACK);
                    scrollArray[i - 19] = new JScrollPane(textAreas[i - 19]);
                    scrollArray[i - 19].setSize(width * 15 / 100, height * 15 / 100);
                    scrollArray[i - 19].setLocation(width * x / 100, height * (y + 5) / 100);
                    container.add(scrollArray[i - 19]);
                } else {
                    if (values.get(i).equals("0.0")) {
                        value = "-";
                    } else {
                        value = values.get(i);
                    }
                    if (i == 15) {
                        value += "/5";
                    }
                    labels[i] = new JLabel(fieldsName[i] + " : " + value);
                    currentFont = labels[i].getFont();
                    labels[i].setFont(currentFont.deriveFont(Font.BOLD, width * 1.1f / 100));
                    labels[i].setSize(width, height * 5 / 100);
                    labels[i].setLocation(width * x / 100, height * y / 100);
                    container.add(labels[i]);
                }
                if ((count % 4) == 0) {
                    x = 4;
                    if (i < 19) {
                        y += 15;
                    } else {
                        y += 25;
                    }
                } else {
                    x += 25;
                }
                count++;
            }
        }
        frame.getContentPane().add(jsp);
    }

    class ProgressDeleteTask extends SwingWorker<Void, Void> {

        @Override
        public Void doInBackground() throws ClassNotFoundException, SQLException, IOException {
            waiting = true;
            int i;
            frame.setCursor(Cursor.WAIT_CURSOR);
            deleteButton.setEnabled(false);
            updateButton.setEnabled(false);
            for (i = 0; i < fieldsLength; i++) {
                if (i < 15) {
                    textFields[i].setEnabled(false);
                } else if (i >= 15 && i < 18) {
                    textAreas[i - 14].setEnabled(false);
                } else if (i == 18) {
                    spinner.setEnabled(false);
                    spinnerEditor.getTextField().setDisabledTextColor(null);
                } else {
                    comboBoxes[i - 19].setEnabled(false);
                }
            }
            String url = helper.getDomainName() + "deleteCandidate.php";
            String urlParameters = "candidateId=" + modelDetails.candidateId;
            String response = helper.postMethod(url, urlParameters);
            if (response.charAt(0) == 'A') {
                JOptionPane.showMessageDialog(frame,
                        response,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                int index = helper.getCandidateModelIndex(modelDetails.candidateId, candidatesModel);
                candidatesModel.remove(index);
                helper.writeToFile(username + " delete the candidate with id: " + modelDetails.candidateId);
                JOptionPane.showMessageDialog(frame,
                        response);
                new Home(username, currentModel, pendingModel, cancelledModel, waitingModel, candidatesModel, candidatesFoundModel, resumesModel).setVisible(true);
                frame.dispose();
            }
            waiting = false;
            deleteButton.setEnabled(true);
            updateButton.setEnabled(true);
            for (i = 0; i < fieldsLength; i++) {
                if (i < 15) {
                    textFields[i].setEnabled(true);
                } else if (i >= 15 && i < 18) {
                    textAreas[i - 14].setEnabled(true);
                } else if (i == 18) {
                    spinner.setEnabled(true);
                    spinnerEditor.getTextField().setDisabledTextColor(Color.BLACK);
                } else {
                    comboBoxes[i - 19].setEnabled(true);
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
            String parameterFields[] = {"name", "address", "birthdate", "email", "residenceNumber", "businessNumber", "driverInformation", "degree",
                "positionDesired", "geographicPreference", "salaryDesired", "currentPositionSalary", "onePreviousPositionSalary",
                "twoPreviousPositionSalary", "threePreviousPositionSalary", "leavingReason", "interviewImpressions", "consultantInitials",
                "ratings", "tenureResponsibilities", "maritalStatus", "travelPreference"};
            waiting = true;
            double salaryDesired, currentPositionSalary, onePreviousPositionSalary, twoPreviousPositionSalary, threePreviousPositionSalary;
            int i;
            if (textFields[9].getText().trim().length() == 0) {
                salaryDesired = 0;
            } else {
                salaryDesired = Double.parseDouble(textFields[9].getText().trim().replace(",", "."));
            }
            if (textFields[10].getText().trim().length() == 0) {
                currentPositionSalary = 0;
            } else {
                currentPositionSalary = Double.parseDouble(textFields[10].getText().trim().replace(",", "."));
            }
            if (textFields[11].getText().trim().length() == 0) {
                onePreviousPositionSalary = 0;
            } else {
                onePreviousPositionSalary = Double.parseDouble(textFields[11].getText().trim().replace(",", "."));
            }
            if (textFields[12].getText().trim().length() == 0) {
                twoPreviousPositionSalary = 0;
            } else {
                twoPreviousPositionSalary = Double.parseDouble(textFields[12].getText().trim().replace(",", "."));
            }
            if (textFields[13].getText().trim().length() == 0) {
                threePreviousPositionSalary = 0;
            } else {
                threePreviousPositionSalary = Double.parseDouble(textFields[13].getText().trim().replace(",", "."));
            }
            frame.setCursor(Cursor.WAIT_CURSOR);
            deleteButton.setEnabled(false);
            updateButton.setEnabled(false);
            String urlParameters = "candidateId=" + modelDetails.candidateId;
            for (i = 0; i < fieldsLength; i++) {
                if (i < 15) {
                    textFields[i].setEnabled(false);
                    urlParameters += "&" + parameterFields[i] + "=" + textFields[i].getText().trim();
                } else if (i >= 15 && i < 18) {
                    textAreas[i - 15].setEnabled(false);
                    urlParameters += "&" + parameterFields[i] + "=" + textAreas[i - 15].getText().trim().replaceAll("\\n", "@");
                } else if (i == 18) {
                    spinner.setEnabled(false);
                    spinnerEditor.getTextField().setDisabledTextColor(null);
                    urlParameters += "&" + parameterFields[i] + "=" + spinner.getValue();
                } else {
                    comboBoxes[i - 19].setEnabled(false);
                    urlParameters += "&" + parameterFields[i] + "=" + comboBoxes[i - 19].getSelectedItem();
                }
            }
            String url = helper.getDomainName() + "updateCandidate.php";
            String response = helper.postMethod(url, urlParameters);
            if (response.charAt(0) == 'A') {
                JOptionPane.showMessageDialog(frame,
                        response,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                CandidateModel temp = new CandidateModel();
                temp.candidateId = modelDetails.candidateId;
                temp.name = textFields[0].getText().trim();
                temp.address = textFields[1].getText().trim();
                temp.birthdate = textFields[2].getText().trim();
                temp.email = textFields[3].getText().trim();
                temp.residenceNumber = textFields[4].getText().trim();
                temp.businessNumber = textFields[5].getText().trim();
                temp.driverInformation = textFields[6].getText().trim();
                temp.degree = textFields[7].getText().trim();
                temp.positionDesired = textFields[8].getText().trim();
                temp.geographicPreference = textFields[9].getText().trim();
                temp.salaryDesired = salaryDesired;
                temp.currentPositionSalary = currentPositionSalary;
                temp.onePreviousPositionSalary = onePreviousPositionSalary;
                temp.twoPreviousPositionSalary = twoPreviousPositionSalary;
                temp.threePreviousPositionSalary = threePreviousPositionSalary;
                temp.leavingReason = textAreas[0].getText().trim().replaceAll("\\n", "@");
                temp.interviewImpressions = textAreas[1].getText().trim().replaceAll("\\n", "@");
                temp.consultantInitials = textAreas[2].getText().trim().replaceAll("\\n", "@");
                temp.ratings = (int) spinner.getValue();
                temp.tenureResponsibilities = String.valueOf(comboBoxes[0].getSelectedItem());
                temp.maritalStatus = String.valueOf(comboBoxes[1].getSelectedItem());
                temp.travelPreference = String.valueOf(comboBoxes[2].getSelectedItem());
                int index = helper.getCandidateModelIndex(modelDetails.candidateId, candidatesModel);
                candidatesModel.set(index, temp);
                helper.writeToFile(username + " update the candidate with id: " + temp.candidateId);
                JOptionPane.showMessageDialog(frame,
                        response);
            }
            waiting = false;
            deleteButton.setEnabled(true);
            updateButton.setEnabled(true);
            for (i = 0; i < fieldsLength; i++) {
                if (i < 15) {
                    textFields[i].setEnabled(true);
                    if (i == 2) {
                        textFields[i].setEnabled(false);
                    }
                } else if (i >= 15 && i < 18) {
                    textAreas[i - 15].setEnabled(true);
                } else if (i == 18) {
                    spinner.setEnabled(true);
                    spinnerEditor.getTextField().setDisabledTextColor(Color.BLACK);
                } else {
                    comboBoxes[i - 19].setEnabled(true);
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
