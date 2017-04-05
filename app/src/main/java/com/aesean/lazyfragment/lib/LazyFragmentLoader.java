package com.aesean.lazyfragment.lib;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

/**
 * LazyFragmentLoader
 *
 * @author xl
 * @version V1.0
 * @since 05/04/2017
 */
public class LazyFragmentLoader implements LazyFragment.ILazyFragmentLoader, Parcelable {

    private String mTag;
    private Bundle mBundle;
    private Class<? extends Fragment> mFragmentClass;

    public LazyFragmentLoader(Class<? extends Fragment> fragmentClass) {
        this(fragmentClass, null);
    }

    public LazyFragmentLoader(Class<? extends Fragment> fragmentClass, Bundle bundle) {
        mFragmentClass = fragmentClass;
        mBundle = bundle;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String tag) {
        mTag = tag;
    }

    @SuppressWarnings("WeakerAccess")
    protected LazyFragmentLoader(Parcel in) {
        mBundle = in.readBundle(getClass().getClassLoader());
    }

    public static final Creator<LazyFragmentLoader> CREATOR = new Creator<LazyFragmentLoader>() {
        @Override
        public LazyFragmentLoader createFromParcel(Parcel in) {
            return new LazyFragmentLoader(in);
        }

        @Override
        public LazyFragmentLoader[] newArray(int size) {
            return new LazyFragmentLoader[size];
        }
    };

    @Override
    public Fragment createFragment(Context context) {
        return Fragment.instantiate(context, mFragmentClass.getName(), mBundle);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(mBundle);
    }
}
