import java.util.ArrayList;

import java.io.*;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main
{

    public static boolean ParseInt(String string)
    {
        try
        {
            int choice = Integer.parseInt(string);
            return true;
        }
        catch(Exception e)
        {
            System.out.println("You did not enter a correct value");
            return false;
        }
    }

    public static boolean ParseDouble(String string)
    {
        try
        {
            Double choice = Double.parseDouble(string);
            return true;
        }
        catch(Exception e)
        {
            System.out.println("You did not enter a correct value");
            return false;
        }
    }



    /// Reads the file, or returns an error message
    public static boolean Startup(ArrayList<Person> passedInList, String fileLocale )
    {


        try(BufferedReader reader = new BufferedReader(new FileReader(fileLocale)))
        {
            String line;
            while((line = reader.readLine()) != null)
            {
                String[] data = line.split("-");
                if(data.length == 4)
                {
                    passedInList.add(new Person(data[0],data[1],data[2],Double.parseDouble(data[3])));
                }
                else
                {
                    System.out.println("The data was not correctly formatted exiting");
                    return false;
                }
            }

            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public static void InitiateHamburgerMenuOptions(ArrayList<Person> LMSDatabase, String fileLocale)
    {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        String option;
        ArrayList<Person> lmsDatabase = LMSDatabase;
        boolean readFile;
        do
        {
            System.out.print("\nHamburger Menu Options type in the number for what you want to do then select enter");
            System.out.println("If Format is not correct, when entering or deleting a user you will be brought back to this menu. ");
            System.out.println("1. Add a new user. ");
            System.out.println("2. Subtract a user. ");
            System.out.println("3. View all users. ");
            System.out.println("4. Exit. ");


            option = scanner.nextLine();

            choice = ParseInt(option) ? Integer.parseInt(option) : 0;

            if(choice <= 0)
                continue;


            switch(choice)
            {
                case 1:
                    AddNewUser(fileLocale);
                    readFile = Startup(LMSDatabase, fileLocale);
                    break;
                case 2:
                    RemoveUser(lmsDatabase);
                    readFile = Startup(LMSDatabase, fileLocale);
                    break;
                case 3:
                    PrintOutAllUsers(lmsDatabase);
                    break;
                case 4:
                    System.out.println("You chose to exit!");

            }

        }
        while(choice != 4);

        scanner.close();
    }

    public static void AddNewUser(String fileLocale)
    {
        Scanner scanner = new Scanner(System.in);
        boolean addedUser = false;
        String[] newUserData = new String[4];


        do
        {
            System.out.print("Enter New User ID: ");
            newUserData[0]  = scanner.nextLine();
            System.out.print("Enter New User Name: ");
            newUserData[1] = scanner.nextLine();
            System.out.print("Enter New User Address: ");
            newUserData[2] = scanner.nextLine();

            boolean theyAddedARealnumber = false;

            do
            {
                System.out.print("Enter New User Balance in xx.xx format: ");
                String checkForDouble = scanner.nextLine();
                if(ParseDouble(checkForDouble))
                {
                    newUserData[3] = checkForDouble;
                    theyAddedARealnumber = true;
                }
                else
                {
                    System.out.println("You need to enter a real number for balance owed.");
                    continue;
                }
            }
            while(!theyAddedARealnumber);

            WriteUserToLMS(newUserData, fileLocale);
            System.out.println("You successfully added the new user!");
            addedUser = true;
        }
        while(!addedUser);
    }

    public static void WriteUserToLMS(String[] newUserData, String fileLocale)
    {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileLocale, true)))
        {
            String newData = String.format("%s-%s-%s-%.2f", newUserData[0], newUserData[1], newUserData[2],Double.parseDouble(newUserData[3]));
            bw.newLine();
            bw.write(newData);
        }
        catch(IOException e)
        {
            System.out.println("Error writing to file");
        }
    }

    public static void RemoveUser(ArrayList<Person> lmsDatabase)
    {

    }

    public static void PrintOutAllUsers(ArrayList<Person> lmsDatabase)
    {
        for (int i = 0; i < lmsDatabase.size(); i++)
        {
            System.out.println(lmsDatabase.get(i).toString());

        }
    }

    public static void main(String[] args)
    {
            ArrayList<Person> LMSDatabase = new ArrayList<>();
            String filelocale = "users.txt";
           boolean readFile =  Startup(LMSDatabase, filelocale);

           if(readFile)
           {
               InitiateHamburgerMenuOptions(LMSDatabase,filelocale);
           }
           else
           {
               System.out.println("Error read file, program terminating");
           }

    }
}