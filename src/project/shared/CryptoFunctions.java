package project.shared;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoFunctions {
	
	public static byte[] encrypt (byte[] key, byte[] target, byte[] IV) {
		return endecrypt (Cipher.ENCRYPT_MODE, key, target, IV);
		
	}
	
	public static byte[] decrypt (byte[] key, byte[] target, byte[] IV) {
		return endecrypt (Cipher.DECRYPT_MODE, key, target, IV);
	}
	
	private static byte[] endecrypt (int mode, byte[] key, byte[] target, byte[] IV) {
		try {
			SecretKey fileKey = new SecretKeySpec (key, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(mode, fileKey, new IvParameterSpec(IV));
			return cipher.doFinal(target);
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public static void main (String[] args) {
		try {
			SecureRandom rand = new SecureRandom();
			byte[] tempFileKeyBytes = new byte[Registrar.HASH_LENGTH/2];
			byte[] IV = new byte[16];
			rand.nextBytes(tempFileKeyBytes);
			rand.nextBytes(IV);
			
			String inpath = "D:/Downloads/temp.jpg";
			String outpath = "C:/Users/Peng/Desktop/temp.jpg";
			File file = new File (inpath);
			FileInputStream input = new FileInputStream(file);
			byte[] fileBytes = new byte[(int) file.length()];
			input.read(fileBytes);
			input.close();
			
			byte[] encrypted = encrypt(tempFileKeyBytes, fileBytes, IV);
			byte[] decrypted = decrypt(tempFileKeyBytes, encrypted, IV);
			
			file = new File (outpath);
			FileOutputStream output = new FileOutputStream(file);
			output.write(decrypted);
			output.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
}
