package com.atul.mangatain.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NovelChapter implements Parcelable {
    public String chapter;
    public String content;
    public String url;

    public NovelChapter() { }

    public NovelChapter(String url, String chapter, String content) {
        this.url = ifNull(url);
        this.chapter = ifNull(chapter);
        this.content = ifNull(content);
    }

    protected NovelChapter(Parcel in) {
        chapter = in.readString();
        content = in.readString();
        url = in.readString();
    }

    public static final Creator<NovelChapter> CREATOR = new Creator<NovelChapter>() {
        @Override
        public NovelChapter createFromParcel(Parcel in) {
            return new NovelChapter(in);
        }

        @Override
        public NovelChapter[] newArray(int size) {
            return new NovelChapter[size];
        }
    };

    private String ifNull(String val){
        return val == null ? "" : val;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(chapter);
        dest.writeString(content);
        dest.writeString(url);
    }

    @Override
    public String toString() {
        return "NovelChapter{" +
                "chapter='" + chapter + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
