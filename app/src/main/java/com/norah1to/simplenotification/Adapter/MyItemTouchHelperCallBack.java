package com.norah1to.simplenotification.Adapter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.norah1to.simplenotification.R;

public class MyItemTouchHelperCallBack extends ItemTouchHelper.Callback {

    private ItemTouchMoveListener itemTouchMoveListener;

    public MyItemTouchHelperCallBack(ItemTouchMoveListener itemTouchMoveListener) {
        this.itemTouchMoveListener = itemTouchMoveListener;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.END;
        int swipeFlags = ItemTouchHelper.RIGHT;
        return makeMovementFlags(swipeFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        itemTouchMoveListener.onRightSwipe(viewHolder.itemView.getContext(), viewHolder.getAdapterPosition());
        Toast.makeText(viewHolder.itemView.getContext(),
                viewHolder.itemView.getContext().getText(R.string.notification_push_done),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {

        // 填色画笔
        Paint colorPaint = new Paint();
        colorPaint.setColor(((TodoListAdapter.TodoViewHolder)viewHolder).priorityColor);
        // 填色
        c.drawRect(0,
                viewHolder.itemView.getY(),
                viewHolder.itemView.getWidth(),
                viewHolder.itemView.getY() + viewHolder.itemView.getHeight(),
                colorPaint);

        // 文字画笔
        TextPaint textPaint = new TextPaint();
        textPaint.setDither(true);
        textPaint.setFilterBitmap(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setTextSize(50);
        String text = recyclerView.getContext().getString(R.string.notification_push_done);
        // 绘画文字
        c.drawText(text,
                viewHolder.itemView.getX() - text.length() * 25 - 16,
                viewHolder.itemView.getY() + 25 + viewHolder.itemView.getHeight()/2,
                textPaint);

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        // 不允许长按激活
        return false;
    }
}
