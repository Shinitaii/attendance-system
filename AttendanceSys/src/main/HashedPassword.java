package main;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashedPassword {
	
	public static byte[] salt;
	
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static String generateHash(String data) {
		byte[] hash = null;
		
		try {
			salt = createSalt();
			System.out.println("Created salt: "+salt.toString());
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			digest.reset();
			digest.update(salt);
			hash = digest.digest(data.getBytes());	
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return bytesToStringHex(hash);
	}
	
	public static String existingSalt(String data, byte[] salt) {
		byte[] hash = null;
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			digest.reset();
			digest.update(salt);
			hash = digest.digest(data.getBytes());	
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return bytesToStringHex(hash);
	}
	
	private static String bytesToStringHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for(int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
	
	private static byte[] createSalt() {
		byte[] bytes = new byte[20];
		SecureRandom random = new SecureRandom();
		random.nextBytes(bytes);
		return bytes;
	}
}
