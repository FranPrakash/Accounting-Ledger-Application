package com.pluralsight;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class AccountingApp {

    public static void main(String[] args) {

        showHomeScreen(); // Calling the method home screen when the application runs for the first time
    }

    public static void showHomeScreen() {
        Scanner myScanner = new Scanner(System.in); //Scanner to get user inputs

        // Main Menu Home screen - Prompt the user questions
        System.out.println("Welcome to the Accounting App!");
        System.out.println("Type D to add a Deposit");
        System.out.println("Type P to make a Payment");
        System.out.println("Type L to go to  Ledger screen Menu");
        System.out.println("Type X to Exit");

        //Variable choice to receive user input
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
                return; //Exit the application (In this option I added return to go backs to where it was called, so the user can exit the application)
            default:
                System.out.println("Your option is not valid");
                break;
        }
        myScanner.close();
    }

    //Create a method to prompt the user for the deposit information and save it to the CSV file
    //Got hel from Osmig
    public static void deposit() {
        Scanner myScanner = new Scanner(System.in); // Scanner to receive user input
        // Prompt the user questions
        System.out.println("Please enter the description");
        String description = myScanner.nextLine();
        System.out.println("Enter the Vendor name");
        String vendorName = myScanner.nextLine();
        System.out.println("Please enter the deposit amount");
        float depositAmount = myScanner.nextFloat();

        //Local date and Local time data type to be inserted in the CSV file as the Capstone instructions says // each line in the file has that
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now().truncatedTo(ChronoUnit.SECONDS); // Calling the truncated method to remove the millisecond

        try { //Try catch to catch the exception error
            FileWriter myFileWriter = new FileWriter("transaction.csv", true); // Creating the file writer objec.t APPEND True write one more line to the file
            String todayDeposit = date + "|" + time + "|" + description + "|" + vendorName + "|" + depositAmount + "\n"; // Transaction.csv file structure
            myFileWriter.write(todayDeposit); //Using my file writing to write the today deposit in the file
            myFileWriter.close();

        } catch (Exception e) {
            System.out.println("An unexpected error occurred");
            e.printStackTrace(); //Show the system msg error to the user, it Identifies the error and details about the exception
        }
      myScanner.close();

    } //Osmig help ends here

    // Create a method payment option of home screen menu (Option P)
    public static void payment() {
        Scanner myScanner = new Scanner(System.in); //Scanner to get use input
        //Prompt the user questions
        System.out.println("Please enter the description: ");
        String description = myScanner.nextLine();
        System.out.println("Please enter the vendor name: ");
        String vendorName = myScanner.nextLine();
        System.out.println("Please enter payment amount: ");
        double paymentAmount = -1 * myScanner.nextDouble(); // -1* to show the payment value as negative entries.
        myScanner.close();
        try {
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now().truncatedTo(ChronoUnit.SECONDS); //Calling the truncated method to remove the millisecond
            FileWriter myFileWriter = new FileWriter("transaction.csv", true); // Writing to the file
            String todayPayment = date + "|" + time + "|" + description + "|" + vendorName + "|" + paymentAmount + "\n"; //Concatenating what I want to write to the file
            myFileWriter.write(todayPayment); // Using my file writing to write today Payment in the file
            myFileWriter.close();

        } catch (Exception e) {
            System.out.println("An unexpected error occurred");
            e.printStackTrace(); //Show the system msg error to the user, it Identifies the error and details about the exception
        }
        //showHomeScreen();
    }

    //Method for ledgerMenu option L (Ledger Display)
    public static void showLedgerMenu() {
        Scanner myScanner = new Scanner(System.in); //Scanner to get input from the users
        //Prompt the user questions
        System.out.println("Welcome to the Ledger Menu!");
        System.out.println("Type A to display all entries");
        System.out.println("Type D to display deposit entries");
        System.out.println("Type P to display only payments");
        System.out.println("Type R to go to Reports Menu");
        System.out.println("Type H to go back to Home Screen");

        String userChoiceLedger = myScanner.nextLine(); //Scanner to receive user choice input
        //switch statement based on the user choice I can do different things. (I have 4 options, whenever I have a scenario that there is a menu is better to use switch)
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
            case "H":
                showHomeScreen(); //Calling home screen method
                break;
            default:
                System.out.println("Your option is not valid");
                break;
        }
        myScanner.close();
    }

    // Method ledger menu to display option A all entries. I am using the Scanner method to read the transaction file and using a while loop to read until there's no more line.
    public static void showAllEntries() {
        try {
            //Create a FileInputStream object pointing to transaction.csv file
            FileInputStream myFileInputStream = new FileInputStream("transaction.csv");
            Scanner myScanner = new Scanner(myFileInputStream); //Create a Scanner to reference the file to be read

            //While loop to read until there is no more data // hasNextLine() method checks if there is another line in the file
            while (myScanner.hasNextLine()) {

                Transaction myTransaction = new Transaction(myScanner.nextLine()); //Passing one line of the file to the constructor that is being called. Making the myTransaction object
                System.out.println(myTransaction.showDetails()); //show details is a method used to show transaction details in a clean format
            }
            myScanner.close();
        } catch (Exception e) {
            System.out.println("An unexpected error occured");
            e.printStackTrace(); //Identify the error and details about the exception
        }
        showLedgerMenu();
    }

    //Method used to display deposit entry Ledger Menu - Option D
    public static void displayDepositEntries() {
        try {
            //Create a FileInputStream object pointing to transaction.csv file
            FileInputStream myFileInputStream = new FileInputStream("transaction.csv");
            Scanner myScanner = new Scanner(myFileInputStream); //Create a Scanner to reference the file to be read

            //While loop to read until there is no more data
            while (myScanner.hasNextLine()) {
                Transaction myTransaction = new Transaction(myScanner.nextLine()); //Passing one line of the file to the constructor that is being called. Making the myTransaction object

                if (myTransaction.getAmount() > 0) //Making sure im getting the positive value as deposit and not the payment which is negative
                    System.out.println(myTransaction.showDetails()); //Show details method that show transaction details in a clean format
            }
            myScanner.close();

        } catch (Exception e) {
            System.out.println("An unexpected error occurred");
            e.printStackTrace();//Identify the error and details about the exception
        }
        showLedgerMenu();
    }

    //Method to display only payment of Ledger menu - Option P
    public static void displayPaymentEntries() {
        try {
            //Create a FileInputStream object pointing to transaction.csv file
            FileInputStream myFileInputStream = new FileInputStream("transaction.csv");
            Scanner myScanner = new Scanner(myFileInputStream);//Create a Scanner to reference the file to be read

            //While loop to read until there is no more data
            while (myScanner.hasNextLine()) { //Checking if the scanner has another line. Run the whole block
                Transaction myTransaction = new Transaction(myScanner.nextLine()); //Passing one line of the file to the constructor that is being called. Making the myTransaction object
                if (myTransaction.getAmount() < 0) // Checking for negative value since payment has to be shown as negative
                    System.out.println(myTransaction.showDetails());
            }
            myScanner.close();

        } catch (Exception e) {
            System.out.println("An unexpected error occurred");
            e.printStackTrace(); //Identify the error and details about the exception
        }
        showLedgerMenu();
    }

    //Method Reports Menu - Home Screen
    public static void showReportMenu() {
        //Scanner to accept user input
        Scanner myScanner = new Scanner(System.in);
        //Prompt the user questions
        System.out.println("Welcome to the Reports Menu!");
        System.out.println("Type 1 to display Month to Date transactions");
        System.out.println("Type 2 to display Previous Month transactions");
        System.out.println("Type 3 to display Year do Date transactions");
        System.out.println("Type 4 to display previous year transactions");
        System.out.println("Type 5 to search the transactions by Vendor");
        System.out.println("Type 0 to go back to the Ledger Menu");

        int userChoiceReportMenu = myScanner.nextInt(); //Scanner to get user input

        //Switch statement based on the user choice I can do different things.
        switch (userChoiceReportMenu) {
            case 1:
                showMonthToDateTransactions(); // Calling the methods
                break;
            case 2:
                showPreviousMonthTransaction();
                break;
            case 3:
                showYearToDateTransaction();
                break;
            case 4:
                showPreviousYearTransaction();
                break;
            case 5:
                searchTransactionByVendor();
                break;
            case 0:
                showLedgerMenu();
                break;
            default:
                System.out.println("Your option is not valid");
        }
    }

    //Method Report Menu - Option 1- Show Month to date transactions
    public static void showMonthToDateTransactions() {
        try {
            //Create a FileInputStream object pointing to transaction.csv file
            FileInputStream myFileInputStream = new FileInputStream("transaction.csv");
            Scanner myScanner = new Scanner(myFileInputStream); //Create a Scanner to reference the file to be read

            while (myScanner.hasNextLine()) { //Checking if the scanner has another line. Run the whole block

                Transaction myTransaction = new Transaction(myScanner.nextLine()); //Passing one line of the file to the constructor that is being called. Making the transaction object

                LocalDate todayDate = LocalDate.now(); // Store today date in the local date variable. I am declaring this variable so I can use in the IF condition
                LocalDate startOfTheMonth = todayDate.withDayOfMonth(1);// Store the first day of the month in local date // In the month today date is getting the first day of the month
                //Got help from Sameem in this if statement
                if (!myTransaction.getDate().isBefore(startOfTheMonth) && !myTransaction.getDate().isAfter(todayDate)) { //comparing the transaction date  with the start of the month and today date
                    System.out.println(myTransaction.showDetails());
                }
            }
            myScanner.close();
        } catch (Exception e) {
            System.out.println("An error occurred");
            e.printStackTrace(); //Identify the error and details about the exception
        }
        showReportMenu();
    }

    // Method Report menu - Show previous month transactions - option 2
    public static void showPreviousMonthTransaction() {
        try {
            //Create a FileInputStream object pointing to transaction.csv file
            FileInputStream myFileInputStream = new FileInputStream("transaction.csv");
            Scanner myScanner = new Scanner(myFileInputStream); //Create a Scanner to reference the file to be read

            //While loop to read the line
            while (myScanner.hasNextLine()) {
                Transaction myTransaction = new Transaction(myScanner.nextLine());//Passing one line of the file to the constructor that is being called. Making the transaction object
                LocalDate todayDate = LocalDate.now(); //Store today date in the local date variable . I am declaring this variable so I can use in the IF condition
                if (myTransaction.getDate().getMonth() == todayDate.getMonth().minus(1)) { // Comparing transaction month to previous month. Left hand month of transaction, right hand previous month
                    System.out.println(myTransaction.showDetails());
                }
            }
            myScanner.close();
        } catch (Exception e) {
            System.out.println("An error occurred");
            e.printStackTrace(); //Identify the error and details about the exception
        }
        showReportMenu();
    }

    //Create a method to show Year to date transaction. option 3
    public static void showYearToDateTransaction() {
        try {
            //Create a FileInputStream object pointing to transaction.csv file
            FileInputStream myFileInputStream = new FileInputStream("transaction.csv");
            Scanner myScanner = new Scanner(myFileInputStream); //create a Scanner to reference the file to be read

            //While loop to read the line
            while (myScanner.hasNextLine()) {

                Transaction myTransaction = new Transaction(myScanner.nextLine());//Passing one line of the file to the constructor that is being called. Making the transaction object

                LocalDate todayDate = LocalDate.now(); //Store today date in the local date variable . I am declaring this variable so I can use in the IF condition

                if (myTransaction.getDate().getYear() == todayDate.getYear() && !myTransaction.getDate().isAfter(todayDate)) // Comparing the transaction date and year with today year, if this is true display my trans
                    System.out.println(myTransaction.showDetails());
            }
            myScanner.close();
        } catch (Exception e) {
            System.out.println("An error occurred");
            e.printStackTrace(); //Identify the error and details about the exception
        }
        showReportMenu();
    }

    //Create a method to show PreviousYear Transaction - Option 4 Report menu
    public static void showPreviousYearTransaction() {
        try {
            //Create a FileInputStream object pointing to transaction.csv file
            FileInputStream myFileInputStream = new FileInputStream("transaction.csv");
            Scanner myScanner = new Scanner(myFileInputStream); //create a Scanner to reference the file to be read

            while (myScanner.hasNextLine()) {
                Transaction myTransaction = new Transaction(myScanner.nextLine()); //Passing one line of the file to the constructor that is being called. Making the transaction object

                LocalDate todayDate = LocalDate.now();//Store today date in the local date variable . I am declaring this variable so I can use in the IF condition

                if (myTransaction.getDate().getYear() == (todayDate.getYear() - 1)) { // Comparing transaction month to previous month. -1 because I am referring to previous month
                    System.out.println(myTransaction.showDetails());
                }
            }
            myScanner.close();
        } catch (Exception e) {
            System.out.println("An error ocurred");
            e.printStackTrace(); //Identify the error and details about the exception
        }
        showReportMenu();
    }

    // Method to search the transaction by vendor - Option 5
    public static void searchTransactionByVendor() {
        //Staring by getting user input
        Scanner mySc = new Scanner(System.in); //Scanner to get user input
        System.out.println("What is the vendor name ? "); //Prompt user for vendor name
        String vendorName = mySc.nextLine();

        try {
            //Create a FileInputStream object pointing to transaction.csv file
            FileInputStream myFileInputStream = new FileInputStream("transaction.csv"); //Create a FileInputStream object pointing to transaction.csv file
            Scanner myScanner = new Scanner(myFileInputStream);//create a Scanner to reference the file to be read

            while (myScanner.hasNextLine()) {

                Transaction myTransaction = new Transaction(myScanner.nextLine()); //Passing one line of the file to the constructor that is being called. Making the transaction object

                if (myTransaction.getVendor().equals(vendorName)) { // Condition to check if get vendor and get name are equal if its true print my transaction
                    System.out.println(myTransaction.showDetails());
                }
            }

        } catch (Exception e) {
            System.out.println("An error ocurred");
            e.printStackTrace(); //Identify the error and details about the exception
        }
        showReportMenu();
    }
}