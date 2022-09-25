package com.synectiks.asset.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptoUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(CryptoUtil.class);
			
	private static final String SECRET_KEY = "6MgBSOpp8kiJ8zb7XHW7yRUCUXyT8ck7kjWltYTGOHU=";
	private static final String SALTVALUE = "G1GMMhcI6lk47FwtsCOy6Ew5hT+gqtKbXmFP8ZJi/YI=";
	private static final String PBE_KEY = "PBKDF2WithHmacSHA256";
	private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
	private static final String ALGO = "AES";
	
	public static String encrypt(String strToEncrypt) {
		try {
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			SecretKeyFactory factory = SecretKeyFactory.getInstance(PBE_KEY);
			KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALTVALUE.getBytes(), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), ALGO);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
		} catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException
				| InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException
				| NoSuchPaddingException e) {
			logger.error("Error occured during encryption: " + e.toString());
		}
		return null;
	}

	public static String decrypt(String strToDecrypt) {
		try {
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			SecretKeyFactory factory = SecretKeyFactory.getInstance(PBE_KEY);
			KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALTVALUE.getBytes(), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), ALGO);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException
				| InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException
				| NoSuchPaddingException e) {
			logger.error("Error occured during decryption: " + e.toString());
		}
		return null;
	}
	
	public static String getSecretEncryptionKey() throws Exception{
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(256); // The AES key size in number of bits
        SecretKey secKey = generator.generateKey();
        String secretKeyString = Base64.getEncoder().encodeToString(secKey.getEncoded());
        return secretKeyString;
    }
	
	public static String encodeBase64(String source) {
		return Base64.getEncoder().encodeToString(source.getBytes());
	}
	
	public static String decodeBase64(String source) {
		byte[] decodedBytes = Base64.getDecoder().decode(source);
		return new String(decodedBytes);
	}
	
}