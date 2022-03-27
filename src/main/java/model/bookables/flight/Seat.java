package model.bookables.flight;

import com.mongodb.DBObject;
import model.bookables.Bookable;

/**
 * 
 * @author Jack Hyatt
 */
public class Seat extends Bookable {
    private String whichClass;
    private boolean isBooked;

    /**
     * Constructor for seat
     */
    public Seat(int seatRow, String seatCol) {
        super(seatRow, seatCol);
        whichClass = "First CLass";
        isBooked = false;
    }

    public Seat(DBObject object) {
        super((Integer) object.get("row"), (String) object.get("column"));
        this.whichClass = (String) object.get("whichClass");
        this.isBooked = (Boolean) object.get("isBooked");
    }

    

    /**
     * Method to set the booking of the seat
     * @return true if it booked, false if already booked
     */
    public boolean book() {
        if(isBooked)
            return false;
        isBooked = true;
        return true;
    }

    /**
     * Method to get the seat number
     * @return seat number as an integer
     */
    public int getSeatNum() {
        return this.row;
    }

    /**
     * Method to get seat class
     * @return the class the seat in is as a string
     */
    public String getSeatClass() {
        return this.whichClass;
    }

    /**
     * Method to get if the seat is booked
     * @return the boolean of if the seat is booked
     */
    public boolean getIsBooked() {
        return this.isBooked;
    }
}