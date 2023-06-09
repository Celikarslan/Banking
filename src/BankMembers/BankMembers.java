/**
 *
 * @author bcelikar
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BankMembers extends javax.swing.JFrame {
    
    private DefaultTableModel model;
    private List<String> accountDataList;

    /**
     * Creates new form BankMembers
     */
    public BankMembers() {
        initComponents();
        model = (DefaultTableModel) table.getModel();
        setLocationRelativeTo(null);
        setVisible(true);
        // Populate the table model with data from the file
        String filename = "account_data.txt";
        accountDataList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] accountInfo = line.split(", ");
                String id = accountInfo[0].substring(4);
                String name = accountInfo[2].substring(6);
                String balance = accountInfo[3].substring(9);
                model.addRow(new Object[]{id, name, balance});
                accountDataList.add(line);
            }
        } catch (IOException e) {
        }

        backButton.addActionListener((ActionEvent e) -> {
                new Menu();
                dispose(); // Close the window
        });

        // Attach an action listener to the delete button
        deleteButton.addActionListener((ActionEvent e) -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                // Remove the selected row from the table model
                model.removeRow(selectedRow);
                
                // Remove the account data from the accountDataList
                accountDataList.remove(selectedRow);
                
                // Remove the account data from the file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                    for (String line : accountDataList) {
                        writer.write(line);
                        writer.newLine();
                    }
                } catch (IOException ex) {
                }
            } else {
                JOptionPane.showMessageDialog(BankMembers.this,
                        "Please select an account to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        backButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(400, 300));

        jScrollPane1.setSize(new java.awt.Dimension(375, 300));

        table.setBackground(new java.awt.Color(199, 160, 65));
        table.setForeground(new java.awt.Color(105, 105, 105));
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Balance"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setColumnSelectionAllowed(true);
        table.setMaximumSize(new java.awt.Dimension(400, 300));
        table.setMinimumSize(new java.awt.Dimension(400, 300));
        table.setPreferredSize(new java.awt.Dimension(400, 300));
        table.setShowGrid(false);
        table.setSize(new java.awt.Dimension(400, 300));
        jScrollPane1.setViewportView(table);
        table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setResizable(false);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(105, 105, 105));
        jPanel2.setForeground(new java.awt.Color(199, 160, 65));
        jPanel2.setPreferredSize(new java.awt.Dimension(400, 50));

        backButton.setForeground(new java.awt.Color(199, 160, 65));
        backButton.setText("Back");
        backButton.setMaximumSize(new java.awt.Dimension(60, 15));
        backButton.setMinimumSize(new java.awt.Dimension(60, 15));
        backButton.setPreferredSize(new java.awt.Dimension(60, 15));
        backButton.setSize(new java.awt.Dimension(60, 15));

        deleteButton.setForeground(new java.awt.Color(199, 160, 65));
        deleteButton.setText("Delete");
        deleteButton.setMaximumSize(new java.awt.Dimension(60, 15));
        deleteButton.setMinimumSize(new java.awt.Dimension(60, 15));
        deleteButton.setPreferredSize(new java.awt.Dimension(60, 15));
        deleteButton.setSize(new java.awt.Dimension(60, 15));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
