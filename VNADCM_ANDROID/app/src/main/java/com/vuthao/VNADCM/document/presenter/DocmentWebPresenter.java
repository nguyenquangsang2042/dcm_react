package com.vuthao.VNADCM.document.presenter;

import android.content.Context;
import android.graphics.Path;

import androidx.core.content.ContextCompat;

import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.ImageUtils;
import com.vuthao.VNADCM.base.api.ApiAuthController;
import com.vuthao.VNADCM.base.api.ApiDocumentController;
import com.vuthao.VNADCM.base.model.Status;
import com.vuthao.VNADCM.base.model.app.Document;
import com.vuthao.VNADCM.base.model.app.DocumentRecently;
import com.vuthao.VNADCM.base.realm.RealmCommentController;
import com.vuthao.VNADCM.base.realm.RealmDocumentCategoryController;
import com.vuthao.VNADCM.base.realm.RealmDocumentController;
import com.vuthao.VNADCM.base.realm.RealmDocumentFavoriteController;
import com.vuthao.VNADCM.base.realm.RealmDocumentInteractiveController;
import com.vuthao.VNADCM.base.realm.RealmDocumentRecentlyController;
import com.vuthao.VNADCM.base.realm.RealmNotifyController;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nhum Lê Sơn Thạch on 16/02/2023.
 */
public class DocmentWebPresenter {
    private final DocmentWebListener listener;
    private final ApiAuthController apiAuthController;
    private final ApiDocumentController apiDocumentController;
    private final RealmDocumentRecentlyController realmDocumentRecentlyController;
    private final RealmDocumentController realmDocumentController;

    public DocmentWebPresenter(DocmentWebListener listener) {
        this.listener = listener;
        this.apiAuthController = new ApiAuthController();
        this.realmDocumentRecentlyController = new RealmDocumentRecentlyController();
        this.apiDocumentController = new ApiDocumentController();
        this.realmDocumentController = new RealmDocumentController();
    }

    public void getTokenWebView() {
        apiAuthController.getTokenWebView(new ApiAuthController.ApiTokenWebViewListener() {
            @Override
            public void onGetTokenSuccess(String token) {
                listener.onGetTokenWebSuccess(token);
            }

            @Override
            public void onGetTokenError() {
                listener.onGetTokenWebError();
            }
        });
    }

    public void getDocumentById(int id) {
        apiDocumentController.getDocumentById(id, new ApiDocumentController.ApiDocumentListener() {
            @Override
            public void onGetDocumentSuccess(ArrayList<Document> documents) {
                addDocumentRecently(id);
                realmDocumentController.addNewItems(documents);
                listener.onGetDocumentSuccess();
            }

            @Override
            public void onGetDocumentError() {
                listener.onGetDocumentError();
            }
        });
    }
    public void getHTML(String data, int lcid,Context context)
    {
        apiDocumentController.getHTML(new ApiDocumentController.ApiHTMLListener() {
            @Override
            public void onGetHTMLSuccess(String html) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UUID uuid = UUID.randomUUID();
                        String strHTML=replaceHrefAndSrc(context,html,uuid.toString());
                        writeHtmlToFile(context,strHTML,uuid.toString());
                    }
                }).start();
            }

            @Override
            public void onGetHTMLError(String err) {

            }
        },data,lcid);
    }
    public String replaceHrefAndSrc(Context context,String htmlString,String guid) {
        org.jsoup.nodes.Document doc = Jsoup.parse(htmlString);

        Elements hrefElements = doc.select("[href]");
        for (Element element : hrefElements) {
            String originalSrc = element.attr("href");
            String newSrc = downloadResource(context,originalSrc,guid);
            element.attr("href", newSrc);
        }

        Elements srcElements = doc.select("[src]");
        for (Element element : srcElements) {
            String originalSrc = element.attr("src");
            String newSrc = downloadResource(context,originalSrc,guid);
            element.attr("src", newSrc);
        }
        Elements scriptElements = doc.select("script");
        for (Element element : scriptElements) {
            if(element.toString().contains("_NODE"))
            {
                Map<String,String> data =getPathAndReplace(context,element.toString(),guid);
                String html=element.toString();
                for (Map.Entry<String, String> entry : data.entrySet()) {
                    html=html.replace(entry.getKey(),entry.getValue());
                }
                element.replaceWith(Jsoup.parse(html));

            }
        }

        return doc.html();
    }
    public Map<String,String> getPathAndReplace(Context context,String html,String guid)
    {
        List<String> data= Arrays.stream(html.split("\\\\\\\\\\\\\\\""))
            .filter(img -> img.contains("https")&& ImageUtils.isImageLink(img)) // Filter items containing "https"
            .collect(Collectors.toList());
        Map<String,String> dataReplace=new HashMap<>();
        for(String str:data)
        {
           dataReplace.put(str, downloadResource(context,str,guid));
        }
        return dataReplace;
    }
    public boolean writeHtmlToFile(Context context,  String htmlContent,String guid) {
        try {
            // Create the file object
            File file = new File(context.getFilesDir()+"/"+guid, guid+".html");

            // Create a FileWriter object
            FileWriter writer = new FileWriter(file);

            // Write the HTML content to the file
            writer.write(htmlContent);

            // Close the writer
            writer.close();

            return true; // File write successful
        } catch (IOException e) {
            e.printStackTrace();
            return false; // File write failed
        }
    }
    public String downloadResource(Context context, String originalSrc,String guid) {
        try {
            URL url = new URL(originalSrc);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();

            String fileName = originalSrc.substring(originalSrc.lastIndexOf('/') + 1);
            String filePath = Paths.get(context.getFilesDir()+"/"+guid, fileName).toString();

            // Create the 'downloaded_resources' directory if it doesn't exist
            File directory = new File(context.getFilesDir()+"/"+guid);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            OutputStream outputStream = new FileOutputStream(filePath);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outputStream.close();

            return  filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return originalSrc; // Return original src if downloading fails
        }
    }

    public String getFolderName(int id) {
        String res = "";
        Document document = realmDocumentController.getItemByDocumentId(id);
        if (document != null) {
            res = document.getTitle() + "_" + id;
            res = Functions.share.replaceString(res);
        }

        return res;
    }

    public void addDocumentRecently(int id) {
        DocumentRecently recently = new DocumentRecently(id, System.currentTimeMillis());
        realmDocumentRecentlyController.addItem(recently);
    }

    public void authCMS() {
        apiAuthController.authCMS(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        listener.onAuthCMSSuccess();
                    } else {
                        listener.onAuthCMSError();
                    }
                } else {
                    listener.onAuthCMSError();
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                listener.onAuthCMSError();
            }
        });
    }

    public interface DocmentWebListener {
        void onGetTokenWebSuccess(String token);
        void onGetTokenWebError();
        void onGetDocumentSuccess();
        void onGetDocumentError();
        void onAuthCMSSuccess();
        void onAuthCMSError();
    }
}
