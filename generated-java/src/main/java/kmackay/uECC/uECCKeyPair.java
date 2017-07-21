package kmackay.uECC;

import kmackay.swig.uECC.uECCSwig;

public class uECCKeyPair {
	public uECCPrivateKey privateKey;
	public uECCPublicKey publicKey;
	
	public static uECCKeyPair makeKeyPair(uECCCurve curve) {
		uECCPublicKey pubKey = new uECCPublicKey(curve);
		uECCPrivateKey privKey = new uECCPrivateKey(curve);
        uECCSwig.uECC_make_key(pubKey.getPointer(), privKey.getPointer(), curve.get());
        return from(privKey, pubKey);
	}
	
	public static uECCKeyPair from(uECCPrivateKey privKey, uECCPublicKey pubKey) {
		return new uECCKeyPair(privKey, pubKey);
	}
	
	private uECCKeyPair(uECCPrivateKey privKey, uECCPublicKey pubKey) {
		this.privateKey= privKey;
		this.publicKey = pubKey;
	}
	
	public uECCPrivateKey getPrivateKey() {
		return privateKey;
	}
	
	public uECCPublicKey getPublicKey() {
		return publicKey;
	}
	
	public byte[] sign(byte[] hash) {
		return getPrivateKey().sign(hash);
	}
	
	public boolean verify(byte[] hash, byte[] signature) {
		return getPublicKey().verify(hash, signature);
	}
	
	public String toString() {
		return String.format("{priv = %s , pub = %s}", getPrivateKey(), getPublicKey());
	}
}
