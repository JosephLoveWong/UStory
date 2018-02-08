package com.promiseland.ustory.base.util;

import android.content.Context;
import android.os.Environment;
import java.io.File;

public final class FileHelper {
    public static File getFileDirectory(Context context) {
        if (APILevelHelper.isAPILevelMinimal(23)) {
            return context.getCacheDir();
        }
        return Environment.getExternalStorageDirectory();
    }

    public static boolean rename(Context context, String from, String to) {
        File oldFolder = new File(getFileDirectory(context), from);
        if (oldFolder.exists()) {
            return oldFolder.renameTo(new File(getFileDirectory(context), to));
        }
        return false;
    }

    public static boolean delete(Context context, String filePath) {
        int i = 0;
        File file = new File(getFileDirectory(context), filePath);
        if (!file.exists()) {
            return false;
        }
        if (file.isDirectory() && file.listFiles() != null) {
            File[] listFiles = file.listFiles();
            int length = listFiles.length;
            while (i < length) {
                deleteRecursive(listFiles[i]);
                i++;
            }
        }
        return file.delete();
    }

    private static boolean deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteRecursive(child);
            }
        }
        if (fileOrDirectory.exists() && fileOrDirectory.delete()) {
            return true;
        }
        return false;
    }
}
