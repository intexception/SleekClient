package org.json;

import java.util.*;

public class XMLParserConfiguration
{
    public static final XMLParserConfiguration ORIGINAL;
    public static final XMLParserConfiguration KEEP_STRINGS;
    private boolean keepStrings;
    private String cDataTagName;
    private boolean convertNilAttributeToNull;
    private Map<String, XMLXsiTypeConverter<?>> xsiTypeMap;
    private Set<String> forceList;
    
    public XMLParserConfiguration() {
        this.keepStrings = false;
        this.cDataTagName = "content";
        this.convertNilAttributeToNull = false;
        this.xsiTypeMap = Collections.emptyMap();
        this.forceList = Collections.emptySet();
    }
    
    @Deprecated
    public XMLParserConfiguration(final boolean keepStrings) {
        this(keepStrings, "content", false);
    }
    
    @Deprecated
    public XMLParserConfiguration(final String cDataTagName) {
        this(false, cDataTagName, false);
    }
    
    @Deprecated
    public XMLParserConfiguration(final boolean keepStrings, final String cDataTagName) {
        this.keepStrings = keepStrings;
        this.cDataTagName = cDataTagName;
        this.convertNilAttributeToNull = false;
    }
    
    @Deprecated
    public XMLParserConfiguration(final boolean keepStrings, final String cDataTagName, final boolean convertNilAttributeToNull) {
        this.keepStrings = keepStrings;
        this.cDataTagName = cDataTagName;
        this.convertNilAttributeToNull = convertNilAttributeToNull;
    }
    
    private XMLParserConfiguration(final boolean keepStrings, final String cDataTagName, final boolean convertNilAttributeToNull, final Map<String, XMLXsiTypeConverter<?>> xsiTypeMap, final Set<String> forceList) {
        this.keepStrings = keepStrings;
        this.cDataTagName = cDataTagName;
        this.convertNilAttributeToNull = convertNilAttributeToNull;
        this.xsiTypeMap = Collections.unmodifiableMap((Map<? extends String, ? extends XMLXsiTypeConverter<?>>)xsiTypeMap);
        this.forceList = Collections.unmodifiableSet((Set<? extends String>)forceList);
    }
    
    @Override
    protected XMLParserConfiguration clone() {
        return new XMLParserConfiguration(this.keepStrings, this.cDataTagName, this.convertNilAttributeToNull, this.xsiTypeMap, this.forceList);
    }
    
    public boolean isKeepStrings() {
        return this.keepStrings;
    }
    
    public XMLParserConfiguration withKeepStrings(final boolean newVal) {
        final XMLParserConfiguration newConfig = this.clone();
        newConfig.keepStrings = newVal;
        return newConfig;
    }
    
    public String getcDataTagName() {
        return this.cDataTagName;
    }
    
    public XMLParserConfiguration withcDataTagName(final String newVal) {
        final XMLParserConfiguration newConfig = this.clone();
        newConfig.cDataTagName = newVal;
        return newConfig;
    }
    
    public boolean isConvertNilAttributeToNull() {
        return this.convertNilAttributeToNull;
    }
    
    public XMLParserConfiguration withConvertNilAttributeToNull(final boolean newVal) {
        final XMLParserConfiguration newConfig = this.clone();
        newConfig.convertNilAttributeToNull = newVal;
        return newConfig;
    }
    
    public Map<String, XMLXsiTypeConverter<?>> getXsiTypeMap() {
        return this.xsiTypeMap;
    }
    
    public XMLParserConfiguration withXsiTypeMap(final Map<String, XMLXsiTypeConverter<?>> xsiTypeMap) {
        final XMLParserConfiguration newConfig = this.clone();
        final Map<String, XMLXsiTypeConverter<?>> cloneXsiTypeMap = new HashMap<String, XMLXsiTypeConverter<?>>(xsiTypeMap);
        newConfig.xsiTypeMap = Collections.unmodifiableMap((Map<? extends String, ? extends XMLXsiTypeConverter<?>>)cloneXsiTypeMap);
        return newConfig;
    }
    
    public Set<String> getForceList() {
        return this.forceList;
    }
    
    public XMLParserConfiguration withForceList(final Set<String> forceList) {
        final XMLParserConfiguration newConfig = this.clone();
        final Set<String> cloneForceList = new HashSet<String>(forceList);
        newConfig.forceList = Collections.unmodifiableSet((Set<? extends String>)cloneForceList);
        return newConfig;
    }
    
    static {
        ORIGINAL = new XMLParserConfiguration();
        KEEP_STRINGS = new XMLParserConfiguration().withKeepStrings(true);
    }
}
