
/**
 *
 * @author bcelikar
 */
import org.jfree.chart.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.border.LineBorder;

public class Window extends javax.swing.JFrame {

    private Member customer;
    // Declare the instance variables for dataset, chart, and table model
    DefaultCategoryDataset dataset;
    JFreeChart chart;
    DefaultTableModel tableModel;
    private ArrayList<Account> accounts;
    private JPanel containerPanel; // Declare the container panel as a class-level variable
    private JScrollPane scrollPane; // Declare the scroll pane as a class-level variable

    /**
     * Creates new form Window
     *
     * @param fileName
     * @param pin
     * @param ID
     */
    public Window(String fileName, String pin, String ID) {
        initComponents();
        accounts = new ArrayList<>();
        customer = memberCreate(ID, fileName);
        accounts = getData(fileName, ID);
        nameLabel.setText("Welcome back " + customer.getName());

        this.setLocationRelativeTo(null);
        this.setVisible(true);
        initializeScrollPane();
        populateScrollPane(fileName);

        createAccountButton.addActionListener((ActionEvent e) -> {
            CreateAccount accountWindow = new CreateAccount(this);
            String enteredDescription = accountWindow.showDialog();
            Account account;
            if (accountWindow.isConfirmed()) {
                if (!accounts.isEmpty()) {
                    account = new Account(accounts.get(accounts.size() - 1).getNumber() + 1);
                } else {
                    account = new Account(100001);
                }
                account.setType(accountWindow.getAccountType());
                account.setDescription(enteredDescription);
                accounts.add(account);

            }
            addData(fileName, customer.getID(), accounts);
            populateScrollPane(fileName);
        });

        depositButton.addActionListener((ActionEvent e) -> {
            Amount dialog = new Amount(this, accounts);
            // Show the dialog and retrieve the entered amount
            double enteredAmount = dialog.showDialog();
            // Check if the amount was confirmed
            if (dialog.isConfirmed()) {
                Account account = dialog.getAccount();
                account.setBalance(enteredAmount);
                addData(fileName, customer.getID(), accounts);
            }
        });

        withdrawButton.addActionListener((ActionEvent e) -> {
            // Create an instance of the AmountInputDialog
            Amount dialog = new Amount(this, accounts);
            // Show the dialog and retrieve the entered amount
            double enteredAmount = dialog.showDialog();
            // Check if the amount was confirmed
            if (dialog.isConfirmed()) {
                Account account = dialog.getAccount();
                if (enteredAmount <= account.getBalance()) {
                    account.setBalance(-enteredAmount);
                    addData(fileName, customer.getID(), accounts);
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
            InterestDialog dialog = new InterestDialog(this, accounts.get(0).getBalance());
            dialog.setVisible(true);

            if (dialog.isConfirmed()) {
                double amount = dialog.getAmount();
                int years = dialog.getYears();
                double interestRate = dialog.getInterest();

                // Clear the existing table model
                tableModel = (DefaultTableModel) jTable1.getModel();
                tableModel.setRowCount(0); // Remove all rows

                // Generate the dataset for the line chart
                dataset = new DefaultCategoryDataset();

                // Calculate and simulate compound interest for the specified number of years
                double balance = amount;
                for (int i = 1; i <= years; i++) {
                    balance += balance * interestRate;
                    dataset.addValue(balance, "Balance", String.valueOf(i));

                    // Add a new row to the table model with the year and balance
                    tableModel.addRow(new Object[]{i, balance});
                }

                // Create the line chart and update the chart panel
                chart = ChartFactory.createLineChart("Compound Interest Simulation", "Year", "Balance", dataset, PlotOrientation.VERTICAL, true, true, false);
                CategoryPlot plot = (CategoryPlot) chart.getPlot();
                plot.getRangeAxis().setLabelFont(plot.getDomainAxis().getLabelFont());

                ChartPanel chartPanel = new ChartPanel(chart);
                jPanel8.removeAll();
                jPanel8.add(chartPanel, BorderLayout.CENTER);
                jPanel8.validate();
            }
        });

    }

    private Member memberCreate(String id, String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // Check if the line contains ID and matches the provided ID
                if (line.startsWith("ID: ") && line.split(": ")[1].equals(id)) {
                    String pinLine;
                    String nameLine;

                    // Check if there are more lines available
                    if (!scanner.hasNextLine()) {
                        break; // Reached the end of the file unexpectedly
                    }
                    pinLine = scanner.nextLine();

                    // Check if there are more lines available
                    if (!scanner.hasNextLine()) {
                        break; // Reached the end of the file unexpectedly
                    }
                    nameLine = scanner.nextLine();

                    // Extracting PIN and Name
                    String pin = pinLine.split(": ")[1];
                    String name = nameLine.split(": ")[1];
                    return new Member(id, pin, name);
                }
            }
        } catch (FileNotFoundException e) {
            return null;
        }
        return null; // Member not found or file format error
    }

    private void initializeScrollPane() {
        // Create a container panel to hold the custom panels
        containerPanel = new JPanel();
        containerPanel.setBackground(new Color(199, 160, 65));
        containerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Create a JScrollPane and set the container panel as its viewport view
        scrollPane = new JScrollPane(containerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        // Set the UI of both vertical and horizontal scroll bars to CustomScrollBarUI
        scrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());

        // Add the scroll pane to your frame or panel
        jPanel8.add(scrollPane, BorderLayout.CENTER);
    }

    private void populateScrollPane(String fileName) {
        // Clear the container panel before adding new custom panels
        containerPanel.removeAll();

        // Iterate over the accounts list
        for (Account account : accounts) {
            // Create individual JLabel components for each account
            JLabel descLabel = new JLabel();
            JLabel jLabel2 = new JLabel();
            JLabel actNumLabel = new JLabel();
            JLabel typeLabel = new JLabel();
            JLabel balanceLabel = new JLabel();
            JLabel jLabel6 = new JLabel();

            // Create a delete button for each account
            JButton deleteButton = new JButton("Delete");

            // Customize the labels' properties
            descLabel.setText(account.getDescription());
            jLabel2.setFont(new Font("Verdana", 0, 8));
            jLabel2.setText("Account Number:");
            actNumLabel.setText(String.valueOf(account.getNumber()));
            typeLabel.setText(account.getType());
            balanceLabel.setText(String.valueOf(account.getBalance()));
            jLabel6.setFont(new Font("Verdana", 0, 8));
            jLabel6.setText("Balance:");

            // Create a JPanel to hold the custom panel components
            JPanel customPanel = new JPanel();
            if (account.getType().equals("Checking")) {
                customPanel.setBackground(new Color(183, 161, 98));
            }
            if (account.getType().equals("Savings")) {
                customPanel.setBackground(new Color(221, 197, 140));
            }

            customPanel.setBorder(new LineBorder(new Color(105, 105, 105), 8, true));
            customPanel.setMaximumSize(new Dimension(150, 380));
            customPanel.setMinimumSize(new Dimension(150, 380));
            customPanel.setPreferredSize(new Dimension(150, 380));
            customPanel.setLayout(new GridLayout(7, 1)); // Adjust the layout as needed

            // Add the custom panel components to the JPanel
            customPanel.add(typeLabel);
            customPanel.add(descLabel);
            customPanel.add(jLabel6);
            customPanel.add(balanceLabel);
            customPanel.add(jLabel2);
            customPanel.add(actNumLabel);
            customPanel.add(deleteButton); // Add the delete button to the custom panel

            // Add an action listener to the delete button
            deleteButton.addActionListener(e -> {
                accounts.remove(account); // Remove the account from the list
                containerPanel.remove(customPanel); // Remove the custom panel from the container panel
                containerPanel.revalidate(); // Revalidate the container panel to reflect the changes
                containerPanel.repaint(); // Repaint the container panel
                addData(fileName, customer.getID(), accounts); // Update the data in the file
            });

            // Add the custom panel to the container panel
            containerPanel.add(customPanel);
        }

        // Revalidate the container panel to reflect the changes
        containerPanel.revalidate();
    }

    private static ArrayList<Account> getData(String filename, String id) {
        ArrayList<Account> tempAccounts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Check if the line starts with the correct ID
                if (line.startsWith("ID: ") && line.split(": ")[1].equals(id)) {
                    String pinLine = reader.readLine();
                    String nameLine = reader.readLine();

                    // Extracting PIN and Name
                    String PIN = pinLine.split(": ")[1];
                    String name = nameLine.split(": ")[1];

                    // Read accounts
                    line = reader.readLine();
                    if (line.equals("Accounts:")) {
                        while ((line = reader.readLine()) != null && line.startsWith("  Account Number: ")) {
                            String accountNumberLine = line;
                            String typeLine = reader.readLine();
                            String descriptionLine = reader.readLine();
                            String balanceLine = reader.readLine();

                            // Extracting Account Number, Description, and Balance
                            int accountNumber = Integer.parseInt(accountNumberLine.split(": ")[1]);
                            String type = typeLine.split(": ")[1];
                            String description = descriptionLine.split(": ")[1];
                            double balance = Double.parseDouble(balanceLine.split(": ")[1]);

                            // Create and add the account to the list
                            Account account = new Account(accountNumber);
                            account.setType(type); // Set account type (if available)
                            account.setDescription(description);
                            account.setBalance(balance);
                            tempAccounts.add(account);
                        }
                    }

                    return tempAccounts;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return tempAccounts; // Return an empty list if member is not found with the given ID
    }

    private static void addData(String filename, String id, ArrayList<Account> accounts) {
        try {
            // Read existing data from the file
            File inputFile = new File(filename);
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            boolean skip = true;
            boolean idData = false;
            boolean corrID = false;

            while ((line = reader.readLine()) != null) {
                if (corrID && !skip && line.equals("Accounts:")) {
                    idData = false;
                    skip = true;
                    writer.write(line);
                    writer.newLine();

                    // Write the new account entries
                    for (Account account : accounts) {
                        writer.write("  Account Number: " + account.getNumber());
                        writer.newLine();
                        writer.write("  Type: " + account.getType());
                        writer.newLine();
                        writer.write("  Description: " + account.getDescription());
                        writer.newLine();
                        writer.write("  Balance: " + account.getBalance());
                        writer.newLine();
                    }
                } else if (line.startsWith("ID: ") && line.split(": ")[1].equals(id)) {
                    skip = false;
                    corrID = true;
                    idData = true;
                    writer.write(line);
                    writer.newLine();
                } else if (line.startsWith("ID: ") && !line.split(": ")[1].equals(id)) {
                    skip = false;
                    corrID = false;
                    writer.write(line);
                    writer.newLine();
                } else if (!skip || idData) {
                    writer.write(line);
                    writer.newLine();
                }
            }

            writer.close();
            reader.close();

            // Rename the temp file to replace the original file
            tempFile.renameTo(inputFile);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
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
        jPanel7 = new javax.swing.JPanel();
        createAccountButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        depositButton = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        withdrawButton = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        simInterestButton = new javax.swing.JButton();
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

        jPanel7.setBackground(new java.awt.Color(105, 105, 105));
        jPanel7.setPreferredSize(new java.awt.Dimension(200, 72));

        createAccountButton.setForeground(new java.awt.Color(199, 160, 65));
        createAccountButton.setText("Create New Account");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(createAccountButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(createAccountButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel7);

        jPanel4.setBackground(new java.awt.Color(105, 105, 105));
        jPanel4.setPreferredSize(new java.awt.Dimension(200, 72));

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
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4);

        jPanel5.setBackground(new java.awt.Color(105, 105, 105));
        jPanel5.setPreferredSize(new java.awt.Dimension(200, 72));

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
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(105, 105, 105));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 72));

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
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel6);

        getContentPane().add(jPanel1, java.awt.BorderLayout.WEST);

        jPanel2.setBackground(new java.awt.Color(199, 160, 65));
        jPanel2.setPreferredSize(new java.awt.Dimension(600, 450));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(199, 160, 65));
        jPanel8.setMaximumSize(new java.awt.Dimension(600, 450));
        jPanel8.setMinimumSize(new java.awt.Dimension(600, 450));
        jPanel8.setPreferredSize(new java.awt.Dimension(600, 450));
        jPanel8.setLayout(new java.awt.BorderLayout());
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
                .addContainerGap(785, Short.MAX_VALUE))
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
    private javax.swing.JButton createAccountButton;
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
