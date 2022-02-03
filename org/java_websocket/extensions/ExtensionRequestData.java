package org.java_websocket.extensions;

import java.util.*;

public class ExtensionRequestData
{
    public static final String EMPTY_VALUE = "";
    private Map<String, String> extensionParameters;
    private String extensionName;
    
    private ExtensionRequestData() {
        this.extensionParameters = new LinkedHashMap<String, String>();
    }
    
    public static ExtensionRequestData parseExtensionRequest(final String extensionRequest) {
        final ExtensionRequestData extensionData = new ExtensionRequestData();
        final String[] parts = extensionRequest.split(";");
        extensionData.extensionName = parts[0].trim();
        for (int i = 1; i < parts.length; ++i) {
            final String[] keyValue = parts[i].split("=");
            String value = "";
            if (keyValue.length > 1) {
                String tempValue = keyValue[1].trim();
                if ((tempValue.startsWith("\"") && tempValue.endsWith("\"")) || (tempValue.startsWith("'") && tempValue.endsWith("'") && tempValue.length() > 2)) {
                    tempValue = tempValue.substring(1, tempValue.length() - 1);
                }
                value = tempValue;
            }
            extensionData.extensionParameters.put(keyValue[0].trim(), value);
        }
        return extensionData;
    }
    
    public String getExtensionName() {
        return this.extensionName;
    }
    
    public Map<String, String> getExtensionParameters() {
        return this.extensionParameters;
    }
}
