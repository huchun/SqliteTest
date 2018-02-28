package com.hucc.sqlitetest.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chunchun.hu on 2018/2/8.
 */

public class Contact implements Parcelable{

    private int id;
    private String name;
    private String phone;

    public Contact() {
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public Contact(Parcel parcel) {
        this.id = parcel.readInt();
        this.name = parcel.readString();
        this.phone = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
         dest.writeInt(this.id);
         dest.writeString(this.name);
         dest.writeString(this.phone);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
