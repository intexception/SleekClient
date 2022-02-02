package optifine;

import java.math.*;

public class MathUtils
{
    public static int getAverage(final int[] p_getAverage_0_) {
        if (p_getAverage_0_.length <= 0) {
            return 0;
        }
        int i = 0;
        for (int j = 0; j < p_getAverage_0_.length; ++j) {
            final int k = p_getAverage_0_[j];
            i += k;
        }
        final int l = i / p_getAverage_0_.length;
        return l;
    }
    
    public static double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
