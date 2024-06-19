/**
 * Class Train that does some logic in the 2nd constructor, sets variables and uses methods for Class Project 4
 *
 * @author Collins Ramsey
 * @version 10/11/2022 @ 4:39 pm
 */
public class Train extends TestTrain {
    // instance variables
    private String origin;
    private int departureTime;
    private int arrivalTime;
    private boolean sameDayArrival;
    private String cargo;
    private double weightOfTrainCargo;
    private static double weightOfAllCargo;

    /**
     * Standard Constructor for objects of class Train
     * 
     * @param none
     */
    public Train() {
        // initialise instance variables
        this.origin = "";
        this.departureTime = 0;
        this.arrivalTime = 0;
        this.sameDayArrival = false;
        this.cargo = "";
        this.weightOfTrainCargo = 0.0;
        this.weightOfAllCargo = 0.0;
    }
    
    /**
     * 2nd Constructor for objects of class Train
     * 
     * @param String, int
     */
    public Train(String origin, int departure, int stops, int duration) {
        // initialise instance variables
        this.origin = origin;
        this.departureTime = departure;
        calculateArrivalTime(stops, duration);
        this.cargo = "";
        this.weightOfTrainCargo = 0.0;
    }

    /**
     * Method calculateArrivalTime takes in 2 params stops and duration and is used to assign sameDayArrival
     * and arrivalTime. Does some math, "I love math"
     *
     * @param  int
     * @return  nothing
     */
    public void calculateArrivalTime(int stops, int duration) {
        stops = stops * 10;
        duration = duration + stops;
        int calculateDuration = (100 * (duration / 60)) + duration % 60;
        calculateDuration += getDepartureTime();
        
        if (calculateDuration % 100 >= 60) {
            calculateDuration = (((calculateDuration % 100) - 60) + 100) + ((calculateDuration / 100) * 100);
        }
        // this if statement assumes no number will exceed the range of 47 hours and 59 minutes
        if (calculateDuration >= 0000 && calculateDuration <= 2359) {
            this.sameDayArrival = true;
            this.arrivalTime = calculateDuration;
        }
        else if (calculateDuration > 2359 && calculateDuration <= 4759) {
            this.sameDayArrival = false;
            this.arrivalTime = calculateDuration - 2400;
        }
    }
    
    /**
     * Method getOrigin used as getter for String origin
     *
     * @param  none
     * @return  String
     */
    public String getOrigin() {
        return this.origin;
    }
    
    /**
     * Method getArrivalTime used as getter for int arrivalTime
     *
     * @param  none
     * @return  int
     */
    public int getArrivalTime() {
        return this.arrivalTime;
    }
    
    /**
     * Method getDepartureTime used as getter for int departureTime
     *
     * @param  none
     * @return  int
     */
    public int getDepartureTime() {
        return this.departureTime;
    }
    
    /**
     * Method getSameDayArrival used as getter for blooean sameDayArrival
     *
     * @param  none
     * @return  boolean
     */
    public boolean getSameDayArrival() {
        return this.sameDayArrival;
    }
    
    /**
     * Method getCargo used as getter for String cargo
     *
     * @param  none
     * @return  String
     */
    public String getCargo() {
        return this.cargo;
    }
    
    /**
     * Method getWeightOfTrainCargo used as getter for double weightOfTrainCargo
     *
     * @param  none
     * @return  double
     */
    public double getWeightOfTrainCargo() {
        return this.weightOfTrainCargo;
    }
    
    /**
     * Method getWeightOfAllCargo used as getter for double weightOfAllCargo
     *
     * @param  none
     * @return  double
     */
    public static double getWeightOfAllCargo() {
        return weightOfAllCargo;
    }
    
    /**
     * Method loadCargo takes 3 params, cargoType, units, and weightPerItem and is used to assign
     * cargo, weightOfTrainCargo, and weightOfAllCargo. Cargo assignment gets concatinated "and" if there is more
     * than one cargoType (very tricky condition).
     *
     * @param  String, int, double
     * @return  nothing
     */
    public void loadCargo(String cargoType, int units, double weightPerItem) {
        String cTypes = units + " " + cargoType;
        
        if (this.cargo.equals("")) {
            this.cargo += cTypes;
        }
        else
        this.cargo += " and " + cTypes;
        
        this.weightOfTrainCargo += (weightPerItem * units);
        this.weightOfAllCargo += (weightPerItem * units);
    }
}