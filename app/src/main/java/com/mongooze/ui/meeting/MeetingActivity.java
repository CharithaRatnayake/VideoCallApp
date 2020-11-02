package com.mongooze.ui.meeting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.modules.core.PermissionListener;
import com.mongooze.R;
import com.mongooze.base.SessionManager;

import org.jitsi.meet.sdk.JitsiMeetActivityDelegate;
import org.jitsi.meet.sdk.JitsiMeetActivityInterface;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetUserInfo;
import org.jitsi.meet.sdk.JitsiMeetView;
import org.jitsi.meet.sdk.JitsiMeetViewListener;

import java.net.URL;
import java.util.Map;

public class MeetingActivity extends AppCompatActivity implements JitsiMeetActivityInterface, JitsiMeetViewListener {

    public static final String ACTION_START_MEETING = "ACTION_START";
    public static final String EXTRA_MEETING_NAME = "com.mongooze.ui.meeting.MeetingActivity.EXTRA_MEETING_NAME";
    private static final String BASE_URL = "https://learn.techleadintl.com/";
    private static final String TAG = MeetingActivity.class.getSimpleName();

    private JitsiMeetView view;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new JitsiMeetView(this);
        sessionManager = new SessionManager(MeetingActivity.this);

        if(getIntent() == null || getIntent().getAction() == null){
            setContentView(R.layout.activity_main);
            return;
        }

        if(getIntent().getAction().equals(ACTION_START_MEETING)){

            JitsiMeetUserInfo userInfo = new JitsiMeetUserInfo();
            userInfo.setDisplayName(sessionManager.getUsername());
            userInfo.setEmail(sessionManager.getEmail());

            try{
                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                        .setServerURL(new URL(BASE_URL))
                        .setRoom(getIntent().getStringExtra(EXTRA_MEETING_NAME))
                        .setUserInfo(userInfo)
                        .setAudioMuted(false)
                        .setVideoMuted(true)
                        .setAudioOnly(true)
                        .setWelcomePageEnabled(false)
                        .build();
                view.join(options);
                view.setListener(this);

                setContentView(view);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        JitsiMeetActivityDelegate.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        JitsiMeetActivityDelegate.onHostResume(this);
    }

    @Override
    public void onBackPressed() {
        JitsiMeetActivityDelegate.onBackPressed();
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

    @Override
    public void onConferenceJoined(Map<String, Object> map) {
        Log.d(TAG, "onConferenceJoined: ");
    }

    @Override
    public void onConferenceTerminated(Map<String, Object> map) {
        Log.d(TAG, "onConferenceTerminated: ");

        MeetingActivity.this.finish();
    }

    @Override
    public void onConferenceWillJoin(Map<String, Object> map) {
        Log.d(TAG, "onConferenceWillJoin: ");
    }
}
