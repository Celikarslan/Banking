# Banking Simulation

This is a Java-based banking simulation with a graphical user interface (GUI) created in Java that lets a user create and delete accounts, view a list of bank accounts, and login given an ID and PIN to their account. It also allows users to deposit and withdraw money and includes a simple interest simulation using the JFreeChart library to create tables and graphs.

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [Features](#features)
- [Dependencies](#dependencies)

## Installation

To run the banking simulation on your local machine, follow these steps:

1. Clone the repository:

   ```bash
   git clone https://github.com/Celikarslan/Banking.git
    ```
2. Navigate to the project directory
    
    ```bash
    cd Banking
    ```
3. Compile the Java source file

    ```bash
    javac *.java
    ```
4. Run the application
    ```bash
    java BankDriver
    ```

    The banking simulation GUI will now launch and be ready for use.

## Usage
Once the GUI is launched, you can perform the following actions:

1. **Create Account**: Click on the "Create Account" button to create a new bank account. Enter the necessary details such as the account holder's name, a unique ID and a PIN for the account.

2. **Delete Account**: Select an account from the list of bank accounts and click on the "Delete Account" button to remove the account from the system.

3. **View Bank Accounts**: Click on the "View Accounts" button to see a list of all the existing bank accounts. The list will display the account holder's name, ID, and current balance.

4. **Login**: Enter your account ID and PIN in the respective fields and click on the "Login" button to access your account. If the provided credentials are valid, you will be redirected to the account dashboard.

5. **Deposit**: Once logged in, you can deposit money into your account by clicking the "Deposit" button and entering the desired amount.

6. **Withdraw**: To withdraw money from your account, click on the "Withdraw" button and enter the desired amount.

7. **Interest Simulation**: Click on the "Interest Simulation" button to access the interest simulation feature. This feature uses the JFreeChart library to create a table and graph representing the growth of interest over time.

## Dependencies
This project has the following dependency:

JFreeChart (version X.X.X): Used for generating graphical representations of interest simulations.
Make sure to include this dependency in your project's classpath or build file.
