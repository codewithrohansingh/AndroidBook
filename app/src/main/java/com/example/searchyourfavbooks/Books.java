package com.example.searchyourfavbooks;

public class Books {
    /**
     * These are the variables used in this app to store and pass the values .
     */

    private String mTitle;
    private String mAuthor;
    private String mPublisher;
    private String mPublishDate;
    private String mSelfLink;

    public String getSelfLink() {
        return mSelfLink;
    }



    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public String getPublishDate() {
        return mPublishDate;
    }

    public Books(String mTitle, String mAuthor, String mPublisher, String mPublishDate, String link) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mPublisher = mPublisher;
        this.mPublishDate = mPublishDate;
        this.mSelfLink = link;
    }
}
