package br.com.dionatanmarques.pomodoro.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import br.com.dionatanmarques.pomodoro.R;
import br.com.dionatanmarques.pomodoro.dao.TaskDao;
import br.com.dionatanmarques.pomodoro.entity.Task;
import br.com.dionatanmarques.pomodoro.observer.ListenValue;
import br.com.dionatanmarques.pomodoro.observer.ListenValueImpl;
import br.com.dionatanmarques.pomodoro.service.TimeService;

public class CounterActivity extends AppCompatActivity {
    private TextView tfTaskTitle;
    private TextView tfTaskDescription;
    private TextView tfTimeCounter;
    private TaskDao taskDao;
    private TimeService mService;
    private boolean mBound;
    private ListenValue listenValue;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        taskDao = new TaskDao(this);

        // Fields
        tfTaskTitle = (TextView) findViewById(R.id.counterTaskTitle);
        tfTaskDescription = (TextView) findViewById(R.id.counterTaskDescription);
        tfTimeCounter = (TextView) findViewById(R.id.timeCounter);

        int id = getIntent().getIntExtra("id", 0);

        if (id > 0) {
            task = taskDao.findById(id);
            if (task != null) {
                tfTaskTitle.setText(task.getTitle());
                tfTaskDescription.setText(task.getDescription());
            }
        }

        listenValue = new ListenValueImpl(tfTimeCounter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent it = new Intent(this, TimeService.class);
        bindService(it, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TimeService.LocalBinder binder = (TimeService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            mService.add(listenValue);
            mService.startTimer(CounterActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    public void done(View view) {
        taskDao.done(task.getId());
        onStop();
        finish();
    }
}
