package kmackay.uECC;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import kmackay.swig.uECC.uECCSwig;

public class uECCTest {
	uECCCurve curve160r1;
	uECCCurve curve192r1;
	uECCCurve curve224r1;
	uECCCurve curve256k1;
	uECCCurve curve256r1;
	uECCKeyPair kp160_1;
	uECCKeyPair kp160_2;
	uECCKeyPair kp160_3;
	uECCKeyPair kp256_1;
	uECCKeyPair kp256_2;

	uECCKeyPair pairs160[];
	uECCKeyPair pairs256[];

	uECCKeyPair allPairs[];

	@Before
	public void setUp() {
		curve160r1 = uECCCurve.uECC_secp160r1();
		curve192r1= uECCCurve.uECC_secp192r1();
		curve224r1 = uECCCurve.uECC_secp224r1();
		curve256r1 = uECCCurve.uECC_secp256r1();
		curve256k1 = uECCCurve.uECC_secp256k1();
		
		kp160_1 = uECCKeyPair.makeKeyPair(curve160r1);
		kp160_2 = uECCKeyPair.makeKeyPair(curve160r1);
		kp160_3 = uECCKeyPair.from(new uECCPrivateKey("00038B1862B4C0FDBCDD7A035435BB20B30207B399",curve160r1), 
				new uECCPublicKey("C0B9F0579D581BBF3464DBE2F42CE194BF698F3C9AC9E2D31A566194DAA79F2C2100FAD3428054AC", curve160r1));
		kp256_1 = uECCKeyPair.makeKeyPair(curve256r1);
		kp256_2 = uECCKeyPair.makeKeyPair(curve256r1);

		pairs160 = new uECCKeyPair[]{
				kp160_1, 
				kp160_2,
				kp160_3
		};
		
		pairs256 = new uECCKeyPair[]{
				kp256_1,
				kp256_2
		};
		allPairs= new uECCKeyPair[]{
				kp160_1, 
				kp160_2,
				kp256_1,
				kp160_3,
				uECCKeyPair.makeKeyPair(curve256k1),
				uECCKeyPair.makeKeyPair(curve224r1),
				uECCKeyPair.makeKeyPair(curve192r1)
		};
	}

	@After
	public void tearDown() {
		kp160_1.destroy();
		kp160_2.destroy();
		kp160_3.destroy();
		kp256_1.destroy();
	}

	
	@Test
	public void testCurveKey() {	
		assertEquals("key size is expected size",40, uECCSwig.uECC_curve_public_key_size(curve160r1.get()));
		assertEquals("key size is expected size",48, uECCSwig.uECC_curve_public_key_size(curve192r1.get()));
		assertEquals("key size is expected size",56, uECCSwig.uECC_curve_public_key_size(curve224r1.get()));
		assertEquals("key size is expected size",64, uECCSwig.uECC_curve_public_key_size(curve256r1.get()));
		assertEquals("key size is expected size",64, uECCSwig.uECC_curve_public_key_size(curve256k1.get()));
	}
	
	
	@Test
	public void testKeySize() {		
		for (uECCKeyPair pair160 : pairs160) {
			assertEquals(21, pair160.getCurve().getPrivateKeySize());
			assertEquals(40, pair160.getCurve().getPublicKeySize());
			assertEquals("key size is expected size",21, pair160.getPrivateKey().getKeySize());
			assertEquals("key size is expected size",40, pair160.getPublicKey().getKeySize());
		}
		
		for (uECCKeyPair pair256 : pairs256) {
			assertEquals(32, pair256.getCurve().getPrivateKeySize());
			assertEquals(64, pair256.getCurve().getPublicKeySize());
			assertEquals("key size is expected size",32, pair256.getPrivateKey().getKeySize());
			assertEquals("key size is expected size",64, pair256.getPublicKey().getKeySize());
		}
		
		
		for (uECCKeyPair pair : allPairs) {
			assertTrue("Key is valid", pair.getPublicKey().isValid());
		}
	}

	@Test(expected=java.lang.IllegalStateException.class)
	public void testDestroyPrivate() {		
		uECCKeyPair pair = uECCKeyPair.makeKeyPair(curve160r1);
		pair.destroy();
		pair.privateKey.getKeySize();
        fail("shouldn't get here");
	}

	@Test(expected=java.lang.IllegalStateException.class)
	public void testDestroyPublic() {		
		uECCKeyPair pair = uECCKeyPair.makeKeyPair(curve160r1);
		pair.destroy();
		pair.publicKey.getKeySize();
        fail("shouldn't get here");
	}
	
	@Test
	public void testPublicKey() {
		uECCKeyPair valid = uECCKeyPair.from(new uECCPrivateKey("00038B1862B4C0FDBCDD7A035435BB20B30207B399",curve160r1), 
				new uECCPublicKey("C0B9F0579D581BBF3464DBE2F42CE194BF698F3C9AC9E2D31A566194DAA79F2C2100FAD3428054AC", curve160r1));
		assertTrue(valid.getPublicKey().isValid());
		uECCKeyPair invalid = uECCKeyPair.from(new uECCPrivateKey("00038B1862B4C0FDBCDD7A035435BB20B30207B399",curve160r1), 
				new uECCPublicKey("C0B9F0579D581BBF3465DBE2F42CE194BF698F3C9AC9E2D31A566194DAA79F2C2100FAD3428054AC", curve160r1));
		assertFalse(invalid.getPublicKey().isValid());
	}
	
	@Test
	public void testAsHex() {
		uECCKeyPair valid = uECCKeyPair.makeKeyPair(curve224r1);
		uECCKeyPair copy = uECCKeyPair.from(new uECCPrivateKey(valid.getPrivateKey().asHex(),curve224r1), 
				new uECCPublicKey(valid.getPublicKey().asHex(), curve224r1));
		assertTrue(valid.getPublicKey().isValid());
		assertEquals(valid.getPrivateKey().asHex(), copy.getPrivateKey().asHex());
		assertEquals(valid.getPublicKey().asHex(), copy.getPublicKey().asHex());
	}
	
	@Test
	public void testSign() throws NoSuchAlgorithmException {
		
		uECCKeyPair externKey = uECCKeyPair.makeKeyPair(curve224r1);

		for (uECCKeyPair pair : allPairs) {
	        String text = "This is the text to hash";
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
			byte[] signature = pair.sign(hash);
			byte[] s1 = text.getBytes();
			assertTrue("checks agaist same pair = ", pair.verify(hash, signature));
			assertTrue("checks agaist same pair but computed public key = ",pair.getPrivateKey().computePublicKey().verify(hash, signature));
			assertFalse("checks agaist pair1 key = ", externKey.verify(hash, signature));
			assertFalse("checks against wrong signature", pair.verify(hash, s1));
		}
	}
	
}
