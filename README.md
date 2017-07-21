Fast prototyping and testing java wrapper for uECC.

Unoptimized, Unmaintained, Undocumented and Unsecure.

## Requirement :
Tested with swig 3.0, java 7/8, micro-ecc (july 2017)

## Compile and test :


```shell
make clone && make
```

note : you may have to adapt 


## Example java api
See the code in the example directory.
The api is self-explanatory.

```c
package uECC;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kmackay.uECC.uECCCurve;
import kmackay.uECC.uECCKeyPair;
import kmackay.uECC.uECCPrivateKey;
import kmackay.uECC.uECCPublicKey;

public class Test {
	
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
```

## Example uECC api :

This code assume the library has been loaded.

```c
package uECC;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

import kmackay.swig.uECC.SWIGTYPE_p_uECC_Curve_t;
import kmackay.swig.uECC.SWIGTYPE_p_unsigned_char;
import kmackay.swig.uECC.uECCSwig;

public class Example {

    public static void main(String argv[]) throws Throwable {
        //uECC.test();
        SWIGTYPE_p_uECC_Curve_t curve = uECCSwig.uECC_secp160r1();
        System.out.println(curve);

        int pubsize = uECCSwig.uECC_curve_public_key_size(curve);
        int privsize = uECCSwig.uECC_curve_private_key_size(curve);
        System.out.println(privsize);
        System.out.println(pubsize);
        SWIGTYPE_p_unsigned_char pubkey = uECCSwig.new_uint8Array(pubsize);
        SWIGTYPE_p_unsigned_char privkey = uECCSwig.new_uint8Array(privsize);

        uECCSwig.uECC_make_key(pubkey, privkey, curve);
        System.out.println("privkey=" + bytesToHex(uint8toByte(privkey, privsize)));
        System.out.println("pubkey=" + bytesToHex(uint8toByte(pubkey, pubsize)));

        SWIGTYPE_p_unsigned_char privkey2 = byteToUint8(asBytes("00038B1862B4C0FDBCDD7A035435BB20B30207B399"));
        SWIGTYPE_p_unsigned_char pubkey2 = byteToUint8(
                asBytes("C0B9F0579D581BBF3464DBE2F42CE194BF698F3C9AC9E2D31A566194DAA79F2C2100FAD3428054AC"));
        System.out.println("privkey2=" + bytesToHex(uint8toByte(privkey2, privsize)));
        System.out.println("pubkey2=" + bytesToHex(uint8toByte(pubkey2, pubsize)));

        //uECC.uint8Array_getitem(pubkey);

        System.out.println("TEST HASH");

        String text = "This is the text to hash";
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashbyte = digest.digest(text.getBytes(StandardCharsets.UTF_8));

        byte[] a = hashbyte;
        SWIGTYPE_p_unsigned_char b = byteToUint8(a);
        byte[] c = uint8toByte(b, a.length);
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(c));

        int hash_size = 10;
        SWIGTYPE_p_unsigned_char hash = b; //uECC.new_uint8Array(hash_size);
        SWIGTYPE_p_unsigned_char signature = uECCSwig.new_uint8Array(pubsize);

        System.out.println("TEST KEY 1");
        uECCSwig.uECC_sign(privkey, hash, hash_size, signature, curve);
        System.out.println("sig1=" + bytesToHex(uint8toByte(signature, pubsize)));
        System.out.println(uECCSwig.uECC_verify(pubkey, hash, hash_size, signature, curve));
        System.out.println(uECCSwig.uECC_verify(pubkey2, hash, hash_size, signature, curve));

        System.out.println("TEST KEY 2");
        uECCSwig.uECC_sign(privkey2, hash, hash_size, signature, curve);
        System.out.println("sig2=" + bytesToHex(uint8toByte(signature, pubsize)));
        System.out.println(uECCSwig.uECC_verify(pubkey, hash, hash_size, signature, curve));
        System.out.println(uECCSwig.uECC_verify(pubkey2, hash, hash_size, signature, curve));

        System.out.println("TEST PREVIOUS SIG 3");

        String[] oldSigs = { "2A59B3CCCD18D85B6C566A5CC5408929E7ABC37A8202A114E679DF6338D64F0CCA69E207EDEA9BE8",
                "9AC1ED59BA8DE83ECFC4239AD370BE73DB7CDDE6A3D8592BA8F6F115B8AF8CD3E569B517AE381EC6",
            "0874C1CFE18E7F7C782A7EBD37E2CECEE7900B38B72A664719F60714E689F2A91E2427EA1800811F" };

        for (String oldSig : oldSigs) {
            SWIGTYPE_p_unsigned_char signature3 = byteToUint8(
                    asBytes(oldSig));
            System.out.println("oldsig=" + bytesToHex(uint8toByte(signature3, pubsize)));
            System.out.println(uECCSwig.uECC_verify(pubkey2, hash, hash_size, signature3, curve));
            uECCSwig.delete_uint8Array(signature3);
        }

        uECCSwig.delete_uint8Array(hash);
        uECCSwig.delete_uint8Array(signature);

    }

    public static byte[] uint8toByte(SWIGTYPE_p_unsigned_char array, int size) {
        byte[] result = new byte[size];
        for (int i = 0; i < size; i++) {
            result[i] = (byte) uECCSwig.uint8Array_getitem(array, i);
        }
        return result;
    }

    public static SWIGTYPE_p_unsigned_char byteToUint8(byte[] bytes) {
        SWIGTYPE_p_unsigned_char result = uECCSwig.new_uint8Array(bytes.length);
        for (int i = 0; i < bytes.length; i++) {
        	uECCSwig.uint8Array_setitem(result, i, bytes[i]);
        }
        return result;
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] asBytes(String s) {
        String s2;
        byte[] b = new byte[s.length() / 2];
        int i;
        for (i = 0; i < s.length() / 2; i++) {
            s2 = s.substring(i * 2, i * 2 + 2);
            b[i] = (byte) (Integer.parseInt(s2, 16) & 0xff);
        }
        return b;
    }

}
```
