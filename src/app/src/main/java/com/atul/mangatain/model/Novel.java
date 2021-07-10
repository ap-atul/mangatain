package com.atul.mangatain.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.atul.mangatain.MTConstants;

import java.util.List;

@Entity(tableName = MTConstants.NOVEL_TABLE)
public class Novel implements Parcelable {
    @NonNull
    @PrimaryKey
    public String title;
    public String art;
    public String description;
    public String author;
    public String status;
    public String rating;
    public String url;
    public List<String> tags;
    public List<NovelChapter> chapters;

    public Novel() {
        title = null;
    }

    public Novel(String url, String title, String art, String description, String author, String status, String rating, List<String> tags, List<NovelChapter> chapters) {
        this.url = url;
        this.title = ifNull(title);
        this.art = ifNull(art);
        this.description = ifNull(description);
        this.author = ifNull(author);
        this.status = ifNull(status);
        this.rating = ifNull(rating);
        this.tags = tags;
        this.chapters = chapters;
    }

    protected Novel(Parcel in) {
        title = in.readString();
        art = in.readString();
        description = in.readString();
        author = in.readString();
        status = in.readString();
        rating = in.readString();
        url = in.readString();
        tags = in.createStringArrayList();
        chapters = in.createTypedArrayList(NovelChapter.CREATOR);
    }

    public static final Creator<Novel> CREATOR = new Creator<Novel>() {
        @Override
        public Novel createFromParcel(Parcel in) {
            return new Novel(in);
        }

        @Override
        public Novel[] newArray(int size) {
            return new Novel[size];
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
        dest.writeString(title);
        dest.writeString(art);
        dest.writeString(description);
        dest.writeString(author);
        dest.writeString(status);
        dest.writeString(rating);
        dest.writeString(url);
        dest.writeStringList(tags);
        dest.writeTypedList(chapters);
    }

    @NonNull
    @Override
    public String toString() {
        return "Novel{" +
                "title='" + title + '\'' +
                ", art='" + art + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", status='" + status + '\'' +
                ", rating='" + rating + '\'' +
                ", url='" + url + '\'' +
                ", tags=" + tags +
                ", chapters=" + chapters +
                '}';
    }
}
