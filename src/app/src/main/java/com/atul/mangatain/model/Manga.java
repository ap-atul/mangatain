package com.atul.mangatain.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Manga implements Parcelable {
    public String title;
    public String art;
    public String url;
    public String chapter;
    public String rating;
    public String status;
    public String summary;
    public String author;
    public String authorUrl;
    public List<String> tags;
    public List<Chapter> chapters;

    public Manga(String title, String url, String summary, String rating, String art, List<String> tags) {
        this.title = this.ifNull(title);
        this.art = this.ifNull(art);
        this.url = this.ifNull(url);
        this.rating = this.ifNull(rating);
        this.summary = this.ifNull(summary);
        this.tags = tags;
    }

    protected Manga(Parcel in) {
        title = in.readString();
        art = in.readString();
        url = in.readString();
        chapter = in.readString();
        rating = in.readString();
        status = in.readString();
        summary = in.readString();
        author = in.readString();
        authorUrl = in.readString();
        tags = in.createStringArrayList();

        chapters = new ArrayList<>();
        in.readTypedList(chapters, Chapter.CREATOR);
    }

    public static final Creator<Manga> CREATOR = new Creator<Manga>() {
        @Override
        public Manga createFromParcel(Parcel in) {
            return new Manga(in);
        }

        @Override
        public Manga[] newArray(int size) {
            return new Manga[size];
        }
    };

    @NonNull
    public String toString() {
        return "Manga [title=" + this.title + ", art=" + this.art + ", url=" + this.url + ", chapter=" + this.chapter + ", rating=" + this.rating + ", status=" + this.status + ", summary=" + this.summary + ", author=" + this.author + ", authorUrl=" + this.authorUrl + ", tags=" + this.tags + ", chapters=" + this.chapters + "]";
    }

    private String ifNull(String val) {
        return val == null ? "" : val;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(art);
        dest.writeString(url);
        dest.writeString(chapter);
        dest.writeString(rating);
        dest.writeString(status);
        dest.writeString(summary);
        dest.writeString(author);
        dest.writeString(authorUrl);
        dest.writeStringList(tags);
        dest.writeTypedList(chapters);
    }
}
