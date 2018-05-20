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

public class ResumeFrame {

    static private JFrame frame;
    private ResumeModel modelDetails;
    private Helper helper;
    private JTextField textField;
    private JTextArea[] textAreas;
    private JScrollPane jsp;
    private int fieldsLength, index, closeOperation;
    private JButton upsertButton;
    private ProgressUpsertTask progressUpsertTask;
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
    private char upsert;

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
    public ResumeFrame(String username, ResumeModel modelDetails, ArrayList<JobOrderModel> currentModel, ArrayList<JobOrderModel> pendingModel, ArrayList<JobOrderModel> cancelledModel, ArrayList<JobOrderModel> waitingModel, ArrayList<CandidateModel> candidatesModel, ArrayList<CandidateModel> candidatesFoundModel, ArrayList<ResumeModel> resumesModel, char upsert, int closeOperation) {
        this.username = username;
        this.pendingModel = pendingModel;
        this.cancelledModel = cancelledModel;
        this.candidatesModel = candidatesModel;
        this.waitingModel = waitingModel;
        this.modelDetails = modelDetails;
        this.currentModel = currentModel;
        this.candidatesFoundModel = candidatesFoundModel;
        this.resumesModel = resumesModel;
        this.upsert = upsert;
        this.closeOperation = closeOperation;
        initialize();
    }

    ResumeFrame() {

    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        int x = 4, y = 12, count = 1;
        String fieldsName[] = {"Common Duties", "Career Opportunities", "Educational Requirements", "Experience Requirements", "Salary Ranges"};
        helper = new Helper();

        ArrayList<String> values = helper.modelResumeValues(modelDetails, upsert);
        frame = new JFrame();
        frame.setDefaultCloseOperation(closeOperation);
        frame.getContentPane().setBackground(Color.GRAY);
        frame.setTitle("Resume");
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
        container.setPreferredSize(new Dimension(width, height * 70 / 100));
        container.setBackground(Color.GRAY);

        if (closeOperation == 3) {
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
        }
        JLabel[] labels = new JLabel[fieldsName.length];
        JScrollPane[] scrollArray = new JScrollPane[4];
        textAreas = new JTextArea[4];
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
            if (i < 4) {
                value = values.get(i).replaceAll("@", "\n");
                textAreas[i] = new JTextArea(value);
                scrollArray[i] = new JScrollPane(textAreas[i]);
                scrollArray[i].setSize(width * 15 / 100, height * 15 / 100);
                scrollArray[i].setLocation(width * x / 100, height * (y + 5) / 100);
                container.add(scrollArray[i]);
            } else {
                value = values.get(i);
                textField = new JTextField(value);
                textField.setSize(width * 15 / 100, height * 5 / 100);
                textField.setLocation(width * x / 100, height * (y + 5) / 100);
                container.add(textField);
            }
            if ((count % 4) == 0) {
                x = 4;
                y += 25;
            } else {
                x += 25;
            }
            count++;
        }

        upsertButton = new JButton();
        if (upsert == 'I') {
            upsertButton.setText("Insert");
        } else {
            upsertButton.setText("Update");
        }
        currentFont = upsertButton.getFont();
        upsertButton.setSize(width * 16 / 100, height * 8 / 100);
        upsertButton.setLocation(width * 42 / 100, height * (y + 27) / 100);
        upsertButton.setFont(currentFont.deriveFont(Font.PLAIN, width * 0.8f / 100));
        upsertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (upsertButton.isEnabled()) {
                    if (!helper.isConnected()) {
                        JOptionPane.showMessageDialog(frame,
                                "Please check your internet connection!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        int found = 0;
                        if (!helper.fieldValidity(textField.getText())) {
                            found = 1;
                        }
                        for (int i = 0; i < 4; i++) {
                            if (found == 1) {
                                break;
                            }
                            if (i < 4) {
                                if (!helper.fieldValidity(textAreas[i].getText())) {
                                    found = 1;
                                }
                            }
                        }
                        if (found == 1) {
                            JOptionPane.showMessageDialog(frame,
                                    "Invalid fields!",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            String msg;
                            if (upsert == 'I') {
                                msg = "Are you sure you want to insert resume for this candidate?";
                            } else {
                                msg = "Are you sure you want to update the resume for this candidate?";
                            }
                            int dialogButton = JOptionPane.YES_NO_OPTION;
                            int dialogResult = JOptionPane.showConfirmDialog(null, msg, "Confirmation", dialogButton);
                            if (dialogResult == JOptionPane.YES_OPTION) {
                                progressUpsertTask = new ProgressUpsertTask();
                                progressUpsertTask.execute();
                            }
                        }
                    }
                }
            }
        });
        container.add(upsertButton);
        frame.getContentPane().add(jsp);
    }

    class ProgressUpsertTask extends SwingWorker<Void, Void> {

        @Override
        public Void doInBackground() throws ClassNotFoundException, SQLException, IOException {
            int i;
            String parameterFields[] = {"commomDuties", "careerOpportunities", "educationalRequirements", "experienceRequirements", "salaryRanges"};
            waiting = true;
            frame.setCursor(Cursor.WAIT_CURSOR);
            upsertButton.setEnabled(false);
            String urlParameters = "candidateId=" + modelDetails.candidateId;
            for (i = 0; i < fieldsLength; i++) {
                if (i < 4) {
                    textAreas[i].setEnabled(false);
                    urlParameters += "&" + parameterFields[i] + "=" + textAreas[i].getText().trim().replaceAll("\\n", "@");
                } else {
                    textField.setEnabled(false);
                    urlParameters += "&" + parameterFields[i] + "=" + textField.getText().trim();
                }
            }
            String url;
            if (upsert == 'I') {
                url = helper.getDomainName() + "insertResume.php";
            } else {
                url = helper.getDomainName() + "updateResume.php";
            }

            String response = helper.postMethod(url, urlParameters);
            if (response.charAt(0) == 'A') {
                JOptionPane.showMessageDialog(frame,
                        response,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                ResumeModel temp = new ResumeModel();
                if (upsert == 'I') {
                    temp.id = Long.parseLong(response);
                } else {
                    temp.id = modelDetails.id;
                }
                temp.commomDuties = textAreas[0].getText().trim().replaceAll("\\n", "@");;
                temp.careerOpportunities = textAreas[1].getText().trim().replaceAll("\\n", "@");;
                temp.educationalRequirements = textAreas[2].getText().trim().replaceAll("\\n", "@");;
                temp.experienceRequirements = textAreas[3].getText().trim().replaceAll("\\n", "@");;
                temp.salaryRanges = textField.getText().trim();
                temp.candidateId = modelDetails.candidateId;
                if (upsert == 'I') {
                    response = "The resume inserted successfully!";
                    resumesModel.add(temp);
                    helper.writeToFile(username + " insert resume with id: " + temp.id + " which belongs to candidate with id: " + modelDetails.candidateId);
                } else {
                    int index = helper.getResumeModelIndex(modelDetails.id, resumesModel, 0);
                    resumesModel.set(index, temp);
                    helper.writeToFile(username + " update the resume with id: " + temp.id + " which belongs to candidate with id: " + modelDetails.candidateId);
                }
                JOptionPane.showMessageDialog(frame,
                        response);
                if (upsert == 'I') {
                    new Home(username, currentModel, pendingModel, cancelledModel, waitingModel, candidatesModel, candidatesFoundModel, resumesModel).setVisible(true);
                    frame.dispose();
                }
            }
            waiting = false;
            upsertButton.setEnabled(true);
            for (i = 0; i < fieldsLength; i++) {
                if (i < 4) {
                    textAreas[i].setEnabled(true);
                } else {
                    textField.setEnabled(true);
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
