package br.com.dionatanmarques.pomodoro.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.dionatanmarques.pomodoro.R;
import br.com.dionatanmarques.pomodoro.entity.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> tasks;

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_card, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.getTitle().setText(task.getTitle());
        holder.getDescription().setText(task.getDescription());
        holder.getPomodoro().setText("Pomodoros: " + String.valueOf(task.getPomodoro()));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private TextView pomodoro;

        public TaskViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.taskTitle);
            description = (TextView) itemView.findViewById(R.id.taskDescription);
            pomodoro = (TextView) itemView.findViewById(R.id.taskPomodoro);
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getPomodoro() {
            return pomodoro;
        }

        public TextView getDescription() {
            return description;
        }
    }
}
