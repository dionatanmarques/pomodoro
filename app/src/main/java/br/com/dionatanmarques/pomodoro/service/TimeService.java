package br.com.dionatanmarques.pomodoro.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.dionatanmarques.pomodoro.observer.ListenValue;
import br.com.dionatanmarques.pomodoro.observer.ServiceNotifier;

public class TimeService extends Service implements ServiceNotifier {
    private final IBinder binder;
    private boolean stop;
    private boolean started;
    private Calendar calendar = Calendar.getInstance();
    private DateFormat dateFormat = new SimpleDateFormat("mm:ss");
    private ListenValue listenValue;

    public TimeService() {
        this.binder = new LocalBinder();
        this.stop = false;
        this.started = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void add(ListenValue obj) {
        this.listenValue = obj;
    }

    @Override
    public void notifyValue(String value) {
        listenValue.newValue(value);
    }

    public class LocalBinder extends Binder {
        public TimeService getService() {
            return TimeService.this;
        }
    }

    public void startTimer() {
        if (!started) {
            calendar.set(Calendar.MINUTE, 25);
            calendar.set(Calendar.SECOND, 00);
            started = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!stop) {
                        String timeText = getTimeText();
                        notifyValue(timeText);
                        if (itsOver(timeText)) {
                            stop();
                        } else {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    stop = false;
                    started = false;
                }
            }).start();
        }
    }

    private boolean itsOver(String timeText) {
        return "00:00".equals(timeText);
    }

    public void stop() {
        this.stop = true;
    }

    private String getTimeText() {
        calendar.add(Calendar.SECOND, -1);
        return dateFormat.format(calendar.getTime());
    }
}
