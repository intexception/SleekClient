package me.kansio.client.utils.chat;

import java.util.*;

public class NameUtil
{
    private static final /* synthetic */ Random random;
    
    public static String generateName() {
        String llIllIIIllIl = "";
        final int llIllIIIllII = (int)Math.round(Math.random() * 4.0) + 5;
        final String llIllIIIlIll = "aeiouy";
        final String llIllIIIlIlI = "bcdfghklmnprstvwz";
        int llIllIIIlIIl = 0;
        int llIllIIIlIII = 0;
        String llIllIIIIlll = "blah";
        for (int llIllIIllIIl = 0; llIllIIllIIl < llIllIIIllII; ++llIllIIllIIl) {
            String llIllIIllIlI = llIllIIIIlll;
            if ((NameUtil.random.nextBoolean() || llIllIIIlIIl == 1) && llIllIIIlIII < 2) {
                while (llIllIIllIlI.equals(llIllIIIIlll)) {
                    final int llIllIIlllII = (int)(Math.random() * "aeiouy".length() - 1.0);
                    llIllIIllIlI = "aeiouy".substring(llIllIIlllII, llIllIIlllII + 1);
                }
                llIllIIIlIIl = 0;
                ++llIllIIIlIII;
            }
            else {
                while (llIllIIllIlI.equals(llIllIIIIlll)) {
                    final int llIllIIllIll = (int)(Math.random() * "bcdfghklmnprstvwz".length() - 1.0);
                    llIllIIllIlI = "bcdfghklmnprstvwz".substring(llIllIIllIll, llIllIIllIll + 1);
                }
                ++llIllIIIlIIl;
                llIllIIIlIII = 0;
            }
            llIllIIIIlll = llIllIIllIlI;
            llIllIIIllIl = llIllIIIllIl.concat(llIllIIllIlI);
        }
        final int llIllIIIIllI = (int)Math.round(Math.random() * 2.0);
        if (llIllIIIIllI == 1) {
            llIllIIIllIl = String.valueOf(new StringBuilder().append(llIllIIIllIl.substring(0, 1).toUpperCase()).append(llIllIIIllIl.substring(1)));
        }
        else if (llIllIIIIllI == 2) {
            for (int llIllIIllIII = 0; llIllIIllIII < llIllIIIllII; ++llIllIIllIII) {
                if ((int)Math.round(Math.random() * 3.0) == 1) {
                    llIllIIIllIl = String.valueOf(new StringBuilder().append(String.valueOf(llIllIIIllIl.substring(0, llIllIIllIII))).append(llIllIIIllIl.substring(llIllIIllIII, llIllIIllIII + 1).toUpperCase()).append((llIllIIllIII == llIllIIIllII) ? "" : llIllIIIllIl.substring(llIllIIllIII + 1)));
                }
            }
        }
        final int llIllIIIIlIl = (int)Math.round(Math.random() * 3.0) + 1;
        final int llIllIIIIlII = (int)Math.round(Math.random() * 3.0);
        final boolean llIllIIIIIll = NameUtil.random.nextBoolean();
        if (llIllIIIIIll) {
            if (llIllIIIIlIl == 1) {
                final int llIllIIlIlll = (int)Math.round(Math.random() * 9.0);
                llIllIIIllIl = llIllIIIllIl.concat(Integer.toString(llIllIIlIlll));
            }
            else if (llIllIIIIlII == 0) {
                final int llIllIIlIlIl = (int)(Math.round(Math.random() * 8.0) + 1L);
                for (int llIllIIlIllI = 0; llIllIIlIllI < llIllIIIIlIl; ++llIllIIlIllI) {
                    llIllIIIllIl = llIllIIIllIl.concat(Integer.toString(llIllIIlIlIl));
                }
            }
            else if (llIllIIIIlII == 1) {
                final int llIllIIlIIll = (int)(Math.round(Math.random() * 8.0) + 1L);
                llIllIIIllIl = llIllIIIllIl.concat(Integer.toString(llIllIIlIIll));
                for (int llIllIIlIlII = 1; llIllIIlIlII < llIllIIIIlIl; ++llIllIIlIlII) {
                    llIllIIIllIl = llIllIIIllIl.concat("0");
                }
            }
            else if (llIllIIIIlII == 2) {
                int llIllIIlIIIl = (int)(Math.round(Math.random() * 8.0) + 1L);
                llIllIIIllIl = llIllIIIllIl.concat(Integer.toString(llIllIIlIIIl));
                for (int llIllIIlIIlI = 0; llIllIIlIIlI < llIllIIIIlIl; ++llIllIIlIIlI) {
                    llIllIIlIIIl = (int)Math.round(Math.random() * 9.0);
                    llIllIIIllIl = llIllIIIllIl.concat(Integer.toString(llIllIIlIIIl));
                }
            }
            else if (llIllIIIIlII == 3) {
                int llIllIIlIIII;
                for (llIllIIlIIII = 99999; Integer.toString(llIllIIlIIII).length() != llIllIIIIlIl; llIllIIlIIII = (int)(Math.round(Math.random() * 12.0) + 1L), llIllIIlIIII = (int)Math.pow(2.0, llIllIIlIIII)) {}
                llIllIIIllIl = llIllIIIllIl.concat(Integer.toString(llIllIIlIIII));
            }
        }
        final boolean llIllIIIIIlI = !llIllIIIIIll && NameUtil.random.nextBoolean();
        if (llIllIIIIIlI) {
            for (String llIllIIIlllI = llIllIIIllIl; llIllIIIllIl.equals(llIllIIIlllI); llIllIIIllIl = llIllIIIllIl.replace("l", "7"), llIllIIIllIl = llIllIIIllIl.replace("L", "7")) {
                final int llIllIIIllll = (int)Math.round(Math.random() * 7.0);
                if (llIllIIIllll == 0) {
                    llIllIIIllIl = llIllIIIllIl.replace("a", "4");
                    llIllIIIllIl = llIllIIIllIl.replace("A", "4");
                }
                if (llIllIIIllll == 1) {
                    llIllIIIllIl = llIllIIIllIl.replace("e", "3");
                    llIllIIIllIl = llIllIIIllIl.replace("E", "3");
                }
                if (llIllIIIllll == 2) {
                    llIllIIIllIl = llIllIIIllIl.replace("g", "6");
                    llIllIIIllIl = llIllIIIllIl.replace("G", "6");
                }
                if (llIllIIIllll == 3) {
                    llIllIIIllIl = llIllIIIllIl.replace("h", "4");
                    llIllIIIllIl = llIllIIIllIl.replace("H", "4");
                }
                if (llIllIIIllll == 4) {
                    llIllIIIllIl = llIllIIIllIl.replace("i", "1");
                    llIllIIIllIl = llIllIIIllIl.replace("I", "1");
                }
                if (llIllIIIllll == 5) {
                    llIllIIIllIl = llIllIIIllIl.replace("o", "0");
                    llIllIIIllIl = llIllIIIllIl.replace("O", "0");
                }
                if (llIllIIIllll == 6) {
                    llIllIIIllIl = llIllIIIllIl.replace("s", "5");
                    llIllIIIllIl = llIllIIIllIl.replace("S", "5");
                }
                if (llIllIIIllll == 7) {}
            }
        }
        final int llIllIIIIIIl = (int)Math.round(Math.random() * 8.0);
        if (llIllIIIIIIl == 3) {
            llIllIIIllIl = "xX".concat(llIllIIIllIl).concat("Xx");
        }
        else if (llIllIIIIIIl == 4) {
            llIllIIIllIl = llIllIIIllIl.concat("LP");
        }
        else if (llIllIIIIIIl == 5) {
            llIllIIIllIl = llIllIIIllIl.concat("HD");
        }
        return llIllIIIllIl;
    }
    
    static {
        random = new Random();
    }
}
