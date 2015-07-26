package com.amatkivskiy.buyersguide.ui.adapters;

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

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.ViewHolder> {

  private List<Car> items = Collections.emptyList();
  private Context context;

  public CarsAdapter(Context context) {
    this.context = context;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    View v = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.car_list_item, viewGroup, false);

    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(ViewHolder viewHolder, final int position) {
    viewHolder.carNameTextView.setText(items.get(position).getName());

    Picasso.with(this.context)
        .load(items.get(position).getMakeIcon())
        .resize(150, 150)
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

  public static class ViewHolder extends RecyclerView.ViewHolder {

    public final TextView carNameTextView;
    public final ImageView carIconImageView;

    public ViewHolder(View v) {
      super(v);
      carNameTextView = (TextView) v.findViewById(R.id.car_name);
      carIconImageView = (ImageView) v.findViewById(R.id.car_icon);
    }
  }
}
