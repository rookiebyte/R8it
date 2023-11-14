package com.rit.robusta.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public final class KeyStores {

    private KeyStores() {
    }

    public static KeyStore readKeyStore(String keyStorePath, char... password) throws KeyStoreException {
        try (InputStream resourceAsStream = Files.getAsStream(keyStorePath)) {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(resourceAsStream, password);
            return keyStore;
        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException ex) {
            throw new KeyStoreException(ex);
        }
    }
}
