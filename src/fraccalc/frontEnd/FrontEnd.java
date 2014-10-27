package fraccalc.frontEnd;

import fraccalc.backEnd.Parser;
import fraccalc.backEnd.Fraction;
import static fraccalc.backEnd.Parser.parse;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Handles user I/O
 *
 * @author bendpalias
 */
public class FrontEnd {
/**
 * main method for the class
 * @param args command-line arguments
 */
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        String input;
        Fraction output;
        String prevAns = "0";
        while (true) {
            System.out.println("Please enter an expression: ");
            input = console.nextLine();
            input = input.replaceAll("ans", prevAns);
            input = input.trim();
            if (input.compareToIgnoreCase("quit") == 0 || input.compareToIgnoreCase("exit") == 0) {
                System.out.println("Bye!");
                console.close();
                System.exit(0);
            } else if (input.compareToIgnoreCase("test") == 0) {
                Parser.test();
            } else if(input.compareToIgnoreCase("help") == 0){
                help();
            }
                    else{
                output = parse(input);
                if (output != null) {
                    prevAns = output.toString();
                    if (output != null) {
                        System.out.println(output.toString());
                    }
                }
            }

        }

    }
    /**
     * Prints out the help file, or prints out an error if it can't find the help file.
     */
    
    public static void help(){
        Scanner file;
        try {
            file = new Scanner(new File("help.txt"));
        } catch (FileNotFoundException ex) {
            System.out.println("Can't find the help file! Are you using the jar?");
            return;
        }
        while(file.hasNext()){
            System.out.println(file.nextLine());
        }
    }
}
