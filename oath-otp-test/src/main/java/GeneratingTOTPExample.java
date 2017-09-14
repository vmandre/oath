import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import com.google.common.io.BaseEncoding;
import com.lochbridge.oath.otp.TOTP;

public class GeneratingTOTPExample {

	public static void main(String[] args) {
		try {
			// Generate an 8-digit TOTP using a 30 second time step, HMAC-SHA-512,
			// and a 64 byte shared secret key.
			String sharedSecretKey = "1234567890123456789012345678901234567890123456789012345678901234";
			byte[] key = sharedSecretKey.getBytes("US-ASCII");
			
			//vand
			String encode = BaseEncoding.base32().encode(key);
			System.out.println(encode);
			byte[] decode = BaseEncoding.base32().decode(encode);
			System.out.println(new String(decode));
			
			TOTP totp = TOTP.key(key).timeStep(TimeUnit.SECONDS.toMillis(30)).digits(8).hmacSha512().build();
			
			System.out.println("TOTP = " + totp.value());
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
