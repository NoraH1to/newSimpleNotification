package com.norah1to.simplenotification.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.norah1to.simplenotification.Entity.Todo;
import com.norah1to.simplenotification.MakeTodoActivity;
import com.norah1to.simplenotification.R;
import com.norah1to.simplenotification.Util.DateUtil;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoViewHolder> {

    private static final String TAG = "TodoListAdapter";

    class TodoViewHolder extends  RecyclerView.ViewHolder {
        // 输入的内容
        private final MaterialTextView contentView;
        // 日期显示
        private final MaterialTextView dateView;
        // 根布局（卡片）
        private final MaterialCardView cardView;
        // 提醒图标
        private final ImageView alarmImgView;
        // 优先级强调色
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
    // 数据列表
    private List<Todo> mTodos;

    public TodoListAdapter(Context context) {
        // 加载布局
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
                case Todo.PROIORITY_HIGH: // 高优先级
                    holder.priorityColorView.setBackgroundColor(
                            ContextCompat.getColor(holder.itemView.getContext(),
                                    R.color.colorPriorityPink));
                    break;
                case Todo.PROIORITY_MID: // 中优先级
                    holder.priorityColorView.setBackgroundColor(
                            ContextCompat.getColor(holder.itemView.getContext(),
                                    R.color.colorPriorityOrange));
                    break;
                case Todo.PROIORITY_LOW: // 低优先级
                    holder.priorityColorView.setBackgroundColor(
                            ContextCompat.getColor(holder.itemView.getContext(),
                                    R.color.colorPriorityGrey));
                    break;
                default: // 默认低优先级样式
                    holder.priorityColorView.setBackgroundColor(
                            ContextCompat.getColor(holder.itemView.getContext(),
                                    R.color.colorPriorityGrey));
                    break;
            }

            // 点击跳转到编辑页
            holder.cardView.setOnClickListener(v -> {
                Log.d(TAG, "onBindViewHolder: " +
                        "position: " + position +
                        "todo: " + "\n" + current.toString());
                Intent intent = new Intent(holder.itemView.getContext(), MakeTodoActivity.class);
                // Intent 中传入 TodoID
                intent.putExtra(Todo.TAG, current.getTodoID());
                holder.itemView.getContext().startActivity(intent);
            });

            // 设置提醒图标是否展示
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

    public void setTodos(List<Todo> todos) {
        mTodos = todos;
        notifyDataSetChanged();
    }

    public void addTodo(Todo todo) {
        if (mTodos != null) {
            // 加到顶部
            mTodos.add(0, todo);
            // 适配器中插入顶部
            notifyItemInserted(0);
            Log.d(TAG, "addTodo: itemCount" + getItemCount());
            // 适配器刷新顶部
            notifyItemRangeChanged(0, 1);
        }
    }

    @Override
    public int getItemCount() {
        if (mTodos != null)
            return mTodos.size();
        else return 0;
    }
}
