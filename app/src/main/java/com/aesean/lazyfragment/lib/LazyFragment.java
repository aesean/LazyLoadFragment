package com.aesean.lazyfragment.lib;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aesean.lazyfragment.R;

import java.io.Serializable;

/**
 * LazyFragment
 *
 * @author xl
 * @version V1.0
 * @since 10/03/2017
 */
public class LazyFragment extends Fragment {
    private static final String KEY_LAZY_FRAGMENT_LOADER = "KEY_LAZY_FRAGMENT_LOADER";

    private boolean mOnCreateView = false;
    private boolean isFirstVisible = true;

    private ViewGroup mLazyRootContainer;
    private ILazyFragmentLoader mLazyFragmentLoader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (savedInstanceState != null) {
            arguments.putAll(savedInstanceState);
        }
        initArguments(arguments);
    }

    private void initArguments(Bundle bundle) {
        Object loader = bundle.get(KEY_LAZY_FRAGMENT_LOADER);
        if (loader instanceof ILazyFragmentLoader) {
            mLazyFragmentLoader = (ILazyFragmentLoader) loader;
            return;
        }
        throw new IllegalArgumentException("LazyFragmentLoader异常，LazyFragmentLoader = "
                + mLazyFragmentLoader + ", 请通过LazyFragment.newInstance创建LazyFragment实例"
                + "，子类请通过createBundle方法创建参数");
    }

    protected ViewGroup getLazyRootContainer() {
        return mLazyRootContainer;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mOnCreateView = true;
        return inflater.inflate(R.layout.fragment_lazy, container, false);
    }

    private void clearRootContainer() {
        mLazyRootContainer.removeAllViews();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLazyRootContainer = (ViewGroup) view.findViewById(R.id.lazy_root_container);
        if (isFirstVisible && getUserVisibleHint()) {
            loadLazyFragment();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mOnCreateView = false;
        isFirstVisible = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mOnCreateView && getUserVisibleHint() && isFirstVisible) {
            loadLazyFragment();
        }
    }

    private void loadLazyFragment() {
        clearRootContainer();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.lazy_root_container, getLazyFragmentLoader(), mLazyFragmentLoader.getTag());
        transaction.commit();
        isFirstVisible = false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            outState.putAll(arguments);
        }
    }

    protected Fragment getLazyFragmentLoader() {
        return mLazyFragmentLoader.createFragment(getContext());
    }

    /**
     * 创建一个惰性加载Fragment
     *
     * @param target    需要加载的Fragment
     * @param arguments 需要给Fragment传递的参数
     * @return 惰性加载Fragment
     */
    public static LazyFragment newInstance(Class<? extends Fragment> target, Bundle arguments) {
        return newInstance(new LazyFragmentLoader(target, arguments));
    }

    static Bundle createBundle(ILazyFragmentLoader target) {
        return createBundle(null, target);
    }

    static Bundle createBundle(Bundle bundle, ILazyFragmentLoader target) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (target instanceof Parcelable) {
            bundle.putParcelable(KEY_LAZY_FRAGMENT_LOADER, (Parcelable) target);
        } else if (target instanceof Serializable) {
            bundle.putSerializable(KEY_LAZY_FRAGMENT_LOADER, (Serializable) target);
        } else {
            throw new IllegalArgumentException("ILazyFragmentLoader 必须实现Serializable或者Parcelable");
        }
        return bundle;
    }

    /**
     * 创建一个惰性加载Fragment
     *
     * @param target 惰性加载器，因为需要序列化和反序列化，注意接口实现必须是静态类。
     *               大多数时候请使用实现类{@link LazyFragmentLoader}
     * @return 惰性加载Fragment
     */
    public static LazyFragment newInstance(ILazyFragmentLoader target) {
        LazyFragment fragment = new LazyFragment();
        fragment.setArguments(createBundle(target));
        return fragment;
    }

    @SuppressWarnings("WeakerAccess")
    public interface ILazyFragmentLoader {
        String getTag();

        Fragment createFragment(Context context);
    }
}
