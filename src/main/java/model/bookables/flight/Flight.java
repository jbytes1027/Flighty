package model.bookables.flight;

import com.mongodb.DBObject;
import model.bookables.Bookable;
import model.bookables.TravelObject;
import utils.TimeUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jack Hyatt
 */
public class Flight extends TravelObject {
    private static final TimeUtils timeUtils = TimeUtils.getInstance();
    private String airportFrom;
    private String airportTo;
    private String cityFrom;
    private String cityTo;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private double startX;
    private double startY;
    private double stopX;
    private double stopY;

    /**
     * Constructor for Bookables.Flight.Flight
     */
    public Flight(LocalDateTime departureTime, LocalDateTime arrivalTime, String airportFrom,
            String airportTo) {
        super();
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.airportFrom = airportFrom;
        this.airportTo = airportTo;
    }

    @SuppressWarnings("unchecked")
    public Flight(DBObject object) {
        super(object);
        this.departureTime = timeUtils.genDateTime(object.get("date_depart") + " "
                + ((String) object.get("time_depart")).substring(0, 5));
        this.arrivalTime = timeUtils.genDateTime(object.get("date_arrive") + " "
                + ((String) object.get("time_arrive")).substring(0, 5));
        this.startX = (double) object.get("from_x");
        this.startY = (double) object.get("from_y");
        this.stopX = (double) object.get("to_x");
        this.stopY = (double) object.get("to_y");
        this.bookables = new ArrayList<>();
        for (DBObject obj : (List<DBObject>) object.get("seats"))
            bookables.add(new Seat(obj, this));
        this.airportFrom = (String) object.get("airport_code_from");
        this.airportTo = (String) object.get("airport_code_to");
        this.cityFrom = (String) object.get("city_from");
        this.cityTo = (String) object.get("city_to");
    }

    public double distanceToDestination(Flight flight) {
        double a2 = Math.pow(this.startX - flight.stopX, 2);
        double b2 = Math.pow(this.startY - flight.stopY, 2);

        return Math.sqrt(a2 + b2);
    }

    public String getAirportFrom() {
        return airportFrom;
    }

    public String getAirportTo() {
        return airportTo;
    }

    public int getNumSeats() {
        return bookables.size();
    }

    public int getNumAvailableSeats() {
        int num = 0;

        for (Seat seat : getOptions())
            if (!seat.getIsBooked())
                num++;

        return num;
    }

    public List<Seat> getAvailableOptions() {
        List<Seat> seats = new ArrayList<>();
        for (Seat seat : getOptions())
            if (!seat.getIsBooked())
            seats.add(seat);

        return seats;
    }

    public List<Seat> getOptions() {
        List<Seat> seats = new ArrayList<>();
        for (Bookable bookable : bookables)
            seats.add((Seat) bookable);

        return seats;
    }

    public double getBusinessPrice() {
        int i = 0;
        while(true) {
            Seat s = (Seat)bookables.get(i);
            if (s.getSeatClass().equals("Business"))
                return s.getPrice();
            if(i == bookables.size())
                return 0;
            i=i+1;
        }
    }

    /**
     * 
     * @return the departure time of the flight as a LocalDate data type
     */
    public LocalDateTime getDepartureTime() {
        return this.departureTime;
    }

    /**
     * 
     * @return the arival time of the flight as a LocalDate data type
     */
    public LocalDateTime getArrivalTime() {
        return this.arrivalTime;
    }

    public Duration getTravelTime() {
        return Duration.between(getDepartureTime(), getArrivalTime());
    }

    @Override
    public String toString() {
        return "{" + "\"airportFrom\": \"" + airportFrom + "\", "
                + "\"airportTo\": \"" + airportTo + "\", "
                + "\"cityFrom\": \"" + cityFrom + "\", "
                + "\"cityTo\": \"" + cityTo + "\", "
                + "\"departureTime\": \"" + departureTime.toString() + "\", "
                + "\"arrivalTime\": \"" + arrivalTime.toString() + "\", "
                + "\"startX\": " + startX + ", "
                + "\"startY\": " + startY + ", "
                + "\"stopX\": " + stopX + ", "
                + "\"stopY\": " + stopY + ", "
                + "\"id\": \"" + id + "\", "
                + "\"bookables\": " + bookables + ", "
                + "\"company\": \"" + company + "\", "
                + "\"rating\": " + rating + ", "
                + "\"features\": " + stringArray(features.toArray()) + ", "
                + "\"filters\": " + stringArray(filters.toArray()) + "}";
    }

    public String stringArray(Object[] arr) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for(Object object : arr) {
            builder.append("\"").append(object.toString()).append("\", ");
        }
        return builder.delete(builder.length() - 2, builder.length()).append("]").toString();
    }
}
