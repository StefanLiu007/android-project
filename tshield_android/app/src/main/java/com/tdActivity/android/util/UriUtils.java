package com.tdActivity.android.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UriUtils {
	private final static String REGEX="otpauth://totp/([^?]+)\\?secret=([a-zA-Z2-7]+).*";
	//对String进行正则表达式验�?
	public static boolean uriMatch(String uri)
	{
		Pattern pattern =Pattern.compile(REGEX);
		Matcher matcher=pattern.matcher(uri);
		return matcher.matches();
	}
}
