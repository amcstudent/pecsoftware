/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class ResumeFrame {

    static private JFrame frame;
    private ResumeModel resumeDetails;
    private Helper helper;

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
    public ResumeFrame(ResumeModel resumeDetails) {
        this.resumeDetails = resumeDetails;
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

        ArrayList<String> values = helper.modelResumeValues(resumeDetails);
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(Color.GRAY);
        frame.setTitle("Resume");
        frame.setResizable(false);

        //size and location of window opened
        frame.setSize(width, height * 92 / 100);
        frame.setLocationRelativeTo(null);

        //initialize components      
        JPanel container = new JPanel();
        JScrollPane jsp = new JScrollPane(container);
        jsp.setLocation(width * 1 / 100, height * 3 / 100);
        jsp.setSize(width * 98 / 100, height * 82 / 100);
        container.setLayout(null);
        container.setPreferredSize(new Dimension(width, height * 43 / 100));
        container.setBackground(Color.GRAY);

        JLabel[] labels = new JLabel[fieldsName.length];
        JScrollPane[] scrollArray = new JScrollPane[4];
        JTextArea[] textAreas = new JTextArea[4];
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
                textAreas[i].setEnabled(false);
                textAreas[i].setDisabledTextColor(Color.BLACK);
                scrollArray[i] = new JScrollPane(textAreas[i]);
                scrollArray[i].setSize(width * 15 / 100, height * 15 / 100);
                scrollArray[i].setLocation(width * x / 100, height * (y + 5) / 100);
                container.add(scrollArray[i]);
            } else {
                value = values.get(i);
                labels[i].setText(labels[i].getText() + " " + value);
            }
            if ((count % 4) == 0) {
                x = 4;
                y += 25;
            } else {
                x += 25;
            }
            count++;
        }
        frame.getContentPane().add(jsp);
    }

}
