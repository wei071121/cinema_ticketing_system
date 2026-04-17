import java.io.Serializable;

/**
 * 抽象座位类，定义所有座位的基本属性和方法
 */
public abstract class Seat implements Serializable {
    protected String seatCode;      // 座位代码 (例如: A1, B5)
    protected boolean booked;       // 是否已预订
    protected double price;         // 座位价格

    public Seat(String seatCode, double price) {
        this.seatCode = seatCode;
        this.price = price;
        this.booked = false;
    }

    /**
     * 预订座位
     */
    public boolean book() {
        if (booked) {
            return false;
        }
        this.booked = true;
        return true;
    }

    /**
     * 取消预订座位
     */
    public void cancelBooking() {
        this.booked = false;
    }

    /**
     * 获取座位代码
     */
    public String getSeatCode() {
        return seatCode;
    }

    /**
     * 获取座位价格
     */
    public double getPrice() {
        return price;
    }

    /**
     * 获取座位是否已预订
     */
    public boolean isBooked() {
        return booked;
    }

    /**
     * 获取座位类型 (VIP 或 REGULAR)
     */
    public abstract String getSeatType();

    /**
     * 获取座位显示符号
     */
    public abstract char getDisplayChar();

    /**
     * 将座位状态转换为字符用于保存
     */
    public char toChar() {
        return booked ? 'X' : 'O';
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - ¥%.2f - %s",
                seatCode, getSeatType(), price,
                booked ? "已预订" : "可用");
    }
}
