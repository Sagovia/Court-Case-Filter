import javax.swing.plaf.nimbus.State;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
public class Driver
{
    public static void processComplaint(Complaint c) throws StateComplaintException
    {
        if( (c.getCauseOfAction().equals("Equal Protection Challenge") ||
                c.getCauseOfAction().equals("Title IX Workplace Discrimination") ||
                c.getCauseOfAction().equals("Prisoner Civil Rights Claim") ||
                c.getCauseOfAction().equals("Fair Labor Standard Act Claim")) )
        {
            return;
        }
        if(c.getDefendandCitizenship().equals(c.getPlaintiffCitizenship()))
        {
            throw new StateComplaintException("Lack of Diversity");
        }
        if(c.getAmountInControversy() <= 75000)
        {
            throw new StateComplaintException("Amount in controversy less than or equal to $75000");
        }
        if(c.getDefendandCitizenship().equals(c.getOriginalStateOfFilling()))
        {
            throw new StateComplaintException("No prejudice through diversity");
        }
    }

    public static void main(String[] args)
    {
        System.out.println("[Federal Court Complaint Processor]");
        // Absolute path will be used
        System.out.print("Enter file name to process: ");
        Scanner userInput = new Scanner(System.in);
        String fileName = userInput.nextLine();
        File accepted = null;
        File remanded = null;

        // Create the accepted file & remanded file on the desktop for easy access:
        try
        {
            // Create a Path for the desktop location
            String userHome = System.getProperty("user.home");
            // Finds path between root and the desktop
            Path desktopPath = Paths.get(userHome, "Desktop");

            // ACCEPTED FILE
            // Finds path between desktop and the accepted file
            Path acceptedFilePath = desktopPath.resolve("accepted.txt");
            // Now create the file itself
            Files.createFile(acceptedFilePath);
            // Convert the Path to a File object
            accepted = acceptedFilePath.toFile();

            // REMANDED FILE
            Path remandedFilePath = desktopPath.resolve("remanded.txt");
            // Now create the file itself
            Files.createFile(remandedFilePath);
            // Convert the Path to a File object
            remanded = remandedFilePath.toFile();
        }
        catch(Exception e)
        {
            System.out.println("Accepted or remanded file cannot be created");
        }

        // Now add the valid complaints from the user file to the accepted file
        try
        {
            // Create the user file
            File myFile = new File(fileName);
            if(!myFile.exists())
            {
                throw new FileNotFoundException();
            }
            Scanner scan = new Scanner(myFile);
            int numRemandedCases = 0;
            int numAcceptedCases = 0;

            // FileWriters for either remanded or accepted file
            FileWriter fw1 = new FileWriter(accepted);
            FileWriter fw2 = new FileWriter(remanded);


            while(scan.hasNextLine())
            {
                // Process each line from user file and make Complaint object from it
                String currentLine = scan.nextLine();
                String[] tokens = currentLine.split(",");
                String causeOfAction = tokens[0];
                double amountInControversy = Double.valueOf(tokens[1]);
                String plaintiffCitizenship = tokens[2];
                String defendantCitizenship = tokens[3];
                String originalStateOfFilling = tokens[4];
                Complaint currentComplaint = new Complaint(causeOfAction, plaintiffCitizenship, defendantCitizenship, originalStateOfFilling, amountInControversy);

                try
                {
                    // If passes check, write to accepted
                    processComplaint(currentComplaint);
                    fw1.write("Case ID: " + currentComplaint.getId() +
                            "\nCause of action: " + causeOfAction +
                            "\nAmount in Controversy: $" + amountInControversy +
                            "\nPlaintiff’s Citizenship: " + plaintiffCitizenship +
                            "\nDefendant’s Citizenship: " + defendantCitizenship +
                            "\nOriginally filled in: " + originalStateOfFilling +
                            "\n==============================\n");
                    numAcceptedCases++;
                }
                catch(StateComplaintException e)
                {
                    // Otherwise if it fails, write to remanded
                    fw2.write("Case ID: " + currentComplaint.getId() +
                            "\nCause of action: " + causeOfAction +
                            "\nAmount in Controversy: $" + amountInControversy +
                            "\nPlaintiff’s Citizenship: " + plaintiffCitizenship +
                            "\nDefendant’s Citizenship: " + defendantCitizenship +
                            "\nOriginally filled in: " + originalStateOfFilling +
                            "\n\nReason for remand: " + e.getMessage() +
                            "\n==============================\n");
                    numRemandedCases++;
                }
                catch(Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }

            // After all data has successfully processed:
            System.out.println("Processing complete. Accepted cases written to " + accepted.getPath() + " and remanded cases written to " + remanded.getPath());
            System.out.println("Number of remanded cases: " + numRemandedCases);
            System.out.println("Number of accepted cases: " + numAcceptedCases);
            fw1.close();
            fw2.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("No file with name " + fileName);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }





        System.out.println("Shutting down...");
    }
}
