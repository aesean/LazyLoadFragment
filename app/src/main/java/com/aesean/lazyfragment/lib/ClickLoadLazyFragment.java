package com.aesean.lazyfragment.lib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * ClickLoadLazyFragment
 * 点击加载Fragment，如果目标Fragment不是放进ViewPager，或者需要手动控制加载可以参考这里。
 *
 * @author xl
 * @version V1.0
 * @since 05/04/2017
 */
public class ClickLoadLazyFragment extends LazyFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUserVisibleHintFalse();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLazyRootContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserVisibleHintTrue();
            }
        });
    }

    private void setUserVisibleHintFalse() {
        ClickLoadLazyFragment.super.setUserVisibleHint(false);
    }

    private void setUserVisibleHintTrue() {
        ClickLoadLazyFragment.super.setUserVisibleHint(true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(false);
    }

    public static ClickLoadLazyFragment newInstance(Class<? extends Fragment> target, Bundle arguments) {
        return newInstance(new LazyFragmentLoader(target, arguments));
    }

    public static ClickLoadLazyFragment newInstance(ILazyFragmentLoader target) {
        ClickLoadLazyFragment fragment = new ClickLoadLazyFragment();
        fragment.setArguments(createBundle(target));
        return fragment;
    }
}
