package com.mongooze.ui.landing.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.jachdev.commonlibs.widget.CustomTextView;
import com.mongooze.R;

public class IntroFragment extends Fragment {

    private static final String ARG_POSITION = "com.mongooze.ui.landing.intro.ui.home.ARG_POSITION";

    int mFragmentType = 0;

    @BindView(R.id.tvTitle)
    CustomTextView tvTitle;
    @BindView(R.id.tvContent)
    CustomTextView tvContent;
    @BindView(R.id.imageView)
    ImageView imageView;

    public static Fragment newInstance(int position) {
        IntroFragment fragment = new IntroFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        mFragmentType = getArguments().getInt(ARG_POSITION);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_intro, container, false);

        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] titles = getResources().getStringArray(R.array.intro_title);
        String[] contents = getResources().getStringArray(R.array.intro_content);
        int[] resources = new int[]{
                R.drawable.ic_reliable, R.drawable.ic_collaborative, R.drawable.ic_secure
        };


        tvTitle.setAnyText(titles[mFragmentType]);
        tvContent.setAnyText(contents[mFragmentType]);
        imageView.setImageResource(resources[mFragmentType]);
    }
}