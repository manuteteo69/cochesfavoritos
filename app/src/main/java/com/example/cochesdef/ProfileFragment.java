package com.example.cochesdef;

import android.content.Intent;                     //  ← nuevo
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.fragment.app.Fragment;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;
import com.google.android.material.card.MaterialCardView;

/**
 * Pantalla “Profile” que replica la maqueta adjunta.
 */
public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        super(R.layout.fragment_profile);   // inflamos directamente el layout
    }

    /* ----------------------------------------------------------------------- */
    /*  Ciclo de vida                                                          */
    /* ----------------------------------------------------------------------- */
    @OptIn(markerClass = ExperimentalBadgeUtils.class)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* ----------------------- Accesos rápidos --------------------------- */
        configShortcut(view, R.id.shortcutAd,
                R.drawable.ic_add_circle,      "Place an Ad");

        configShortcut(view, R.id.shortcutInbox,
                R.drawable.ic_mail_outline,    "Inbox", 2);   // badge “2”

        configShortcut(view, R.id.shortcutAnalytics,
                R.drawable.ic_chart_bar,       "Analytics");

        configShortcut(view, R.id.shortcutFav,
                R.drawable.ic_favoritez, "Favourites");

        /* ---------------------- Your Account ------------------------------ */
        configEntry(view, R.id.entryMyAds,
                R.drawable.ic_bookmark, getString(R.string.my_ads));

        configEntry(view, R.id.entrySavedSearches,
                R.drawable.ic_search, getString(R.string.saved_searches));

        configEntry(view, R.id.entryPayments,
                R.drawable.ic_credit_card, getString(R.string.payments));

        configEntry(view, R.id.entryProfile,
                R.drawable.ic_profilez, getString(R.string.profile));

        /* ------------------------ Settings -------------------------------- */
        configEntryWithDrawable(view, R.id.entryCountry,
                R.drawable.ic_public, getString(R.string.country),
                R.drawable.flag_uk);

        configEntryWithText(view, R.id.entryLanguage,
                R.drawable.ic_language, getString(R.string.language),
                "English", true);

        configEntry(view, R.id.entryPreference,
                R.drawable.ic_tune, getString(R.string.preference));

        configEntry(view, R.id.entryNotifications,
                R.drawable.ic_notification, getString(R.string.notifications));

        /* ---------------------- Listeners a cada fila --------------------- */
        setEntryClick(R.id.entryMyAds,          getString(R.string.my_ads));
        setEntryClick(R.id.entrySavedSearches,  getString(R.string.saved_searches));
        setEntryClick(R.id.entryPayments,       getString(R.string.payments));
        setEntryClick(R.id.entryProfile,        getString(R.string.profile));
        setEntryClick(R.id.entryCountry,        getString(R.string.country));
        setEntryClick(R.id.entryLanguage,       getString(R.string.language));
        setEntryClick(R.id.entryPreference,     getString(R.string.preference));
        setEntryClick(R.id.entryNotifications,  getString(R.string.notifications));
    }

    /* ----------------------------------------------------------------------- */
    /*  Helpers                                                                */
    /* ----------------------------------------------------------------------- */

    /** Listener genérico que lanza DestinationActivity con el título */
    private void setEntryClick(int entryId, String title) {
        View entry = requireView().findViewById(entryId);
        entry.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), DestinationActivity.class);
            intent.putExtra(DestinationActivity.EXTRA_TITLE, title);
            startActivity(intent);
        });
    }

    /** Atajo sin badge */
    private void configShortcut(View root, int id, int iconRes, String label) {
        configShortcut(root, id, iconRes, label, 0);
    }

    /** Atajo con badge opcional */
    @OptIn(markerClass = ExperimentalBadgeUtils.class)
    private void configShortcut(View root, int id,
                                int iconRes, String label, int badgeNumber) {

        LinearLayout shortcut      = root.findViewById(id);
        MaterialCardView container = shortcut.findViewById(R.id.shortcutContainer);
        ImageView icon             = shortcut.findViewById(R.id.ivShortcut);
        TextView  text             = shortcut.findViewById(R.id.tvShortcutLabel);

        icon.setImageResource(iconRes);
        text.setText(label);

        if (badgeNumber > 0) {
            shortcut.post(() -> {
                BadgeDrawable badge = BadgeDrawable.create(requireContext());
                badge.setNumber(badgeNumber);
                badge.setBadgeGravity(BadgeDrawable.TOP_END);

                BadgeUtils.attachBadgeDrawable(badge, icon, container);
            });
        }
    }

    /* ---------- Métodos configEntry… (sin cambios) ---------- */

    private void configEntry(View root, int entryId, int iconRes, String title) {
        View entry = root.findViewById(entryId);
        ((ImageView) entry.findViewById(R.id.ivIcon)).setImageResource(iconRes);
        ((TextView)  entry.findViewById(R.id.tvTitle)).setText(title);
    }

    private void configEntryWithText(View root, int entryId, int iconRes,
                                     String title, String valueText, boolean disabled) {
        configEntry(root, entryId, iconRes, title);
        View entry = root.findViewById(entryId);
        FrameLayout container = entry.findViewById(R.id.valueContainer);
        TextView tv = new TextView(requireContext());
        tv.setText(valueText); tv.setTextSize(14); tv.setEnabled(!disabled);
        container.addView(tv); container.setVisibility(View.VISIBLE);
    }

    private void configEntryWithDrawable(View root, int entryId, int iconRes,
                                         String title, int drawableRes) {
        configEntry(root, entryId, iconRes, title);
        View entry = root.findViewById(entryId);
        FrameLayout container = entry.findViewById(R.id.valueContainer);
        ImageView iv = new ImageView(requireContext());
        iv.setImageResource(drawableRes);
        iv.setLayoutParams(new FrameLayout.LayoutParams(72, 72));
        container.addView(iv); container.setVisibility(View.VISIBLE);
    }
}
