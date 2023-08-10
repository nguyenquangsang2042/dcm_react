package com.vuthao.VNADCM.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.base.activity.BaseActivity;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Functions {
    // Mang cac ky tu goc co dau
    private static final char[] SOURCE_CHARACTERS = {'À', 'Á', 'Â', 'Ã', 'È', 'É',
            'Ê', 'Ì', 'Í', 'Ò', 'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â',
            'ã', 'è', 'é', 'ê', 'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý',
            'Ă', 'ă', 'Đ', 'đ', 'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ',
            'ạ', 'Ả', 'ả', 'Ấ', 'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ',
            'Ắ', 'ắ', 'Ằ', 'ằ', 'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ',
            'ẻ', 'Ẽ', 'ẽ', 'Ế', 'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ',
            'Ỉ', 'ỉ', 'Ị', 'ị', 'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ',
            'ổ', 'Ỗ', 'ỗ', 'Ộ', 'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ',
            'Ợ', 'ợ', 'Ụ', 'ụ', 'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ',
            'ữ', 'Ự', 'ự'};

    // Mang cac ky tu thay the khong dau
    private static final char[] DESTINATION_CHARACTERS = {'A', 'A', 'A', 'A', 'E',
            'E', 'E', 'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a',
            'a', 'a', 'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u',
            'y', 'A', 'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u',
            'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A',
            'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e',
            'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E',
            'e', 'I', 'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
            'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
            'o', 'O', 'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',
            'U', 'u', 'U', 'u'};

    public static Functions share = new Functions();

    public static char removeAccent(char ch) {
        int index = Arrays.binarySearch(SOURCE_CHARACTERS, ch);
        if (index >= 0) {
            ch = DESTINATION_CHARACTERS[index];
        }
        return ch;
    }

    /**
     * Bo dau 1 chuoi
     *
     * @param s String
     * @return String after removed
     */
    public static String removeAccent(String s) {
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < sb.length(); i++) {
            sb.setCharAt(i, removeAccent(sb.charAt(i)));
        }
        return sb.toString();
    }

    /* Get current width of screen */
    public int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /* Get current Height of screen */
    public int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public String html2text(String html) {
        return Jsoup.parse(html).text();
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isNullOrEmpty(AbstractList items) {
        return items == null || items.isEmpty();
    }

    public int convertDpToPixel(float dp, Context context) {
        return (int) (dp * ((float) context.getResources().getDisplayMetrics().densityDpi / (float) DisplayMetrics.DENSITY_DEFAULT));
    }

    public String decodeBase64(String s) {
        String res = "";
        byte[] data = Base64.decode(s, Base64.DEFAULT);
        res = new String(data, StandardCharsets.UTF_8);
        return res.trim();
    }

    private static long getFileSizeInBytes(File f) {
        long ret = 0;
        if (f.isFile()) {
            return f.length();
        } else if (f.isDirectory()) {
            File[] contents = f.listFiles();
            for (File content : contents) {
                if (content.isFile()) {
                    ret += content.length();
                } else if (content.isDirectory())
                    ret += getFileSizeInBytes(content);
            }
        }
        return ret;
    }

    public String encodeBase64(String s) {
        String res = "";
        byte[] data = s.getBytes(StandardCharsets.UTF_8);
        res = Base64.encodeToString(data, Base64.DEFAULT);
        return res.trim();
    }

    public String replaceString(String string) {
        return string.replaceAll("[;\\/:*?\"<>|&']"," ");
    }

    public long formatStringToLong(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date d = sdf.parse(date);
            return d.getTime();
        } catch (Exception e) {
            return 0;
        }
    }

    public String formatLongToString(long l, String format) {
        DateFormat df = new SimpleDateFormat(format);
        String dateString = "";
        try {
            Date d = new Date(l);
            dateString = df.format(d);
            return dateString;
        } catch (Exception e) {
            return "";
        }
    }

    public String getFormatFileSize(long size) {
        DecimalFormat df = new DecimalFormat("0.00");
        float sizeKb = 1024.0f;
        float sizeMb = sizeKb * sizeKb;
        float sizeGb = sizeMb * sizeKb;
        float sizeTerra = sizeGb * sizeKb;

        if (size < sizeMb)
            return df.format(size / sizeKb) + " KB";
        else if (size < sizeGb)
            return df.format(size / sizeMb) + " MB";
        else if (size < sizeTerra)
            return df.format(size / sizeGb) + " GB";

        return "";
    }

    /**
     * Colorize a specific substring in a string for TextView. Use it like this: <pre>
     * textView.setText(
     *     Strings.colorized("The some words are black some are the default.","black", Color.BLACK),
     *     TextView.BufferType.SPANNABLE
     * );
     * </pre>
     *
     * @param text Text that contains a substring to colorize
     * @param word The substring to colorize
     * @param argb The color
     * @return the Spannable for TextView's consumption
     */
    public static Spannable colorized(final String text, final String word, final int argb) {
        final Spannable spannable = new SpannableString(text);
        int substringStart = 0;
        int start;
        while ((start = text.indexOf(word, substringStart)) >= 0) {
            spannable.setSpan(
                    new ForegroundColorSpan(argb), start, start + word.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            substringStart = start + word.length();
        }
        return spannable;
    }

    public static <T> ArrayList<T> jsonToList(String json, Class<T> classOfT) {
        TypeToken<T> typeToken = (TypeToken<T>) TypeToken.getParameterized(ArrayList.class, classOfT);
        return new Gson().fromJson(json, typeToken.getType());
    }

    public static <T> T jsonToObj(String json, Class<T> classOfT) {
        return new Gson().fromJson(json, classOfT);
    }

    public static void Toast(Context context, String thongbao) {
        Toast.makeText(context, thongbao, Toast.LENGTH_SHORT).show();
    }

    public static String isNullOrDefault(String s, String def) {
        if (!isNullOrEmpty(s)) {
            return s;
        } else {
            return "";
        }
    }

    public String trimEnd(String s, String suffix) {
        if (s.endsWith(suffix)) {
            return s.substring(0, s.length() - suffix.length());
        }
        return s;
    }

    public static void TrackingError(Exception exception) {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "TrackingErrorHP.txt");
            FileOutputStream out = new FileOutputStream(file, file.exists());
            String mess = "\n======================== " + share.formatLongToString(System.currentTimeMillis(), "dd/MM/yyyy HH:mm:ss") + "=============================\n";
            mess += exception.getMessage();
            StringWriter errors = new StringWriter();
            exception.printStackTrace(new PrintWriter(errors));
            mess += errors.toString();
            out.write(mess.getBytes(StandardCharsets.UTF_8));
            out.flush();
            out.close();
        } catch (Exception ex) {
            Log.i("ERROR", exception.getMessage());
        }
    }

    public static void TrackingError(String message) {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "TrackingErrorHP.txt");
            FileOutputStream out = new FileOutputStream(file, file.exists());
            String mess = "\n======================== " + share.formatLongToString(System.currentTimeMillis(), "dd/MM/yyyy HH:mm:ss") + "=============================\n";
            mess += message;
            out.write(mess.getBytes(StandardCharsets.UTF_8));
            out.flush();
            out.close();
        } catch (Exception ex) {
            Log.i("ERROR", ex.getMessage());
        }
    }

    public boolean isEmulator() {
        return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("sdk_gphone64_arm64")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator");
    }

    public ArrayList<String> getAllProperty(Class<?> classOfT) {
        ArrayList<String> res = new ArrayList<>();
        Field[] fields = classOfT.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            res.add(f.getName());
        }

        return res;
    }

    public ArrayList<Integer> getParameterUrlDoc(String url) {
        ArrayList<Integer> retValue = new ArrayList<>();
        if (!Functions.isNullOrEmpty(url)) {
            if (url.indexOf(".html") > 0) {
                url = url.substring(0, url.indexOf(".html"));

                if (!Functions.isNullOrEmpty(url)) {
                    String[] arrs = url.split("/");
                    if (arrs.length > 2) {
                        String[] end = arrs[arrs.length - 1].split("-");
                        String mResourceId = end[end.length - 1];
                        end = arrs[arrs.length - 2].split("-");
                        String mCategoryId = end[end.length - 1];

                        retValue.add(Integer.parseInt(mResourceId));
                        retValue.add(1);
                        retValue.add(Integer.parseInt(mCategoryId));
                    }
                }
            }
        }
        return retValue;
    }

    public String getFileExtension(String filePath) {
        int strLength = filePath.lastIndexOf(".");
        if (strLength > 0) {
            return filePath.substring(strLength).toLowerCase();
        }
        return null;
    }

    public String getMineTypeExtension(String fileName) {
        String extension = getFileExtension(fileName);

        switch (extension) {
            case ".doc":
            case ".docx": {
                return "application/msword";
            }
            case ".pdf": {
                return "application/pdf";
            }
            case ".ppt": {
                return "application/vnd.ms-powerpoint";
            }
            case ".xls":
            case ".xlsx": {
                return "application/vnd.ms-excel";
            }
            case ".zip": {
                return "application/zip";
            }
        }

        return "";
    }

    public void openFile(Activity activity, String path) {
        try {
            File file = new File(path);
            Uri uri = Uri.fromFile(file);

            String extension = getFileExtension(path);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            switch (extension) {
                case ".doc":
                case ".docx": {
                    intent.setDataAndType(uri, "application/msword");
                    break;
                }
                case ".pdf": {
                    intent.setDataAndType(uri, "application/pdf");
                    break;
                }
                case ".ppt": {
                    intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
                    break;
                }
                case ".xls":
                case ".xlsx": {
                    intent.setDataAndType(uri, "application/vnd.ms-excel");
                    break;
                }
                case ".zip": {
                    intent.setDataAndType(uri, "application/zip");
                    break;
                }
                case ".rar": {
                    intent.setDataAndType(uri, "application/x-rar-compressed");
                    break;
                }
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);

        } catch (Exception ex) {
            ((BaseActivity) activity).showToast(activity.getString(R.string.open_file_error), 60, null);
        }
    }
}
