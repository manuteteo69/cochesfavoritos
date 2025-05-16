package com.example.cochesdef;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private final List<Car> carList;

    public FavoriteAdapter(List<Car> carList) {
        this.carList = carList;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.carName.setText(car.getName());
        holder.carPrice.setText(car.getPrice());
        holder.carYear.setText(car.getYear());
        holder.carImage.setImageResource(car.getImageResId());

        // Lógica del botón eliminar
        holder.deleteButton.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                carList.remove(pos);
                notifyItemRemoved(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        final ImageView carImage;
        final TextView carName;
        final TextView carPrice;
        final TextView carYear;
        final ImageView deleteButton;
        final View foregroundLayout;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            carImage = itemView.findViewById(R.id.car_image);
            carName = itemView.findViewById(R.id.car_name);
            carPrice = itemView.findViewById(R.id.car_price);
            carYear = itemView.findViewById(R.id.car_year);
            deleteButton = itemView.findViewById(R.id.delete_button);
            foregroundLayout = itemView.findViewById(R.id.foreground_layout);
        }
    }
}
