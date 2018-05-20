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

public class JobDetails {

    static private JFrame frame;
    private JobOrderModel modelDetails;
    private ArrayList<JobOrderModel> currentModel, pendingModel, cancelledModel, waitingModel;
    private ArrayList<CandidateModel> candidatesModel, candidatesFoundModel;
    private ArrayList<ResumeModel> resumesModel;
    private String username, candidateName;
    private Helper helper;
    private JTextArea[] textAreas;
    private JScrollPane jsp;
    private JButton pendingButton, foundButton, deleteButton;
    private boolean waiting = false;
    private ProgressTaskJob progressTaskJob;
    private ProgressGetCandidatesTask progressGetCandidatesTask;
    private int buttonClicked, searchJobSatusComboSelectedIndex, width, height;
    private long candidateId;

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
    public JobDetails(String username, JobOrderModel modelDetails, ArrayList<JobOrderModel> currentModel, ArrayList<JobOrderModel> pendingModel, ArrayList<JobOrderModel> cancelledModel, ArrayList<JobOrderModel> waitingModel, ArrayList<CandidateModel> candidatesModel, ArrayList<CandidateModel> candidatesFoundModel, ArrayList<ResumeModel> resumesModel, int searchJobSatusComboSelectedIndex) {
        this.username = username;
        this.modelDetails = modelDetails;
        this.currentModel = currentModel;
        this.pendingModel = pendingModel;
        this.cancelledModel = cancelledModel;
        this.waitingModel = waitingModel;
        this.candidatesModel = candidatesModel;
        this.resumesModel = resumesModel;
        this.searchJobSatusComboSelectedIndex = searchJobSatusComboSelectedIndex;
        initialize();
    }

    JobDetails() {

    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) screenSize.getWidth();
        height = (int) screenSize.getHeight();
        int x = 4, y = 12, count = 1;
        String fieldsName[] = {"Position", "Date", "Company", "Contact Name", "Department", "Billing Contact", "Phone", "Salary", "Starting Date", "Address", "Business type",
            "Placement Fee", "Placement Date", "Actual Starting Date", "New Position", "Educational Requirements", "Experience Requirements", "Duties", "Bonuses", "Travel Requirements", "Car",
            "Career Opportunities", "Interview", "Order Taker", "Counselor Ultimate Placement"};
        helper = new Helper();

        //save modelDetails values into an arraylist
        ArrayList<String> values = helper.modelJobDetailsValues(modelDetails);
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.GRAY);
        frame.setTitle("Details - InvoieNo: " + modelDetails.invoiceNo);
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
        container.setPreferredSize(new Dimension(width, height * 178 / 100));
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
                    new Home(username, currentModel, pendingModel, cancelledModel, waitingModel, candidatesModel, candidatesFoundModel, resumesModel).setVisible(true);
                    frame.dispose();
                }
            }
        });
        container.add(backLabel);
        JLabel[] labels = new JLabel[fieldsName.length];
        JScrollPane[] scrollArray = new JScrollPane[10];
        textAreas = new JTextArea[10];
        Font currentFont;
        for (int i = 0; i < fieldsName.length; i++) {
            String value = "";
            labels[i] = new JLabel(fieldsName[i] + " : ");
            currentFont = labels[i].getFont();
            labels[i].setFont(currentFont.deriveFont(Font.BOLD, width * 1.1f / 100));
            labels[i].setSize(width, height * 5 / 100);
            labels[i].setLocation(width * x / 100, height * y / 100);
            container.add(labels[i]);
            if (i < 15) {
                if (values.get(i).equals("0.0")) {
                    value = "-";
                } else {
                    value = values.get(i);
                }
                labels[i].setText(labels[i].getText() + value);
            } else {
                value = values.get(i).replaceAll("@", "\n");
                textAreas[i - 15] = new JTextArea(value);
                textAreas[i - 15].setEnabled(false);
                textAreas[i - 15].setDisabledTextColor(Color.BLACK);
                scrollArray[i - 15] = new JScrollPane(textAreas[i - 15]);
                scrollArray[i - 15].setSize(width * 15 / 100, height * 15 / 100);
                scrollArray[i - 15].setLocation(width * x / 100, height * (y + 5) / 100);
                container.add(scrollArray[i - 15]);
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
        pendingButton = new JButton("Pending");
        if (searchJobSatusComboSelectedIndex == 0 || searchJobSatusComboSelectedIndex == 1) {
            pendingButton.setVisible(true);
        } else {
            pendingButton.setVisible(false);
        }
        currentFont = pendingButton.getFont();
        pendingButton.setSize(width * 13 / 100, height * 6 / 100);
        pendingButton.setLocation(width * 26 / 100, height * (y + 31) / 100);
        pendingButton.setFont(currentFont.deriveFont(Font.PLAIN, width * 0.8f / 100));
        pendingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (pendingButton.isEnabled()) {
                    if (!helper.isConnected()) {
                        JOptionPane.showMessageDialog(frame,
                                "Please check your internet connection!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to put the job request to pending list?", "Confirmation", dialogButton);
                        if (dialogResult == JOptionPane.YES_OPTION) {
                            buttonClicked = 1;
                            progressTaskJob = new ProgressTaskJob();
                            progressTaskJob.execute();
                        }
                    }
                }
            }
        });
        container.add(pendingButton);

        deleteButton = new JButton("Delete");
        currentFont = deleteButton.getFont();
        deleteButton.setSize(width * 13 / 100, height * 6 / 100);
        deleteButton.setLocation(width * 43 / 100, height * (y + 31) / 100);
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
                        int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected job request?", "Confirmation", dialogButton);
                        if (dialogResult == JOptionPane.YES_OPTION) {
                            buttonClicked = 3;
                            progressTaskJob = new ProgressTaskJob();
                            progressTaskJob.execute();
                        }
                    }
                }
            }
        });
        container.add(deleteButton);

        foundButton = new JButton("Found");
        if (searchJobSatusComboSelectedIndex == 0 || searchJobSatusComboSelectedIndex == 1) {
            foundButton.setVisible(true);
        } else {
            foundButton.setVisible(false);
        }
        currentFont = foundButton.getFont();
        foundButton.setSize(width * 13 / 100, height * 6 / 100);
        foundButton.setLocation(width * 60 / 100, height * (y + 31) / 100);
        foundButton.setFont(currentFont.deriveFont(Font.PLAIN, width * 0.8f / 100));
        foundButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (foundButton.isEnabled()) {
                    if (!helper.isConnected()) {
                        JOptionPane.showMessageDialog(frame,
                                "Please check your internet connection!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        progressGetCandidatesTask = new ProgressGetCandidatesTask();
                        progressGetCandidatesTask.execute();
                    }

                }
            }
        });
        container.add(foundButton);
        frame.getContentPane().add(jsp);
    }

    class ProgressTaskJob extends SwingWorker<Void, Void> {

        @Override
        public Void doInBackground() throws ClassNotFoundException, SQLException, IOException {
            waiting = true;
            pendingButton.setEnabled(false);
            foundButton.setEnabled(false);
            deleteButton.setEnabled(false);
            frame.setCursor(Cursor.WAIT_CURSOR);
            if (buttonClicked == 1) //pending button clicked
            {
                String urlParameters = "id=" + modelDetails.jobOrderId;
                String url = helper.getDomainName() + "insertPendingJob.php";
                String response = helper.postMethod(url, urlParameters);
                if (response.charAt(0) == 'A') {
                    JOptionPane.showMessageDialog(frame,
                            response,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    helper.writeToFile(username + " put into pending list the jobOrderId: " + modelDetails.jobOrderId);
                    int index = helper.getJobModelIndex(modelDetails.jobOrderId, currentModel);
                    currentModel.remove(index);
                    pendingModel.add(modelDetails);
                    JOptionPane.showMessageDialog(frame,
                            "The job request has successfully moved to pending list!");
                    new Home(username, currentModel, pendingModel, cancelledModel, waitingModel, candidatesModel, candidatesFoundModel, resumesModel).setVisible(true);
                    frame.dispose();
                }
            } else if (buttonClicked == 2) //found button clicked
            {
                String urlParameters = "id=" + modelDetails.jobOrderId + "&candidateId=" + candidateId + "&clientId=" + modelDetails.clientId;
                String url = "";
                if (searchJobSatusComboSelectedIndex == 0) {
                    url = helper.getDomainName() + "foundCurrentJob.php";
                } else {
                    url = helper.getDomainName() + "foundPendingJob.php";
                }
                String response = helper.postMethod(url, urlParameters);
                if (response.charAt(0) == 'A') {
                    JOptionPane.showMessageDialog(frame,
                            response,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        String text = "Dear Sir,\n\nAfter your request we found 1 candidate ( " + candidateName + " ) for your company!\n\nWe will communicate with " + modelDetails.contactName + " to " + modelDetails.phone;
                        helper.sendEmail(modelDetails.clientEmail, text, "PEC - Candidate Found");
                    } catch (MessagingException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    helper.writeToFile(username + " found a candidate ( " + candidateName + " ) for jobOrderId: " + modelDetails.jobOrderId);
                    if (searchJobSatusComboSelectedIndex == 0) {
                        int index = helper.getJobModelIndex(modelDetails.jobOrderId, currentModel);
                        currentModel.remove(index);
                    } else {
                        int index = helper.getJobModelIndex(modelDetails.jobOrderId, pendingModel);
                        pendingModel.remove(index);
                    }
                    waitingModel.add(modelDetails);
                    JOptionPane.showMessageDialog(frame,
                            "An email has been sent to the company!");
                    new Home(username, currentModel, pendingModel, cancelledModel, waitingModel, candidatesModel, candidatesFoundModel, resumesModel).setVisible(true);
                    frame.dispose();
                }
            } else //delete button clicked
            {
                String url = "";
                if (searchJobSatusComboSelectedIndex == 0) {
                    url = helper.getDomainName() + "deleteCurrentJob.php";
                } else if (searchJobSatusComboSelectedIndex == 1) {
                    url = helper.getDomainName() + "deletePendingJob.php";
                } else if (searchJobSatusComboSelectedIndex == 2) {
                    url = helper.getDomainName() + "deleteCancelledJob.php";
                } else {
                    url = helper.getDomainName() + "deleteWaitingJob.php";
                }
                String urlParameters = "id=" + modelDetails.jobOrderId;
                String response = helper.postMethod(url, urlParameters);
                if (response.charAt(0) == 'A') {
                    JOptionPane.showMessageDialog(frame,
                            response,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    helper.writeToFile(username + " delete jobOrderId: " + modelDetails.jobOrderId);
                    if (searchJobSatusComboSelectedIndex == 0) {
                        int index = helper.getJobModelIndex(modelDetails.jobOrderId, currentModel);
                        currentModel.remove(index);
                    } else if (searchJobSatusComboSelectedIndex == 1) {
                        int index = helper.getJobModelIndex(modelDetails.jobOrderId, pendingModel);
                        pendingModel.remove(index);
                    } else if (searchJobSatusComboSelectedIndex == 2) {
                        int index = helper.getJobModelIndex(modelDetails.jobOrderId, cancelledModel);
                        cancelledModel.remove(index);
                    } else {
                        int index = helper.getJobModelIndex(modelDetails.jobOrderId, waitingModel);
                        waitingModel.remove(index);
                    }
                    JOptionPane.showMessageDialog(frame,
                            "The selected job order has been deleted successfully!");
                    new Home(username, currentModel, pendingModel, cancelledModel, waitingModel, candidatesModel, candidatesFoundModel, resumesModel).setVisible(true);
                    frame.dispose();
                }
            }
            waiting = false;
            pendingButton.setEnabled(true);
            foundButton.setEnabled(true);
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

    class ProgressGetCandidatesTask extends SwingWorker<Void, Void> {

        @Override
        public Void doInBackground() throws ClassNotFoundException, SQLException, IOException {
            waiting = true;
            frame.setCursor(Cursor.WAIT_CURSOR);
            pendingButton.setEnabled(false);
            foundButton.setEnabled(false);
            deleteButton.setEnabled(false);
            String urlString = helper.getDomainName() + "candidatesFoundHistory.php?id=" + modelDetails.clientId;
            ArrayList<CandidateModel> tempCandidatesModel = helper.getCandidates(urlString, 1, 2);
            JFrame _frame = new JFrame("Candidates");
            _frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            _frame.getContentPane().setBackground(Color.GRAY);
            _frame.setResizable(false);
            _frame.setVisible(true);
            _frame.setLayout(null);
            _frame.setSize(width * 30 / 100, height * 80 / 100);
            _frame.setLocationRelativeTo(null);
            JTable table = new JTable();
            JScrollPane scrollPane = new JScrollPane(table);
            table.setFillsViewportHeight(true);
            table.setDefaultRenderer(Object.class, new MyTableCellRender());
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            tableModel.addColumn("Name");
            for (CandidateModel item : tempCandidatesModel) {
                Vector row = new Vector();
                row.add(item.name);
                tableModel.addRow(row);
            }
            table.setRowHeight(height * 5 / 100);
            table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 18));
            table.setShowGrid(true);
            table.setBackground(new Color(204, 204, 204));
            scrollPane.setLocation(width * 5 / 100, height * 5 / 100);
            scrollPane.setSize(width * 20 / 100, height * 50 / 100);
            _frame.add(scrollPane);

            JButton foundButton1 = new JButton("Found");
            Font currentFont = foundButton1.getFont();
            foundButton1.setSize(width * 10 / 100, height * 5 / 100);
            foundButton1.setLocation(width * 10 / 100, height * 65 / 100);
            foundButton1.setFont(currentFont.deriveFont(Font.PLAIN, width * 0.8f / 100));
            foundButton1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (!helper.isConnected()) {
                        JOptionPane.showMessageDialog(_frame,
                                "Please check your internet connection!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else if (table.getSelectedRowCount() != 1) {
                        JOptionPane.showMessageDialog(_frame,
                                "You must choose only one candidate!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you found a candidate for this company?", "Confirmation", dialogButton);
                        if (dialogResult == JOptionPane.YES_OPTION) {
                            CandidateModel temp = tempCandidatesModel.get(table.getSelectedRow());
                            candidateId = temp.candidateId;
                            candidateName = temp.name;
                            _frame.dispose();
                            buttonClicked = 2;
                            progressTaskJob = new ProgressTaskJob();
                            progressTaskJob.execute();
                        }
                    }
                }
            });
            _frame.add(foundButton1);
            waiting = false;
            pendingButton.setEnabled(true);
            foundButton.setEnabled(true);
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
