package cn.tangtj.pishare.dispense;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class PiCompute {

    private static final BigDecimal ONE = BigDecimal.ONE;
    private static final BigDecimal TWO = new BigDecimal(2);
    private static final BigDecimal FOUR = new BigDecimal(4);
    private static final BigDecimal FIVE = new BigDecimal(5);
    private static final BigDecimal SIX = new BigDecimal(6);
    private static final BigDecimal EIGHT = new BigDecimal(8);
    private static final BigDecimal SIXTEEN = new BigDecimal(16);

    BigDecimal calc16dPI(int d) {
        return FOUR.multiply(calc16dSj(d, 1)).add(BigDecimal.valueOf(3)).subtract(TWO.multiply(calc16dSj(d, 4)).divideAndRemainder(ONE)[1]).subtract(calc16dSj(d, 5).divideAndRemainder(ONE)[1]).subtract(calc16dSj(d, 6).divideAndRemainder(ONE)[1]).divideAndRemainder(ONE)[1];
    }

    BigDecimal calc16dSj(int d, int j) {
        int ACCURACY = d + 10;
        BigDecimal part1 = BigDecimal.ZERO;
        BigDecimal part2 = BigDecimal.ZERO;
        for (int k = 0; k <= d; k++) {
            BigDecimal t = EIGHT.multiply(BigDecimal.valueOf(k)).add(BigDecimal.valueOf(j));
            int powTime = d - k;
            BigDecimal result = quickPowMod(powTime, t).divide(t, ACCURACY, RoundingMode.HALF_UP);
            part1 = part1.add(result);
        }
        for (int k = d + 1; k < ACCURACY; k++) {
            part2 = part2.add(
                    ONE.divide(
                            SIXTEEN.pow(k - d)
                                    .multiply(
                                            EIGHT.multiply(
                                                    BigDecimal.valueOf(k)
                                            )
                                                    .add(BigDecimal.valueOf(j)
                                                    )
                                    ), ACCURACY, RoundingMode.HALF_UP));
        }
        BigDecimal r = part1.add(part2);
        return r;
    }


    public BigDecimal quickPowMod(final int d, BigDecimal modNum) {
        BigDecimal sum = BigDecimal.ONE;
        BigDecimal base = SIXTEEN;
        int c = d;
        while (c > 0) {
            //奇数
            if (c % 2 == 1) {
                sum = sum.multiply(base).divideAndRemainder(modNum)[1];
            }
            base = base.multiply(base).divideAndRemainder(modNum)[1];
            c = c / 2;
        }
        return sum;
    }
}
