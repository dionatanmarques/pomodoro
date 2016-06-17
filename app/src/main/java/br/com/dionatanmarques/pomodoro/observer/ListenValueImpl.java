package br.com.dionatanmarques.pomodoro.observer;

import android.os.Handler;
import android.widget.TextView;

public class ListenValueImpl implements ListenValue {
    private Handler handler;
    private TextView textView;

    public ListenValueImpl(TextView textView) {
        this.handler = new Handler();
        this.textView = textView;
    }

    @Override
    public void newValue(final String value) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(value);
            }
        });
    }
}
