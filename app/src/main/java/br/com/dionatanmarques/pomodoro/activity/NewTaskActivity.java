package br.com.dionatanmarques.pomodoro.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.com.dionatanmarques.pomodoro.R;
import br.com.dionatanmarques.pomodoro.dao.TaskDao;
import br.com.dionatanmarques.pomodoro.entity.Task;

public class NewTaskActivity extends AppCompatActivity {

    private TextView tfTaskTitle;
    private TextView tfTaskDescription;
    private TextView tfTaskPomodoro;
    private TaskDao taskDao;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        // Backbutton
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        taskDao = new TaskDao(this);

        // Fields
        tfTaskTitle = (TextView) findViewById(R.id.tfTaskTitle);
        tfTaskDescription = (TextView) findViewById(R.id.tfTaskDescription);
        tfTaskPomodoro = (TextView) findViewById(R.id.tfTaskPomodoro);

        int id = getIntent().getIntExtra("id", 0);

        if (id == 0) {
            task = new Task();
        } else {
            task = taskDao.findById(id);
            if (task != null) {
                tfTaskTitle.setText(task.getTitle());
                tfTaskDescription.setText(task.getDescription());
                tfTaskPomodoro.setText(String.valueOf(task.getPomodoro()));
            }
        }
    }

    public void newTask(View v) {
        String title = tfTaskTitle.getText().toString();
        String description = tfTaskDescription.getText().toString();
        String pomodoro = tfTaskPomodoro.getText().toString();

        if (title == null || "".equals(title.trim())) {
            Toast.makeText(this, "Enter task title", Toast.LENGTH_SHORT).show();
        } else if (description == null || "".equals(description.trim())) {
            Toast.makeText(this, "Enter task description", Toast.LENGTH_SHORT).show();
        } else if (pomodoro == null || "".equals(pomodoro.trim())) {
            Toast.makeText(this, "Enter task pomodoro", Toast.LENGTH_SHORT).show();
        } else if (!pomodoro.matches("\\d+(?:\\.\\d+)?")) {
            Toast.makeText(this, "The pomodoro task must be a number", Toast.LENGTH_SHORT).show();
        } else {
            task.setTitle(title);
            task.setDescription(description);
            task.setPomodoro(Integer.parseInt(pomodoro));
            taskDao.save(task);
            finish();
        }
    }
}
