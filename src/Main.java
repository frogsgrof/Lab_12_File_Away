import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardOpenOption.CREATE;
import javax.swing.JFileChooser;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        JFileChooser chooser = new JFileChooser(); // create instance of jFileChooser
        File selectedFile; // declare file
        String rec; // declare text string from file

        try {
            // sets the working directory to home
            File workingDirectory = new File(System.getProperty("user.home"));
            chooser.setCurrentDirectory(workingDirectory);

            // if they pick a valid file
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile(); // this is the file to read
                Path file = selectedFile.toPath(); // set the path to that file

                // instantiating buffered input stream and reader
                InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                // list for counting words
                ArrayList<String> words = new ArrayList<>();
                int line = 0; // line counter
                int charCount = 0; // counts total characters
                while (reader.ready()) {
                    rec = reader.readLine(); // loop over text file, reading each line to the string

                    // separate words in line
                    String temp = "";
                    for (int i = 0; i < rec.length(); i++) {
                        charCount++; // counts a character

                        // if the character is a space, the last word is over
                        if (rec.charAt(i) == ' ') {
                            words.add(temp); // dump temp into list
                            temp = ""; // empty temp
                        } else {
                            // if that character is not a space, add it to temp
                            temp = temp + rec.charAt(i);
                        }
                    }

                    // if there were double spaces in the text file, there are now empty strings in the ArrayList
                    // so we have to check the entire list and remove any empty strings:
                    for (int i = 0; i < words.size(); i++) {
                        if (words.get(i).equals("")) {
                            words.remove(i);
                        }
                    }
                    line++; // up line counter

                    // print the string storing the current line with the line number
                    System.out.printf("\nLine %2d:   %-60s ", line, rec);
                }

                // print number of words and characters
                System.out.println("\n\nWord count: " + words.size());
                System.out.println("Character count: " + charCount);

                reader.close(); // close the reader
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}