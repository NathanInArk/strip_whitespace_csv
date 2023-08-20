import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("This program removes whitespace from files that end in .CSV. ");
        System.out.print("Changes will be saved to a new file in the same folder, the original one will be untouched. ");

        // Using a scanner to get the user to provide the path for the file
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the path to the CSV file, if the file is in the same folder you can enter just the name: ");
        String filePath = scanner.nextLine();
        //Checking that the file exist
        File csvFile = new File(filePath);
        if (!csvFile.exists()) {
            System.out.println("File does not exist.");
            return;
        }
        //calling modules from the File class to get the parent folder and name of the file
        String parentFolder = csvFile.getParent();
        String fileName = csvFile.getName();
        String newFilePath;
        //making a new file in the same folder to write the trimmed CSV to
        if (parentFolder != null && new File(parentFolder, fileName).exists()) {
            newFilePath = parentFolder + File.separator + fileName.replace(".csv", "-trimmed.csv");
        } else {
            newFilePath = filePath.replace(".csv", "-trimmed.csv");
        }
        //checking that the file is a csv
        if (!fileName.endsWith(".csv")) {
            System.out.println("Invalid file format. Only CSV files are supported.");
            return;
        }
        // Initizing a variable to check track of the number of changes
        int changesCount = 0;
        //try block to read from the file and write to the new file
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(newFilePath))) {
            //casting the line of the file into a string
            String line;
            // looping through the document line by line until it reaches the end of it
            while ((line = reader.readLine()) != null) {
                StringBuilder row_of_csv = new StringBuilder();
                // splitting the line via the commas
                String[] cell_by_row = line.split("[,]",0);
                // lopping over the new string array to trim whitespace from each cell
                for (String cell : cell_by_row)
                {
                String trimmedLine = cell.trim();
                //adding the trimmed cell to a string separated by commas
                row_of_csv.append(trimmedLine +",");
                }
                //incrementing the changes counter
            changesCount += line.length() - row_of_csv.length();
            //appending the built string of trimmed cells to the document

            writer.append(row_of_csv);
            //adding a new line after it to keep the format of a CSV
            writer.newLine();
            }
        //Using a generic error incase something fails
        } catch (IOException e) {
            System.out.println("An error occurred while processing the file.");
            return;
        }
        //out put to user the number of changes and where the new file is
        System.out.println("Trimmed CSV file created successfully.");
        System.out.println("Path to the new file: " + newFilePath);
        System.out.println("Number of changes made: " + changesCount);
    }
}
