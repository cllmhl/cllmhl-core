package it.fe.cllmhl.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

public final class StringUtil {
	private static String convertToCamelCase(String pString) {
		StringBuffer lStringBuffer = new StringBuffer();
		String[] lStringArray = pString.split("_");
		boolean firstTime = true;
		for (String lString : lStringArray) {
			if (firstTime) {
				lStringBuffer.append(lString.toLowerCase());
				firstTime = false;
			} else {
				lStringBuffer.append(Character.toUpperCase(lString.charAt(0)));
				lStringBuffer.append(lString.substring(1).toLowerCase());
			}
		}
		return lStringBuffer.toString();
	}
	
	public static boolean isBlank(String string){
	    return StringUtils.isBlank(string);
	}
    public static boolean isNotBlank(String string){
        return StringUtils.isNotBlank(string);
    }
	public static String convertToJavaClassName(String pString){
		String lStrClassName = convertToCamelCase(pString);
		return lStrClassName.substring(0, 1).toUpperCase() + lStrClassName.substring(1);
	}
	public static String convertToJavaVariableName(String pString){
		String lStrVariableName = convertToCamelCase(pString);
		return lStrVariableName.substring(0, 1).toLowerCase() + lStrVariableName.substring(1);
	}
	public static String reflectionToString(Object pObject){
		return ToStringBuilder.reflectionToString(pObject);
	}
	
	private StringUtil(){}
}
