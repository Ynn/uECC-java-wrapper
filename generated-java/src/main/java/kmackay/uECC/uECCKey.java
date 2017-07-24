package kmackay.uECC;

import java.io.Closeable;
import java.io.IOException;

import kmackay.swig.uECC.SWIGTYPE_p_unsigned_char;
import kmackay.swig.uECC.uECCSwig;

public abstract class uECCKey implements Closeable{

	protected final SWIGTYPE_p_unsigned_char key;
	protected final uECCCurve curve;
	protected boolean destroyed = false;

	protected uECCKey(String hex, uECCCurve curve) {
		this.curve = curve;

		byte[] bytes = BinaryUtil.asBytes(hex);
		checkSize(bytes.length);

		key = BinaryUtil.byteToUint8(bytes);
	}

	protected uECCKey(byte[] bytes, uECCCurve curve) {
		this.curve = curve;
		checkSize(bytes.length);
		key = BinaryUtil.byteToUint8(bytes);
	}
	
	public uECCCurve getCurve() {
		checkDestroyed();
		assert curve != null : "The curve can not be null at this stage";
		return curve;
	}

	protected uECCKey(SWIGTYPE_p_unsigned_char key, uECCCurve curve) {
		this.curve = curve;
		if(key == null) {
			throw new IllegalArgumentException("key must be initialized");
		}
		this.key = key;
	}

	public uECCKey(uECCCurve curve) {
		this.curve = curve;
		this.key = uECCSwig.new_uint8Array(getKeySize());
	}

	protected boolean checkSize(int size) {
		return size == getKeySize();
	}

	SWIGTYPE_p_unsigned_char getPointer(){
		return key;
	}

	public void destroy() {
		checkDestroyed();
		uECCSwig.delete_uint8Array(key);
		destroyed = true;
	}

	public byte[] asBytes() {
		checkDestroyed();
		return BinaryUtil.uint8toByte(key, getKeySize());
	}

	public String asHex() {
		checkDestroyed();
		return BinaryUtil.bytesToHex(asBytes());
	}

	public void close() throws IOException {
		this.destroy();
	}

	@Override
	protected void finalize() throws Throwable {
		this.destroy();
	}

	protected void checkDestroyed() {
		if(destroyed) {
			throw new IllegalStateException("Try to use a destroyed key");
		}
	}
	
	public abstract int getKeySize();

	@Override
	public String toString() {
		checkDestroyed();
		return asHex();
	}
	
}
