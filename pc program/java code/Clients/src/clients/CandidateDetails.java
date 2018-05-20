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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

public class CandidateDetails {

    static private JFrame frame;
    private CandidateModel modelDetails;
    private Helper helper;
    private JTextField[] textFields;
    private JTextArea[] textAreas;
    private JScrollPane jsp;
    private int fieldsLength;
    private JButton checkButton, deleteButton;
    private ProgressCheckTask progressCheckTask;
    private ProgressDeleteTask progressDeleteTask;
    private ArrayList<JobOrderModel> jobOrderModel;
    private ArrayList<CandidateModel> candidatesModel;
    ArrayList<ResumeModel> resumesModel;
    private long id, maxInvoiceNo;
    private CompanyModel companyModel;
    private boolean waiting = false;
    private long jobOrderId;

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
    public CandidateDetails(CompanyModel companyModel, ArrayList<JobOrderModel> jobOrderModel, ArrayList<CandidateModel> candidatesModel, ArrayList<ResumeModel> resumesModel, CandidateModel modelDetails) {
        this.companyModel = companyModel;
        this.candidatesModel = candidatesModel;
        this.modelDetails = modelDetails;
        this.jobOrderModel = jobOrderModel;
        this.resumesModel = resumesModel;
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
        String fieldsName[] = {"Name", "Address", "Birthdate", "Residence Number", "Business Number", "Driver Information", "Degree",
            "Position Desired", "Geographic Preference", "Salary Desired", "Current Position Salary", "One Previous Position Salary",
            "Two Previous Position Salary", "Three Previous Position Salary", "Ratings", "Tenure Responsibilities", "Marital Status", "Travel Preference",
            "Leaving Reason", "Interview Impressions", "Consultant Initials"};
        helper = new Helper();

        //save modelDetails values into an arraylist
        ArrayList<String> values = helper.modelCandidateDetailsValues(modelDetails);
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.GRAY);
        frame.setTitle("Details");
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
        container.setPreferredSize(new Dimension(width, height * 152 / 100));
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
        JScrollPane[] scrollArray = new JScrollPane[3];
        textAreas = new JTextArea[3];
        fieldsLength = fieldsName.length;
        Font currentFont;
        for (int i = 0; i < fieldsName.length; i++) {
            String value = "";

            labels[i] = new JLabel(fieldsName[i] + " : ");
            currentFont = labels[i].getFont();
            labels[i].setFont(currentFont.deriveFont(Font.BOLD, width * 1.1f / 100));
            labels[i].setSize(width, height * 5 / 100);
            labels[i].setLocation(width * x / 100, height * y / 100);
            container.add(labels[i]);

            if (i >= 18) {
                value = values.get(i).replaceAll("@", "\n");
                textAreas[i - 18] = new JTextArea(value);
                textAreas[i - 18].setEnabled(false);
                textAreas[i - 18].setDisabledTextColor(Color.BLACK);
                scrollArray[i - 18] = new JScrollPane(textAreas[i - 18]);
                scrollArray[i - 18].setSize(width * 15 / 100, height * 15 / 100);
                scrollArray[i - 18].setLocation(width * x / 100, height * (y + 5) / 100);
                container.add(scrollArray[i - 18]);
            } else {
                if (values.get(i).equals("0.0")) {
                    value = "-";
                } else {
                    value = values.get(i);
                }
                if (i == 14) {
                    value += "/5";
                }
                labels[i].setText(labels[i].getText() + value);
            }
            if ((count % 4) == 0) {
                x = 4;
                if (i < 18) {
                    y += 15;
                } else {
                    y += 25;
                }
            } else {
                x += 25;
            }
            count++;
        }

        checkButton = new JButton("Checked");
        currentFont = checkButton.getFont();
        checkButton.setSize(width * 14 / 100, height * 6 / 100);
        checkButton.setLocation(width * 30 / 100, height * (y + 35) / 100);
        checkButton.setFont(currentFont.deriveFont(Font.PLAIN, width * 0.8f / 100));
        checkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (checkButton.isEnabled()) {
                    if (!helper.isConnected()) {
                        JOptionPane.showMessageDialog(frame,
                                "Please check your internet connection!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        JFrame frame = new JFrame("Departments");
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.getContentPane().setBackground(Color.GRAY);
                        frame.setResizable(false);
                        frame.setVisible(true);
                        frame.setLayout(null);
                        frame.setSize(width * 30 / 100, height * 80 / 100);
                        frame.setLocationRelativeTo(null);
                        JTable table = new JTable();
                        JScrollPane scrollPane = new JScrollPane(table);
                        table.setFillsViewportHeight(true);
                        table.setDefaultRenderer(Object.class, new MyTableCellRender());
                        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                        tableModel.addColumn("Department");
                        for (JobOrderModel item : jobOrderModel) {
                            Vector row = new Vector();
                            row.add(item.department);
                            tableModel.addRow(row);
                        }
                        table.setRowHeight(height * 5 / 100);
                        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 18));
                        table.setShowGrid(true);
                        table.setBackground(new Color(204, 204, 204));
                        scrollPane.setLocation(width * 5 / 100, height * 5 / 100);
                        scrollPane.setSize(width * 20 / 100, height * 50 / 100);
                        frame.add(scrollPane);

                        JButton frameCheckedButton = new JButton("Checked");
                        Font currentFont = frameCheckedButton.getFont();
                        frameCheckedButton.setSize(width * 10 / 100, height * 5 / 100);
                        frameCheckedButton.setLocation(width * 10 / 100, height * 65 / 100);
                        frameCheckedButton.setFont(currentFont.deriveFont(Font.PLAIN, width * 0.8f / 100));
                        frameCheckedButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                if (!helper.isConnected()) {
                                    JOptionPane.showMessageDialog(frame,
                                            "Please check your internet connection!",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                } else if (table.getSelectedRowCount() != 1) {
                                    JOptionPane.showMessageDialog(frame,
                                            "You must choose only one department!",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                } else {
                                    int dialogButton = JOptionPane.YES_NO_OPTION;
                                    int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", dialogButton);
                                    if (dialogResult == JOptionPane.YES_OPTION) {
                                        frame.dispose();
                                        jobOrderId = jobOrderModel.get(table.getSelectedRow()).jobOrderId;
                                        progressCheckTask = new ProgressCheckTask();
                                        progressCheckTask.execute();
                                    }
                                }
                            }
                        });
                        frame.add(frameCheckedButton);
                    }
                }
            }
        });
        container.add(checkButton);

        deleteButton = new JButton("Delete");
        deleteButton.setSize(width * 14 / 100, height * 6 / 100);
        deleteButton.setLocation(width * 56 / 100, height * (y + 35) / 100);
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
                        int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", dialogButton);
                        if (dialogResult == JOptionPane.YES_OPTION) {
                            progressDeleteTask = new ProgressDeleteTask();
                            progressDeleteTask.execute();
                        }
                    }
                }
            }
        });
        container.add(deleteButton);
        frame.getContentPane().add(jsp);
    }

    class ProgressCheckTask extends SwingWorker<Void, Void> {

        @Override
        public Void doInBackground() throws ClassNotFoundException, SQLException, IOException {
            waiting = true;
            frame.setCursor(Cursor.WAIT_CURSOR);
            checkButton.setEnabled(false);
            deleteButton.setEnabled(false);
            String url = helper.getDomainName() + "checkCandidate.php";
            String urlParameters = "candidateId=" + modelDetails.candidateId + "&jobOrderId=" + jobOrderId + "&clientId=" + companyModel.id;
            String response = helper.postMethod(url, urlParameters);
            if (response.charAt(0) == 'A') {
                JOptionPane.showMessageDialog(frame,
                        response,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                int index = helper.getCandidateModelIndex(modelDetails.candidateId, candidatesModel);
                candidatesModel.remove(index);
                index = helper.getJobModelIndex(jobOrderId, jobOrderModel);
                jobOrderModel.remove(index);
                try {
                    String text = "Dear PEC,\n\nAfter we check candidate ( " + modelDetails.name + ") we would like to make an interview.";
                    helper.sendEmail("kostasorfeasamc@gmail.com", text, "Candidate Checked");
                    if (modelDetails.email.length() > 0) {
                        String phone = "";
                        if (modelDetails.businessNumber.trim().length() > 0 && modelDetails.residenceNumber.trim().length() > 0) {
                            phone += "Business Number: " + modelDetails.businessNumber + " or to Residence Number: " + modelDetails.residenceNumber;
                        } else if (modelDetails.businessNumber.trim().length() > 0) {
                            phone += "Business Number: " + modelDetails.businessNumber;
                        } else if (modelDetails.residenceNumber.trim().length() > 0) {
                            phone += "Residence Number: " + modelDetails.residenceNumber;
                        }
                        text = "Dear " + modelDetails.name + ",\n\nAfter we check your information through PEC Company we would like to make you an interview\n"
                                + "We will call you to " + phone;
                        helper.sendEmail(modelDetails.email, text, companyModel.name + " - Interview");
                    }
                } catch (MessagingException ex) {
                    Logger.getLogger(CandidateDetails.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(frame,
                        response);
                new Home(companyModel, jobOrderModel, candidatesModel, resumesModel).setVisible(true);
                frame.dispose();
            }
            waiting = false;
            checkButton.setEnabled(true);
            deleteButton.setEnabled(true);
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

    class ProgressDeleteTask extends SwingWorker<Void, Void> {

        @Override
        public Void doInBackground() throws ClassNotFoundException, SQLException, IOException {
            frame.setCursor(Cursor.WAIT_CURSOR);
            checkButton.setEnabled(false);
            deleteButton.setEnabled(false);
            waiting = true;
            String url = helper.getDomainName() + "deleteCandidateFound.php";
            String urlParameters = "candidateId=" + modelDetails.candidateId + "&clientId=" + companyModel.id;
            String response = helper.postMethod(url, urlParameters);
            if (response.charAt(0) == 'A') {
                JOptionPane.showMessageDialog(frame,
                        response,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                int index = helper.getCandidateModelIndex(modelDetails.candidateId, candidatesModel);
                candidatesModel.remove(index);
                JOptionPane.showMessageDialog(frame,
                        response);
            }
            waiting = false;
            checkButton.setEnabled(true);
            deleteButton.setEnabled(true);
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
