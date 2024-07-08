package com.example.gestodelivroslidos;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gestodelivroslidos.database.DatabaseHelper;
import com.example.gestodelivroslidos.models.Livro;
import com.example.gestodelivroslidos.models.LivroDetalhes;

import java.util.ArrayList;
import java.util.List;

public class LivrosFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;
    private LivroAdapter livroAdapter;
    private List<LivroDetalhes> livrosDetalhesList;
    private List<LivroDetalhes> filteredLivrosDetalhesList;

    private List<Livro> livrosList;
    private List<Livro> filteredLivrosList;

    public LivrosFragment() {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_livros, container, false);
     //   Toast.makeText(requireContext(), "Este aplicativo não foi desenvolvido por mim.", Toast.LENGTH_LONG).show();


        recyclerView = view.findViewById(R.id.recyclerViewLivros);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        livrosDetalhesList = databaseHelper.getAllBooksAndNotes();
        filteredLivrosDetalhesList = new ArrayList<>(livrosDetalhesList);

//        livroAdapter = new LivroAdapter(getContext(), filteredLivrosDetalhesList);
//        recyclerView.setAdapter(livroAdapter);

        if (livrosDetalhesList.isEmpty()) {
            Toast.makeText(getContext(), "Sem livros disponíveis.", Toast.LENGTH_LONG).show();
        } else {
            livroAdapter = new LivroAdapter(getContext(), filteredLivrosDetalhesList);
            recyclerView.setAdapter(livroAdapter);
        }



        EditText searchBar = view.findViewById(R.id.search_bar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        return view;
    }

    private void filter(String text) {
        filteredLivrosDetalhesList.clear();
        for (LivroDetalhes livroDetalhes : livrosDetalhesList) {
            if (livroDetalhes.getTitulo().toLowerCase().contains(text.toLowerCase()) ||
                    livroDetalhes.getAutor().toLowerCase().contains(text.toLowerCase()) ||
                    livroDetalhes.getGenero().toLowerCase().contains(text.toLowerCase()) ||
                    livroDetalhes.getLocal().toLowerCase().contains(text.toLowerCase())) {
                filteredLivrosDetalhesList.add(livroDetalhes);
            }
        }
        livroAdapter.notifyDataSetChanged();
    }
}
