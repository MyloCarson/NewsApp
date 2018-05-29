package com.mylocarson.newsapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ArticlesItem implements Parcelable {
    public static final Parcelable.Creator<ArticlesItem> CREATOR = new Parcelable.Creator<ArticlesItem>() {
        @Override
        public ArticlesItem createFromParcel(Parcel source) {
            return new ArticlesItem(source);
        }

        @Override
        public ArticlesItem[] newArray(int size) {
            return new ArticlesItem[size];
        }
    };
    private String publishedAt;
    private String author;
    private String urlToImage;
    private String description;
    private Source source;
    private String title;
    private String url;
    private int articleId_fromDB;

    public ArticlesItem() {
    }

    protected ArticlesItem(Parcel in) {
        this.publishedAt = in.readString();
        this.author = in.readString();
        this.urlToImage = in.readString();
        this.description = in.readString();
        this.source = in.readParcelable(Source.class.getClassLoader());
        this.title = in.readString();
        this.url = in.readString();
    }

    public int getArticleId_fromDB() {
        return articleId_fromDB;
    }

    public void setArticleId_fromDB(int articleId_fromDB) {
        this.articleId_fromDB = articleId_fromDB;
    }

// --Commented out by Inspection START (29/05/2018, 17:44):
//    public String getPublishedAt() {
//        return publishedAt;
//    }
// --Commented out by Inspection STOP (29/05/2018, 17:44)

// --Commented out by Inspection START (29/05/2018, 17:44):
//    public void setPublishedAt(String publishedAt) {
//        this.publishedAt = publishedAt;
//    }
// --Commented out by Inspection STOP (29/05/2018, 17:44)

// --Commented out by Inspection START (29/05/2018, 17:44):
//    public String getAuthor() {
//        return author;
//    }
// --Commented out by Inspection STOP (29/05/2018, 17:44)

// --Commented out by Inspection START (29/05/2018, 17:44):
//    public void setAuthor(String author) {
//        this.author = author;
//    }
// --Commented out by Inspection STOP (29/05/2018, 17:44)

    public String getUrlToImage() {
        return urlToImage;
    }

// --Commented out by Inspection START (29/05/2018, 17:44):
//    public void setUrlToImage(String urlToImage) {
//        this.urlToImage = urlToImage;
//    }
// --Commented out by Inspection STOP (29/05/2018, 17:44)

    public String getDescription() {
        return description;
    }

// --Commented out by Inspection START (29/05/2018, 17:44):
//    public void setDescription(String description) {
//        this.description = description;
//    }
// --Commented out by Inspection STOP (29/05/2018, 17:44)

// --Commented out by Inspection START (29/05/2018, 17:44):
//    public Source getSource() {
//        return source;
//    }
// --Commented out by Inspection STOP (29/05/2018, 17:44)

// --Commented out by Inspection START (29/05/2018, 17:44):
//    public void setSource(Source source) {
//        this.source = source;
//    }
// --Commented out by Inspection STOP (29/05/2018, 17:44)

    public String getTitle() {
        return title;
    }

// --Commented out by Inspection START (29/05/2018, 17:44):
//    public void setTitle(String title) {
//        this.title = title;
//    }
// --Commented out by Inspection STOP (29/05/2018, 17:44)

    public String getUrl() {
        return url;
    }

// --Commented out by Inspection START (29/05/2018, 17:44):
//    public void setUrl(String url) {
//        this.url = url;
//    }
// --Commented out by Inspection STOP (29/05/2018, 17:44)

    @Override
    public String toString() {
        return
                "ArticlesItem{" +
                        "publishedAt = '" + publishedAt + '\'' +
                        ",author = '" + author + '\'' +
                        ",urlToImage = '" + urlToImage + '\'' +
                        ",description = '" + description + '\'' +
                        ",source = '" + source + '\'' +
                        ",title = '" + title + '\'' +
                        ",url = '" + url + '\'' +
                        "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.publishedAt);
        dest.writeString(this.author);
        dest.writeString(this.urlToImage);
        dest.writeString(this.description);
        dest.writeParcelable(this.source, flags);
        dest.writeString(this.title);
        dest.writeString(this.url);
    }
}
