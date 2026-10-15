/*
 KotlinGram — mascot helper.
*/
package com.kotlingram.messenger;

import org.telegram.messenger.R;

public class KotlinMascot {

    public enum Mood {
        SMILE(R.drawable.kg_mascot_smile),         // welcome, success, ghost off
        NEUTRAL(R.drawable.kg_mascot_neutral),     // calm, "shh", ghost mode on, empty states
        SURPRISED(R.drawable.kg_mascot_surprised), // new / fast / "wow" moments
        WORRIED(R.drawable.kg_mascot_worried),     // warnings, no connection, security
        CRY(R.drawable.kg_mascot_cry);             // errors, failed login, nothing found

        public final int res;

        Mood(int res) {
            this.res = res;
        }
    }

    /** Intro pages: 0 welcome, 1 fast, 2 free, 3 powerful, 4 secure, 5 private */
    public static Mood forIntroPage(int page) {
        switch (page) {
            case 1: return Mood.SURPRISED;
            case 3: return Mood.NEUTRAL;
            case 4: return Mood.WORRIED;
            default: return Mood.SMILE;
        }
    }

    public static Mood forGhost(boolean ghostOn) {
        return ghostOn ? Mood.NEUTRAL : Mood.SMILE;
    }
}
