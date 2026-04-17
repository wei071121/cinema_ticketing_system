/**
 * 普通座位类 - 代表普通价格的座位
 */
public class RegularSeat extends Seat {
    private static final double REGULAR_PRICE = 18.0;  // 普通座位价格

    public RegularSeat(String seatCode) {
        super(seatCode, REGULAR_PRICE);
    }

    @Override
    public String getSeatType() {
        return "普通座位";
    }

    @Override
    public char getDisplayChar() {
        return booked ? 'X' : 'O';
    }
}
