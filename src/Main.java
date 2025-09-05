import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.io.*;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main
{

    //Making Sure the String is an Int - This is a helper method
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

    //Making Sure the String is a Double - This is a helper method
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

    //This Method will be running the Hamburger Menu
    public static void InitiateHamburgerMenuOptions(ArrayList<Person> LMSDatabase, String fileLocale)
    {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        String option;
        ArrayList<Person> lmsDatabase = LMSDatabase;
        boolean readFile;
        //Do Loops loop at least 1 time, so I am using a Do While to constantly run the hamburger menu until 4 is pressed
        do
        {
            System.out.print("\nHamburger Menu Options type in the number for what you want to do then select enter");
            System.out.println("If Format is not correct, when entering or deleting a user you will be brought back to this menu. ");
            System.out.println("1. Add a new user. ");
            System.out.println("2. Subtract a user. ");
            System.out.println("3. View all users. ");
            System.out.println("4. Exit. ");


            option = scanner.nextLine();

            //Make sure choice is a number otherwise restart
            choice = ParseInt(option) ? Integer.parseInt(option) : 0;

            if(choice <= 0)
                continue;


            switch(choice)
            {
                case 1:
                    AddNewUser(lmsDatabase,fileLocale);
                    break;
                case 2:
                    RemoveUser(lmsDatabase,fileLocale);
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

    //Add a new user
    public static void AddNewUser(ArrayList<Person> lmsDatabase,String fileLocale)
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
                //As long as all 4 data piece are correct ---- The other 3 are just strings of whatever somebody wants
                // We will add the doubleAmount and then set theyAddedARealnumber to true to leave the loop
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

            // Creating a new instance of Person object to add to lmsDatabase
            Person newUser = new Person(newUserData[0],newUserData[1],newUserData[2], Double.parseDouble(newUserData[3]));

            // If lmsDatabase add a new user ( it will) we are going to wipe the file and recreate from short term memory
            if(lmsDatabase.add(newUser))
                DeleteAndWriteFile(lmsDatabase, fileLocale);



            System.out.println("You successfully added the new user!");
            addedUser = true;
        }
        while(!addedUser);
    }

    //Deletes existing file and writes a new one
    public static void DeleteAndWriteFile(ArrayList<Person> lmsDatabase,String fileLocale)
    {

        //Using a new instance of Buffered Writer to add every Person in lmsDatabsse to the new file, and then disposing of resources
        //We are not appending to a file it is being remade
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileLocale)))
        {
            for(Person p : lmsDatabase)
            {
                String newData = String.format("%s-%s-%s-%.2f", p.GetUserID(), p.GetUserName(), p.GetUserAddress(),p.GetUserBalance());
                bw.write(newData);
                bw.newLine();
            }
        }
        catch(IOException e)
        {
            System.out.println("Error writing to file");
        }
    }


    //Removes a user
    public static void RemoveUser(ArrayList<Person> lmsDatabase,String fileLocale )
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the User ID: ");
        String userID = scanner.nextLine();
        //This is the closest to LINQ I see in java, so doing a query to remove uId where uID equals the userID that the user entered
        if(lmsDatabase.removeIf(uId -> uId.GetUserID().equals(userID)))
        {
            //If we find a user, we send the lmsDatabase to the DeleteandWriteFile method to delete the file and rewrite it
            DeleteAndWriteFile(lmsDatabase,fileLocale);
            System.out.println("Successfully removed the user!");
        }
        else
            System.out.println("User not found!");

    }

    public static void PrintOutAllUsers(ArrayList<Person> lmsDatabase)
    {

        //Iterate each Person in lmsDatabase and print to console.
        for(Person p : lmsDatabase)
            System.out.println(p.GetUserID() + " " + p.GetUserName() + " " + p.GetUserAddress() + " " + p.GetUserBalance());
    }

    public static void main(String[] args) throws URISyntaxException
    {

            ArrayList<Person> LMSDatabase = new ArrayList<>();
            //Absolute path to have jar be able to access and to have Main be able to access the file.
            // I spent more time trying to see how to get a jar and Main to access the same file, than I spent making this project, and this was the best I could find
            //Is their a better way to do this? I have never worked with jar files before.
            String fileLocale = "out/artifacts/SDLCAssignmentPart2_jar/users.txt";


        //make sure we can read the file
        boolean readFile =  Startup(LMSDatabase, fileLocale);

        //If we read the file, we initiate the Hamburger Menu
           if(readFile)
           {
               InitiateHamburgerMenuOptions(LMSDatabase, fileLocale);
           }
           else
           {
               System.out.println("Error read file, program terminating");
           }

    }
}