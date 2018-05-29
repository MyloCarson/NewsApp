package com.mylocarson.newsapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Source implements Parcelable {
    public static final Creator<Source> CREATOR = new Creator<Source>() {
        @Override
        public Source createFromParcel(Parcel source) {
            return new Source(source);
        }

        @Override
        public Source[] newArray(int size) {
            return new Source[size];
        }
    };
    private String name;
    private String id;

    public Source() {
    }

    Source(Parcel in) {
        this.name = in.readString();
        this.id = in.readString();
    }

// --Commented out by Inspection START (29/05/2018, 17:44):
//    public String getName() {
//        return name;
//    }
// --Commented out by Inspection STOP (29/05/2018, 17:44)

// --Commented out by Inspection START (29/05/2018, 17:44):
//    public void setName(String name) {
//        this.name = name;
//    }
// --Commented out by Inspection STOP (29/05/2018, 17:44)

// --Commented out by Inspection START (29/05/2018, 17:44):
//    public String getId() {
//        return id;
//    }
// --Commented out by Inspection STOP (29/05/2018, 17:44)

// --Commented out by Inspection START (29/05/2018, 17:44):
//    public void setId(String id) {
//        this.id = id;
//    }
// --Commented out by Inspection STOP (29/05/2018, 17:44)

    @Override
    public String toString() {
        return
                "Source{" +
                        "name = '" + name + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.id);
    }
}
