package kmackay.uECC;

import java.io.IOException;

import cz.adamh.utils.NativeUtils;
import kmackay.swig.uECC.SWIGTYPE_p_uECC_Curve_t;
import kmackay.swig.uECC.uECCSwig;

public class uECCCurve {
	
	static {   
		try {    
			System.out.println("LOADING libuECC.so");
			NativeUtils.loadLibraryFromJar("/libuECC.so");   
		} catch (IOException e) {    
			System.err.println("Failed to load the library libuECC.so");
			e.printStackTrace(); // This is probably not the best way to handle exception :-)  
			System.exit(1);
		}    
	}  	
	
	private SWIGTYPE_p_uECC_Curve_t curve;
	private CurveTYPE type; 

	public enum CurveTYPE{secp160r1, secp192r1, secp224r1, secp256r1, secp256k1}

	public static uECCCurve uECC_secp160r1() {
		return new uECCCurve(uECCSwig.uECC_secp160r1(),CurveTYPE.secp160r1);
	}
	public static uECCCurve uECC_secp192r1() {
		return new uECCCurve(uECCSwig.uECC_secp192r1(),CurveTYPE.secp192r1);
	}
	public static uECCCurve uECC_secp224r1() {
		return new uECCCurve(uECCSwig.uECC_secp224r1(),CurveTYPE.secp224r1);
	}
	public static uECCCurve uECC_secp256r1() {
		return new uECCCurve(uECCSwig.uECC_secp256r1(),CurveTYPE.secp256r1);
	}
	public static uECCCurve uECC_secp256k1() {
		return new uECCCurve(uECCSwig.uECC_secp256k1(),CurveTYPE.secp256k1);
	}

	private uECCCurve(SWIGTYPE_p_uECC_Curve_t curve, CurveTYPE type) {
		this.curve=curve;
		this.type = type;
	}

	public SWIGTYPE_p_uECC_Curve_t get(){
		return curve;
	}

	public CurveTYPE getType() {
		return type;
	}
	
	public int getPublicKeySize(){
		return uECCSwig.uECC_curve_public_key_size(curve);
	}
	
	public int getPrivateKeySize(){
		return uECCSwig.uECC_curve_private_key_size(curve);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "curve "+ type;
	}

}
