package com.promiseland.ustory.base.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import java.io.ByteArrayOutputStream;
import okhttp3.MediaType;
import okhttp3.MultipartBody.Part;
import okhttp3.RequestBody;

public class FileUploadHelper {
    public static Part getMultipartBodyForImageUpload(String fieldName, String fileName, Bitmap imageToUpload) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageToUpload.compress(CompressFormat.JPEG, 100, stream);
        return Part.createFormData(fieldName, fileName, RequestBody.create(MediaType.parse("multipart/form-data"), stream.toByteArray()));
    }
}
