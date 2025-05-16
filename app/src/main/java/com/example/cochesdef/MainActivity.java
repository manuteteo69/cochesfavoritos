package com.example.cochesdef;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // Set default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FavoritesFragment())
                    .commit();
            bottomNav.setSelectedItemId(R.id.nav_favorites);
        }
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    selectedFragment = new HomeFragment();
                } else if (itemId == R.id.nav_deals) {
                    selectedFragment = new DealsFragment();

                    // 💥 Ocultar badge cuando se pulsa
                    BadgeDrawable badge = bottomNav.getBadge(R.id.nav_deals);
                    if (badge != null) {
                        badge.setVisible(false);
                        // O también: bottomNav.removeBadge(R.id.nav_deals);
                    }
                } else if (itemId == R.id.nav_search) {
                    selectedFragment = new SearchFragment();
                } else if (itemId == R.id.nav_favorites) {
                    selectedFragment = new FavoritesFragment();
                } else if (itemId == R.id.nav_profile) {
                    selectedFragment = new ProfileFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                }

                return true;
            };
}
