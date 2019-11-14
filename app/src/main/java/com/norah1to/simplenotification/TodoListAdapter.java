package com.norah1to.simplenotification;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.norah1to.simplenotification.Entity.Todo;
import com.norah1to.simplenotification.Todos.MakeTodoActivity;
import com.norah1to.simplenotification.Util.DateUtil;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoViewHolder> {

    private static final String TAG = "TodoListAdapter";

    class TodoViewHolder extends  RecyclerView.ViewHolder {
        private final MaterialTextView contentView;
        private final MaterialTextView dateView;
        private final MaterialCardView cardView;

        private TodoViewHolder (View itemView) {
            super(itemView);
            contentView = itemView.findViewById(R.id.text_todoitem_content);
            dateView = itemView.findViewById(R.id.text_todoitem_date);
            cardView = itemView.findViewById(R.id.card_todoitem);
        }
    }

    private final LayoutInflater mInflater;
    private List<Todo> mTodos; // Cached copy of words

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
            holder.dateView.setText(DateUtil.formDatestr(current.getDate()));
            // todo：跳转到编辑页面
            holder.cardView.setOnClickListener(v -> {
                Intent intent = new Intent(holder.itemView.getContext(), MakeTodoActivity.class);
                intent.putExtra(Todo.TAG, mTodos.get(position));
                holder.itemView.getContext().startActivity(intent);
            });
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
        }
    }

    @Override
    public int getItemCount() {
        if (mTodos != null)
            return mTodos.size();
        else return 0;
    }
}
