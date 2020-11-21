package com.chatserver.util;

import java.util.Random;

public class Utility {
	
	private static final String SESSIONID_VALIDCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-=_+[]{}|;:,.<>";
		
	public static final int SESSIONID_LENGTH = 60;
	
	public static String createSessionId () {
		int length = SESSIONID_VALIDCHARS.length();
	    Random random = new Random();
	    String result = "S";
	    for (int x=0; x< SESSIONID_LENGTH-1; x++) {
	    	result = result + SESSIONID_VALIDCHARS.charAt(random.nextInt((length)));
	    }
	    return result;
	}
}
