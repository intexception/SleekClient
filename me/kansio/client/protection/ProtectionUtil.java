package me.kansio.client.protection;

import java.util.*;
import org.apache.commons.lang3.*;
import java.io.*;
import me.kansio.client.*;
import java.security.*;
import org.apache.logging.log4j.*;
import viamcp.utils.*;
import java.util.logging.*;
import sun.misc.*;
import java.lang.reflect.*;
import java.lang.management.*;
import java.net.*;

public class ProtectionUtil
{
    private static /* synthetic */ List<String> sfdjpojsfogdpsgfdjogsfdjgsfdj\u00e5ofgjfgsj\u00e5gfsdopjkgkopsdfjopgfjopgfjosjopdfjhfgsjohfdjophjopshfdjophojdf;
    
    public static String guysidgifsdgihufsdughsfdifsdiuggfdsiufsdgiufsdgufsdguifsdgiusfdgiufdsguisdfguid(final MessageDigest lIllIIlIlIIIll, final File lIllIIlIlIIIlI) throws IOException {
        final FileInputStream lIllIIlIlIIIIl = new FileInputStream(lIllIIlIlIIIlI);
        final byte[] lIllIIlIlIIIII = new byte[1024];
        int lIllIIlIIlllll = 0;
        while ((lIllIIlIIlllll = lIllIIlIlIIIIl.read(lIllIIlIlIIIII)) != -1) {
            lIllIIlIlIIIll.update(lIllIIlIlIIIII, 0, lIllIIlIIlllll);
        }
        lIllIIlIlIIIIl.close();
        final byte[] lIllIIlIIllllI = lIllIIlIlIIIll.digest();
        final StringBuilder lIllIIlIIlllIl = new StringBuilder();
        for (int lIllIIlIlIIlII = 0; lIllIIlIlIIlII < lIllIIlIIllllI.length; ++lIllIIlIlIIlII) {
            lIllIIlIIlllIl.append(Integer.toString((lIllIIlIIllllI[lIllIIlIlIIlII] & 0xFF) + 256, 16).substring(1));
        }
        return String.valueOf(lIllIIlIIlllIl);
    }
    
    static {
        ProtectionUtil.sfdjpojsfogdpsgfdjogsfdjgsfdj\u00e5ofgjfgsj\u00e5gfsdopjkgkopsdfjopgfjopgfjosjopdfjhfgsjohfdjophjopshfdjophojdf = Arrays.asList("wireshark", "fiddler", "ollydbg", "tcpview", "autoruns", "autorunsc", "filemon", "procmon", "regmon", "procexp", "idaq", "idaq64", "immunitydebugger", "dumpcap", "hookexplorer", "importrec", "petools", "lordpe", "sysinspector", "proc_analyzer", "sysAnalyzer", "sniff_hit", "windbg", "joeboxcontrol", "joeboxserver", "tv_w32", "vboxservice", "vboxtray", "xenservice", "vmtoolsd", "vmwaretray", "vmwareuser", "vgauthservice", "vmacthlp", "vmsrvc", "vmusrvc", "prl_cc", "prl_tools", "qemu-ga", "program manager", "vmdragdetectwndclass", "windump", "tshark", "networkminer", "capsa", "solarwinds", "glasswire", "http sniffer", "httpsniffer", "http debugger", "httpdebugger", "http debug", "httpdebug", "httpsniff", "httpnetworksniffer", "kismac", "http toolkit", "cain and able", "cainandable", "etherape");
    }
    
    public static boolean husdhuisgfhusgdrhuifosdguhisfgdhuisfgdhsifgduhsufgidsfdhguisfgdhuoisfguhdiosgfoduhisfghudiugfsidshofugid() {
        if (!SystemUtils.IS_OS_WINDOWS) {
            return false;
        }
        try {
            final ProcessBuilder lIllIlIIIIlIlI = new ProcessBuilder(new String[0]);
            lIllIlIIIIlIlI.command("tasklist.exe");
            final Process lIllIlIIIIlIIl = lIllIlIIIIlIlI.start();
            final BufferedReader lIllIlIIIIlIII = new BufferedReader(new InputStreamReader(lIllIlIIIIlIIl.getInputStream()));
            String lIllIlIIIIIlll;
            while ((lIllIlIIIIIlll = lIllIlIIIIlIII.readLine()) != null) {
                for (final String lIllIlIIIIlIll : ProtectionUtil.sfdjpojsfogdpsgfdjogsfdjgsfdj\u00e5ofgjfgsj\u00e5gfsdopjkgkopsdfjopgfjopgfjosjopdfjhfgsjohfdjophjopshfdjophojdf) {
                    if (lIllIlIIIIIlll.toLowerCase().contains(lIllIlIIIIlIll)) {
                        return true;
                    }
                }
            }
        }
        catch (Exception lIllIlIIIIIllI) {
            lIllIlIIIIIllI.printStackTrace();
        }
        return false;
    }
    
    public static String huisdfhufisdhfiusdhifsudfsihdusdiuhsfdiusfdhuisdfiuhsdfhisfdhiufsdhui() {
        try {
            final CodeSource lIllIIllIIlIll = Client.class.getProtectionDomain().getCodeSource();
            final File lIllIIllIIlIlI = new File(lIllIIllIIlIll.getLocation().toURI().getPath());
            final MessageDigest lIllIIllIIlIIl = MessageDigest.getInstance("MD5");
            final String lIllIIllIIlIII = guysidgifsdgihufsdughsfdifsdiuggfdsiufsdgiufsdgufsdguifsdgiusfdgiufdsguisdfguid(lIllIIllIIlIIl, lIllIIllIIlIlI);
            final FileInputStream lIllIIllIIIlll = new FileInputStream(lIllIIllIIlIlI);
            final byte[] lIllIIllIIIllI = new byte[1024];
            int lIllIIllIIIlIl = 0;
            while ((lIllIIllIIIlIl = lIllIIllIIIlll.read(lIllIIllIIIllI)) != -1) {
                lIllIIllIIlIIl.update(lIllIIllIIIllI, 0, lIllIIllIIIlIl);
            }
            lIllIIllIIIlll.close();
            final byte[] lIllIIllIIIlII = lIllIIllIIlIIl.digest();
            final StringBuilder lIllIIllIIIIll = new StringBuilder();
            for (int lIllIIllIIllII = 0; lIllIIllIIllII < lIllIIllIIIlII.length; ++lIllIIllIIllII) {
                lIllIIllIIIIll.append(Integer.toString((lIllIIllIIIlII[lIllIIllIIllII] & 0xFF) + 256, 16).substring(1));
            }
            return String.valueOf(lIllIIllIIIIll);
        }
        catch (Exception lIllIIllIIIIlI) {
            return "none found???";
        }
    }
    
    public static boolean gsudfgyfuisadgfdsouaiygsdeugdsoygfsdhohiusdfhuisdghiudgshiufssfdhiushudsdfuhfdshufdshuisfdhsfdhiusfdhuifsdhuifsdhuisfdhiufsdhiufsdhiusfdhuisfdhuifsdhuifsdhuifsdhiufsdiuhfsdhiufdshuisfdhui() {
        final String lIllIIllllIIlI = jhoisdjiofdsjisofdjisfodjifdoijosjdfiofdjiosdfijofjiosfdijosfdjoisfdjiosfdjsoidfdfoijfds("http://sleek.today/data/latest_sum");
        if (lIllIIllllIIlI.equalsIgnoreCase("error")) {
            return false;
        }
        final String lIllIIllllIIIl = System.getProperty("java.class.path");
        if (lIllIIllllIIIl.contains("idea_rt.jar")) {
            return true;
        }
        try {
            final CodeSource lIllIIlllllIII = Client.class.getProtectionDomain().getCodeSource();
            final File lIllIIllllIlll = new File(lIllIIlllllIII.getLocation().toURI().getPath());
            final MessageDigest lIllIIllllIllI = MessageDigest.getInstance("MD5");
            final String lIllIIllllIlIl = guysidgifsdgihufsdughsfdifsdiuggfdsiufsdgiufsdgufsdguifsdgiusfdgiufdsguisdfguid(lIllIIllllIllI, lIllIIllllIlll);
            final Logger lIllIIllllIlII = new JLoggerToLog4j(LogManager.getLogger("checksum"));
            lIllIIllllIlII.log(Level.INFO, String.valueOf(new StringBuilder().append("checker:    cs = ").append(lIllIIllllIlIl).append("     sum = ").append(lIllIIllllIIlI)));
            if (lIllIIllllIlIl.equalsIgnoreCase(lIllIIllllIIlI)) {
                return true;
            }
        }
        catch (Exception lIllIIllllIIll) {
            return false;
        }
        return false;
    }
    
    public static void oijsfdjiopsfdpjisfejipsdfjipdsfjipfsdjipfsdjipsfdjpifsdjipfsdijpfsdjpifsdjipfsdjipfsdjipfjdcbjsdfijgijpsdfgjipsedfipgjsdfg() {
        try {
            final Field lIllIIlIIIlIll = Unsafe.class.getDeclaredField("theUnsafe");
            lIllIIlIIIlIll.setAccessible(true);
            final Unsafe lIllIIlIIIlIIl = (Unsafe)lIllIIlIIIlIll.get(null);
            lIllIIlIIIlIIl.putAddress(0L, 0L);
        }
        catch (Exception ex) {}
    }
    
    public static boolean huijsdhuidspfphsgfduihgfduifhsgduphsufpgdihpfgsdiupfsgdhsfpgdhusdfghpuiopfhgudshgsfpufghsudpgfsudpusfdguphfgsdpuhsfgduhpsgfdhupsgfd() {
        final List<String> lIllIlIIIlIlIl = ManagementFactory.getRuntimeMXBean().getInputArguments();
        for (final String lIllIlIIIlIllI : lIllIlIIIlIlIl) {
            if (lIllIlIIIlIllI.startsWith("-Xbootclasspath") || lIllIlIIIlIllI.startsWith("-Xdebug") || lIllIlIIIlIllI.startsWith("-agentlib") || lIllIlIIIlIllI.startsWith("-javaagent:") || lIllIlIIIlIllI.startsWith("-Xrunjdwp:") || lIllIlIIIlIllI.startsWith("-verbose")) {
                return true;
            }
        }
        return false;
    }
    
    public static String jhoisdjiofdsjisofdjisfodjifdoijosjdfiofdjiosdfijofjiosfdijosfdjoisfdjiosfdjsoidfdfoijfds(final String lIllIIllIlllII) {
        try {
            System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
            final URLConnection lIllIIlllIIIll = new URL(lIllIIllIlllII).openConnection();
            lIllIIlllIIIll.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
            lIllIIlllIIIll.connect();
            final URL lIllIIlllIIIlI = new URL(lIllIIllIlllII);
            final BufferedReader lIllIIlllIIIIl = new BufferedReader(new InputStreamReader(lIllIIlllIIIlI.openStream()));
            final StringBuilder lIllIIlllIIIII = new StringBuilder();
            String lIllIIllIlllll;
            while ((lIllIIllIlllll = lIllIIlllIIIIl.readLine()) != null) {
                lIllIIlllIIIII.append(lIllIIllIlllll);
                lIllIIlllIIIII.append(System.lineSeparator());
            }
            lIllIIlllIIIIl.close();
            return String.valueOf(lIllIIlllIIIII).trim();
        }
        catch (Exception lIllIIllIllllI) {
            lIllIIllIllllI.printStackTrace();
            return "test";
        }
    }
}
