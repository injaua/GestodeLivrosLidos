//package com.example.gestodelivroslidos;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import androidx.fragment.app.Fragment;
//
//import com.example.gestodelivroslidos.database.DatabaseHelper;
//import com.example.gestodelivroslidos.models.Livro;
//import com.google.android.material.button.MaterialButton;
//import com.google.android.material.textfield.TextInputLayout;
//
//import java.util.Calendar;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link AdicionarFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class AdicionarFragment extends Fragment {
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
//    private Spinner spinnerGenero;
//    private ArrayAdapter<CharSequence> generoAdapter;
//
//    public AdicionarFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AdicionarFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AdicionarFragment newInstance(String param1, String param2) {
//        AdicionarFragment fragment = new AdicionarFragment();
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
//        View view = inflater.inflate(R.layout.fragment_adicionar, container, false);
//        //DatabaseHelper databaseHelper = new DatabaseHelper(this);
//        DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());
//
//       // Toast.makeText(requireContext(), "Este aplicativo não foi desenvolvido por mim.", Toast.LENGTH_LONG).show();
//
//        int livro_id; // Valor padrão, substitua pelo valor real do livro
//
//        // Initialize UI components
//        EditText editTextTitulo = view.findViewById(R.id.editTextTitulo);
//        EditText editTextAutor = view.findViewById(R.id.editTextAutor);
//        EditText editTextNumeroPaginas = view.findViewById(R.id.editTextNumeroPaginas);
//        EditText editTextEdicao = view.findViewById(R.id.editTextEdicao);
//        EditText editTextAno = view.findViewById(R.id.editTextAno);
//        EditText editTextLocal = view.findViewById(R.id.editTextLocal);
//        spinnerGenero = view.findViewById(R.id.spinnerGenero);
//        MaterialButton buttonAdicionar = view.findViewById(R.id.buttonAdicionar);
//
//        // Setup Spinner
//        generoAdapter = ArrayAdapter.createFromResource(requireContext(),
//                R.array.generos, android.R.layout.simple_spinner_item);
//        generoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerGenero.setAdapter(generoAdapter);
//
//        Bundle args = getArguments();
//        if (args != null && args.containsKey("livroId")) {
//            livro_id = args.getInt("livroId");
//            String titulo = args.getString("titulo");
//            String autor = args.getString("autor");
//            String genero = args.getString("genero");
//            int paginas = args.getInt("NrPagina");
//            String edicao = args.getString("edicao");
//            int ano = args.getInt("ano");
//            String local = args.getString("local");
//
//
//            editTextTitulo.setText(titulo);
//            editTextAutor.setText(autor);
//            spinnerGenero.getSelectedItem(genero);
//            editTextNumeroPaginas.setText(String.valueOf(paginas));
//            editTextEdicao.setText(edicao);
//            editTextLocal.setText(local);
//            editTextAno.setText(String.valueOf(ano));
//        } else {
//            livro_id = -1;
//        }
//
//        // Handle button click
//        buttonAdicionar.setOnClickListener(v -> {
//            if (validateFields(editTextTitulo, editTextAutor, editTextNumeroPaginas, editTextEdicao, editTextAno, editTextLocal)) {
//                // Retrieve values from fields
//                String titulo = editTextTitulo.getText().toString().trim();
//                String autor = editTextAutor.getText().toString().trim();
//                String genero = spinnerGenero.getSelectedItem().toString();
//                int numeroPaginas = Integer.parseInt(editTextNumeroPaginas.getText().toString().trim());
//                String edicao = editTextEdicao.getText().toString().trim();
//                int ano = Integer.parseInt(editTextAno.getText().toString().trim());
//                String local = editTextLocal.getText().toString().trim();
//
//                // Check if year is valid
//                if (ano > Calendar.getInstance().get(Calendar.YEAR)) {
//                    editTextAno.setError("Ano inválido");
//                    return;
//                }
//
//                // Check number of pages constraint
//                if (numeroPaginas > 2500) {
//                    editTextNumeroPaginas.setError("Número de páginas não pode ser maior que 2500");
//                    return;
//                }
//
//                // Check edition constraint
//                if (edicao.length() > 2) {
//                    editTextEdicao.setError("Edição deve ter no máximo 2 dígitos");
//                    return;
//                }
//
//                Livro livro = new Livro(titulo, autor, genero, numeroPaginas, edicao, ano, local);
//                Livro livroUpdate = new Livro(livro_id, titulo, autor, genero, numeroPaginas, edicao, ano, local);
//
//                long id = databaseHelper.inserirLivro(livro);
//
//                if (id != -1) {
//                    // Clear fields or show success message
//                    clearFields(editTextTitulo, editTextAutor, editTextNumeroPaginas, editTextEdicao, editTextAno, editTextLocal);
//                    Toast.makeText(requireContext(), "Livro adicionado com sucesso", Toast.LENGTH_SHORT).show();
//
//                    new Handler().postDelayed(() -> {
//                        // Replace current fragment with DashboardFragment
//                        Fragment dashboardFragment = new DashboardFragment();
//                        requireActivity().getSupportFragmentManager()
//                                .beginTransaction()
//                                .replace(R.id.framelayout, dashboardFragment)
//                                .addToBackStack(null)  // Optional: If you want to navigate back to the previous fragment
//                                .commit();
//                    }, 3500);
//
//
//
//                } else {
//                    Toast.makeText(requireContext(), "Erro ao adicionar livro", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        return view;
//    }
//
//    private boolean validateFields(EditText... layouts) {
//        for (EditText editText : layouts) {
//            if (editText == null || TextUtils.isEmpty(editText.getText().toString().trim())) {
//                editText.setError("Campo obrigatório");
//                return false;
//            } else {
//                editText.setError(null);
//            }
//        }
//        return true;
//    }
//
//
//    private void clearFields(EditText... layouts) {
//        for (EditText layout : layouts) {
//            layout.setText("");
//        }
//    }
//
//}


package com.example.gestodelivroslidos;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.gestodelivroslidos.database.DatabaseHelper;
import com.example.gestodelivroslidos.models.Livro;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;

public class AdicionarFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Spinner spinnerGenero;
    private ArrayAdapter<CharSequence> generoAdapter;

    public AdicionarFragment() {
        // Required empty public constructor
    }

    public static AdicionarFragment newInstance(String param1, String param2) {
        AdicionarFragment fragment = new AdicionarFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adicionar, container, false);
        DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());

        int livro_id;

        EditText editTextTitulo = view.findViewById(R.id.editTextTitulo);
        EditText editTextAutor = view.findViewById(R.id.editTextAutor);
        EditText editTextNumeroPaginas = view.findViewById(R.id.editTextNumeroPaginas);
        EditText editTextEdicao = view.findViewById(R.id.editTextEdicao);
        EditText editTextAno = view.findViewById(R.id.editTextAno);
        EditText editTextLocal = view.findViewById(R.id.editTextLocal);
        spinnerGenero = view.findViewById(R.id.spinnerGenero);
        MaterialButton buttonAdicionar = view.findViewById(R.id.buttonAdicionar);

        generoAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.generos, android.R.layout.simple_spinner_item);
        generoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenero.setAdapter(generoAdapter);


        Bundle args = getArguments();
        if (args != null && args.containsKey("livroId")) {
            livro_id = args.getInt("livroId");

            Livro livro = databaseHelper.getLivroById(livro_id);

            editTextTitulo.setText(livro.getTitulo());
            editTextAutor.setText(livro.getAutor());
            spinnerGenero.setSelection(generoAdapter.getPosition(livro.getGenero()));
            editTextNumeroPaginas.setText(String.valueOf(livro.getNumeroPaginas()));
            editTextEdicao.setText(livro.getEdicao());
            editTextLocal.setText(livro.getLocal());
            editTextAno.setText(String.valueOf(livro.getAno()));
        } else {
            livro_id = -1;
        }




        buttonAdicionar.setOnClickListener(v -> {
            if (validateFields(editTextTitulo, editTextAutor, editTextNumeroPaginas, editTextEdicao, editTextAno, editTextLocal)) {
                String titulo = editTextTitulo.getText().toString().trim();
                String autor = editTextAutor.getText().toString().trim();
                String genero = spinnerGenero.getSelectedItem().toString();
                int numeroPaginas = Integer.parseInt(editTextNumeroPaginas.getText().toString().trim());
                String edicao = editTextEdicao.getText().toString().trim();
                int ano = Integer.parseInt(editTextAno.getText().toString().trim());
                String local = editTextLocal.getText().toString().trim();

                if (ano > Calendar.getInstance().get(Calendar.YEAR)) {
                    editTextAno.setError("Ano inválido");
                    return;
                }

                if (numeroPaginas > 2500) {
                    editTextNumeroPaginas.setError("Número de páginas não pode ser maior que 2500");
                    return;
                }

                if (edicao.length() > 2) {
                    editTextEdicao.setError("Edição deve ter no máximo 2 dígitos");
                    return;
                }

                Livro livro = new Livro(livro_id, titulo, autor, genero, numeroPaginas, edicao, ano, local);

                long id;
                boolean isUpdated = false;
                if (livro_id == -1) {
                    id = databaseHelper.inserirLivro(livro);
                } else {
                    isUpdated = databaseHelper.atualizarLivro(livro);
                    id = isUpdated ? livro_id : -1;
                }

                if (id != -1) {
                    clearFields(editTextTitulo, editTextAutor, editTextNumeroPaginas, editTextEdicao, editTextAno, editTextLocal);
                    if (livro_id == -1) {
                        Toast.makeText(requireContext(), "Livro adicionado com sucesso", Toast.LENGTH_SHORT).show();
                    } else if (isUpdated) {
                        Toast.makeText(requireContext(), "Livro actualizado com sucesso", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Falha ao actualizar o livro", Toast.LENGTH_SHORT).show();
                    }

                    new Handler().postDelayed(() -> {
                        Fragment dashboardFragment = new DashboardFragment();
                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.framelayout, dashboardFragment)
                                .addToBackStack(null)
                                .commit();
                    }, 3500);
                } else {
                    Toast.makeText(requireContext(), "Erro ao adicionar/atualizar livro", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private boolean validateFields(EditText... layouts) {
        for (EditText editText : layouts) {
            if (editText == null || TextUtils.isEmpty(editText.getText().toString().trim())) {
                editText.setError("Campo obrigatório");
                return false;
            } else {
                editText.setError(null);
            }
        }
        return true;
    }

    private void clearFields(EditText... layouts) {
        for (EditText layout : layouts) {
            layout.setText("");
        }
    }
}
