package com.aesean.lazyfragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aesean.lazyfragment.lib.ClickLoadLazyFragment;
import com.aesean.lazyfragment.lib.LazyFragment;
import com.aesean.lazyfragment.lib.LazyFragmentLoader;

/**
 * SampleFragment
 * 使用示例
 *
 * @author xl
 * @version V1.0
 * @since 02/04/2017
 */
public class SampleFragment extends Fragment {
    private static final String KEY_TITLE = "KEY_TITLE";
    private static final String TAG = "SampleFragment";
    private String mTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getArguments().getString(KEY_TITLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: ");
        return inflater.inflate(R.layout.fragment_sample, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated: ");
        view.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.findViewById(R.id.progress_bar).setVisibility(View.GONE);
                TextView textView = (TextView) view.findViewById(R.id.text_view);
                textView.setText(mTitle);
            }
        }, 1000);
    }

    public static Bundle newArguments(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, title);
        return bundle;
    }

    public static ClickLoadLazyFragment newSampleLazyInstance(String title) {
        return ClickLoadLazyFragment.newInstance(new LazyFragmentLoader(SampleFragment.class, newArguments(title)));
    }

    public static LazyFragment newLazyInstance(String title) {
        return LazyFragment.newInstance(new LazyFragmentLoader(SampleFragment.class, newArguments(title)));
    }

    public static SampleFragment newInstance(String title) {
        SampleFragment fragment = new SampleFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }
}

