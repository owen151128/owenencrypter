package kr.owens.util;

import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * Providing features related to FileEncrypter class
 */
public class FileEncrypter {
    private static final Logger logger = Logger.getLogger(FileEncrypter.class.getName());
    private static FileEncrypter instance;

    private FileEncrypter() {
    }

    /**
     * Return instance for Single-tone pattern
     *
     * @return FileEncrypter instance
     */
    public static synchronized FileEncrypter getInstance() {
        if (instance == null) {
            instance = new FileEncrypter();
        }

        return instance;
    }

    public boolean encryptFile(Path targetPath) {
        return true;
    }
}
