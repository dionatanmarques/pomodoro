package br.com.dionatanmarques.pomodoro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.com.dionatanmarques.pomodoro.R;
import br.com.dionatanmarques.pomodoro.adapter.TaskAdapter;
import br.com.dionatanmarques.pomodoro.dao.TaskDao;
import br.com.dionatanmarques.pomodoro.entity.Task;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fabNewTask;
    private RecyclerView taskList;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private TaskDao taskDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Button New Task
        fabNewTask = (FloatingActionButton) findViewById(R.id.fabNewTask);
        // Task List
        taskDao = new TaskDao(this);
    }

    public void newTask(View v) {
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Task> tasks = taskDao.findAll();

        taskList = (RecyclerView) findViewById(R.id.taskList);
        taskList.setHasFixedSize(true);

        adapter = new TaskAdapter(tasks);
        taskList.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(this);
        taskList.setLayoutManager(layoutManager);
    }
}
