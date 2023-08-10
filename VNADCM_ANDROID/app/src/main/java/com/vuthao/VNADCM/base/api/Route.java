package com.vuthao.VNADCM.base.api;

import com.google.gson.JsonObject;
import com.vuthao.VNADCM.base.Constants;
import com.vuthao.VNADCM.base.model.ApiData;
import com.vuthao.VNADCM.base.model.ApiList;
import com.vuthao.VNADCM.base.model.ApiObject;
import com.vuthao.VNADCM.base.model.MoreInfo;
import com.vuthao.VNADCM.base.model.Status;
import com.vuthao.VNADCM.base.model.app.DocumentAreaCategory;
import com.vuthao.VNADCM.base.model.app.Comment;
import com.vuthao.VNADCM.base.model.app.Document;
import com.vuthao.VNADCM.base.model.app.DocumentCategory;
import com.vuthao.VNADCM.base.model.app.DocumentFavorite;
import com.vuthao.VNADCM.base.model.app.DocumentInteractive;
import com.vuthao.VNADCM.base.model.app.FavoriteFolder;
import com.vuthao.VNADCM.base.model.app.Notify;
import com.vuthao.VNADCM.base.model.app.SearchTrend;
import com.vuthao.VNADCM.base.model.app.Settings;
import com.vuthao.VNADCM.base.model.app.User;
import com.vuthao.VNADCM.base.model.app.ConfigureNotification;
import com.vuthao.VNADCM.base.model.custom.HomeResource;
import com.vuthao.VNADCM.base.model.custom.MasterData;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Nhum Lê Sơn Thạch on 09/02/2023.
 */
public interface Route {
    @FormUrlEncoded
    @POST(Constants.SUB_SITE + "/api/ApiMobile.ashx?func=AdfsLogin")
    Call<ApiData> auth(
            @Field("data") String data
    );

    @FormUrlEncoded
    @POST(Constants.SUB_SITE + "/api/ApiMobile.ashx?func=CurrentUser")
    Call<ApiList<User>> getCurrentUserInfo(
            @Field("data") String data
    );

    @GET(Constants.SUB_SITE + "/api/ApiMobile.ashx?func=GetHomeResourceZone&Params=LangId")
    Call<ApiList<DocumentAreaCategory>> getCategories(
            @Query("LangId") String lang
    );

    @GET(Constants.SUB_SITE + "/api/ApiMobile.ashx?func=GetTopNotify&Params=Offset,Limit")
    Call<ApiObject<Notify>> getNotifies(
            @Query("Offset") String index,
            @Query("Limit") String limit
    );

    @GET(Constants.SUB_SITE + "/api/ApiMobile.ashx?func=GetUnReadNotify&Params=Offset,Limit")
    Call<ApiObject<Notify>> getUnReadNotifies(
            @Query("Offset") String index,
            @Query("Limit") String limit
    );

    @GET(Constants.SUB_SITE + "/api/ApiMobile.ashx?func=GetUser&Params=Modified")
    Call<ApiList<User>> getUsers(
            @Query("Modified") String modified
    );

    @GET(Constants.SUB_SITE + "/api/ApiMobile.ashx?func=Get&Params=Modified")
    Call<ApiObject<MasterData>> getAllMasterData(
            @Query("BeanName") String beanName,
            @Query("Modified") String modified
    );

    @GET(Constants.SUB_SITE + "/api/handler.ashx?tbl=favorite&func=select")
    Call<ApiList<FavoriteFolder>> getFavorites(
            @Query("lang") String lang
    );

    @POST(Constants.SUB_SITE + "/api/ApiMobile.ashx?func=GetCommentByUser&Params=Offset,Limit")
    @FormUrlEncoded
    Call<ApiObject<Comment>> getComments(
            @Field("data") String data,
            @Query("Limit") String limit,
            @Query("Offset") String offset
    );

    @GET(Constants.SUB_SITE + "/api/ApiMobile.ashx?func=SignOut&Params=DeviceId")
    Call<Status> signOut(
            @Query("DeviceId") String deviceId
    );

    @GET(Constants.SUB_SITE + "/api/ApiMobile.ashx?func=GetConfigNotification")
    Call<ApiObject<ConfigureNotification>> getConfigNotification();

    @POST(Constants.SUB_SITE + "/api/ApiMobile.ashx?func=SelectByArea&Params=LangId,Offset,Limit")
    @FormUrlEncoded
    Call<ApiObject<DocumentCategory>> getListDocumentCategory(
            @Query("LangId") String langId,
            @Query("Limit") String limit,
            @Query("Offset") String offset,
            @Field("data") String data
    );

    @GET(Constants.SUB_SITE + "/api/ApiMobile.ashx?func=GetDocumentFavoriteById&Params=FolderId,Limit,Offset")
    Call<ApiObject<DocumentFavorite>> getDocumentFavoriteById(
            @Query("FolderId") String id,
            @Query("Limit") String limit,
            @Query("Offset") String offset
    );

    @GET(Constants.SUB_SITE + "/api/ApiMobile.ashx?func=MobileAutoLoginWeb")
    Call<JsonObject> getTokenMobileWebView();

    @GET(Constants.SUB_SITE + "/API/search.ashx?func=GetRecommend")
    Call<ApiList<SearchTrend>> getSearchTrend(
            @Query("lid") String lid,
            @Query("langid") String langid
    );

    @GET(Constants.SUB_SITE + "/api/ApiMobile.ashx?func=GetDocumentById&Params=DocumentId")
    Call<ApiList<Document>> getDocumentById(
            @Query("DocumentId") String id
    );

    @GET(Constants.SUB_SITE + "/api/ApiMobile.ashx?func=Get&BeanName=ViewDocumentNew,DocumentMostView,DocumentFavorite&Params=LangId,Limit,Offset")
    Call<ApiObject<HomeResource>> getHomeResources(
            @Query("Limit") String limit,
            @Query("Offset") String offset
    );

    @GET(Constants.SUB_SITE + "/api/ApiMobile.ashx?func=GetInteractiveDocument&Params=Offset,Limit,LangId")
    Call<ApiObject<DocumentInteractive>> getDocumentInteractive(
            @Query("Limit") String limit,
            @Query("Offset") String offset,
            @Query("LangId") String langId);

    @GET(Constants.SUB_SITE + "/api/ApiMobile.ashx?func=GetSettings&Params=KeySetting")
    Call<ApiList<Settings>> getCurrentVersion(
            @Query("KeySetting") String key
    );

    @GET(Constants.SUB_SITE + "/api/ApiMobile.ashx?func=SaveUserConfigNotification")
    Call<Status> updateConfigureNotification(
            @Query("data") String data
    );

    @GET(Constants.SUB_SITE + "/api/ApiMobile.ashx?func=MarkAsRead&Params=ResourceId")
    Call<Status> markAsRead(
            @Query("ResourceId") String rid
    );

    @GET(Constants.SUB_SITE + "/api/ApiMobile.ashx?func=AdfsCMS")
    Call<Status> authCMS();

    @GET(Constants.SUB_SITE + "/api/ApiMobile.ashx?func=GetUnReadNotify&Params=IsCount&IsCount=1")
    Call<ApiData> getCountNotify();

    @GET(Constants.SUB_SITE + "/api/ApiMobile.ashx?func=GetLogContext")
    Call<Status> getLogContext();

    @FormUrlEncoded
    @POST(Constants.SUB_SITE +"/api/detail.ashx?tbl=document&func=viewoffline")
    Call<ApiObject<String>> getHTML(
            @Query("LCID")int LCID,
            @Query("langid")int langid,
            @Field("data") String data);
}
