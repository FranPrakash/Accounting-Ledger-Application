package com.pluralsight;

import javax.print.DocFlavor;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.AbstractMap;
import java.util.Scanner;

public class AccountingApp {
    public static void main(String[] args) {

        Scanner myScanner = new Scanner(System.in);

        // Main Menu Home screen
        System.out.println("Welcome to the Accounting App!");
        System.out.println("Type D to add a Deposit");
        System.out.println("Type P to make a Payment");
        System.out.println("Type L to go to  Ledger screen display");
        System.out.println("Type X to Exit");

        //Variable to receive user input
        String userChoice = myScanner.nextLine();

        //Switch statement to get user choice. Overall strategy create different method for different actions that the user want to take

        switch (userChoice) { //Using variable user choice in switch statement to decide what to run
            case "D":
                deposit(); //run the deposit method
                break;
            case "P":
                payment();
                break;
            case "L":
                showLedgerMenu();
                break;
            case "X":
                return; //returns to the computer - go backs to where it was called so the user can exit the application
            default:
                System.out.println("Your option is not valid");
                break;
        }
    }

    //Osmig help
    //Create a method to prompt the user for the deposit information and save it to the CSV file

    public static void deposit() {
        // Try catch to catch the exception error
        try {
            Scanner myScanner = new Scanner(System.in); // Scanner to receive user input
            System.out.println("Please enter the description");
            String description = myScanner.nextLine();
            System.out.println("Enter the Vendor name");
            String vendorName = myScanner.nextLine();
            System.out.println("Please enter the deposit amount");
            float depositAmount = myScanner.nextFloat();

            //Local date and local time method to be inserted in the CSv file as the instructions says
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);

            FileWriter myFileWriter = new FileWriter("transaction.csv", true); //Append true write one more line to the file
            String todayDeposit = date + "|" + time + "|" + description + "|" + vendorName + "|" + depositAmount + "\n";
            myFileWriter.write(todayDeposit);
            myFileWriter.close();
        } catch (Exception e) {
            System.out.println("An unexpected error occurred");
            e.printStackTrace(); // Show the system msg error to the user //Just want to implemet this here to learn
        }


    } //Osmig help ends here

    // Create a method payment option of home screen menu

    public static void payment() {
        try {
            Scanner myScanner = new Scanner(System.in);
            System.out.println("Please enter the description: ");
            String description = myScanner.nextLine();
            System.out.println("Please enter the vendor name: ");
            String vendorName = myScanner.nextLine();
            System.out.println("Please enter payment amount: ");
            double paymentAmount = -1 * myScanner.nextDouble(); // -1 to show the payment as negative entrie

            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
            FileWriter myFileWriter = new FileWriter("transaction.csv", true);
            String todayPayment = date + "|" + time + "|" + description + "|" + vendorName + "|" + paymentAmount + "\n";
            myFileWriter.write(todayPayment);
            myFileWriter.close();

        } catch (Exception e) {
            System.out.println("An unexpected error occured");
            e.printStackTrace();
        }

    }


    //Method for ledgerMenu option
    public static void showLedgerMenu() {

        Scanner myScanner = new Scanner(System.in);

        System.out.println("Welcome to the Ledger Menu!");
        System.out.println("Type A to display all entries");
        System.out.println("Type D to display deposit entries");
        System.out.println("Type P to display only payments");
        System.out.println("Type R to go to Reports Menu");

        String userChoiceLedger = myScanner.nextLine();

        switch (userChoiceLedger) {
            case "A":
                showAllEntries();
                break;
            case "D":
                displayDepositEntries();
                break;
            case "P":
                displayPaymentEntries();
                break;
            case "R":
                showReportMenu();
                break;
            default:
                System.out.println("Your option is not valid");
                break;

        }

    }

    // Method ledger menu to display option A all entries. I am using the Scanner method to read the transaction file and using a while loop to read until there's no more line.
    public static void showAllEntries() {
        try {

            //create a FileInputStream object pointing to transaction.csv file
            FileInputStream myFileInputStream = new FileInputStream("transaction.csv");
            Scanner myScanner = new Scanner(myFileInputStream); // create a Scanner to reference the file to be read


            //While loop to read until there is no more data
            while (myScanner.hasNextLine()) {
                String transactionLine; //Temporary store the transaction line
                transactionLine = myScanner.nextLine();
                System.out.println(transactionLine);
            }

            myScanner.close();

        } catch (Exception e) {
            System.out.println("An unexpected error occured");
            e.printStackTrace();

        }

    }

    //Method used to display deposit entry Ledger Menu - Option D
    public static void displayDepositEntries() {
        try {
            //Create a FileInputStream object pointing to transaction.csv file
            FileInputStream myFileInputStream = new FileInputStream("transaction.csv");
            Scanner myScanner = new Scanner(myFileInputStream);

            //While loop to read until there is no more data
            while (myScanner.hasNextLine()) {
                String transactionLine = myScanner.nextLine();//Temporary store the transaction line
                String[] transactionLineSplit = transactionLine.split("\\|"); // Create this String array so that I can split the transaction line variable and store it in the string array
                float transactionAmount = Float.parseFloat(transactionLineSplit[4]); // Convert amount from string to float
                if (transactionAmount > 0) //Making sure im getting the posit value as deposit and not the payment which is negative
                    System.out.println(transactionLine);
            }

            myScanner.close();

        } catch (Exception e) {
            System.out.println("An unexpected error occured");
            e.printStackTrace();

        }

    }

    //Method to display only payment of Ledger menu - Option P
    public static void displayPaymentEntries() {

        try {
            //Create a FileInputStream object pointing to transaction.csv file
            FileInputStream myFileInputStream = new FileInputStream("transaction.csv");
            Scanner myScanner = new Scanner(myFileInputStream);
            //While loop to read until there is no more data
            while (myScanner.hasNextLine()) {
                String transactionLine = myScanner.nextLine();
                String[] transactionLineSplit = transactionLine.split("\\|");
                float transactionAmount = Float.parseFloat(transactionLineSplit[4]);
                if (transactionAmount < 0)
                    System.out.println(transactionLine);
            }

            myScanner.close();

        } catch (Exception e) {
            System.out.println("An unexpected error occured");
            e.printStackTrace();
        }


    }

    //Method Reports menu
    public static void showReportMenu() {

        Scanner myScanner = new Scanner(System.in);
        System.out.println("Welcome to the Reports Menu!");
        System.out.println("Type 1 to see Month to Date transactions");
        System.out.println("Type 2 to see Previous Month transactions");
        System.out.println("Type 3 to see Year do Date transactions");
        System.out.println("Type 4 to display previous year transactions");
        System.out.println("Type 5 to search the transactions by Vendor");
        System.out.println("Type 0 to go back to the Ledger page ");
        System.out.println("Type H to go back to the home page");

        int userChoiceReportMenu = myScanner.nextInt();


        switch (userChoiceReportMenu) {
            case 1:
                System.out.println("You choose Month to date transactions");
                break;
            case 2:
                System.out.println("You choose previous month transactions");
                break;
            case 3:
                System.out.println(" You choose Year to date transaction");
                break;
            case 4:
                System.out.println("You choose previous year transaction");
                break;
            case 5:
                System.out.println("You choose search the transaction by vendor");
                break;
            case 0:
                System.out.println("You choose to go back to the home page");
                break;
            case 8:
                System.out.println("You choose go back to home page");
                break;
            default:
                System.out.println("Your option is not valid");


        }


    }
}


// TODO: Create a method to load transactions from the file to some data structure (maybe Hashmap or ArrayList)

// TODO: Create a method to show Reports Menu to the user


// TODO: Create a method to transactions by a certain type: deposits or payments

// TODO: Create a method to show transactions by date (accept inputs start date and end date)
// might use this method to address reports 1 to 4

// TODO: Create a method to transactions by vendor name

// TODO: Create a custom search method


