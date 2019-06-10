package ar.edu.unlam.tpseguridad.dao;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.inject.Inject;

import org.apache.commons.codec.binary.Hex;
import org.springframework.util.Base64Utils;

import ar.edu.unlam.tpseguridad.modelo.Usuario;
import ar.edu.unlam.tpseguridad.servicios.ServicioLogin;
import ar.edu.unlam.tpseguridad.servicios.ServicioUsuario;

public class SaltHashedPassword {
		
	private static final Random RANDOM = new SecureRandom();
	
	////////////////////////////////////////////////////////////
	
	public static byte[] getNextSalt() {
	    byte[] salt = new byte[32];
	    RANDOM.nextBytes(salt);
	    return salt;
	  }
	////////////////////////////////////////////////////////////
	
	public static String convertirSalt(byte[] salt) {

		String saltString = Base64Utils.encodeToString(salt);

		return saltString;
	}
	
	////////////////////////////////////////////////////////////
	
	public static String generarHash(Usuario usuario, byte[] salt) throws UnsupportedEncodingException {
	
		String password = usuario.getPassword();
        int iterations = 10000;
        int keyLength = 512;
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = salt;//salt.getBytes();
        byte[] hashedBytes = hashPassword(passwordChars, saltBytes, iterations, keyLength);
        String hashedString = Hex.encodeHexString(hashedBytes);
        

        return hashedString;
        
    }

	//////////////////////////////////////////////////////////////
	
    public static byte[] hashPassword( final char[] password, final byte[] salt, final int iterations, final int keyLength ) {

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
            PBEKeySpec spec = new PBEKeySpec( password, salt, iterations, keyLength );
            SecretKey key = skf.generateSecret( spec );
            byte[] res = key.getEncoded( );
            return res;
        } catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) {
            throw new RuntimeException( e );
        }
    }
	
	
	
	
}
