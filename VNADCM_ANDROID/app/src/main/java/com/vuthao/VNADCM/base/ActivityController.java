package com.vuthao.VNADCM.base;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.URLUtil;

import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.document.DocumentDetailWebActivity;

import java.util.ArrayList;

/**
 * Created by Nhum Lê Sơn Thạch on 22/02/2023.
 */
public class ActivityController {
    public static ActivityController share = new ActivityController();

    public void goToDocumentDetail(Activity activity, String url) {
        Intent intent = new Intent(activity, DocumentDetailWebActivity.class);

        ArrayList<Integer> params = Functions.share.getParameterUrlDoc(url);
        if (params.size() > 0) {
            intent.putExtra("ResourceId", params.get(0));
            intent.putExtra("DocumentGroupId", params.get(1));
            intent.putExtra("CategoryId", params.get(2));
        } else {
            boolean isValid = URLUtil.isValidUrl(url);
            if (isValid) {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            } else {
                if (url.indexOf(Constants.SUB_SITE) > 0) {
                    url = Constants.BASE_URL + url;
                } else {
                    url = Constants.BASE_URL + "/" + Constants.SUB_SITE + url;
                }
            }
            intent.putExtra("Url", url + "&Mobile=thachdepzai");
        }

        activity.startActivity(intent);
    }
}
