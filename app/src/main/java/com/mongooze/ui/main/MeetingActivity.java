package com.mongooze.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.facebook.react.modules.core.PermissionListener;
import com.mongooze.R;

import org.jitsi.meet.sdk.JitsiMeetActivityDelegate;
import org.jitsi.meet.sdk.JitsiMeetActivityInterface;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetView;

import java.net.MalformedURLException;
import java.net.URL;

public class MeetingActivity extends AppCompatActivity implements JitsiMeetActivityInterface {

    public static final String ACTION_START_MEETING = "ACTION_START";
    public static final String EXTRA_MEETING_NAME = "com.mongooze.ui.main.MeetingActivity.EXTRA_MEETING_NAME";
    private static final String BASE_URL = "https://learn.techleadintl.com/";

    private JitsiMeetView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new JitsiMeetView(this);

        if(getIntent() == null || getIntent().getAction() == null){
            setContentView(R.layout.activity_main);
            return;
        }

        if(getIntent().getAction().equals(ACTION_START_MEETING)){
            try{
                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                        .setServerURL(new URL(BASE_URL))
                        .setRoom(getIntent().getStringExtra(EXTRA_MEETING_NAME))
                        .setAudioMuted(true)
                        .setVideoMuted(true)
                        .setAudioOnly(true)
                        .setWelcomePageEnabled(false)
                        .build();
                view.join(options);

                setContentView(view);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        JitsiMeetActivityDelegate.onHostResume(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        JitsiMeetActivityDelegate.onHostPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        view.dispose();
        view = null;

        JitsiMeetActivityDelegate.onHostDestroy(this);
    }

    @Override
    public void requestPermissions(String[] permissions, int requestCode, PermissionListener permissionListener) {
        JitsiMeetActivityDelegate.onRequestPermissionsResult(requestCode, permissions, new int[]{});
    }
}
