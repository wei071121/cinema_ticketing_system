/**
 * Regular Seat class - represents standard price seats
 */
public class RegularSeat extends Seat {
    private static final double REGULAR_PRICE = 18.0;  // Regular seat price

    public RegularSeat(String seatCode) {
        super(seatCode, REGULAR_PRICE);
    }

    @Override
    public String getSeatType() {
        return "Regular";
    }

    @Override
    public char getDisplayChar() {
        return booked ? 'X' : 'O';
    }
}
