import java.io.Serializable;

/**
 * Abstract Seat class defining basic properties and methods for all seats
 */
public abstract class Seat implements Serializable {
    protected String seatCode;      // Seat code (e.g., A1, B5)
    protected boolean booked;       // Whether seat is booked
    protected double price;         // Seat price

    public Seat(String seatCode, double price) {
        this.seatCode = seatCode;
        this.price = price;
        this.booked = false;
    }

    /**
     * Book the seat
     */
    public boolean book() {
        if (booked) {
            return false;
        }
        this.booked = true;
        return true;
    }

    /**
     * Cancel seat booking
     */
    public void cancelBooking() {
        this.booked = false;
    }

    /**
     * Get the seat code
     */
    public String getSeatCode() {
        return seatCode;
    }

    /**
     * Get seat price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Check if the seat is booked
     */
    public boolean isBooked() {
        return booked;
    }

    /**
     * Get the seat type (VIP or REGULAR)
     */
    public abstract String getSeatType();

    /**
     * Get the character to display for this seat
     */
    public abstract char getDisplayChar();

    /**
     * Convert seat state to character for file saving
     */
    public char toChar() {
        return booked ? 'X' : 'O';
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - $%.2f - %s",
                seatCode, getSeatType(), price,
                booked ? "Booked" : "Available");
    }
}
