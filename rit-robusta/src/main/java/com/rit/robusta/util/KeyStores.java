package com.rit.robusta.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public final class KeyStores {

    private static final String FILE_PREFIX = "file:";

    private KeyStores() {
    }

    public static KeyStore readKeyStore(String keyStorePath, char... password) throws KeyStoreException {
        var filePath = normalizeFilePath(keyStorePath);
        try (InputStream resourceAsStream = Files.getAsStream(filePath)) {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(resourceAsStream, password);
            return keyStore;
        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException ex) {
            throw new KeyStoreException(ex);
        }
    }

    private static String normalizeFilePath(String path) {
        if (path.contains(FILE_PREFIX)) {
            return path.replace(FILE_PREFIX, Strings.EMPTY);
        }
        return path;
    }
}
