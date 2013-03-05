package it.fe.cllmhl.util;

import it.fe.cllmhl.core.CoreErrors;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

public class I18nBean extends BaseBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String patternInteger;
	private String patternDecimal;
	private String patternDate;
	private String patternTime;
	private String patternTimestamp;
	private String localePatternInteger;
	private String localePatternDecimal;
	private String localePatternDate;
	private String localePatternTime;
	private String localePatternTimestamp;
	
	public I18nBean(Locale pLocale) {
		ResourceBundle lResourceBundle = ResourceBundle.getBundle(CoreErrors.RESOURCE_BUNDLE, pLocale);
		patternInteger = lResourceBundle.getString("pattern.integer");
		patternDecimal = lResourceBundle.getString("pattern.decimal");
		patternDate = lResourceBundle.getString("pattern.date");
		patternTime = lResourceBundle.getString("pattern.time");
		patternTimestamp = lResourceBundle.getString("pattern.timestamp");
		localePatternInteger = LocaleUtil.getLocalizedNumberPattern(patternInteger, pLocale);
		localePatternDecimal = LocaleUtil.getLocalizedNumberPattern(patternDecimal, pLocale);
		localePatternDate = LocaleUtil.getLocalizedDatePattern(patternDate, pLocale);
		localePatternTime = LocaleUtil.getLocalizedDatePattern(patternTime, pLocale);
		localePatternTimestamp = LocaleUtil.getLocalizedDatePattern(patternTimestamp, pLocale);
	}

	public final String getPatternInteger() {
		return patternInteger;
	}

	public final String getPatternDecimal() {
		return patternDecimal;
	}

	public final String getPatternDate() {
		return patternDate;
	}

	public final String getPatternTime() {
		return patternTime;
	}

	public final String getPatternTimestamp() {
		return patternTimestamp;
	}

	public final String getLocalePatternInteger() {
		return localePatternInteger;
	}

	public final String getLocalePatternDecimal() {
		return localePatternDecimal;
	}

	public final String getLocalePatternDate() {
		return localePatternDate;
	}

	public final String getLocalePatternTime() {
		return localePatternTime;
	}

	public final String getLocalePatternTimestamp() {
		return localePatternTimestamp;
	}
}
