package com.example.yikes;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CallActivity extends AppCompatActivity {
    protected YIKES yikes;
    View view;
    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
    Ringtone r;

    Button Answer, Decline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_activity);
        yikes = (YIKES)this.getApplicationContext();
        yikes.setCurrentActivity(this);
        view = this.getWindow().getDecorView();
//        view.setBackgroundColor(0xFFFFFF);
        r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();

        Answer = findViewById(R.id.callAnswer);
        Answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View parent) {
                r.stop();
                Answer.setVisibility(View.GONE);
            }
        }
        );

        Decline = findViewById(R.id.callDecline);
        Decline.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(final View parent) {
                                          r.stop();
                                          Intent intent = new Intent(CallActivity.this, MainActivity.class);
                                          startActivity(intent);
                                      }
                                  }
        );



    }
}
