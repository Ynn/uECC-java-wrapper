package uECC;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

import cz.adamh.utils.NativeUtils;
import kmackay.swig.uECC.SWIGTYPE_p_uECC_Curve_t;
import kmackay.swig.uECC.SWIGTYPE_p_unsigned_char;
import kmackay.swig.uECC.uECC;

public class Example {
	  static {   
		    try {    
		      NativeUtils.loadLibraryFromJar("/libuECC.so");   
		    } catch (IOException e) {    
              e.printStackTrace(); 
              System.exit(0);
		    }    
		  }  
	
    public static void main(String argv[]) throws Throwable {
        //uECC.test();
        SWIGTYPE_p_uECC_Curve_t curve = uECC.uECC_secp160r1();
        System.out.println(curve);

        int pubsize = uECC.uECC_curve_public_key_size(curve);
        int privsize = uECC.uECC_curve_private_key_size(curve);
        System.out.println(privsize);
        System.out.println(pubsize);
        SWIGTYPE_p_unsigned_char pubkey = uECC.new_uint8Array(pubsize);
        SWIGTYPE_p_unsigned_char privkey = uECC.new_uint8Array(privsize);

        uECC.uECC_make_key(pubkey, privkey, curve);
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
        SWIGTYPE_p_unsigned_char signature = uECC.new_uint8Array(pubsize);

        System.out.println("TEST KEY 1");
        uECC.uECC_sign(privkey, hash, hash_size, signature, curve);
        System.out.println("sig1=" + bytesToHex(uint8toByte(signature, pubsize)));
        System.out.println(uECC.uECC_verify(pubkey, hash, hash_size, signature, curve));
        System.out.println(uECC.uECC_verify(pubkey2, hash, hash_size, signature, curve));

        System.out.println("TEST KEY 2");
        uECC.uECC_sign(privkey2, hash, hash_size, signature, curve);
        System.out.println("sig2=" + bytesToHex(uint8toByte(signature, pubsize)));
        System.out.println(uECC.uECC_verify(pubkey, hash, hash_size, signature, curve));
        System.out.println(uECC.uECC_verify(pubkey2, hash, hash_size, signature, curve));

        System.out.println("TEST PREVIOUS SIG 3");

        String[] oldSigs = { "2A59B3CCCD18D85B6C566A5CC5408929E7ABC37A8202A114E679DF6338D64F0CCA69E207EDEA9BE8",
                "9AC1ED59BA8DE83ECFC4239AD370BE73DB7CDDE6A3D8592BA8F6F115B8AF8CD3E569B517AE381EC6",
            "0874C1CFE18E7F7C782A7EBD37E2CECEE7900B38B72A664719F60714E689F2A91E2427EA1800811F" };

        for (String oldSig : oldSigs) {
            SWIGTYPE_p_unsigned_char signature3 = byteToUint8(
                    asBytes(oldSig));
            System.out.println("oldsig=" + bytesToHex(uint8toByte(signature3, pubsize)));
            System.out.println(uECC.uECC_verify(pubkey2, hash, hash_size, signature3, curve));
            uECC.delete_uint8Array(signature3);
        }

        uECC.delete_uint8Array(hash);
        uECC.delete_uint8Array(signature);

    }

    public static byte[] uint8toByte(SWIGTYPE_p_unsigned_char array, int size) {
        byte[] result = new byte[size];
        for (int i = 0; i < size; i++) {
            result[i] = (byte) uECC.uint8Array_getitem(array, i);
        }
        return result;
    }

    public static SWIGTYPE_p_unsigned_char byteToUint8(byte[] bytes) {
        SWIGTYPE_p_unsigned_char result = uECC.new_uint8Array(bytes.length);
        for (int i = 0; i < bytes.length; i++) {
            uECC.uint8Array_setitem(result, i, bytes[i]);
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
