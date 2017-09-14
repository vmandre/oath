import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import com.google.common.io.BaseEncoding;
import com.lochbridge.oath.otp.HmacShaAlgorithm;
import com.lochbridge.oath.otp.TOTP;
import com.lochbridge.oath.otp.TOTPValidator;

public class ValidatingTOTPExample {

	public static void main(String[] args) {
		// The following example illustrates support for Google Authenticator.
		// Google Authenticator supports 6-digit TOTPs, and base32 string shared
		// secret keys.
		//
		// Step 1: Generate a 160-bit shared secret key.
		byte[] bytes = new byte[20];
		SecureRandom random = new SecureRandom();
		random.nextBytes(bytes);
		String secretKey = BaseEncoding.base32().encode(bytes);

		// Step 2: Provision the shared secret key.
		// a) Encrypt the 'secretKey' and store it in a secured location.
		// b) Deliver the 'secretKey' to the client in a secured fashion (e.g. QR code over SSL)

		// Step 3: The client registers the 'secretKey' in their Google Authenticator app. The app
		// should now be generating TOTPs every 30 seconds.

		// Step 4: The client initiates a two-factor authentication session (i.e. online banking login).
		// a) The client provides their username and password (knowledge factor)
		// b) The client provides the TOTP code displayed from their Google Authenticator app.
		//		    (typically b) would be performed as second step after a) has succeeded)
		// c) The user submits the request to the server.

		// Step 5: The server authenticates the user.
		// (assume the username and password were authenticated)
		String clientTOTP = "79939642"; //"48803815"; //"12343466"; //"01287962"; // the TOTP value the client submitted.
		String encryptedSecretKey = "GEZDGNBVGY3TQOJQGEZDGNBVGY3TQOJQGEZDGNBVGY3TQOJQGEZDGNBVGY3TQOJQGEZDGNBVGY3TQOJQGEZDGNBVGY3TQOJQGEZDGNA="; // retrieved from some secured data store.
//vand		String secretKey32 = decrypt(encryptedSecretKey); // assume decrypt(...) is implemented
		String secretKey32 = encryptedSecretKey; // assume decrypt(...) is implemented
//		byte[] key = BaseEncoding.base32().decode(secretKey32);
		
//vand
		String sharedSecretKey = "1234567890123456789012345678901234567890123456789012345678901234";
		byte[] key = null;
		try {
			key = sharedSecretKey.getBytes("US-ASCII");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//vand		
		
		
		
		System.out.println(new String(key));		
//vand		TOTP totp = TOTP.key(key).timeStep(TimeUnit.SECONDS.toMillis(30)).digits(8).hmacSha1().build();
		TOTP totp = TOTP.key(key).timeStep(TimeUnit.SECONDS.toMillis(30)).digits(8).hmacSha512().build();
System.out.println("TOTP gerado: " + totp.value());		
		if (totp.value().equals(clientTOTP)) {
		    // passed authentication...
			System.out.println("passed authentication...");
		} else {
		    // failed authentication...
			System.out.println("failed authentication...");
		}
		
		// Alternatively, to validate the client TOTP, you can use the TOTPValidator class:
		boolean valid = TOTPValidator.window(3).isValid(key, TimeUnit.SECONDS.toMillis(30), 8, 
		    HmacShaAlgorithm.HMAC_SHA_512, clientTOTP);
		
		System.out.println(valid ? "Token válido" : "Token inválido");
	}

	private static String decrypt(String encryptedSecretKey) {
		byte[] decode = BaseEncoding.base64().decode(encryptedSecretKey);
		return new String(decode);
	}

}
