/**
 * VIP Seat class - represents premium priced VIP seats
 */
public class VIPSeat extends Seat {
    private static final double VIP_PRICE = 36.0;  // VIP seat price

    public VIPSeat(String seatCode) {
        super(seatCode, VIP_PRICE);
    }

    @Override
    public String getSeatType() {
        return "VIP";
    }

    @Override
    public char getDisplayChar() {
        return booked ? 'X' : 'V';  // VIP seats displayed as V
    }
}
