package com.example.gestodelivroslidos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestodelivroslidos.database.DatabaseHelper;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private DatabaseHelper db;

    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
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
        db = new DatabaseHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

      //  Toast.makeText(requireContext(), "Este aplicativo não foi desenvolvido por mim.", Toast.LENGTH_LONG).show();

        // Identifique o TextView textSair
        TextView textSair = view.findViewById(R.id.textSair);

        // Configure um OnClickListener no TextView textSair
        textSair.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        // Obter dados do banco de dados
        int totalLidos = db.getLivrosLidos().size();
        int totalEmAndamento = db.getLivrosEmAndamento().size();
       int naoLidos = db.getAllLivros().size() - totalLidos - totalEmAndamento; // Assumindo que os livros podem ser lidos, em andamento ou disponíveis
        int totalDisponiveis = db.getAllLivros().size();
        HashMap<String, Integer> livrosPorGenero = (HashMap<String, Integer>) db.getLivrosPorGenero2();
        HashMap<String, Integer> livrosPorGenero2 = (HashMap<String, Integer>) db.getLivrosPorGenero();
        HashMap<String, Integer> livrosPorAutor = (HashMap<String, Integer>) db.getLivrosPorAutor();
        int lidosEsteMes = db.getLivrosLidosNesteMes().size();

        // Configurar gráfico de barras
        BarChart mBarChart = view.findViewById(R.id.barchart);
        Random random = new Random();

        for (String genero : livrosPorGenero.keySet()) {
            // Gerar uma cor aleatória com RGB balanceado
            int color = getRandomColor(random);

            // Adicionar barra ao gráfico com a cor gerada
            mBarChart.addBar(new BarModel(genero, livrosPorGenero.get(genero), color));
        }

        mBarChart.setBarWidth(120f);
        mBarChart.startAnimation();

        // Atualizar TextViews
        TextView textLivros = view.findViewById(R.id.textLivros);
        textLivros.setText("Disponíveis: " + totalDisponiveis);

        TextView textLivrosNaoLidos = view.findViewById(R.id.textLivrosNaoLidos);
        textLivrosNaoLidos.setText("Por Ler: " + naoLidos);

        TextView textLivrosAndamento = view.findViewById(R.id.textLivrosAndamento);
        textLivrosAndamento.setText("Em andamento: " + totalEmAndamento);

        TextView textLivrosConcluido = view.findViewById(R.id.textLivrosconcluido);
        textLivrosConcluido.setText("Concluídos: " + totalLidos);

        TextView textLivrosGenero = view.findViewById(R.id.textLivros_lidosGenero);
        //textLivrosGenero.setText("Por Gênero: " + livrosPorGenero.size());
        textLivrosGenero.setText("Gênero + lido: " + getMostReadGenero(livrosPorGenero2));

        TextView textLivrosMes = view.findViewById(R.id.textLivrosMes);
        textLivrosMes.setText("Neste mês: " + lidosEsteMes);

        TextView textLivrosAutor = view.findViewById(R.id.textLivrosAutor);
        textLivrosAutor.setText("Autor + lido: " + getMostReadAuthor(livrosPorAutor));


        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
            }
        });

            return view;
    }


    private String getMostReadAuthor(Map<String, Integer> livrosPorAutor) {
        String mostReadAuthor = "";
        int maxCount = 0;
        for (String autor : livrosPorAutor.keySet()) {
            if (livrosPorAutor.get(autor) > maxCount) {
                maxCount = livrosPorAutor.get(autor);
                mostReadAuthor = autor;
            }
        }
        return mostReadAuthor;
    }


private String getMostReadGenero(Map<String, Integer> livrosPorGenero) {
    String mostReadGenero = "";
    int maxCount = 0;
    for (String genero : livrosPorGenero.keySet()) {
        if (livrosPorGenero.get(genero) > maxCount) {
            maxCount = livrosPorGenero.get(genero);
            mostReadGenero = genero;
        }
    }
    return mostReadGenero;
}


    private int getRandomColor(Random random) {
        // Gerar valores RGB entre 0 e 255
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        // Combinar os valores RGB em uma cor inteira ARGB (com transparência completa)
        return Color.argb(255, red, green, blue);
    }
}
