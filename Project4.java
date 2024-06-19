/**
 * Class Project4 that does a lot of logic, reads input files, and writes output files. 
 * Uses methods and variables from the Train Class
 *
 * @author Collins Ramsey
 * @version 10/11/2022 @ 4:39 pm
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.Arrays;

public class Project4 {
    //WHEN DID YOU FIRST START WORKING ON THIS ASSIGNMENT (date and time): <---------------
    //ANSWER: 10/11/2022 @ 4:39 pm  <---------------

    //DO NOT ALTER THE MAIN METHOD
    public static void main( String[] args ) {
        Train[] train = readFile( "departures.txt" );
        
        PrintWriter outStream = null;
        try { outStream = new PrintWriter( "arrivals.txt" ); }
        catch ( FileNotFoundException fnf ) { System.exit( -2 ); }
        
        //each trip will arive by the end of next day
        outStream.printf( "%d trains arriving today and tomorrow with total cargo %.2f lbs%n", train.length, Train.getWeightOfAllCargo() );
        writeReport(    0,  759, true, train, outStream );
        writeReport(  800, 1559, true, train, outStream );
        writeReport( 1600, 2359, true, train, outStream );
        
        writeReport(    0,  759, false, train, outStream );
        writeReport(  800, 1559, false, train, outStream );
        writeReport( 1600, 2359, false, train, outStream );
        
        outStream.close();
    }//DO NOT ALTER THE MAIN METHOD
    
    /**
     * Method readFile takes param of String as a file name and reads it's contents.
     * Initializes Array, assigns variables, and returns Array
     *
     * @param  String
     * @return  Array
     */
    public static Train[] readFile( String inputFileName ) {
        Scanner in = null;
        
        try {
            File f = new File(inputFileName);
            in = new Scanner(f);
        }
        catch (FileNotFoundException e) {
            System.out.println("The file you're trying to open cannot be found.");
        }
        
        // initializes train array with size of first number in txt file
        int i = 0;
        Train[] train = new Train[in.nextInt()];
        in.nextLine();
        
        // iterate through the rest of the file 
        while (in.hasNext()) {
            // set element i to the next Train object first
            int k;
            train[i] = new Train(in.next(), in.nextInt(), in.nextInt(), in.nextInt());
            // grab last int and use it in for loop, that int is the number of items in that train object
            int count = in.nextInt();
            in.nextLine();
            
            // loop through items and order them correctly
            for (k = 0; k < count; ++k) {
                int units;
                double weight;
                String cargoType;
                
                units = in.nextInt();
                weight = in.nextDouble();
                cargoType = in.nextLine().trim();
                
                train[i].loadCargo(cargoType, units, weight);
            }
            ++i;
        }
        
        in.close();
        
        return train;
    }
    
    /**
     * Method writeReport takes params start, end, today, t, and out. Uses these variables with 
     * conjunction to methods from Train Class to write a report to a new file. 
     * Does a lot of logic and some math, "I freaking love math"
     *
     * @param  int, boolean, Array, PrintWriter
     * @return  nothing
     */
    public static void writeReport( int start, int end, boolean today, Train[] t, PrintWriter out ) {
        // prints new line after writeReport is called
        out.println("");
        String arriveToday;
        
        // checks boolean and sets arriveToday to either today or tomorrow
        if (today == true) {
            arriveToday = "today";
        }
        else {
            arriveToday = "tomorrow";
        }
        
        out.printf("Arriving %s %04d-%04d:%n", arriveToday, start, end);
        
        // some variables used in the for loop below
        int i;
        int count = 0;
        int crewMembers = 1;
        double totalWeight = 0.0;
        double regularWeight = 0.0;
        
        // loops through the train Array and checks each one to sort it into the correct arrivalTime
        for (i = 0; i < t.length; ++i) {
            if (t[i].getArrivalTime() >= start && t[i].getArrivalTime() <= end && t[i].getSameDayArrival() == today) {
                out.printf("   %04d: The %04d train from %s:%n",t[i].getArrivalTime(), t[i].getDepartureTime(), t[i].getOrigin());
                out.printf("         %-3.2f lbs of %s%n", t[i].getWeightOfTrainCargo(), t[i].getCargo());
                
                // needed assignments in cases of two or more trains in the same arrivalTime frame
                // and to hold the value of the current train weight
                totalWeight += t[i].getWeightOfTrainCargo();
                regularWeight = t[i].getWeightOfTrainCargo();
                
                // checks if more than one train arrives in the same time frame
                // else checks if the one train has a weight of 500 or more
                if (count >= 1) {
                    crewMembers += Math.ceil(totalWeight) / 500;
                    out.printf("Unloading crews needed: %d%n", crewMembers);
                }
                else if (Math.ceil(t[i].getWeightOfTrainCargo()) >= 500) {
                    crewMembers += Math.ceil(t[i].getWeightOfTrainCargo()) / 500;
                    out.printf("Unloading crews needed: %d%n", crewMembers);
                }
                count++;
            }
            else if (t[i].getArrivalTime() >= start && t[i].getArrivalTime() <= end && t[i].getSameDayArrival() == today) {
                out.printf("   %04d: The %04d train from %s:%n",t[i].getArrivalTime(), t[i].getDepartureTime(), t[i].getOrigin());
                out.printf("         %-3.2f lbs of %s%n", t[i].getWeightOfTrainCargo(), t[i].getCargo());
                
                // needed assignments in cases of two or more trains in the same arrivalTime frame
                // and to hold the value of the current train weight
                totalWeight += t[i].getWeightOfTrainCargo();
                regularWeight = t[i].getWeightOfTrainCargo();
                
                // checks if more than one train arrives in the same time frame
                // else checks if the one train has a weight of 500 or more
                if (count >= 1) {
                    crewMembers += Math.ceil(totalWeight) / 500;
                    out.printf("Unloading crews needed: %d%n", crewMembers);
                }
                else if (Math.ceil(t[i].getWeightOfTrainCargo()) >= 500) {
                    crewMembers += Math.ceil(t[i].getWeightOfTrainCargo()) / 500;
                    out.printf("Unloading crews needed: %d%n", crewMembers);
                }
                count++;
            }
            }
        
        // checks if no trains arrived in this time frame
        if (count == 0) {
            out.println("No trains.");
        }
        
        // checks if one train arrived that's below a weight of 500
        if (count == 1) {
            if (regularWeight < 500) {
                out.printf("Unloading crews needed: %d%n", crewMembers);
            }
        }
    }
}