package uECC;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kmackay.uECC.uECCCurve;
import kmackay.uECC.uECCKeyPair;
import kmackay.uECC.uECCPrivateKey;
import kmackay.uECC.uECCPublicKey;

public class Example {
    
	public static void main(String[] args) throws NoSuchAlgorithmException {
		uECCCurve curve160 = uECCCurve.uECC_secp160r1();
		uECCCurve curve256 = uECCCurve.uECC_secp256r1();

		uECCKeyPair kp160_1 = uECCKeyPair.makeKeyPair(curve160);
		uECCKeyPair kp160_2 = uECCKeyPair.makeKeyPair(curve160);
		uECCKeyPair kp160_3 = uECCKeyPair.from(new uECCPrivateKey("00038B1862B4C0FDBCDD7A035435BB20B30207B399",curve160), 
				 new uECCPublicKey("C0B9F0579D581BBF3464DBE2F42CE194BF698F3C9AC9E2D31A566194DAA79F2C2100FAD3428054AC", curve160));
		
		uECCKeyPair kp256_1 = uECCKeyPair.makeKeyPair(curve256);

		uECCKeyPair pairs[] = new uECCKeyPair[]{
				kp160_1, 
				kp160_2,
				kp256_1,
				kp160_3
				};

		for (uECCKeyPair pair : pairs) {
			System.out.println(pair);
	        String text = "This is the text to hash";
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
			byte[] signature = pair.sign(hash);
			byte[] s1 = kp160_1.sign(hash);
			System.out.println("checks agaist same pair = "+ pair.verify(hash, signature));
			System.out.println("checks agaist same pair1 = "+ kp160_1.verify(hash, signature));
			System.out.println("checks against pair1 signature" + pair.verify(hash, s1));
		}
	}
}
