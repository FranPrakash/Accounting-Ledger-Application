package com.pluralsight;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class AccountingApp {
    public static void main(String[] args) {

        showHomeScreen(); // Created this method so I can call it in the ledger menu option H and other places

    }

    public static void showHomeScreen() {
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
            e.printStackTrace(); // Show the system msg error to the user //Just want to implement this here to learn
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
            double paymentAmount = -1 * myScanner.nextDouble(); // -1* to show the payment value as negative entries.

            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
            FileWriter myFileWriter = new FileWriter("transaction.csv", true);
            String todayPayment = date + "|" + time + "|" + description + "|" + vendorName + "|" + paymentAmount + "\n";
            myFileWriter.write(todayPayment);
            myFileWriter.close();

        } catch (Exception e) {
            System.out.println("An unexpected error occurred");
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
        System.out.println("Type H to go back to Home Screen");

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
            case "H":
                showHomeScreen();
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
            System.out.println("An unexpected error occurred");
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
                if (transactionAmount < 0) // getting the negative value since payment has to be shown as negative
                    System.out.println(transactionLine);
            }

            myScanner.close();

        } catch (Exception e) {
            System.out.println("An unexpected error occurred");
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

        int userChoiceReportMenu = myScanner.nextInt();


        switch (userChoiceReportMenu) {
            case 1:
                monthToDateTransactions();
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

    //Method Report menu - Option 1 Month to date transactions
    public static void monthToDateTransactions() {

        try {
            //Create a FileInputStream object pointing to transaction.csv file
            FileInputStream myFileInputStream = new FileInputStream("transaction.csv");
            Scanner myScanner = new Scanner(myFileInputStream); //create a Scanner to reference the file to be read

            while (myScanner.hasNextLine()) { //Checking if the scanner has another line. Run the whole block

                Transaction myTransaction = new Transaction(myScanner.nextLine()); //Passing one line of the file to the constructor that is being called. Making the transaction object

                LocalDate todayDate = LocalDate.now(); // Store today date in the local date variable . In declaring this variable so I can use in the IF condition
                LocalDate startOfTheMonth = todayDate.withDayOfMonth(1);// Store the first day of the month in local date // In the month thaT today date is get the first day of the month
                if (!myTransaction.getDate().isBefore(startOfTheMonth) && !myTransaction.getDate().isAfter(todayDate)) { //comparing the transaction date  with the start of the month and today date
                    System.out.println(myTransaction.showDetails());
                }
            }
            myScanner.close();
        } catch (Exception e) {
            System.out.println("An error occurred");
            e.printStackTrace(); //Identify the error and details about the exception
        }
    }

    // TODO : Method Report menu - Show previous month transactions - option 2
    public static void showPreviousMonthTransaction() {
        try {
            //Create a FileInputStream object pointing to transaction.csv file
            FileInputStream myFileInputStream = new FileInputStream("transaction.csv");
            Scanner myScanner = new Scanner(myFileInputStream); //create a Scanner to reference the file to be read

            //While loop to read the line
            while (myScanner.hasNextLine()) {

                Transaction myTransaction = new Transaction(myScanner.nextLine());//Passing one line of the file to the constructor that is being called. Making the transaction object
                LocalDate todayDate = LocalDate.now(); // Store today date in the local date variable . In declaring this variable so I can use in the IF condition
                if (myTransaction.getDate().getMonth() == todayDate.getMonth().minus(1)) { // Comparing transaction month to previous month
                    System.out.println(myTransaction.showDetails());
                }
            }
            myScanner.close();
        } catch (Exception e) {
            System.out.println("An error occurred");
            e.printStackTrace(); //Identify the error and details about the exception
        }


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

                LocalDate todayDate = LocalDate.now();


                if (myTransaction.getDate().getYear() == todayDate.getYear() && !myTransaction.getDate().isAfter(todayDate)) // TODO: fix this logic
                    System.out.println(myTransaction.showDetails());
            }
            myScanner.close();
        } catch (Exception e) {
            System.out.println("An error occurred");
            e.printStackTrace();

        }


    }

    //Create a method to show PreviousYear Transaction // TODO : Finish this method
    public static void showPreviousYearTransaction() {

        try {
            //Create a FileInputStream object pointing to transaction.csv file
            FileInputStream myFileInputStream = new FileInputStream("transaction.csv");
            Scanner myScanner = new Scanner(myFileInputStream); //create a Scanner to reference the file to be read

            while (myScanner.hasNextLine()) {

                Transaction myTransaction = new Transaction(myScanner.nextLine()); //Passing one line of the file to the constructor that is being called. Making the transaction object

                LocalDate todayDate = LocalDate.now();//Store today date in the local date variable . I am declaring this variable so I can use in the IF condition
                //LocalDate todayYear = LocalDate.now().minus(1); //Store today date in the local date variable . I am declaring this variable so I can use in the IF condition

                //if (myTransaction.getDate().getYear() == todayDate.getYear().minus(1)) { // Comparing transaction month to previous month
                System.out.println(myTransaction.showDetails());

            }
            myScanner.close();
        } catch (Exception e) {
            System.out.println("An error ocurred");
            e.printStackTrace();
        }
    }


    // Method to search the transaction by vendor - Option 5
    public static void searchTransactionByVendor() {
        try {

            //Create a FileInputStream object pointing to transaction.csv file
            FileInputStream myFileInputStream = new FileInputStream("transaction.csv"); //Create a FileInputStream object pointing to transaction.csv file
            Scanner myScanner = new Scanner(myFileInputStream);//create a Scanner to reference the file to be read

            Transaction myTransaction = new Transaction(myScanner.nextLine()); //Passing one line of the file to the constructor that is being called. Making the transaction object

           while (myScanner.hasNextLine()) {
               String vendorSearch = myTransaction.getVendor();
           }


        } catch (Exception e) {
            System.out.println("An error ocurred");
            e.printStackTrace();
        }

    }

}


// TODO: Create a method to show previous month transactions
// TODO: Create a method to show Year to date transactions
// TODO: Create a method to display previous year transactions
// TODO: Create a method to search the transactions by vendor name (option 5)
// TODO: Create a method to go back to Ledger page
// TODO: Create a custom search method (Challenge)


