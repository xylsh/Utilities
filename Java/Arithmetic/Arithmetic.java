import java.math.BigDecimal;

/**
 * Arithmetic类封装了使用BigDecimal进行浮点数加减乘除的运算。
 */
public class Arithmetic {

    // 默认除法保留的小数位数
    private static final int DEFAULT_NUMBER_OF_DOUBLE_DIGITS = 10;
    private static final int DEFAULT_NUMBER_OF_FLOAT_DIGITS = 5;

    /**
     * 计算两个Float数相加
     * 
     * @param f1
     *            加数1
     * @param f2
     *            加数2
     * @return 2个加数的和
     */
    public static float add(Float f1, Float f2) {
        BigDecimal b1 = new BigDecimal(f1.toString());
        BigDecimal b2 = new BigDecimal(f2.toString());
        return b1.add(b2).floatValue();
    }

    /**
     * 计算两个Float数相减
     * 
     * @param f1
     *            被减数
     * @param f2
     *            减数
     * @return d1-d2的差
     */
    public static float sub(Float f1, Float f2) {
        BigDecimal b1 = new BigDecimal(f1.toString());
        BigDecimal b2 = new BigDecimal(f2.toString());
        return b1.subtract(b2).floatValue();
    }

    /**
     * 两个Float数相乘
     * 
     * @param f1
     *            乘数1
     * @param f2
     *            乘数2
     * @return 2个乘数的积
     */
    public static float mul(Float f1, Float f2) {
        BigDecimal b1 = new BigDecimal(f1.toString());
        BigDecimal b2 = new BigDecimal(f2.toString());
        return b1.multiply(b2).floatValue();
    }

    /**
     * 两个Float数相除
     * 
     * @param f1
     *            被除数
     * @param f2
     *            除数
     * @return Double d1/d2的商
     */
    public static float div(Float f1, Float f2) {
        BigDecimal b1 = new BigDecimal(f1.toString());
        BigDecimal b2 = new BigDecimal(f2.toString());
        return b1.divide(b2, DEFAULT_NUMBER_OF_FLOAT_DIGITS,
                BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 两个Float数相除，保留n位小数
     * 
     * @param f1
     *            被除数
     * @param f2
     *            除数
     * @param n
     *            保留n位小数,n>=0
     * @return Double
     */
    public static float div(Float f1, Float f2, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("小数位数n必须大于等于0.");
        }
        BigDecimal b1 = new BigDecimal(f1.toString());
        BigDecimal b2 = new BigDecimal(f2.toString());
        return b1.divide(b2, n, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 计算两个Double数相加
     * 
     * @param d1
     *            加数1
     * @param d2
     *            加数2
     * @return 2个加数的和
     */
    public static Double add(Double d1, Double d2) {
        BigDecimal b1 = new BigDecimal(d1.toString());
        BigDecimal b2 = new BigDecimal(d2.toString());
        return b1.add(b2).doubleValue();
    }

    /**
     * 计算两个Double数相减
     * 
     * @param d1
     *            被减数
     * @param d2
     *            减数
     * @return d1-d2的差
     */
    public static Double sub(Double d1, Double d2) {
        BigDecimal b1 = new BigDecimal(d1.toString());
        BigDecimal b2 = new BigDecimal(d2.toString());
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 两个Double数相乘
     * 
     * @param d1
     *            乘数1
     * @param d2
     *            乘数2
     * @return 2个乘数的积
     */
    public static Double mul(Double d1, Double d2) {
        BigDecimal b1 = new BigDecimal(d1.toString());
        BigDecimal b2 = new BigDecimal(d2.toString());
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 两个Double数相除
     * 
     * @param d1
     *            被除数
     * @param d2
     *            除数
     * @return Double d1/d2的商
     */
    public static Double div(Double d1, Double d2) {
        BigDecimal b1 = new BigDecimal(d1.toString());
        BigDecimal b2 = new BigDecimal(d2.toString());
        return b1.divide(b2, DEFAULT_NUMBER_OF_DOUBLE_DIGITS,
                BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 两个Double数相除，保留n位小数
     * 
     * @param d1
     *            被除数
     * @param d2
     *            除数
     * @param n
     *            保留n位小数,n>=0
     * @return Double d1/d2的商
     */
    public static Double div(Double d1, Double d2, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("小数位数n必须大于等于0.");
        }
        BigDecimal b1 = new BigDecimal(d1.toString());
        BigDecimal b2 = new BigDecimal(d2.toString());
        return b1.divide(b2, n, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
