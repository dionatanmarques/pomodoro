package br.com.dionatanmarques.pomodoro.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.dionatanmarques.pomodoro.R;
import br.com.dionatanmarques.pomodoro.activity.MainActivity;
import br.com.dionatanmarques.pomodoro.dao.TaskDao;
import br.com.dionatanmarques.pomodoro.entity.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> tasks;
    private TaskDao taskDao;

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

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private TextView pomodoro;
        private Button btnDelete;

        public TaskViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.taskTitle);
            description = (TextView) itemView.findViewById(R.id.taskDescription);
            pomodoro = (TextView) itemView.findViewById(R.id.taskPomodoro);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Task task = tasks.get(getAdapterPosition());
                    taskDao = new TaskDao(v.getContext());
                    taskDao.delete(task.getId());
                    tasks.remove(task);
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), tasks.size());
                }
            });
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
