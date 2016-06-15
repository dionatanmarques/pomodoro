package br.com.dionatanmarques.pomodoro.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class TimeService extends Service {
    private final IBinder binder;
    private boolean stop;

    public TimeService() {
        this.binder = new LocalBinder();
        this.stop = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class LocalBinder extends Binder {
        public TimeService getService() {
            return TimeService.this;
        }
    }

    public void startTimer() {
        int i = 0;
        while (!stop) {
            try {
                Thread.sleep(1000);
                Log.i("App", "Value: " + i);
                i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        this.stop = true;
    }
}
