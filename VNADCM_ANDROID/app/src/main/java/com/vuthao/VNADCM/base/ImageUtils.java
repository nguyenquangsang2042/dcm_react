package com.vuthao.VNADCM.base;

import android.webkit.MimeTypeMap;

import java.net.URL;

public class ImageUtils {

    public static String extractFileName(String url) {
        String fileName = null;
        try {
            URL urlObj = new URL(url);
            String path = urlObj.getPath();
            fileName = path.substring(path.lastIndexOf('/') + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }
    public static boolean isImageLink(String link) {
        String[] imageExtensions = {"jpg", "jpeg", "png", "gif", "bmp"};

        // Extract the file extension from the URL
        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(link);

        // Check if the extracted extension is in the list of image extensions
        for (String extension : imageExtensions) {
            if (extension.equalsIgnoreCase(fileExtension)) {
                return true;
            }
        }

        return false;
    }
}
