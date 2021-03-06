/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package kmackay.swig.uECC;

public class uECCSwigJNI {
  public final static native long new_uint8Array(int jarg1);
  public final static native void delete_uint8Array(long jarg1);
  public final static native short uint8Array_getitem(long jarg1, int jarg2);
  public final static native void uint8Array_setitem(long jarg1, int jarg2, short jarg3);
  public final static native int uECC_arch_other_get();
  public final static native int uECC_x86_get();
  public final static native int uECC_x86_64_get();
  public final static native int uECC_arm_get();
  public final static native int uECC_arm_thumb_get();
  public final static native int uECC_arm_thumb2_get();
  public final static native int uECC_arm64_get();
  public final static native int uECC_avr_get();
  public final static native int uECC_OPTIMIZATION_LEVEL_get();
  public final static native int uECC_SQUARE_FUNC_get();
  public final static native int uECC_VLI_NATIVE_LITTLE_ENDIAN_get();
  public final static native int uECC_SUPPORTS_secp160r1_get();
  public final static native int uECC_SUPPORTS_secp192r1_get();
  public final static native int uECC_SUPPORTS_secp224r1_get();
  public final static native int uECC_SUPPORTS_secp256r1_get();
  public final static native int uECC_SUPPORTS_secp256k1_get();
  public final static native int uECC_SUPPORT_COMPRESSED_POINT_get();
  public final static native long uECC_secp160r1();
  public final static native long uECC_secp192r1();
  public final static native long uECC_secp224r1();
  public final static native long uECC_secp256r1();
  public final static native long uECC_secp256k1();
  public final static native void uECC_set_rng(long jarg1);
  public final static native long uECC_get_rng();
  public final static native int uECC_curve_private_key_size(long jarg1);
  public final static native int uECC_curve_public_key_size(long jarg1);
  public final static native int uECC_make_key(long jarg1, long jarg2, long jarg3);
  public final static native int uECC_shared_secret(long jarg1, long jarg2, long jarg3, long jarg4);
  public final static native void uECC_compress(long jarg1, long jarg2, long jarg3);
  public final static native void uECC_decompress(long jarg1, long jarg2, long jarg3);
  public final static native int uECC_valid_public_key(long jarg1, long jarg2);
  public final static native int uECC_compute_public_key(long jarg1, long jarg2, long jarg3);
  public final static native int uECC_sign(long jarg1, long jarg2, long jarg3, long jarg4, long jarg5);
  public final static native void uECC_HashContext_init_hash_set(long jarg1, uECC_HashContext jarg1_, long jarg2);
  public final static native long uECC_HashContext_init_hash_get(long jarg1, uECC_HashContext jarg1_);
  public final static native void uECC_HashContext_update_hash_set(long jarg1, uECC_HashContext jarg1_, long jarg2);
  public final static native long uECC_HashContext_update_hash_get(long jarg1, uECC_HashContext jarg1_);
  public final static native void uECC_HashContext_finish_hash_set(long jarg1, uECC_HashContext jarg1_, long jarg2);
  public final static native long uECC_HashContext_finish_hash_get(long jarg1, uECC_HashContext jarg1_);
  public final static native void uECC_HashContext_block_size_set(long jarg1, uECC_HashContext jarg1_, long jarg2);
  public final static native long uECC_HashContext_block_size_get(long jarg1, uECC_HashContext jarg1_);
  public final static native void uECC_HashContext_result_size_set(long jarg1, uECC_HashContext jarg1_, long jarg2);
  public final static native long uECC_HashContext_result_size_get(long jarg1, uECC_HashContext jarg1_);
  public final static native void uECC_HashContext_tmp_set(long jarg1, uECC_HashContext jarg1_, long jarg2);
  public final static native long uECC_HashContext_tmp_get(long jarg1, uECC_HashContext jarg1_);
  public final static native long new_uECC_HashContext();
  public final static native void delete_uECC_HashContext(long jarg1);
  public final static native int uECC_sign_deterministic(long jarg1, long jarg2, long jarg3, long jarg4, uECC_HashContext jarg4_, long jarg5, long jarg6);
  public final static native int uECC_verify(long jarg1, long jarg2, long jarg3, long jarg4, long jarg5);
  public final static native int uECC_PLATFORM_get();
  public final static native int uECC_ARM_USE_UMAAL_get();
  public final static native int uECC_WORD_SIZE_get();
  public final static native int SUPPORTS_INT128_get();
  public final static native int HIGH_BIT_SET_get();
  public final static native int uECC_WORD_BITS_get();
  public final static native int uECC_WORD_BITS_SHIFT_get();
  public final static native int uECC_WORD_BITS_MASK_get();
}
