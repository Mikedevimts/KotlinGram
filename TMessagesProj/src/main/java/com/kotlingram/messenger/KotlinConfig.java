/*
 KotlinGram — based on exteraGram (GPLv2), which is based on Telegram for Android.
 This file is part of KotlinGram's own additions.
*/
package com.kotlingram.messenger;

import android.app.Activity;
import android.content.SharedPreferences;

import org.telegram.messenger.ApplicationLoader;

public class KotlinConfig {

    public static final String VERSION = "1.0.0";
    public static final String BASE = "exteraGram 9.6.6";
    // Third-party services. Disabled for now (KotlinGram has its own keys/updates).
    public static final boolean FIREBASE_ENABLED = false;
    public static final boolean OFFICIAL_UPDATES_ENABLED = false;
    public static final String AUTHOR = "Майк Дивемц";
    public static final String CHANNEL_USERNAME = "KotlinGramOfficial";
    public static final String CHANNEL_URL = "https://t.me/KotlinGramOfficial";
    public static final String AUTHOR_URL = "https://t.me/MIKEDEVIM";
    public static final String BRAND_ASSETS_URL = "https://kotlinlang.org/docs/kotlin-brand-assets.html#kotlin-logo";
    public static final String SOURCE_URL = "https://github.com/exteraSquad/exteraGram";

    // Ghost mode
    public static boolean ghostEnabled;      // master switch
    public static boolean ghostHideOnline;   // do not send "online" status
    public static boolean ghostHideRead;     // do not send "read" receipts for chats
    public static boolean ghostHideTyping;   // do not send typing/recording actions
    public static boolean ghostHideListened; // do not send "voice/video listened" receipts

    // Fake Premium (visual only)
    public static boolean fakePremium;       // show decorative star next to own name

    private static SharedPreferences preferences;

    static {
        load();
    }

    private static void load() {
        preferences = ApplicationLoader.applicationContext.getSharedPreferences("kotlingram", Activity.MODE_PRIVATE);
        ghostEnabled = preferences.getBoolean("ghostEnabled", false);
        ghostHideOnline = preferences.getBoolean("ghostHideOnline", true);
        ghostHideRead = preferences.getBoolean("ghostHideRead", true);
        ghostHideTyping = preferences.getBoolean("ghostHideTyping", true);
        ghostHideListened = preferences.getBoolean("ghostHideListened", true);
        fakePremium = preferences.getBoolean("fakePremium", false);
    }

    public static void putBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    // Helpers used by core hooks: master switch AND individual switch.
    public static boolean blockOnline()   { return ghostEnabled && ghostHideOnline; }
    public static boolean blockRead()     { return ghostEnabled && ghostHideRead; }
    public static boolean blockTyping()   { return ghostEnabled && ghostHideTyping; }
    public static boolean blockListened() { return ghostEnabled && ghostHideListened; }
}
