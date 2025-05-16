package com.example.cochesdef;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        Button notifyButton = view.findViewById(R.id.notify_button); // Asegúrate de que este ID exista en home_fragment.xml
        notifyButton.setOnClickListener(v -> {
            BottomNavigationView bottomNav = requireActivity().findViewById(R.id.bottom_navigation);
            BadgeDrawable badge = bottomNav.getOrCreateBadge(R.id.nav_deals); // Segundo icono
            badge.setVisible(true);
            badge.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red)); // Cambia si tienes otro color
        });

        return view;
    }
}
