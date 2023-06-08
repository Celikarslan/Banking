
import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author bcelikar
 */
public class Menu extends JFrame {

    public Menu() {
        initComponents();
        restrictToNumericInput(idTextField);
        restrictToNumericInput(pinTextField);
        this.setVisible(true);

        // Attach an action listener to the submit button
        loginButton.addActionListener((ActionEvent e) -> {
            String id = idTextField.getText();
            String pin = new String(pinTextField.getPassword());
            
            // Perform your desired action with the ID and PIN values here
            // For example, you can store them in a file
            String filename = "account_data.txt";
            boolean idExists = checkIfIdExists(filename, id);
            if (!idExists) {
                JOptionPane.showMessageDialog(new JFrame(), "ID does not exist. Please choose a different ID.");
            } else if (!checkPin(filename, id, pin)) {
                JOptionPane.showMessageDialog(new JFrame(), "Pin is not correct. Please enter another pin");
            } else {
                new Window(filename, id);
                dispose();
            }
        });
    }

    private static boolean checkIfIdExists(String filename, String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID: " + id)) {
                    return true; // ID already exists in the file
                }
            }
        } catch (IOException e) {
        }
        return false; // ID does not exist in the file
    }

    // Helper method to check if the given pin is correct for the given id
    private static boolean checkPin(String filename, String id, String pin) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID: " + id + ", PIN: " + pin + ",")) {
                    return true; // ID already exists in the file
                }
            }
        } catch (IOException e) {
            // Handle the exception
        }
        return false; // ID does not exist in the file
    }

    // Helper method to restrict the text field to numeric input only
    private static void restrictToNumericInput(JTextField textField) {
        PlainDocument document = (PlainDocument) textField.getDocument();
        document.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("[0-9]+")) {
                    super.insertString(fb, offset, text, attrs);
                }
            }

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("[0-9]+")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }
    // Helper method to check if the given ID exists in the file

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        listMembersButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        idTextField = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        pinTextField = new javax.swing.JPasswordField();
        jPanel5 = new javax.swing.JPanel();
        loginButton = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        createAccountButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 600, 350));
        setMaximumSize(new java.awt.Dimension(600, 350));
        setPreferredSize(new java.awt.Dimension(600, 350));

        jPanel1.setBackground(new java.awt.Color(255, 239, 127));
        jPanel1.setPreferredSize(new java.awt.Dimension(250, 350));

        jPanel3.setBackground(new java.awt.Color(255, 239, 127));
        jPanel3.setAlignmentX(0.0F);
        jPanel3.setAlignmentY(0.0F);
        jPanel3.setPreferredSize(new java.awt.Dimension(250, 100));

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Welcome Back");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        listMembersButton.setBackground(new java.awt.Color(255, 239, 127));
        listMembersButton.setFont(new java.awt.Font("Verdana", 0, 9)); // NOI18N
        listMembersButton.setText("List Members");
        listMembersButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        listMembersButton.setOpaque(true);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(listMembersButton))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jLabel1)))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(listMembersButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(29, 29, 29))
        );

        jPanel1.add(jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 239, 127));
        jPanel4.setPreferredSize(new java.awt.Dimension(250, 120));

        jPanel6.setBackground(new java.awt.Color(255, 239, 127));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jLabel2.setBackground(new java.awt.Color(255, 234, 80));
        jLabel2.setText("ID Number:");
        jPanel6.add(jLabel2, java.awt.BorderLayout.NORTH);
        jPanel6.add(idTextField, java.awt.BorderLayout.SOUTH);

        jPanel7.setBackground(new java.awt.Color(255, 239, 127));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jLabel3.setText("Pin Number:");
        jPanel7.add(jLabel3, java.awt.BorderLayout.NORTH);
        jPanel7.add(pinTextField, java.awt.BorderLayout.SOUTH);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        jPanel1.add(jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 239, 127));
        jPanel5.setPreferredSize(new java.awt.Dimension(250, 50));

        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(loginButton)
                .addGap(0, 27, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel5);

        jPanel8.setBackground(new java.awt.Color(255, 239, 127));
        jPanel8.setPreferredSize(new java.awt.Dimension(250, 120));

        createAccountButton.setBackground(new java.awt.Color(255, 239, 127));
        createAccountButton.setFont(new java.awt.Font("Verdana", 0, 9)); // NOI18N
        createAccountButton.setText("Don't have an account yet? Sign up.");
        createAccountButton.setActionCommand("Don't have an account yet? Sign up.");
        createAccountButton.setBorderPainted(false);
        createAccountButton.setFocusPainted(false);
        createAccountButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(createAccountButton, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(createAccountButton)
                .addGap(0, 103, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel8);

        getContentPane().add(jPanel1, java.awt.BorderLayout.WEST);

        jPanel2.setBackground(new java.awt.Color(255, 239, 160));
        jPanel2.setPreferredSize(new java.awt.Dimension(350, 350));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/GBK.png"))); // NOI18N
        jLabel5.setToolTipText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(0, 1908, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_loginButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createAccountButton;
    private javax.swing.JTextField idTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JButton listMembersButton;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField pinTextField;
    // End of variables declaration//GEN-END:variables
}

