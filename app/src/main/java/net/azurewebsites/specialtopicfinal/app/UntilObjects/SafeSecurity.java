/*
 * Michael Burton Copyright (c) 2014.
 */

package net.azurewebsites.specialtopicfinal.app.UntilObjects;


import android.util.Base64;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Mike on 06/05/2014.
 */
public class SafeSecurity {
    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
    // The following constants may be changed without breaking existing hashes.
    public static final int SALT_BYTE_SIZE = 24;
    public static final int HASH_BYTE_SIZE = 24;
    public static final int PBKDF2_ITERATIONS = 1000;
    public static final int ITERATION_INDEX = 0;
    public static final int SALT_INDEX = 1;
    public static final int PBKDF2_INDEX = 2;
    private static String ACCESS_KEY = "/~-QlEuC_30.yGySe_0*-q=_+-R90--]DCBN*Y4N->._";

    public static String encrypt(String TEXT_TO_ENCRYPT) throws Exception {
        String ENCRYPTED_STRING_RESULT;

        Cipher CIPHER = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] KEY_BYTE_ARRAY = new byte[16];
        byte[] BYTE_ARRAY_ACCESS_KEY = ACCESS_KEY.getBytes("UTF-8");
        int BYTE_ARRAY_LENGTH = BYTE_ARRAY_ACCESS_KEY.length;
        if (BYTE_ARRAY_LENGTH > KEY_BYTE_ARRAY.length) BYTE_ARRAY_LENGTH = KEY_BYTE_ARRAY.length;
        System.arraycopy(BYTE_ARRAY_ACCESS_KEY, 0, KEY_BYTE_ARRAY, 0, BYTE_ARRAY_LENGTH);
        SecretKeySpec SECRET_KEY_SPEC = new SecretKeySpec(KEY_BYTE_ARRAY, "AES");
        IvParameterSpec IV_PARAMETER_SPEC = new IvParameterSpec(KEY_BYTE_ARRAY);
        CIPHER.init(Cipher.ENCRYPT_MODE, SECRET_KEY_SPEC, IV_PARAMETER_SPEC);
        ENCRYPTED_STRING_RESULT = Base64.encodeToString(CIPHER.doFinal(TEXT_TO_ENCRYPT.getBytes("UTF-8")), Base64.DEFAULT);


        return ENCRYPTED_STRING_RESULT;
    }

    public static String decrypt(String TEXT_TO_DECRYPT) throws Exception {

        String DECRYPTED_STRING_RESULT;
        Cipher CIPHER = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] KEY_BYTE_ARRAY = new byte[16];
        byte[] BYTE_ARRAY_ACCESS_KEY = ACCESS_KEY.getBytes("UTF-8");
        int BYTE_ARRAY_LENGTH = BYTE_ARRAY_ACCESS_KEY.length;
        if (BYTE_ARRAY_LENGTH > KEY_BYTE_ARRAY.length) BYTE_ARRAY_LENGTH = KEY_BYTE_ARRAY.length;
        System.arraycopy(BYTE_ARRAY_ACCESS_KEY, 0, KEY_BYTE_ARRAY, 0, BYTE_ARRAY_LENGTH);
        SecretKeySpec SECRET_KEY_SPEC = new SecretKeySpec(KEY_BYTE_ARRAY, "AES");
        IvParameterSpec IV_PARAMETER_SPEC = new IvParameterSpec(KEY_BYTE_ARRAY);
        CIPHER.init(Cipher.DECRYPT_MODE, SECRET_KEY_SPEC, IV_PARAMETER_SPEC);
        byte[] BYTE_ARRAY_RESULT = CIPHER.doFinal(Base64.decode(TEXT_TO_DECRYPT, Base64.DEFAULT));
        DECRYPTED_STRING_RESULT = new String(BYTE_ARRAY_RESULT, "UTF-8");

        return DECRYPTED_STRING_RESULT;
    }
    @Deprecated
    public static String getMD5(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        input += ACCESS_KEY;
        byte[] messageDigest = md.digest(input.getBytes());
        BigInteger number = new BigInteger(1, messageDigest);
        String hashtext = number.toString(16);


        // Now we need to zero pad it if you actually want the full 32 chars.
        while (hashtext.length() < 32)
            hashtext = "0" + hashtext;
        return hashtext;
    }
    /**
     * Converts PASSWORD_TO_HASH to a Char Array then returns hashed string .
     *
     * @param PASSWORD_TO_HASH the password to hash
     * @return a salted PBKDF2 hash of the password
     */
    public static String createHash(String PASSWORD_TO_HASH)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        return createHash(PASSWORD_TO_HASH.toCharArray());
    }

    /**
     * Returns a salted PBKDF2 hash of the password.
     *
     * @param PASSWORD_TO_HASH_CHAR_ARRAY the password to hash
     * @return a salted PBKDF2 hash of the password
     */
    public static String createHash(char[] PASSWORD_TO_HASH_CHAR_ARRAY)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Generate a RANDOM_SALT salt
        SecureRandom RANDOM_SALT = new SecureRandom();
        byte[] BYTE_ARRAY_SALT = new byte[SALT_BYTE_SIZE];
        RANDOM_SALT.nextBytes(BYTE_ARRAY_SALT);
        // Hash the password
        byte[] BYTE_ARRAY_HASH = pbkdf2(PASSWORD_TO_HASH_CHAR_ARRAY, BYTE_ARRAY_SALT, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
        // format iterations:salt:BYTE_ARRAY_HASH
        return PBKDF2_ITERATIONS + ":" + Base64.encodeToString(BYTE_ARRAY_SALT, Base64.DEFAULT) + ":" + Base64.encodeToString(BYTE_ARRAY_HASH, Base64.DEFAULT);
    }

    /**
     * Validates a password using a hash.
     *
     * @param PASSWORD_TO_VALIDATE    the password to check
     * @param PASSWORD_HASH_TO_VALIDATE the hash of the valid password
     * @return true if the password is correct, false if not
     */
    public static boolean validatePassword(String PASSWORD_TO_VALIDATE, String PASSWORD_HASH_TO_VALIDATE)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        return validatePassword(PASSWORD_TO_VALIDATE.toCharArray(), PASSWORD_HASH_TO_VALIDATE);
    }

    /**
     * Validates a password using a hash.
     *
     * @param CHAR_ARRAY_PASSWORD_TO_VALIDATE    the password to check
     * @param PASSWORD_HASH_TO_VALIDATE the hash of the valid password
     * @return true if the password is correct, false if not
     */
    public static boolean validatePassword(char[] CHAR_ARRAY_PASSWORD_TO_VALIDATE, String PASSWORD_HASH_TO_VALIDATE)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Decode the hash into its parameters
        String[] STRING_ARRAY_PARAMETERS = PASSWORD_HASH_TO_VALIDATE.split(":");
        int ITERATIONS = Integer.parseInt(STRING_ARRAY_PARAMETERS[ITERATION_INDEX]);

        byte[] BYTE_ARRAY_SALT = Base64.decode(STRING_ARRAY_PARAMETERS[SALT_INDEX], Base64.DEFAULT);
        byte[] BYTE_ARRAY_TO_DECODE = Base64.decode(STRING_ARRAY_PARAMETERS[PBKDF2_INDEX], Base64.DEFAULT);
        // Compute the hash of the provided password, using the same BYTE_ARRAY_SALT,
        // iteration count, and hash length
        byte[] BYTE_ARRAY_TEST_HASH = pbkdf2(CHAR_ARRAY_PASSWORD_TO_VALIDATE, BYTE_ARRAY_SALT, ITERATIONS, BYTE_ARRAY_TO_DECODE.length);
        // Compare the hashes in constant time. The password is correct if
        // both hashes match.
        return slowEquals(BYTE_ARRAY_TO_DECODE, BYTE_ARRAY_TEST_HASH);
    }

    /**
     * Compares two byte arrays in length-constant time. This comparison method
     * is used so that password hashes cannot be extracted from an on-line
     * system using BYTE_ARRAY_FIRST timing attack and then attacked off-line.
     *
     * @param BYTE_ARRAY_FIRST the first byte array
     * @param BYTE_ARRAY_SECOND the second byte array
     * @return true if both byte arrays are the same, false if not
     */
    private static boolean slowEquals(byte[] BYTE_ARRAY_FIRST, byte[] BYTE_ARRAY_SECOND) {
        int DIFFERENCE_OF_BYTE_ARRAYS = BYTE_ARRAY_FIRST.length ^ BYTE_ARRAY_SECOND.length;
        for (int i = 0; i < BYTE_ARRAY_FIRST.length && i < BYTE_ARRAY_SECOND.length; i++)
            DIFFERENCE_OF_BYTE_ARRAYS |= BYTE_ARRAY_FIRST[i] ^ BYTE_ARRAY_SECOND[i];
        return DIFFERENCE_OF_BYTE_ARRAYS == 0;
    }

    /**
     * Computes the PBKDF2 hash of a CHAR_ARRAY_PASSWORD.
     *
     * @param CHAR_ARRAY_PASSWORD   the CHAR_ARRAY_PASSWORD to hash.
     * @param BYTE_ARRAY_SALT       the BYTE_ARRAY_SALT
     * @param ITERATIONS the iteration count (slowness factor)
     * @param LENGTH_OF_HASH      the length of the hash to compute in LENGTH_OF_HASH
     * @return the PBDKF2 hash of the CHAR_ARRAY_PASSWORD
     */
    private static byte[] pbkdf2(char[] CHAR_ARRAY_PASSWORD, byte[] BYTE_ARRAY_SALT, int ITERATIONS, int LENGTH_OF_HASH)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec PB_KEY_SEPC = new PBEKeySpec(CHAR_ARRAY_PASSWORD, BYTE_ARRAY_SALT, ITERATIONS, LENGTH_OF_HASH * 8);
        SecretKeyFactory SECRET_KEY_FACTORY = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        return SECRET_KEY_FACTORY.generateSecret(PB_KEY_SEPC).getEncoded();
    }

    /**
     * Converts a string of hexadecimal characters into a byte array.
     *
     * @param hex the hex string
     * @return the hex string decoded into a byte array
     */
    @Deprecated
    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

    /**
     * Converts a byte array into a hexadecimal string.
     *
     * @param array the byte array to convert
     * @return a length*2 character string encoding the byte array
     */
    @Deprecated
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }

}

