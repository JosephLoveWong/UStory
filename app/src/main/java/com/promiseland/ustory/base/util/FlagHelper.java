package com.promiseland.ustory.base.util;

public class FlagHelper {
    public static boolean hasFlag(int allFlags, int flagToCheck) {
        return (allFlags & flagToCheck) == flagToCheck;
    }

    public static int createFlags(int... flags) {
        int result = 0;
        for (int flag : flags) {
            result |= flag;
        }
        return result;
    }
}
