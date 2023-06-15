package com.example.myapplication;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OrderFragment extends Fragment {

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);

        // Get a reference to the BottomNavigationView
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);

        // Set the selected item in the BottomNavigationView to Order
        if (bottomNavigationView != null) {
            bottomNavigationView.setSelectedItemId(R.id.bottom_order);
        }

        // Get a reference to the title TextView
        TextView titleTextView = rootView.findViewById(R.id.titleTextView);

        // Set the title text to "Orden"
        titleTextView.setText("Orden");

        return rootView;
    }
}
