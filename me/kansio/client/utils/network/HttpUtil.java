package me.kansio.client.utils.network;

import java.util.*;
import me.kansio.client.*;
import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.security.*;

public class HttpUtil
{
    public static String fetch(final String llllllllllllllllllllIlIIllIlIIIl, final String llllllllllllllllllllIlIIllIllIII, final String llllllllllllllllllllIlIIllIIllll, final Map<String, String> llllllllllllllllllllIlIIllIlIllI) throws IOException {
        final URL llllllllllllllllllllIlIIllIlIlIl = new URL(llllllllllllllllllllIlIIllIllIII);
        final HttpURLConnection llllllllllllllllllllIlIIllIlIlII = (HttpURLConnection)llllllllllllllllllllIlIIllIlIlIl.openConnection();
        llllllllllllllllllllIlIIllIlIlII.setConnectTimeout(120000);
        llllllllllllllllllllIlIIllIlIlII.setReadTimeout(120000);
        if (llllllllllllllllllllIlIIllIlIIIl != null) {
            llllllllllllllllllllIlIIllIlIlII.setRequestMethod(llllllllllllllllllllIlIIllIlIIIl);
        }
        if (llllllllllllllllllllIlIIllIlIllI != null) {
            for (final String llllllllllllllllllllIlIIllIlllII : llllllllllllllllllllIlIIllIlIllI.keySet()) {
                llllllllllllllllllllIlIIllIlIlII.addRequestProperty(llllllllllllllllllllIlIIllIlllII, llllllllllllllllllllIlIIllIlIllI.get(llllllllllllllllllllIlIIllIlllII));
            }
        }
        if (llllllllllllllllllllIlIIllIIllll != null) {
            llllllllllllllllllllIlIIllIlIlII.setDoOutput(true);
            final OutputStream llllllllllllllllllllIlIIllIllIll = llllllllllllllllllllIlIIllIlIlII.getOutputStream();
            llllllllllllllllllllIlIIllIllIll.write(llllllllllllllllllllIlIIllIIllll.getBytes());
            llllllllllllllllllllIlIIllIllIll.flush();
            llllllllllllllllllllIlIIllIllIll.close();
        }
        final InputStream llllllllllllllllllllIlIIllIlIIll = llllllllllllllllllllIlIIllIlIlII.getInputStream();
        final String llllllllllllllllllllIlIIllIlIIlI = streamToString(llllllllllllllllllllIlIIllIlIIll);
        if (llllllllllllllllllllIlIIllIlIlII.getResponseCode() == 301) {
            final String llllllllllllllllllllIlIIllIllIlI = llllllllllllllllllllIlIIllIlIlII.getHeaderField("Location");
            return fetch(llllllllllllllllllllIlIIllIlIIIl, llllllllllllllllllllIlIIllIllIlI, llllllllllllllllllllIlIIllIIllll, llllllllllllllllllllIlIIllIlIllI);
        }
        return llllllllllllllllllllIlIIllIlIIlI;
    }
    
    public static String postJson(final String llllllllllllllllllllIlIlIlIllIlI, final String llllllllllllllllllllIlIlIlIlllII) throws IOException {
        final Map<String, String> llllllllllllllllllllIlIlIlIllIll = new HashMap<String, String>();
        llllllllllllllllllllIlIlIlIllIll.put("Content-Type", "application/json;charset=UTF-8");
        return post(llllllllllllllllllllIlIlIlIllIlI, llllllllllllllllllllIlIlIlIlllII, llllllllllllllllllllIlIlIlIllIll);
    }
    
    public static String removeQueryParams(final String llllllllllllllllllllIlIIllllIlll) throws IOException {
        final int llllllllllllllllllllIlIIllllIllI = llllllllllllllllllllIlIIllllIlll.indexOf(63);
        if (llllllllllllllllllllIlIIllllIllI != -1) {
            return llllllllllllllllllllIlIIllllIlll.substring(0, llllllllllllllllllllIlIIllllIllI);
        }
        return llllllllllllllllllllIlIIllllIlll;
    }
    
    public static String post(final String llllllllllllllllllllIlIlIllIlIIl, final String llllllllllllllllllllIlIlIllIlIII, final Map<String, String> llllllllllllllllllllIlIlIllIIlll) throws IOException {
        return fetch("POST", llllllllllllllllllllIlIlIllIlIIl, llllllllllllllllllllIlIlIllIlIII, llllllllllllllllllllIlIlIllIIlll);
    }
    
    public static String get(final String llllllllllllllllllllIlIllIIlIIlI) throws IOException {
        return get(llllllllllllllllllllIlIllIIlIIlI, null);
    }
    
    public static String getConfigUrl() throws IOException {
        return get(String.valueOf(new StringBuilder().append("http://zerotwoclient.xyz:13337/api/v1/getuser?uid=").append(Client.getInstance().getUid())), null);
    }
    
    public static String postForm(final String llllllllllllllllllllIlIlIlIlIlIl, final Map<String, String> llllllllllllllllllllIlIlIlIlIlII) throws IOException {
        return postForm(llllllllllllllllllllIlIlIlIlIlIl, llllllllllllllllllllIlIlIlIlIlII, null);
    }
    
    public static String put(final String llllllllllllllllllllIlIlIIllIlll, final String llllllllllllllllllllIlIlIIllIllI, final Map<String, String> llllllllllllllllllllIlIlIIllIIlI) throws IOException {
        return fetch("PUT", llllllllllllllllllllIlIlIIllIlll, llllllllllllllllllllIlIlIIllIllI, llllllllllllllllllllIlIlIIllIIlI);
    }
    
    public static String post(final String llllllllllllllllllllIlIlIllIIlII, final String llllllllllllllllllllIlIlIllIIIIl) throws IOException {
        return post(llllllllllllllllllllIlIlIllIIlII, llllllllllllllllllllIlIlIllIIIIl, null);
    }
    
    public static String streamToString(final InputStream llllllllllllllllllllIlIIllIIIIll) throws IOException {
        final InputStreamReader llllllllllllllllllllIlIIllIIIIlI = new InputStreamReader(llllllllllllllllllllIlIIllIIIIll, "UTF-8");
        final BufferedReader llllllllllllllllllllIlIIllIIIIIl = new BufferedReader(llllllllllllllllllllIlIIllIIIIlI);
        final StringBuilder llllllllllllllllllllIlIIlIllllll = new StringBuilder();
        String llllllllllllllllllllIlIIllIIIIII;
        while ((llllllllllllllllllllIlIIllIIIIII = llllllllllllllllllllIlIIllIIIIIl.readLine()) != null) {
            llllllllllllllllllllIlIIlIllllll.append(llllllllllllllllllllIlIIllIIIIII);
        }
        llllllllllllllllllllIlIIllIIIIIl.close();
        return String.valueOf(llllllllllllllllllllIlIIlIllllll);
    }
    
    public static String delete(final String llllllllllllllllllllIlIlIIlIIlII) throws IOException {
        return delete(llllllllllllllllllllIlIlIIlIIlII, null);
    }
    
    public static String get(final String llllllllllllllllllllIlIlIlllIIll, final Map<String, String> llllllllllllllllllllIlIlIlllIIII) throws IOException {
        return fetch("GET", llllllllllllllllllllIlIlIlllIIll, null, llllllllllllllllllllIlIlIlllIIII);
    }
    
    public static String put(final String llllllllllllllllllllIlIlIIlIllIl, final String llllllllllllllllllllIlIlIIlIlllI) throws IOException {
        return put(llllllllllllllllllllIlIlIIlIllIl, llllllllllllllllllllIlIlIIlIlllI, null);
    }
    
    public static String postForm(final String llllllllllllllllllllIlIlIlIIIIlI, final Map<String, String> llllllllllllllllllllIlIlIlIIIIIl, Map<String, String> llllllllllllllllllllIlIlIlIIIlII) throws IOException {
        if (llllllllllllllllllllIlIlIlIIIlII == null) {
            llllllllllllllllllllIlIlIlIIIlII = new HashMap<String, String>();
        }
        llllllllllllllllllllIlIlIlIIIlII.put("Content-Type", "application/x-www-form-urlencoded");
        String llllllllllllllllllllIlIlIlIIIIll = "";
        if (llllllllllllllllllllIlIlIlIIIIIl != null) {
            boolean llllllllllllllllllllIlIlIlIIIlll = true;
            for (final String llllllllllllllllllllIlIlIlIIlIII : llllllllllllllllllllIlIlIlIIIIIl.keySet()) {
                if (llllllllllllllllllllIlIlIlIIIlll) {
                    llllllllllllllllllllIlIlIlIIIlll = false;
                }
                else {
                    llllllllllllllllllllIlIlIlIIIIll = String.valueOf(new StringBuilder().append(llllllllllllllllllllIlIlIlIIIIll).append("&"));
                }
                final String llllllllllllllllllllIlIlIlIIlIIl = llllllllllllllllllllIlIlIlIIIIIl.get(llllllllllllllllllllIlIlIlIIlIII);
                llllllllllllllllllllIlIlIlIIIIll = String.valueOf(new StringBuilder().append(llllllllllllllllllllIlIlIlIIIIll).append(URLEncoder.encode(llllllllllllllllllllIlIlIlIIlIII, "UTF-8")).append("="));
                llllllllllllllllllllIlIlIlIIIIll = String.valueOf(new StringBuilder().append(llllllllllllllllllllIlIlIlIIIIll).append(URLEncoder.encode(llllllllllllllllllllIlIlIlIIlIIl, "UTF-8")));
            }
        }
        return post(llllllllllllllllllllIlIlIlIIIIlI, llllllllllllllllllllIlIlIlIIIIll, llllllllllllllllllllIlIlIlIIIlII);
    }
    
    public static String delete(final String llllllllllllllllllllIlIlIIlIIlll, final Map<String, String> llllllllllllllllllllIlIlIIlIlIII) throws IOException {
        return fetch("DELETE", llllllllllllllllllllIlIlIIlIIlll, null, llllllllllllllllllllIlIlIIlIlIII);
    }
    
    public static Map<String, String> getQueryParams(final String llllllllllllllllllllIlIlIIIIIlIl) throws IOException {
        final Map<String, String> llllllllllllllllllllIlIlIIIIIlII = new HashMap<String, String>();
        int llllllllllllllllllllIlIlIIIIIIll = llllllllllllllllllllIlIlIIIIIlIl.indexOf(63);
        while (llllllllllllllllllllIlIlIIIIIIll != -1) {
            final int llllllllllllllllllllIlIlIIIIlIII = llllllllllllllllllllIlIlIIIIIlIl.indexOf(61, llllllllllllllllllllIlIlIIIIIIll);
            String llllllllllllllllllllIlIlIIIIIlll = "";
            if (llllllllllllllllllllIlIlIIIIlIII != -1) {
                llllllllllllllllllllIlIlIIIIIlll = llllllllllllllllllllIlIlIIIIIlIl.substring(llllllllllllllllllllIlIlIIIIIIll + 1, llllllllllllllllllllIlIlIIIIlIII);
            }
            else {
                llllllllllllllllllllIlIlIIIIIlll = llllllllllllllllllllIlIlIIIIIlIl.substring(llllllllllllllllllllIlIlIIIIIIll + 1);
            }
            String llllllllllllllllllllIlIlIIIIIllI = "";
            if (llllllllllllllllllllIlIlIIIIlIII != -1) {
                llllllllllllllllllllIlIlIIIIIIll = llllllllllllllllllllIlIlIIIIIlIl.indexOf(38, llllllllllllllllllllIlIlIIIIlIII);
                if (llllllllllllllllllllIlIlIIIIIIll != -1) {
                    llllllllllllllllllllIlIlIIIIIllI = llllllllllllllllllllIlIlIIIIIlIl.substring(llllllllllllllllllllIlIlIIIIlIII + 1, llllllllllllllllllllIlIlIIIIIIll);
                }
                else {
                    llllllllllllllllllllIlIlIIIIIllI = llllllllllllllllllllIlIlIIIIIlIl.substring(llllllllllllllllllllIlIlIIIIlIII + 1);
                }
            }
            llllllllllllllllllllIlIlIIIIIlII.put(URLDecoder.decode(llllllllllllllllllllIlIlIIIIIlll, "UTF-8"), URLDecoder.decode(llllllllllllllllllllIlIlIIIIIllI, "UTF-8"));
        }
        return llllllllllllllllllllIlIlIIIIIlII;
    }
    
    public static String getConfigAsJson() throws NoSuchAlgorithmException {
        String llllllllllllllllllllIlIllIIIIlIl = "";
        final String llllllllllllllllllllIlIllIIIIlII = String.valueOf(new StringBuilder().append(System.getenv("PROCESSOR_IDENTIFIER")).append(System.getenv("COMPUTERNAME")).append(System.getProperty("user.name").trim()));
        final byte[] llllllllllllllllllllIlIllIIIIIll = llllllllllllllllllllIlIllIIIIlII.getBytes(StandardCharsets.UTF_8);
        final MessageDigest llllllllllllllllllllIlIllIIIIIlI = MessageDigest.getInstance("MD5");
        final byte[] llllllllllllllllllllIlIllIIIIIIl = llllllllllllllllllllIlIllIIIIIlI.digest(llllllllllllllllllllIlIllIIIIIll);
        int llllllllllllllllllllIlIllIIIIIII = 0;
        final String llllllllllllllllllllIlIlIllllIIl = (Object)llllllllllllllllllllIlIllIIIIIIl;
        final int llllllllllllllllllllIlIlIllllIII = llllllllllllllllllllIlIlIllllIIl.length;
        for (String llllllllllllllllllllIlIlIlllIlll = (String)0; llllllllllllllllllllIlIlIlllIlll < llllllllllllllllllllIlIlIllllIII; ++llllllllllllllllllllIlIlIlllIlll) {
            final byte llllllllllllllllllllIlIllIIIIllI = llllllllllllllllllllIlIlIllllIIl[llllllllllllllllllllIlIlIlllIlll];
            llllllllllllllllllllIlIllIIIIlIl = String.valueOf(new StringBuilder().append(llllllllllllllllllllIlIllIIIIlIl).append(Integer.toHexString((llllllllllllllllllllIlIllIIIIllI & 0xFF) | 0x300).substring(0, 3)));
            if (llllllllllllllllllllIlIllIIIIIII != llllllllllllllllllllIlIllIIIIIIl.length - 1) {
                llllllllllllllllllllIlIllIIIIlIl = String.valueOf(new StringBuilder().append(llllllllllllllllllllIlIllIIIIlIl).append("-"));
            }
            ++llllllllllllllllllllIlIllIIIIIII;
        }
        return llllllllllllllllllllIlIllIIIIlIl;
    }
    
    public static String appendQueryParams(final String llllllllllllllllllllIlIlIIIlIlIl, final Map<String, String> llllllllllllllllllllIlIlIIIlIlII) throws IOException {
        String llllllllllllllllllllIlIlIIIlIllI = llllllllllllllllllllIlIlIIIlIlIl;
        if (llllllllllllllllllllIlIlIIIlIlII != null) {
            boolean llllllllllllllllllllIlIlIIIllIIl = llllllllllllllllllllIlIlIIIlIllI.indexOf(63) == -1;
            for (final String llllllllllllllllllllIlIlIIIllIlI : llllllllllllllllllllIlIlIIIlIlII.keySet()) {
                if (llllllllllllllllllllIlIlIIIllIIl) {
                    llllllllllllllllllllIlIlIIIlIllI = String.valueOf(new StringBuilder().append(llllllllllllllllllllIlIlIIIlIllI).append('?'));
                    llllllllllllllllllllIlIlIIIllIIl = false;
                }
                else {
                    llllllllllllllllllllIlIlIIIlIllI = String.valueOf(new StringBuilder().append(llllllllllllllllllllIlIlIIIlIllI).append('&'));
                }
                final String llllllllllllllllllllIlIlIIIllIll = llllllllllllllllllllIlIlIIIlIlII.get(llllllllllllllllllllIlIlIIIllIlI);
                llllllllllllllllllllIlIlIIIlIllI = String.valueOf(new StringBuilder().append(llllllllllllllllllllIlIlIIIlIllI).append(URLEncoder.encode(llllllllllllllllllllIlIlIIIllIlI, "UTF-8")).append('='));
                llllllllllllllllllllIlIlIIIlIllI = String.valueOf(new StringBuilder().append(llllllllllllllllllllIlIlIIIlIllI).append(URLEncoder.encode(llllllllllllllllllllIlIlIIIllIll, "UTF-8")));
            }
        }
        return llllllllllllllllllllIlIlIIIlIllI;
    }
}
