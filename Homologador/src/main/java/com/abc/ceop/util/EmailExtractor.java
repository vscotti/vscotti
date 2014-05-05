package com.abc.ceop.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailExtractor {

    public static String extractEmail(String str) {
        final String RE_MAIL = "([\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Za-z]{2,4})";

        Pattern p = Pattern.compile(RE_MAIL);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return (m.group(1));
        }

        return null;
    }
	
	
}
