package com.mongooze.ui.landing.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jachdev.commonlibs.validator.Validator;
import com.jachdev.commonlibs.widget.CustomEditText;
import com.mongooze.R;
import com.mongooze.base.SessionManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserDialogFragment extends DialogFragment {

    @BindView(R.id.etName)
    CustomEditText etName;
    @BindView(R.id.etEmail)
    CustomEditText etEmail;

    private UserDialogFragmentListener listener;

    public static UserDialogFragment newInstance() {
        UserDialogFragment fragment = new UserDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_dialog, container, false);

        ButterKnife.bind(this, view);

        init();

        return view;
    }

    private void init() {
        SessionManager manager = new SessionManager(getContext());

        etName.setAnyText(manager.getUsername());
        etEmail.setAnyText(manager.getEmail());
    }

    @OnClick(R.id.btnSave)
    public void btnSave(){
        SessionManager manager = new SessionManager(getContext());

        if(Validator.isValidUserName(etName) && Validator.isValidEmail(etEmail)){
            manager.setUsername(etName.getTrimText());
            manager.setEmail(etEmail.getTrimText());

            UserDialogFragment.this.dismiss();

            if(listener != null)
                listener.onSave();
        }
    }

    @OnClick(R.id.btnCancel)
    public void btnCancel(){
        UserDialogFragment.this.dismiss();

        if(listener != null)
            listener.onCancel();
    }

    public void setListener(UserDialogFragmentListener listener) {
        this.listener = listener;
    }

    public interface UserDialogFragmentListener{
        void onSave();
        void onCancel();
    }
}
