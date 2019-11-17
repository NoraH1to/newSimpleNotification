package com.norah1to.simplenotification;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.norah1to.simplenotification.Entity.Todo;
import com.norah1to.simplenotification.Util.DateUtil;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoViewHolder> {

    private static final String TAG = "TodoListAdapter";

    class TodoViewHolder extends  RecyclerView.ViewHolder {
        private final MaterialTextView contentView;
        private final MaterialTextView dateView;
        private final MaterialCardView cardView;
        private final ImageView alarmImgView;
        private final View priorityColorView;

        private TodoViewHolder (View itemView) {
            super(itemView);
            contentView = itemView.findViewById(R.id.text_todoitem_content);
            dateView = itemView.findViewById(R.id.text_todoitem_date);
            cardView = itemView.findViewById(R.id.card_todoitem);
            alarmImgView = itemView.findViewById(R.id.img_todoitem);
            priorityColorView = itemView.findViewById(R.id.color_view_todoitem);
        }
    }

    private final LayoutInflater mInflater;
    private List<Todo> mTodos;

    TodoListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.todo_item, parent, false);
        return new TodoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        if (mTodos != null) {
            Todo current = mTodos.get(position);
            holder.contentView.setText(current.getContent());
            holder.dateView.setText(DateUtil.formDatestr(current.getNoticeTime()));
            // 根据优先级切换卡片强调色
            switch (current.getPriority()) {
                case Todo.PROIORITY_HIGH:
                    holder.priorityColorView.setBackgroundColor(
                            ContextCompat.getColor(holder.itemView.getContext(),
                                    R.color.colorPriorityPink));
                    break;
                case Todo.PROIORITY_MID:
                    holder.priorityColorView.setBackgroundColor(
                            ContextCompat.getColor(holder.itemView.getContext(),
                                    R.color.colorPriorityOrange));
                    break;
                case Todo.PROIORITY_LOW:
                    holder.priorityColorView.setBackgroundColor(
                            ContextCompat.getColor(holder.itemView.getContext(),
                                    R.color.colorPriorityGrey));
                    break;
                default:
                    holder.priorityColorView.setBackgroundColor(
                            ContextCompat.getColor(holder.itemView.getContext(),
                                    R.color.colorPriorityGrey));
                    break;
            }
            holder.cardView.setOnClickListener(v -> {
                Log.d(TAG, "onBindViewHolder: " +
                        "position: " + position +
                        "todo: " + "\n" + current.toString());
                Intent intent = new Intent(holder.itemView.getContext(), MakeTodoActivity.class);
                intent.putExtra(Todo.TAG, current.getTodoID());
                holder.itemView.getContext().startActivity(intent);
            });
            if (current.getNotice() == Todo.STATE_NOT_NOTICE) {
                holder.alarmImgView.setVisibility(View.INVISIBLE);
            } else {
                holder.alarmImgView.setVisibility(View.VISIBLE);
            }
        } else {
            holder.contentView.setText("No todo");
            holder.dateView.setText("No date");
        }
    }

    void setTodos(List<Todo> todos) {
        mTodos = todos;
        notifyDataSetChanged();
    }

    void addTodo(Todo todo) {
        if (mTodos != null) {
            mTodos.add(0, todo);
            notifyItemInserted(0);
            notifyItemRangeChanged(0, getItemCount());
        }
    }

    @Override
    public int getItemCount() {
        if (mTodos != null)
            return mTodos.size();
        else return 0;
    }
}
