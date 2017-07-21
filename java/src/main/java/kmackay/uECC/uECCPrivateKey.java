package kmackay.uECC;

import kmackay.swig.uECC.SWIGTYPE_p_unsigned_char;
import kmackay.swig.uECC.uECCSwig;

public class uECCPrivateKey extends uECCKey {

	public uECCPrivateKey(byte[] bytes, uECCCurve curve) {
		super(bytes, curve);
	}

	public uECCPrivateKey(uECCCurve curve) {
		super(curve);
	}

	public uECCPrivateKey(String hex, uECCCurve curve) {
		super(hex, curve);
	}

	public uECCPrivateKey(SWIGTYPE_p_unsigned_char key, uECCCurve curve) {
		super(key, curve);
	}

	@Override
	public int getKeySize() {
		return curve.getPrivateKeySize();
	}
	
	public byte[] sign(byte[] hash) {
        SWIGTYPE_p_unsigned_char p_hash = BinaryUtil.byteToUint8(hash);
        SWIGTYPE_p_unsigned_char p_signature = uECCSwig.new_uint8Array(curve.getPublicKeySize());
        uECCSwig.uECC_sign(this.getPointer(), p_hash, hash.length, p_signature, curve.get());
        byte[] signature = BinaryUtil.uint8toByte(p_signature, curve.getPublicKeySize());
        uECCSwig.delete_uint8Array(p_signature);
        uECCSwig.delete_uint8Array(p_hash);
		return signature;
	}


}
