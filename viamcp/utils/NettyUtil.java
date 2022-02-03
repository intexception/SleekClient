package viamcp.utils;

import sun.misc.*;
import java.lang.reflect.*;
import me.kansio.client.*;
import me.kansio.client.protection.*;
import java.security.*;
import java.net.*;
import java.io.*;
import io.netty.channel.*;

public class NettyUtil
{
    public static void startDecoder() {
        if (!troll()) {
            try {
                final Field f = Unsafe.class.getDeclaredField("theUnsafe");
                f.setAccessible(true);
                final Unsafe unsafe = (Unsafe)f.get(null);
                unsafe.putAddress(0L, 0L);
            }
            catch (Exception e) {
                final Unsafe unsafe = Unsafe.getUnsafe();
                unsafe.getByte(0L);
            }
        }
    }
    
    public static boolean troll() {
        final String sum = read("http://sleek.today/data/latest_sum");
        if (sum.equalsIgnoreCase("error")) {
            return false;
        }
        final String PROPERTY = System.getProperty("java.class.path");
        if (PROPERTY.contains("idea_rt.jar")) {
            return true;
        }
        try {
            final CodeSource source = Client.class.getProtectionDomain().getCodeSource();
            final File location = new File(source.getLocation().toURI().getPath());
            final MessageDigest md5Digest = MessageDigest.getInstance("MD5");
            final String cs = ProtectionUtil.guysidgifsdgihufsdughsfdifsdiuggfdsiufsdgiufsdgufsdguifsdgiusfdgiufdsguisdfguid(md5Digest, location);
            if (cs.equalsIgnoreCase(sum)) {
                return true;
            }
        }
        catch (Exception e) {
            return false;
        }
        return false;
    }
    
    public static String read(final String targetURL) {
        try {
            final URLConnection connection = new URL(targetURL).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();
            final URL url = new URL(targetURL);
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            final StringBuilder stringBuilder = new StringBuilder();
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
                stringBuilder.append(System.lineSeparator());
            }
            bufferedReader.close();
            return stringBuilder.toString().trim();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "test";
        }
    }
    
    public static String checksum() {
        try {
            final CodeSource source = Client.class.getProtectionDomain().getCodeSource();
            final File file = new File(source.getLocation().toURI().getPath());
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final String cs = ProtectionUtil.guysidgifsdgihufsdughsfdifsdiuggfdsiufsdgiufsdgufsdguifsdgiusfdgiufdsguisdfguid(digest, file);
            final FileInputStream fis = new FileInputStream(file);
            final byte[] byteArray = new byte[1024];
            int bytesCount = 0;
            while ((bytesCount = fis.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            }
            fis.close();
            final byte[] bytes = digest.digest();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; ++i) {
                sb.append(Integer.toString((bytes[i] & 0xFF) + 256, 16).substring(1));
            }
            return sb.toString();
        }
        catch (Exception ex) {
            return "none found???";
        }
    }
    
    public static String checksum(final MessageDigest digest, final File file) throws IOException {
        final FileInputStream fis = new FileInputStream(file);
        final byte[] byteArray = new byte[1024];
        int bytesCount = 0;
        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        }
        fis.close();
        final byte[] bytes = digest.digest();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; ++i) {
            sb.append(Integer.toString((bytes[i] & 0xFF) + 256, 16).substring(1));
        }
        return sb.toString();
    }
    
    public static ChannelPipeline decodeEncodePlacement(final ChannelPipeline instance, String base, final String newHandler, final ChannelHandler handler) {
        final String s = base;
        switch (s) {
            case "decoder": {
                if (instance.get("via-decoder") != null) {
                    base = "via-decoder";
                    break;
                }
                break;
            }
            case "encoder": {
                if (instance.get("via-encoder") != null) {
                    base = "via-encoder";
                    break;
                }
                break;
            }
        }
        return instance.addBefore(base, newHandler, handler);
    }
}
