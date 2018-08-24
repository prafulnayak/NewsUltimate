package org.sairaa.newsultimate;

import android.graphics.Bitmap;

public class News {
    private String id,
            type,
            sectionId,
            sectionName,
            webPublicationDate,
            webTitle,
            webUrl,
            apiUrl,
            tagContributor,
            isHosted;
    private Bitmap fieldThumbnail;


    public News(String id, String type, String sectionId, String sectionName,
                String webPublicationDate, String webTitle, String webUrl, String apiUrl,
                Bitmap fieldThumbnail,
                String tagContributor,
                String isHosted){

        this.id = id;
        this.type = type;
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.webPublicationDate = webPublicationDate;
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.apiUrl = apiUrl;
        this.fieldThumbnail = fieldThumbnail;
        this.tagContributor = tagContributor;
        this.isHosted = isHosted;


    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getSectionId() {
        return sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public Bitmap getFieldThumbnail() {
        return fieldThumbnail;
    }

    public String getTagContributor() {
        return tagContributor;
    }

    public String getIsHosted() {
        return isHosted;
    }

}
