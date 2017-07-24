package kmackay.uECC;
import java.io.IOException;

import cz.adamh.utils.NativeUtils;
import kmackay.swig.uECC.uECCSwigConstants;

public class uECC implements uECCSwigConstants {
	static {   
		try {    
			NativeUtils.loadLibraryFromJar("/libuECC.so");   
		} catch (IOException e) {    
			e.printStackTrace(); // This is probably not the best way to handle exception :-)  
			System.exit(1);
		}    
	}  	
/*
  public static int uECC_make_key(SWIGTYPE_p_unsigned_char public_key, SWIGTYPE_p_unsigned_char private_key, SWIGTYPE_p_uECC_Curve_t curve);
  public static int uECC_shared_secret(SWIGTYPE_p_unsigned_char public_key, SWIGTYPE_p_unsigned_char private_key, SWIGTYPE_p_unsigned_char secret, SWIGTYPE_p_uECC_Curve_t curve);
  public static void uECC_compress(SWIGTYPE_p_unsigned_char public_key, SWIGTYPE_p_unsigned_char compressed, SWIGTYPE_p_uECC_Curve_t curve);
  public static void uECC_decompress(SWIGTYPE_p_unsigned_char compressed, SWIGTYPE_p_unsigned_char public_key, SWIGTYPE_p_uECC_Curve_t curve);
  public static int uECC_valid_public_key(SWIGTYPE_p_unsigned_char public_key, SWIGTYPE_p_uECC_Curve_t curve);
  public static int uECC_compute_public_key(SWIGTYPE_p_unsigned_char private_key, SWIGTYPE_p_unsigned_char public_key, SWIGTYPE_p_uECC_Curve_t curve);
  public static int uECC_sign(SWIGTYPE_p_unsigned_char private_key, SWIGTYPE_p_unsigned_char message_hash, long hash_size, SWIGTYPE_p_unsigned_char signature, SWIGTYPE_p_uECC_Curve_t curve);
  public static int uECC_sign_deterministic(SWIGTYPE_p_unsigned_char private_key, SWIGTYPE_p_unsigned_char message_hash, long hash_size, uECC_HashContext hash_context, SWIGTYPE_p_unsigned_char signature, SWIGTYPE_p_uECC_Curve_t curve);
  public static int uECC_verify(SWIGTYPE_p_unsigned_char public_key, SWIGTYPE_p_unsigned_char message_hash, long hash_size, SWIGTYPE_p_unsigned_char signature, SWIGTYPE_p_uECC_Curve_t curve);
*/
}

