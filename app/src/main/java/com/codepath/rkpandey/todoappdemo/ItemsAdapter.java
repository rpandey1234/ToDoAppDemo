package com.codepath.rkpandey.todoappdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter knows how to display every data entry of the list in each row of the RecyclerView.
 * It knows they type of the view, its view layout and which data goes where in the view.
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    Context context;
    List<String> items;
    OnLongClickListener onLongClickListener;
    OnClickListener onClickListener;

    public interface OnLongClickListener {
        void onItemLongClicked(int position);
    }

    public interface OnClickListener {
        void onClickListener(int position);
    }

    public ItemsAdapter(Context context, List<String> items, OnLongClickListener onLongClickListener, OnClickListener onClickListener) {
        this.context = context;
        this.items = items;
        this.onLongClickListener = onLongClickListener;
        this.onClickListener = onClickListener;
    }

    // Responsible for creating view holders
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View todoView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(todoView);
    }

    // Responsible for binding data to a particular view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    // Tells the recycler view how many items are in the list
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Class-container to provide easy access to views that represent each row of the list.
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        /**
         * "Binds" data to TextView, effectively updates views with given values
         * that represent data for each individual row.
         *
         * @param todoText Text to be displayed for this to-do item.
         */
        public void bind(String todoText) {
            tvItem.setText(todoText);

            // assign listeners to provide callbacks for user clicks/taps/press events
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // Inform MainActivity that this item was long pressed
                    onLongClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
            tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickListener(getAdapterPosition());
                }
            });
        }
    }
}
