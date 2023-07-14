package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class OrderFragment extends Fragment {
    private ListView listView;
    private CustomAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        listView = view.findViewById(R.id.listView);
        adapter = new CustomAdapter(getActivity());

        // Asigna el adaptador a la lista
        listView.setAdapter(adapter);

        // Agrega algunos elementos de ejemplo
        adapter.addItem("Elemento 1");
        adapter.addItem("Elemento 2");
        adapter.addItem("Elemento 3");

        return view;
    }
}
