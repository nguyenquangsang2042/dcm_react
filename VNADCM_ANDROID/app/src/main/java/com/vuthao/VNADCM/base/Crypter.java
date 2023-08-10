package com.vuthao.VNADCM.base;

import static android.icu.lang.UCharacter.JoiningGroup.E;

import android.security.keystore.KeyGenParameterSpec;
import android.util.Log;

import androidx.annotation.NonNull;

import com.vuthao.VNADCM.base.api.session.SerializableHttpCookie;

import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.bouncycastle.asn1.pkcs.RSAPublicKey;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.bouncycastle.jcajce.provider.asymmetric.util.PrimeCertaintyCalculator;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Crypter {
    private static final String PUBLIC_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJex4mluSYKV94FKj3zfC20VkNo2soX6TU90of38DZGYjIZOK41O+Ij/Ikwv+kHJsoZX1dA07gVTRHsInd9nP40CAwEAAQ==";
    private static final String PRIVATE_KEY = "";
    private static final String TAG = Crypter.class
            .getSimpleName();

    private static RSAKeyParameters rsaKeyParameters;

    public static String encrypt(String data) {
        try {
            byte[] keyInfoData = Base64.decode(Crypter.PUBLIC_KEY);
            rsaKeyParameters = (RSAKeyParameters) PublicKeyFactory.createKey(keyInfoData);

            byte[] payloadBytes = data.getBytes(StandardCharsets.UTF_8);
            AsymmetricBlockCipher cipher = getAsymmetricBlockCipher(true);
            byte[] encrypted = process(cipher, payloadBytes);

            return Base64.toBase64String(encrypted);
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
        }

        return null;
    }

    public static String decrypt(String data) {
        try {
            String key = Functions.share.decodeBase64(Crypter.PRIVATE_KEY);
            rsaKeyParameters = (RSAKeyParameters) readPrivateKey(key);

            byte[] payloadBytes = Base64.decode(data);
            AsymmetricBlockCipher cipher = getAsymmetricBlockCipher(false);
            byte[] encrypted = process(cipher, payloadBytes);
            return new String(encrypted);
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
        }

        return null;
    }

    @NonNull
    private static AsymmetricBlockCipher getAsymmetricBlockCipher(boolean forEncryption) {
        PKCS1Encoding cipher = new PKCS1Encoding(new RSAEngine());
        cipher.init(forEncryption, rsaKeyParameters);
        return cipher;
    }

    private static AsymmetricKeyParameter readPrivateKey(String pemData) throws IOException {
        PemReader pr = new PemReader(new StringReader(pemData));
        PEMParser parser = new PEMParser(pr);
        PEMKeyPair keyPair = (PEMKeyPair) parser.readObject();
        return PrivateKeyFactory
                .createKey(keyPair.getPrivateKeyInfo());
    }

    private static byte[] process(AsymmetricBlockCipher cipher, byte[] payloadBytes) throws InvalidCipherTextException {
        int length = payloadBytes.length;
        int blockSize = cipher.getInputBlockSize();

        List<Byte> plainTextBytes = new ArrayList<>();
        for (int chunkPosition = 0; chunkPosition < length; chunkPosition += blockSize) {
            int chunkSize = Math.min(blockSize, length - chunkPosition);
            byte[] bockGet = cipher.processBlock(payloadBytes, chunkPosition, chunkSize);
            for (byte b : bockGet) {
                plainTextBytes.add(b);
            }
        }

        byte[] bytes = new byte[plainTextBytes.size()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = plainTextBytes.get(i);
        }

        return bytes;
    }
}
