package kmackay.uECC;

import java.io.Closeable;
import java.io.IOException;

import kmackay.swig.uECC.SWIGTYPE_p_unsigned_char;
import kmackay.swig.uECC.uECCSwig;

public abstract class uECCKey implements Closeable{

	protected final SWIGTYPE_p_unsigned_char key;
	protected final uECCCurve curve;

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
		uECCSwig.delete_uint8Array(key);
	}

	public byte[] asBytes() {
		return BinaryUtil.uint8toByte(key, getKeySize());
	}

	public String asHex() {
		return BinaryUtil.bytesToHex(asBytes());
	}

	public void close() throws IOException {
		this.destroy();
	}

	@Override
	protected void finalize() throws Throwable {
		this.destroy();
	}

	public abstract int getKeySize();

	@Override
	public String toString() {
		return asHex();
	}
	
}
