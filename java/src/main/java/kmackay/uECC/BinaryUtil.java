package kmackay.uECC;

import kmackay.swig.uECC.SWIGTYPE_p_unsigned_char;
import kmackay.swig.uECC.uECCSwig;

public class BinaryUtil {
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
