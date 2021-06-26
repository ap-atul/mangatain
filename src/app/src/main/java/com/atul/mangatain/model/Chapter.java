package com.atul.mangatain.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class Chapter implements Parcelable {
    public String title;
    public String url;
    public String publication;
    public List<String> pages;

    public Chapter(){}

    public Chapter(String title, String url, String publication, List<String> pages) {
        this.title = this.ifNull(title);
        this.url = this.ifNull(url);
        this.publication = this.ifNull(publication);
        this.pages = pages;
    }

    protected Chapter(Parcel in) {
        title = in.readString();
        url = in.readString();
        publication = in.readString();
        pages = in.createStringArrayList();
    }

    public static final Creator<Chapter> CREATOR = new Creator<Chapter>() {
        @Override
        public Chapter createFromParcel(Parcel in) {
            return new Chapter(in);
        }

        @Override
        public Chapter[] newArray(int size) {
            return new Chapter[size];
        }
    };

    @NonNull
    public String toString() {
        return "Chapter [title=" + this.title + ", url=" + this.url + ", publication=" + this.publication + ", pages=" + this.pages + "]";
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
        dest.writeString(url);
        dest.writeString(publication);
        dest.writeStringList(pages);
    }
}