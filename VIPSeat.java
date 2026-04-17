/**
 * VIP座位类 - 代表高价格的VIP座位
 */
public class VIPSeat extends Seat {
    private static final double VIP_PRICE = 36.0;  // VIP座位价格

    public VIPSeat(String seatCode) {
        super(seatCode, VIP_PRICE);
    }

    @Override
    public String getSeatType() {
        return "VIP座位";
    }

    @Override
    public char getDisplayChar() {
        return booked ? 'X' : 'V';  // VIP座位用 V 表示
    }
}
