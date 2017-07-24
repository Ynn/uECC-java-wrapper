package kmackay.uECC;

import kmackay.swig.uECC.SWIGTYPE_p_unsigned_char;
import kmackay.swig.uECC.uECCSwig;

public class uECCPublicKey extends uECCKey {

	public uECCPublicKey(byte[] bytes, uECCCurve curve) {
		super(bytes, curve);
	}

	public uECCPublicKey(uECCCurve curve) {
		super(curve);
	}

	public uECCPublicKey(String hex, uECCCurve curve) {
		super(hex, curve);
	}

	public uECCPublicKey(SWIGTYPE_p_unsigned_char key, uECCCurve curve) {
		super(key, curve);
	}
	
	public boolean verify(byte[] hash, byte[] signature) {
		checkDestroyed();
        SWIGTYPE_p_unsigned_char p_hash = BinaryUtil.byteToUint8(hash);
        SWIGTYPE_p_unsigned_char p_signature = BinaryUtil.byteToUint8(signature);
        int i =uECCSwig.uECC_verify(this.getPointer(), p_hash, hash.length, p_signature, curve.get());
        uECCSwig.delete_uint8Array(p_signature);
        uECCSwig.delete_uint8Array(p_hash);
		return i == 1;
	}
	
	public boolean isValid() {
		checkDestroyed();
		return uECCSwig.uECC_valid_public_key(key, curve.get())==1;
	}

	@Override
	public int getKeySize() {
		checkDestroyed();
		return curve.getPublicKeySize();
	}

}
