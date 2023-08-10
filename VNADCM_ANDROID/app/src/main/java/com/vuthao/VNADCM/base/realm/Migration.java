package com.vuthao.VNADCM.base.realm;

import androidx.annotation.Nullable;

import com.vuthao.VNADCM.base.model.app.Comment;
import com.vuthao.VNADCM.base.model.app.ConfigureNotification;
import com.vuthao.VNADCM.base.model.app.DocumentAreaCategory;
import com.vuthao.VNADCM.base.model.app.Document;
import com.vuthao.VNADCM.base.model.app.DocumentCategory;
import com.vuthao.VNADCM.base.model.app.DocumentFavorite;
import com.vuthao.VNADCM.base.model.app.DocumentFile;
import com.vuthao.VNADCM.base.model.app.DocumentInteractive;
import com.vuthao.VNADCM.base.model.app.DocumentRecently;
import com.vuthao.VNADCM.base.model.app.DocumentType;
import com.vuthao.VNADCM.base.model.app.FavoriteFolder;
import com.vuthao.VNADCM.base.model.app.Notify;
import com.vuthao.VNADCM.base.model.app.SearchHistory;
import com.vuthao.VNADCM.base.model.app.Settings;
import com.vuthao.VNADCM.base.model.app.User;

import java.lang.reflect.Field;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class Migration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();
        checkVersionRealm(schema, oldVersion);
    }

    private void checkVersionRealm(RealmSchema schema, long oldVersion) {
        if (oldVersion == 0) {
            // region DocumentAreaCategory
            if (schema.contains("DocumentAreaCategory")) {
                final RealmObjectSchema documentAreaCategory = schema.get("DocumentAreaCategory");
                if (!documentAreaCategory.hasField("ID")) documentAreaCategory.addField("ID", int.class);
                if (!documentAreaCategory.hasField("Title")) documentAreaCategory.addField("Title", String.class);
                if (!documentAreaCategory.hasField("Url")) documentAreaCategory.addField("Url", String.class);
                if (!documentAreaCategory.hasField("ParentId")) documentAreaCategory.addField("ParentId", int.class);
                if (!documentAreaCategory.hasField("Rank")) documentAreaCategory.addField("Rank", int.class);
                if (!documentAreaCategory.hasField("Description")) documentAreaCategory.addField("Description", String.class);
                if (!documentAreaCategory.hasField("Image")) documentAreaCategory.addField("Image", String.class);
                if (!documentAreaCategory.hasField("Created")) documentAreaCategory.addField("Created", String.class);
                if (!documentAreaCategory.hasField("Modified")) documentAreaCategory.addField("Modified", String.class);
            } else {
                final RealmObjectSchema documentAreaCategory = schema.create("DocumentAreaCategory");
                documentAreaCategory.addField("ID", int.class);
                documentAreaCategory.addField("Title", String.class);
                documentAreaCategory.addField("Url", String.class);
                documentAreaCategory.addField("ParentId", int.class);
                documentAreaCategory.addField("Rank", int.class);
                documentAreaCategory.addField("Description", String.class);
                documentAreaCategory.addField("Image", String.class);
                documentAreaCategory.addField("Created", String.class);
                documentAreaCategory.addField("Modified", String.class);
            }

            // endregion

            // region Document
            if (schema.contains("Document")) {
                final RealmObjectSchema document = schema.get("Document");
                if (!document.hasField("ID")) document.addField("ID", int.class);
                if (!document.hasField("Title")) document.addField("Title", String.class);
                if (!document.hasField("TitleEN")) document.addField("TitleEN", String.class);
                if (!document.hasField("DocumentId")) document.addField("DocumentId", int.class);
                if (!document.hasField("Url")) document.addField("Url", String.class);
                if (!document.hasField("StorageCode")) document.addField("StorageCode", String.class);
                if (!document.hasField("Status")) document.addField("Status", int.class);
                if (!document.hasField("IssueDate")) document.addField("IssueDate", String.class);
                if (!document.hasField("Thumbnail")) document.addField("Thumbnail", String.class);
                if (!document.hasField("LastTimeView")) document.addField("LastTimeView", String.class);
                if (!document.hasField("IsMostViewedL")) document.addField("IsMostViewedL", boolean.class);
                if (!document.hasField("IsNewestL")) document.addField("IsNewestL", boolean.class);
                if (!document.hasField("IsFavoriteL")) document.addField("IsFavoriteL", boolean.class);
                if (!document.hasField("AreaCategoryId")) document.addField("AreaCategoryId", int.class);
            } else {
                final RealmObjectSchema document = schema.create("Document");
                document.addField("ID", int.class);
                document.addField("Title", String.class);
                document.addField("TitleEN", String.class);
                document.addField("DocumentId", int.class);
                document.addField("Url", String.class);
                document.addField("StorageCode", String.class);
                document.addField("Status", int.class);
                document.addField("IssueDate", String.class);
                document.addField("Thumbnail", String.class);
                document.addField("LastTimeView", String.class);
                document.addField("IsMostViewedL", boolean.class);
                document.addField("IsNewestL", boolean.class);
                document.addField("IsFavoriteL", boolean.class);
                document.addField("AreaCategoryId", int.class);
            }

            // endregion

            // region Comment
            if (schema.contains("Comment")) {
                final RealmObjectSchema comment = schema.get("Comment");
                if (!comment.hasField("ID")) comment.addField("ID", String.class);
                if (!comment.hasField("Title")) comment.addField("Title", String.class);
                if (!comment.hasField("StorageCode")) comment.addField("StorageCode", String.class);
                if (!comment.hasField("Version")) comment.addField("Version", int.class);
                if (!comment.hasField("Content")) comment.addField("Content", String.class);
                if (!comment.hasField("Created")) comment.addField("Created", String.class);
                if (!comment.hasField("IsApproved")) comment.addField("IsApproved", int.class);
                if (!comment.hasField("ResourceUrl")) comment.addField("ResourceUrl", String.class);
                if (!comment.hasField("Status")) comment.addField("Status", int.class);
                if (!comment.hasField("Thumbnail")) comment.addField("Thumbnail", String.class);
                if (!comment.hasField("DocumentId")) comment.addField("DocumentId", int.class);
            } else {
                final RealmObjectSchema comment = schema.create("Comment");
                comment.addField("ID", String.class);
                comment.addField("Title", String.class);
                comment.addField("StorageCode", String.class);
                comment.addField("Version", int.class);
                comment.addField("Content", String.class);
                comment.addField("Created", String.class);
                comment.addField("IsApproved", int.class);
                comment.addField("ResourceUrl", String.class);
                comment.addField("Status", int.class);
                comment.addField("Thumbnail", String.class);
                comment.addField("DocumentId", int.class);
            }
            // endregion

            // region FavoriteFolder
            if (schema.contains("FavoriteFolder")) {
                final RealmObjectSchema favoriteFolder = schema.get("FavoriteFolder");
                if (!favoriteFolder.hasField("ID")) favoriteFolder.addField("ID", String.class);
                if (!favoriteFolder.hasField("Title")) favoriteFolder.addField("Title", String.class);
                if (!favoriteFolder.hasField("ParentId")) favoriteFolder.addField("ParentId", String.class);
                if (!favoriteFolder.hasField("Rank")) favoriteFolder.addField("Rank", int.class);
                if (!favoriteFolder.hasField("ResourceUrl")) favoriteFolder.addField("ResourceUrl", String.class);
                if (!favoriteFolder.hasField("CreatedBy")) favoriteFolder.addField("CreatedBy", String.class);
                if (!favoriteFolder.hasField("Modified")) favoriteFolder.addField("Modified", String.class);
                if (!favoriteFolder.hasField("Created")) favoriteFolder.addField("Created", String.class);
            } else {
                final RealmObjectSchema favoriteFolder = schema.create("FavoriteFolder");
                favoriteFolder.addField("ID", String.class);
                favoriteFolder.addField("Title", String.class);
                favoriteFolder.addField("ParentId", String.class);
                favoriteFolder.addField("Rank", int.class);
                favoriteFolder.addField("ResourceUrl", String.class);
                favoriteFolder.addField("CreatedBy", String.class);
                favoriteFolder.addField("Modified", String.class);
                favoriteFolder.addField("Created", String.class);
            }
            // endregion

            // region DocumentFavorite
            if (schema.contains("DocumentFavorite")) {
                final RealmObjectSchema documentFavorite = schema.get("DocumentFavorite");
                if (!documentFavorite.hasField("ID")) documentFavorite.addField("ID", String.class);
                if (!documentFavorite.hasField("ResourceTitle")) documentFavorite.addField("ResourceTitle", String.class);
                if (!documentFavorite.hasField("ResourceUrl")) documentFavorite.addField("ResourceUrl", String.class);
                if (!documentFavorite.hasField("ResourceId")) documentFavorite.addField("ResourceId", String.class);
                if (!documentFavorite.hasField("FolderId")) documentFavorite.addField("FolderId", String.class);
                if (!documentFavorite.hasField("CreatedBy")) documentFavorite.addField("CreatedBy", String.class);
                if (!documentFavorite.hasField("Modified")) documentFavorite.addField("Modified", String.class);
                if (!documentFavorite.hasField("Created")) documentFavorite.addField("Created", String.class);
                if (!documentFavorite.hasField("FolderTitle")) documentFavorite.addField("FolderTitle", String.class);
                if (!documentFavorite.hasField("Thumbnail")) documentFavorite.addField("Thumbnail", String.class);
                if (!documentFavorite.hasField("DocumentId")) documentFavorite.addField("DocumentId", int.class);
            } else {
                final RealmObjectSchema documentFavorite = schema.create("DocumentFavorite");
                documentFavorite.addField("ID", String.class);
                documentFavorite.addField("ResourceTitle", String.class);
                documentFavorite.addField("ResourceUrl", String.class);
                documentFavorite.addField("ResourceId", String.class);
                documentFavorite.addField("FolderId", String.class);
                documentFavorite.addField("CreatedBy", String.class);
                documentFavorite.addField("Modified", String.class);
                documentFavorite.addField("Created", String.class);
                documentFavorite.addField("FolderTitle", String.class);
                documentFavorite.addField("Thumbnail", String.class);
                documentFavorite.addField("DocumentId", int.class);
            }
            // endregion

            // region DocumentCategory
            if (schema.contains("DocumentCategory")) {
                final RealmObjectSchema documentCategory = schema.get("DocumentCategory");
                if (!documentCategory.hasField("ID")) documentCategory.addField("ID", int.class);
                if (!documentCategory.hasField("Title")) documentCategory.addField("Title", String.class);
                if (!documentCategory.hasField("Url")) documentCategory.addField("Url", String.class);
                if (!documentCategory.hasField("DocumentId")) documentCategory.addField("DocumentId", int.class);
                if (!documentCategory.hasField("StorageCode")) documentCategory.addField("StorageCode", String.class);
                if (!documentCategory.hasField("AreaCategoryId")) documentCategory.addField("AreaCategoryId", int.class);
                if (!documentCategory.hasField("Version")) documentCategory.addField("Version", int.class);
                if (!documentCategory.hasField("IssueDate")) documentCategory.addField("IssueDate", String.class);
                if (!documentCategory.hasField("Status")) documentCategory.addField("Status", int.class);
                if (!documentCategory.hasField("StatusName")) documentCategory.addField("StatusName", String.class);
                if (!documentCategory.hasField("Code")) documentCategory.addField("Code", String.class);
                if (!documentCategory.hasField("Thumbnail")) documentCategory.addField("Thumbnail", String.class);
            } else {
                final RealmObjectSchema documentCategory = schema.create("DocumentCategory");
                documentCategory.addField("ID", int.class);
                documentCategory.addField("Title", String.class);
                documentCategory.addField("Url", String.class);
                documentCategory.addField("DocumentId", int.class);
                documentCategory.addField("StorageCode", String.class);
                documentCategory.addField("AreaCategoryId", int.class);
                documentCategory.addField("Version", int.class);
                documentCategory.addField("IssueDate", String.class);
                documentCategory.addField("Status", int.class);
                documentCategory.addField("StatusName", String.class);
                documentCategory.addField("Code", String.class);
                documentCategory.addField("Thumbnail", String.class);
            }

            // endregion

            // region Notify
            if (schema.contains("Notify")) {
                final RealmObjectSchema notify = schema.get("Notify");
                if (!notify.hasField("ID")) notify.addField("ID", String.class);
                if (!notify.hasField("UserId")) notify.addField("UserId", String.class);
                if (!notify.hasField("Content")) notify.addField("Content", String.class);
                if (!notify.hasField("ContentEN")) notify.addField("ContentEN", String.class);
                if (!notify.hasField("ItemImage")) notify.addField("ItemImage", String.class);
                if (!notify.hasField("Link")) notify.addField("Link", String.class);
                if (!notify.hasField("Icon")) notify.addField("Icon", String.class);
                if (!notify.hasField("FlgRead")) notify.addField("FlgRead", boolean.class);
                if (!notify.hasField("flgConfirm")) notify.addField("flgConfirm", boolean.class);
                if (!notify.hasField("flgConfirmed")) notify.addField("flgConfirmed", boolean.class);
                if (!notify.hasField("ShowPopup")) notify.addField("ShowPopup", boolean.class);
                if (!notify.hasField("ResourceId")) notify.addField("ResourceId", String.class);
                if (!notify.hasField("ResourceCategoryId")) notify.addField("ResourceCategoryId", int.class);
                if (!notify.hasField("ResourceSubCategoryId")) notify.addField("ResourceSubCategoryId", int.class);
                if (!notify.hasField("ActionTime")) notify.addField("ActionTime", String.class);
                if (!notify.hasField("PopupTitle")) notify.addField("PopupTitle", String.class);
                if (!notify.hasField("PopupTitleEN")) notify.addField("PopupTitleEN", String.class);
                if (!notify.hasField("Modified")) notify.addField("Modified", String.class);
                if (!notify.hasField("Created")) notify.addField("Created", String.class);
            } else {
                final RealmObjectSchema notify = schema.create("Notify");
                notify.addField("ID", String.class);
                notify.addField("UserId", String.class);
                notify.addField("Content", String.class);
                notify.addField("ContentEN", String.class);
                notify.addField("ItemImage", String.class);
                notify.addField("Link", String.class);
                notify.addField("Icon", String.class);
                notify.addField("FlgRead", boolean.class);
                notify.addField("flgConfirm", boolean.class);
                notify.addField("flgConfirmed", boolean.class);
                notify.addField("ShowPopup", boolean.class);
                notify.addField("ResourceId", String.class);
                notify.addField("ResourceCategoryId", int.class);
                notify.addField("ResourceSubCategoryId", int.class);
                notify.addField("ActionTime", String.class);
                notify.addField("PopupTitle", String.class);
                notify.addField("PopupTitleEN", String.class);
                notify.addField("Modified", String.class);
                notify.addField("Created", String.class);
            }
            // endregion

            // region SearchHistory
            if (schema.contains("SearchHistory")) {
                final RealmObjectSchema searchHistory = schema.get("SearchHistory");
                if (!searchHistory.hasField("Title")) searchHistory.addField("Title", String.class);
                if (!searchHistory.hasField("Modified")) searchHistory.addField("Modified", Long.class);
            } else {
                final RealmObjectSchema searchHistory = schema.create("SearchHistory");
                searchHistory.addField("Title", String.class);
                searchHistory.addField("Modified", Long.class);
            }

            // endregion

            // region DocumentRecently
            if (schema.contains("DocumentRecently")) {
                final RealmObjectSchema documentRecently = schema.get("DocumentRecently");
                if (!documentRecently.hasField("ID")) documentRecently.addField("ID", int.class);
                if (!documentRecently.hasField("Modified")) documentRecently.addField("Modified", Long.class);
            } else {
                final RealmObjectSchema documentRecently = schema.create("DocumentRecently");
                documentRecently.addField("ID", int.class);
                documentRecently.addField("Modified", Long.class);
            }
            // endregion

            // region ConfigureNotification
            if (schema.contains("ConfigureNotification")) {
                final RealmObjectSchema configureNotification = schema.get("ConfigureNotification");
                if (!configureNotification.hasField("ID")) configureNotification.addField("ID", int.class);
                if (!configureNotification.hasField("Title")) configureNotification.addField("Title", String.class);
                if (!configureNotification.hasField("TitleEN")) configureNotification.addField("TitleEN", String.class);
                if (!configureNotification.hasField("Description")) configureNotification.addField("Description", String.class);
                if (!configureNotification.hasField("DescriptionEN")) configureNotification.addField("DescriptionEN", String.class);
                if (!configureNotification.hasField("ActionCategoryId")) configureNotification.addField("ActionCategoryId", int.class);
                if (!configureNotification.hasField("ResourceCategoryId")) configureNotification.addField("ResourceCategoryId", int.class);
                if (!configureNotification.hasField("ResourceSubCategoryId")) configureNotification.addField("ResourceSubCategoryId", int.class);
                if (!configureNotification.hasField("IsConfig")) configureNotification.addField("IsConfig", int.class);
                if (!configureNotification.hasField("Rank")) configureNotification.addField("Rank", int.class);
                if (!configureNotification.hasField("Created")) configureNotification.addField("Created", String.class);
                if (!configureNotification.hasField("Modified")) configureNotification.addField("Modified", String.class);
            } else {
                final RealmObjectSchema configureNotification = schema.create("ConfigureNotification");
                configureNotification.addField("ID", int.class);
                configureNotification.addField("Title", String.class);
                configureNotification.addField("TitleEN", String.class);
                configureNotification.addField("Description", String.class);
                configureNotification.addField("DescriptionEN", String.class);
                configureNotification.addField("ActionCategoryId", int.class);
                configureNotification.addField("ResourceCategoryId", int.class);
                configureNotification.addField("ResourceSubCategoryId", int.class);
                configureNotification.addField("IsConfig", int.class);
                configureNotification.addField("Rank", int.class);
                configureNotification.addField("Created", String.class);
                configureNotification.addField("Modified", String.class);
            }

            // endregion

            // region DocumentInteractive
            if (schema.contains("DocumentInteractive")) {
                final RealmObjectSchema documentInteractive = schema.get("DocumentInteractive");
                if (!documentInteractive.hasField("ID")) documentInteractive.addField("ID", String.class);
                if (!documentInteractive.hasField("Title")) documentInteractive.addField("Title", String.class);
                if (!documentInteractive.hasField("ResourceId")) documentInteractive.addField("ResourceId", String.class);
                if (!documentInteractive.hasField("ResourceUrl")) documentInteractive.addField("ResourceUrl", String.class);
                if (!documentInteractive.hasField("Created")) documentInteractive.addField("Created", String.class);
                if (!documentInteractive.hasField("Type")) documentInteractive.addField("Type", String.class);
                if (!documentInteractive.hasField("StorageCode")) documentInteractive.addField("StorageCode", String.class);
                if (!documentInteractive.hasField("VersionShow")) documentInteractive.addField("VersionShow", int.class);
                if (!documentInteractive.hasField("DocumentType")) documentInteractive.addField("DocumentType", String.class);
                if (!documentInteractive.hasField("Department")) documentInteractive.addField("Department", String.class);
                if (!documentInteractive.hasField("IsAutoFollow")) documentInteractive.addField("IsAutoFollow", int.class);
                if (!documentInteractive.hasField("Thumbnail")) documentInteractive.addField("Thumbnail", String.class);
                if (!documentInteractive.hasField("DocumentId")) documentInteractive.addField("DocumentId", int.class);
            } else {
                final RealmObjectSchema documentInteractive = schema.create("DocumentInteractive");
                documentInteractive.addField("ID", String.class);
                documentInteractive.addField("Title", String.class);
                documentInteractive.addField("ResourceId", String.class);
                documentInteractive.addField("ResourceUrl", String.class);
                documentInteractive.addField("Created", String.class);
                documentInteractive.addField("Type", String.class);
                documentInteractive.addField("StorageCode", String.class);
                documentInteractive.addField("VersionShow", int.class);
                documentInteractive.addField("DocumentType", String.class);
                documentInteractive.addField("Department", String.class);
                documentInteractive.addField("IsAutoFollow", int.class);
                documentInteractive.addField("Thumbnail", String.class);
                documentInteractive.addField("DocumentId", int.class);
            }

            // endregion

            // region DocumentType
            if (schema.contains("DocumentType")) {
                final RealmObjectSchema documentType = schema.get("DocumentType");
                if (!documentType.hasField("ID")) documentType.addField("ID", int.class);
                if (!documentType.hasField("Title")) documentType.addField("Title", String.class);
                if (!documentType.hasField("TitleEN")) documentType.addField("TitleEN", String.class);
                if (!documentType.hasField("LangId")) documentType.addField("LangId", int.class);
                if (!documentType.hasField("Url")) documentType.addField("Url", String.class);
            } else {
                final RealmObjectSchema documentType = schema.create("DocumentType");
                documentType.addField("ID", int.class);
                documentType.addField("Title", String.class);
                documentType.addField("TitleEN", String.class);
                documentType.addField("LangId", int.class);
                documentType.addField("Url", String.class);
            }

            // endregion

            // region DocumentFile
            if (schema.contains("DocumentFile")) {
                final RealmObjectSchema documentFile = schema.get("DocumentFile");
                if (!documentFile.hasField("ID")) documentFile.addField("ID", int.class);
                if (!documentFile.hasField("Path")) documentFile.addField("Path", String.class);
                if (!documentFile.hasField("Extension")) documentFile.addField("Extension", String.class);
            } else {
                final RealmObjectSchema documentFile = schema.create("DocumentFile");
                documentFile.addField("ID", int.class);
                documentFile.addField("Path", String.class);
                documentFile.addField("Extension", String.class);
            }

            // endregion

            oldVersion++;
        }
    }

    @Override
    public int hashCode() {
        return Migration.class.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        return obj instanceof Migration;
    }
}
