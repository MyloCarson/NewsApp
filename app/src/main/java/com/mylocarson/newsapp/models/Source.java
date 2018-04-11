package com.mylocarson.newsapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Source implements Parcelable {
	private String name;
	private String id;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
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

	public Source() {
	}

	protected Source(Parcel in) {
		this.name = in.readString();
		this.id = in.readString();
	}

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
}
