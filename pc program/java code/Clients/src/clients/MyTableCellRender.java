/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

class MyTableCellRender extends DefaultTableCellRenderer {

    public MyTableCellRender() {

    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (isSelected) {
            c.setBackground(new Color(0, 120, 215));
        } else {
            c.setBackground(new Color(181, 181, 181));
        }
        return c;
    }
}
