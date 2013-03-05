package it.fe.cllmhl;



class ApplicationBean {
    private String mode;
    private String version;
    private String locale;
    private String theme;

    public String getMode() {
        return mode;
    }
    public void setMode(String mode) {
        this.mode = mode;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getLocale() {
        return locale;
    }
    public void setLocale(String locale) {
        this.locale = locale;
    }
    public String getTheme() {
        return theme;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }
    @Override
    public String toString() {
        StringBuffer lStringBuffer = new StringBuffer();
        lStringBuffer.append("mode=").append(mode);
        lStringBuffer.append(",version=").append(version);
        lStringBuffer.append(",localee=").append(locale);
        lStringBuffer.append(",theme=").append(theme);
        return lStringBuffer.toString();
    }
    
}
