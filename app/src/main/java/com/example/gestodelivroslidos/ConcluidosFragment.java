package com.example.gestodelivroslidos;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gestodelivroslidos.database.DatabaseHelper;
import com.example.gestodelivroslidos.models.Livro;
import com.example.gestodelivroslidos.models.LivroDetalhes;

import java.util.ArrayList;
import java.util.List;

public class ConcluidosFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView2;
    private DatabaseHelper databaseHelper;
    private LivrosLidosAdapter livrosLidosAdapter;
    private List<LivroDetalhes> livrosDetalhesList;
    private List<LivroDetalhes> filteredLivrosDetalhesList;

    public ConcluidosFragment() {
        // Required empty public constructor
    }

    public static ConcluidosFragment newInstance(String param1, String param2) {
        ConcluidosFragment fragment = new ConcluidosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        databaseHelper = new DatabaseHelper(requireContext()); // Initialize databaseHelper
    }

    @SuppressLint("MissingInflatedId")
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_concluidos, container, false);


        recyclerView2 = view.findViewById(R.id.recyclerViewLivrosConcluidos);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        livrosDetalhesList = databaseHelper.getCompletedBooks();
        filteredLivrosDetalhesList = new ArrayList<>(livrosDetalhesList);

        if (livrosDetalhesList.isEmpty()) {
            Toast.makeText(getContext(), "Não possui livros lidos.", Toast.LENGTH_LONG).show();
        } else {
            livrosLidosAdapter = new LivrosLidosAdapter(getContext(), filteredLivrosDetalhesList);
            recyclerView2.setAdapter(livrosLidosAdapter);
        }

        return view;
    }

}
