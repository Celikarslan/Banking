
/**
 *
 * @author bcelikar
 */
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import org.jfree.chart.axis.CategoryAxis;

public class Window extends javax.swing.JFrame {

    private Account customer;
    // Declare the instance variables for dataset, chart, and table model
    DefaultCategoryDataset dataset;
    JFreeChart chart;
    DefaultTableModel tableModel;

    /**
     * Creates new form Window
     *
     * @param fileName
     * @param ID
     */
    public Window(String fileName, String ID) {
        initComponents();
        customer = accountCreate(ID, fileName);
        balanceLabel.setText("Balance: $" + String.format("%.2f", customer.getBalance()));
        nameLabel.setText("Welcome back " + customer.getName());

        this.setLocationRelativeTo(null);
        this.setVisible(true);

        depositButton.addActionListener((ActionEvent e) -> {
            Amount dialog = new Amount(this);
            // Show the dialog and retrieve the entered amount
            double enteredAmount = dialog.showDialog();
            // Check if the amount was confirmed
            if (dialog.isConfirmed()) {
                customer.setBalance(enteredAmount);
                updateBalance(fileName, customer.getID(), customer.getBalance());
            }
        });

        withdrawButton.addActionListener((ActionEvent e) -> {
            // Create an instance of the AmountInputDialog
            Amount dialog = new Amount(this);
            // Show the dialog and retrieve the entered amount
            double enteredAmount = dialog.showDialog();
            // Check if the amount was confirmed
            if (dialog.isConfirmed()) {
                if (enteredAmount <= customer.getBalance()) {
                    customer.setBalance(-enteredAmount);
                    updateBalance(fileName, customer.getID(), customer.getBalance());
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Cannot withdraw more than balance");
                }
            }
        });

        backButton.addActionListener((ActionEvent e) -> {
            new Menu();
            dispose();
        });

        simInterestButton.addActionListener((ActionEvent e) -> {
            InterestDialog dialog = new InterestDialog(this, customer.getBalance());
            dialog.setVisible(true);

            double amount = dialog.getAmount();
            int years = dialog.getYears();
            double interestRate = dialog.getInterest();

            // Clear the existing table model
            tableModel = (DefaultTableModel) jTable1.getModel();
            tableModel.setRowCount(0); // Remove all rows

            // Check if chart already exists
            if (chart != null) {
                // Clear the existing dataset
                dataset.clear();
            } else {
                // Create a new dataset for the graph
                dataset = new DefaultCategoryDataset();
            }

            double totalAmount = amount;
            for (int i = 0; i <= years; i++) {
                dataset.addValue(totalAmount, "Total Amount", Integer.toString(i));
                totalAmount += amount * interestRate;
            }

            // Check if chart already exists
            if (chart != null) {
                // Update the dataset in the existing chart
                chart.getCategoryPlot().setDataset(dataset);

                // Configure range axes to fit the new dataset
                CategoryPlot plot = chart.getCategoryPlot();
                plot.configureRangeAxes();
            } else {
                // Create a chart using the dataset
                chart = ChartFactory.createLineChart(
                        "Interest Simulation", "Year", "Total Amount",
                        dataset, PlotOrientation.VERTICAL,
                        true, true, false);

                // Create a chart panel to display the chart
                ChartPanel chartPanel = new ChartPanel(chart);

                // Set the layout manager for jPanel8
                jPanel8.setLayout(new BorderLayout());

                // Clear jPanel8 before adding the chart panel
                jPanel8.removeAll();

                // Add the chart panel to jPanel8
                jPanel8.add(chartPanel, BorderLayout.CENTER);

                
            }

            // Create a table to display the total amount every year
            Object[][] data = new Object[years][2];
            totalAmount = amount;
            for (int i = 0; i < years; i++) {
                data[i][0] = Integer.toString(i + 1);
                data[i][1] = totalAmount;
                totalAmount += amount * interestRate;
                totalAmount = Double.parseDouble(String.format("%.2f", totalAmount));
                tableModel.addRow(new Object[]{i + 1, totalAmount});
            }

            // Make chart and table visible
            jPanel8.setVisible(true);
            jPanel9.setVisible(true);
        });

        // Hide chart and table initially
        jPanel8.setVisible(false);
        jPanel9.setVisible(false);
    }

    private void updateBalance(String filename, String id, double newBalance) {
        balanceLabel.setText("Balance: $" + String.format("%.2f", newBalance));
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            StringBuilder fileContent = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID: " + id + ", ")) {
                    String[] accountInfo = line.split(", ");
                    String balanceString = accountInfo[3].substring(9);
                    line = line.replace(balanceString, String.format("%.2f", newBalance));
                }
                fileContent.append(line).append(System.lineSeparator());
            }

            // Write the updated file content back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                writer.write(fileContent.toString());
            }
        } catch (IOException e) {
        }
    }

    private Account accountCreate(String id, String filename) {
        String accountData = getAccountData(filename, id);
        String[] accountInfo = accountData.split(", ");
        String name = accountInfo[2].substring(6);
        String balance = accountInfo[3].substring(9);
        Double bal = Double.valueOf(balance);
        Account cust = new Account(id, name);
        cust.setBalance(bal);
        return cust;
    }

    // Helper method to get the account data (name and balance) for the given ID
    private static String getAccountData(String filename, String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID: " + id + ", ")) {
                    return line;
                }
            }
        } catch (IOException e) {
        }
        return null;
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
        jPanel3 = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        backButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        depositButton = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        withdrawButton = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        simInterestButton = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        balanceLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1000, 450));
        setMinimumSize(new java.awt.Dimension(1000, 450));
        setPreferredSize(new java.awt.Dimension(1000, 450));

        jPanel1.setBackground(new java.awt.Color(105, 105, 105));
        jPanel1.setPreferredSize(new java.awt.Dimension(200, 450));

        jPanel3.setBackground(new java.awt.Color(105, 105, 105));
        jPanel3.setPreferredSize(new java.awt.Dimension(200, 115));

        nameLabel.setBackground(new java.awt.Color(105, 105, 105));
        nameLabel.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        nameLabel.setForeground(new java.awt.Color(199, 160, 65));
        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nameLabel.setText("Name Label");
        nameLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        backButton.setForeground(new java.awt.Color(199, 160, 65));
        backButton.setText("Back");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 87, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(jPanel3);

        jPanel4.setBackground(new java.awt.Color(105, 105, 105));
        jPanel4.setPreferredSize(new java.awt.Dimension(200, 60));

        depositButton.setForeground(new java.awt.Color(199, 160, 65));
        depositButton.setLabel("Deposit");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(depositButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(depositButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4);

        jPanel5.setBackground(new java.awt.Color(105, 105, 105));
        jPanel5.setPreferredSize(new java.awt.Dimension(200, 60));

        withdrawButton.setForeground(new java.awt.Color(199, 160, 65));
        withdrawButton.setLabel("Withdraw");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(withdrawButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(withdrawButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(105, 105, 105));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 60));

        simInterestButton.setForeground(new java.awt.Color(199, 160, 65));
        simInterestButton.setText("Simulate Interest");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(simInterestButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(simInterestButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel6);

        jPanel7.setBackground(new java.awt.Color(105, 105, 105));
        jPanel7.setPreferredSize(new java.awt.Dimension(200, 155));

        balanceLabel.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        balanceLabel.setForeground(new java.awt.Color(199, 160, 65));
        balanceLabel.setText("Balance: ");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(balanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(balanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel7);

        getContentPane().add(jPanel1, java.awt.BorderLayout.WEST);

        jPanel2.setBackground(new java.awt.Color(199, 160, 65));
        jPanel2.setPreferredSize(new java.awt.Dimension(600, 450));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(199, 160, 65));
        jPanel8.setMaximumSize(new java.awt.Dimension(600, 450));
        jPanel8.setMinimumSize(new java.awt.Dimension(600, 450));
        jPanel8.setPreferredSize(new java.awt.Dimension(600, 450));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel8, java.awt.BorderLayout.WEST);

        jPanel9.setMaximumSize(new java.awt.Dimension(200, 450));
        jPanel9.setMinimumSize(new java.awt.Dimension(200, 450));
        jPanel9.setPreferredSize(new java.awt.Dimension(200, 450));
        jPanel9.setSize(new java.awt.Dimension(200, 450));

        jScrollPane1.setMaximumSize(new java.awt.Dimension(200, 450));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(200, 450));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(200, 450));
        jScrollPane1.setSize(new java.awt.Dimension(200, 450));

        jTable1.setBackground(new java.awt.Color(105, 105, 105));
        jTable1.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        jTable1.setForeground(new java.awt.Color(199, 160, 65));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Years", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setPreferredSize(new java.awt.Dimension(200, 450));
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
        }

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1003, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel9, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JLabel balanceLabel;
    private javax.swing.JButton depositButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton simInterestButton;
    private javax.swing.JButton withdrawButton;
    // End of variables declaration//GEN-END:variables
}
