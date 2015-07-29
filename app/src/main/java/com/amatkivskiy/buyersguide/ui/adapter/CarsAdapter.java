package com.amatkivskiy.buyersguide.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amatkivskiy.buyersguide.R;
import com.amatkivskiy.buyersguide.model.Car;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.ViewHolder> {

  //recyclerView OnItemClickListener and OnItemLongClickListener implementations.
  public interface OnItemClickListener {

    void onItemClicked(int position);
  }

  public interface OnItemLongClickListener {

    boolean onItemLongClicked(int position);
  }

  private List<Car> items = Collections.emptyList();
  private OnItemClickListener onItemClick;
  private OnItemLongClickListener onItemLongClick;
  private Context context;

  public CarsAdapter(Context context, OnItemClickListener onItemClick,
                     OnItemLongClickListener onItemLongClick) {
    this.context = context;
    this.onItemClick = onItemClick;
    this.onItemLongClick = onItemLongClick;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    View v = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.car_list_item, viewGroup, false);

    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(ViewHolder viewHolder, final int position) {
    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onItemClick.onItemClicked(position);
      }
    });
    viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        return onItemLongClick.onItemLongClicked(position);
      }
    });
    viewHolder.carNameTextView.setText(items.get(position).getName());

    Picasso.with(this.context)
        .load(items.get(position).getMakeIcon())
        .resize(150, 150)
        .placeholder(R.drawable.car_placeholder)
        .error(R.drawable.ic_error)
        .into(viewHolder.carIconImageView);
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  public List<Car> getItems() {
    return items;
  }

  public void setItems(List<Car> items) {
    this.items = items;
    this.notifyDataSetChanged();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.car_name)
    TextView carNameTextView;
    @Bind(R.id.car_icon)
    ImageView carIconImageView;

    public ViewHolder(View v) {
      super(v);
      ButterKnife.bind(this, v);
    }
  }
}
