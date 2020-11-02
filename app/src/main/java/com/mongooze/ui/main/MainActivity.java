package com.mongooze.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.Manifest;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jachdev.commonlibs.validator.Validator;
import com.jachdev.commonlibs.widget.CustomEditText;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mongooze.R;
import com.mongooze.base.SessionManager;
import com.mongooze.ui.main.dialog.UserDialogFragment;
import com.mongooze.ui.meeting.MeetingActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.etMeeting)
    CustomEditText etMeeting;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        sessionManager = new SessionManager(getApplication());
    }

    @OnClick(R.id.btnJoin)
    public void onClickJoin(){
        if(Validator.isNoQuerySafe(etMeeting)){
            startActivity();
        }
    }

    @OnClick(R.id.ibSettings)
    public void onClickSettings(){
        showDialog(null);
    }

    private void startActivity() {
        if(sessionManager.isUserAvailable()){

            Dexter.withActivity(this)
                    .withPermissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.GET_ACCOUNTS
                    ).withListener(new MultiplePermissionsListener() {
                @Override public void onPermissionsChecked(MultiplePermissionsReport report) {

                    Intent intent = new Intent(getApplication(), MeetingActivity.class);
                    intent.setAction(MeetingActivity.ACTION_START_MEETING);
                    intent.putExtra(MeetingActivity.EXTRA_MEETING_NAME, etMeeting.getTextString());
                    startActivity(intent);
                }
                @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).check();
        }else{
            showDialog(new UserDialogFragment.UserDialogFragmentListener() {
                @Override
                public void onSave() {
                    Log.d(TAG, "onSave");
                    startActivity();
                }

                @Override
                public void onCancel() {
                    Log.d(TAG, "onCancel");
                }
            });
        }
    }

    private void showDialog(UserDialogFragment.UserDialogFragmentListener listener) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        UserDialogFragment fragment = UserDialogFragment.newInstance();
        fragment.setListener(listener);
        fragment.show(ft, "dialog");
    }
}
