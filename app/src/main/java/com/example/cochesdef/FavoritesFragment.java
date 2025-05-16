package com.example.cochesdef;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;
    private final List<Car> carList = new ArrayList<>();
    private ImageView arrowIcon;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        arrowIcon = view.findViewById(R.id.arrow_icon);
        recyclerView = view.findViewById(R.id.favorites_recycler_view);

        initializeCarList();
        setupRecyclerView(view);

        // Toggle visual de la flecha
        arrowIcon.setOnClickListener(v -> {
            Object tag = arrowIcon.getTag();
            boolean isDown = tag == null || "down".equals(tag.toString());

            arrowIcon.setImageResource(isDown ? R.drawable.ic_arrow_up : R.drawable.ic_arrow_down);
            arrowIcon.setTag(isDown ? "up" : "down");
        });

        return view;
    }


    private void initializeCarList() {
        carList.clear();
        carList.add(new Car("Porsche 911 Carrera", "GBP 98,700.00", "2021", R.drawable.porsche_911));
        carList.add(new Car("Mercedes SLS AMG", "GBP 98,700.00", "2018", R.drawable.mercedes_sls));
        carList.add(new Car("840i Coupe M Sport", "GBP 82,000.00", "2018", R.drawable.bmw_840i));
        carList.add(new Car("Mercedes-Benz ML 400", "GBP 98,700.00", "2018", R.drawable.mercedes_ml));
        carList.add(new Car("McLaren 720S Coupe", "GBP 230,500.00", "2018", R.drawable.mclaren_720s));
    }

    private void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.favorites_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FavoriteAdapter(carList);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false; // No drag and drop
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // No borrar directamente, se hace con el botón delete
                adapter.notifyItemChanged(viewHolder.getBindingAdapterPosition());
            }

            @Override
            public void onChildDraw(@NonNull Canvas c,
                                    @NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY,
                                    int actionState, boolean isCurrentlyActive) {

                FavoriteAdapter.FavoriteViewHolder holder = (FavoriteAdapter.FavoriteViewHolder) viewHolder;
                View foregroundView = holder.foregroundLayout;

                // Limita swipe al 30% del ancho
                float maxSwipe = -foregroundView.getWidth() * 0.3f;
                float clampedDx = Math.max(dX, maxSwipe);

                // Aplica desplazamiento
                ViewCompat.setTranslationX(foregroundView, clampedDx);
            }

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                        @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0, ItemTouchHelper.LEFT);
            }
        };

        new ItemTouchHelper(swipeCallback).attachToRecyclerView(recyclerView);
    }
}
