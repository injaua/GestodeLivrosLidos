//package com.example.gestodelivroslidos;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link AndamentoFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class AndamentoFragment extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public AndamentoFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AndamentoFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AndamentoFragment newInstance(String param1, String param2) {
//        AndamentoFragment fragment = new AndamentoFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_andamento, container, false);
//    }
//}

package com.example.gestodelivroslidos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gestodelivroslidos.database.DatabaseHelper;
import com.example.gestodelivroslidos.models.LivroDetalhes;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AndamentoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AndamentoFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView3;
    private LivroEmAndamentoAdapter livroEmAndamentoAdapter;
    private List<LivroDetalhes> livrosDetalhesList;
    private DatabaseHelper databaseHelper;

    public AndamentoFragment() {
        // Required empty public constructor
    }

    public static AndamentoFragment newInstance(String param1, String param2) {
        AndamentoFragment fragment = new AndamentoFragment();
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
        databaseHelper = new DatabaseHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_andamento, container, false);

      //  Toast.makeText(requireContext(), "Este aplicativo não foi desenvolvido por mim.", Toast.LENGTH_LONG).show();


        recyclerView3 = view.findViewById(R.id.recyclerViewLivrosEmAndamento);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext()));

        livrosDetalhesList = databaseHelper.getInProgressBooks();
        if (livrosDetalhesList.isEmpty()) {
            Toast.makeText(getContext(), "Não há livros em andamento.", Toast.LENGTH_SHORT).show();
        } else {
            livroEmAndamentoAdapter = new LivroEmAndamentoAdapter(livrosDetalhesList);
            recyclerView3.setAdapter(livroEmAndamentoAdapter);
        }

        return view;
    }
}
