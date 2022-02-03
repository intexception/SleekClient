package me.kansio.client.utils.math;

import java.util.*;
import java.util.concurrent.*;
import java.math.*;
import me.kansio.client.*;
import me.kansio.client.protection.*;
import java.io.*;
import java.security.*;

public class MathUtil
{
    public static int getRandomInteger(final int lIllllllIllIlI, final int lIllllllIlIlll) {
        return (int)(Math.random() * (lIllllllIllIlI - lIllllllIlIlll)) + lIllllllIlIlll;
    }
    
    public static float getRandomInRange(final float lIllllllllIIll, final float lIllllllllIlll) {
        final Random lIllllllllIllI = new Random();
        final float lIllllllllIlIl = lIllllllllIlll - lIllllllllIIll;
        final float lIllllllllIlII = lIllllllllIllI.nextFloat() * lIllllllllIlIl;
        return lIllllllllIlII + lIllllllllIIll;
    }
    
    public static float round(float lIlllllIlllIll, final float lIlllllIlllIlI, final boolean lIlllllIllIllI) {
        if (lIlllllIllIllI) {
            if (ThreadLocalRandom.current().nextBoolean()) {
                lIlllllIlllIll -= lIlllllIlllIll % lIlllllIlllIlI;
            }
            else {
                lIlllllIlllIll += lIlllllIlllIll % lIlllllIlllIlI;
            }
        }
        else {
            lIlllllIlllIll -= lIlllllIlllIll % lIlllllIlllIlI;
        }
        return lIlllllIlllIll;
    }
    
    public static int secRanInt(final int llIIIIIIIIlIIl, final int llIIIIIIIIlIll) {
        final SecureRandom llIIIIIIIIlIlI = new SecureRandom();
        return llIIIIIIIIlIlI.nextInt() * (llIIIIIIIIlIll - llIIIIIIIIlIIl) + llIIIIIIIIlIIl;
    }
    
    public static int getRandomInRange(final int lIlllllllIlIII, final int lIlllllllIIlll) {
        final Random lIlllllllIlIIl = new Random();
        return lIlllllllIlIIl.nextInt(lIlllllllIIlll - lIlllllllIlIII + 1) + lIlllllllIlIII;
    }
    
    public static double setRandom(final double lIllllllIlIIll, final double lIllllllIlIIlI) {
        final Random lIllllllIlIIIl = new Random();
        return lIllllllIlIIll + lIllllllIlIIIl.nextDouble() * (lIllllllIlIIlI - lIllllllIlIIll);
    }
    
    public static float round(final float lIllllllIIIIII, final float lIlllllIllllll) {
        return lIllllllIIIIII % lIlllllIllllll;
    }
    
    public static double getIncre(final double lIllllllIlllll, final double lIllllllIllllI) {
        final double lIlllllllIIIII = 1.0 / lIllllllIllllI;
        return Math.round(lIllllllIlllll * lIlllllllIIIII) / lIlllllllIIIII;
    }
    
    public static float setRandom(final float lIllllllIIlIlI, final float lIllllllIIIllI) {
        final Random lIllllllIIlIII = new Random();
        return lIllllllIIlIlI + lIllllllIIlIII.nextFloat() * (lIllllllIIIllI - lIllllllIIlIlI);
    }
    
    public static double round(final double lIlllllIllIIlI, final int lIlllllIlIlllI) {
        if (lIlllllIlIlllI < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal lIlllllIllIIII = new BigDecimal(lIlllllIllIIlI);
        lIlllllIllIIII = lIlllllIllIIII.setScale(lIlllllIlIlllI, RoundingMode.HALF_UP);
        return lIlllllIllIIII.doubleValue();
    }
    
    public static String checksum() {
        try {
            final CodeSource llIIIIIIlIIIlI = Client.class.getProtectionDomain().getCodeSource();
            final File llIIIIIIlIIIIl = new File(llIIIIIIlIIIlI.getLocation().toURI().getPath());
            final MessageDigest llIIIIIIlIIIII = MessageDigest.getInstance("MD5");
            final String llIIIIIIIlllll = ProtectionUtil.guysidgifsdgihufsdughsfdifsdiuggfdsiufsdgiufsdgufsdguifsdgiusfdgiufdsguisdfguid(llIIIIIIlIIIII, llIIIIIIlIIIIl);
            final FileInputStream llIIIIIIIllllI = new FileInputStream(llIIIIIIlIIIIl);
            final byte[] llIIIIIIIlllIl = new byte[1024];
            int llIIIIIIIlllII = 0;
            while ((llIIIIIIIlllII = llIIIIIIIllllI.read(llIIIIIIIlllIl)) != -1) {
                llIIIIIIlIIIII.update(llIIIIIIIlllIl, 0, llIIIIIIIlllII);
            }
            llIIIIIIIllllI.close();
            final byte[] llIIIIIIIllIll = llIIIIIIlIIIII.digest();
            final StringBuilder llIIIIIIIllIlI = new StringBuilder();
            for (int llIIIIIIlIIIll = 0; llIIIIIIlIIIll < llIIIIIIIllIll.length; ++llIIIIIIlIIIll) {
                llIIIIIIIllIlI.append(Integer.toString((llIIIIIIIllIll[llIIIIIIlIIIll] & 0xFF) + 256, 16).substring(1));
            }
            return String.valueOf(llIIIIIIIllIlI);
        }
        catch (Exception ex) {
            return "none found???";
        }
    }
    
    public static double map(final double lIlllllIlIIlII, final double lIlllllIlIIIll, final double lIlllllIlIIllI, final double lIlllllIlIIIIl) {
        return Math.min(Math.max(lIlllllIlIIllI + lIlllllIlIIlII / lIlllllIlIIIll * (lIlllllIlIIIIl - lIlllllIlIIllI), lIlllllIlIIllI), lIlllllIlIIIIl);
    }
    
    public static double secRanDouble(final double llIIIIIIIIIIII, final double lIllllllllllll) {
        final SecureRandom llIIIIIIIIIIIl = new SecureRandom();
        return llIIIIIIIIIIIl.nextDouble() * (lIllllllllllll - llIIIIIIIIIIII) + llIIIIIIIIIIII;
    }
    
    public static double getRandomInRange(final double llIIIIIIlllIIl, final double llIIIIIIlllIII) {
        final Random llIIIIIIllIlll = new Random();
        final double llIIIIIIllIllI = llIIIIIIlllIII - llIIIIIIlllIIl;
        double llIIIIIIllIlIl = llIIIIIIllIlll.nextDouble() * llIIIIIIllIllI;
        if (llIIIIIIllIlIl > llIIIIIIlllIII) {
            llIIIIIIllIlIl = llIIIIIIlllIII;
        }
        double llIIIIIIllIlII = llIIIIIIllIlIl + llIIIIIIlllIIl;
        if (llIIIIIIllIlII > llIIIIIIlllIII) {
            llIIIIIIllIlII = llIIIIIIlllIII;
        }
        return llIIIIIIllIlII;
    }
}
