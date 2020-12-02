package com.chatserver.util;

import java.math.BigInteger;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class CreateIdUtil {

	private static final String SESSIONID_VALIDCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-=_+[]{}|;:,.<>";
		
	public static final int SESSIONID_LENGTH = 60;
	
	private static final int IDTYPE_LENGTH = 12; // requred length for id value
	
	public static final int IDTYPE_USERID = 10;
	public static final int IDTYPE_CHATROOM = 20;
	public static final int IDTYPE_MESSAGEID = 30;
		
	public static String createSessionId () {
		int length = SESSIONID_VALIDCHARS.length();
	    Random random = new Random();
	    String result = "S";
	    for (int x=0; x< SESSIONID_LENGTH-1; x++) {
	    	result = result + SESSIONID_VALIDCHARS.charAt(random.nextInt((length)));
	    }
	    return result;
	}
	
	public static BigInteger createId(int inIdType) {
		BigInteger theResult = BigInteger.valueOf(inIdType);
		Random random = new Random();
	    for (int x=0; x<IDTYPE_LENGTH; x++) {
			theResult = theResult.multiply(new BigInteger("10"));
			theResult = theResult.add(new BigInteger(String.valueOf(random.nextInt(10))));
		}
		return theResult;
	}
	
}
