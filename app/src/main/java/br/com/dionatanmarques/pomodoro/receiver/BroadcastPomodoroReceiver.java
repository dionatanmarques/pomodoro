package br.com.dionatanmarques.pomodoro.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import br.com.dionatanmarques.pomodoro.R;

public class BroadcastPomodoroReceiver extends BroadcastReceiver {
    public BroadcastPomodoroReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if("ALARM_POMODORO".equals(action)) {
            playAlarm(context);
        }
    }

    private void playAlarm(Context context) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.alarm);
        mediaPlayer.setLooping(false);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                mp.release();
            }
        });
        mediaPlayer.start();
    }
}