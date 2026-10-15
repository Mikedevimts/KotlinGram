/*
 KotlinGram stub (original exteraGram boost package is closed-source).
 Message encryption is disabled: nothing is encrypted or decrypted.
*/
package com.exteragram.messenger.boost.encryption;

import org.telegram.messenger.MessageObject;

public class EncryptionHelper {

    // own interface: java.util.function.Consumer needs API 24+
    public interface Callback {
        void accept(String value);
    }

    public static final String[] names = new String[]{""};

    private static final BaseEncryptor NONE = new BaseEncryptor();

    public static boolean isEncrypted(String text) {
        return false;
    }

    public static boolean isEncrypted(MessageObject messageObject, Object unused) {
        return false;
    }

    public static BaseEncryptor getEncryptorBy(long dialogId) {
        return NONE;
    }

    public static int getEncryptorTypeFor(long dialogId) {
        return 0;
    }

    public static void setEncryptorTypeFor(long dialogId, int type) {
    }

    public static void encryptMessage(String text, long dialogId, int type, Callback callback) {
        if (callback != null) {
            callback.accept(text);
        }
    }

    public static String encryptMessage(String text, long dialogId, BaseEncryptor encryptor) {
        return text;
    }

    public static MessageObject decryptMessage(MessageObject messageObject) {
        return messageObject;
    }

    public static MessageObject decryptMessage(MessageObject messageObject, MessageObject.GroupedMessages group) {
        return messageObject;
    }
}
