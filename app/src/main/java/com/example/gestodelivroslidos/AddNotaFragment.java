package com.example.gestodelivroslidos;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.gestodelivroslidos.database.DatabaseHelper;
import com.example.gestodelivroslidos.models.Notas;
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddNotaFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AddNotaFragment() {
        // Required empty public constructor
    }

    public static AddNotaFragment newInstance(String param1, String param2) {
        AddNotaFragment fragment = new AddNotaFragment();
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
    View view = inflater.inflate(R.layout.fragment_add_nota, container, false);
    DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());

    EditText editTextData = view.findViewById(R.id.editTextData);
    EditText editTextPagina = view.findViewById(R.id.editTextPaginas);
    EditText editTextAnotacao = view.findViewById(R.id.editTextAnotacao);
    MaterialButton buttonAdicionar = view.findViewById(R.id.buttonAdicionar);

    String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    editTextData.setText(currentDate);

    editTextData.setOnClickListener(v -> showDatePickerDialog(editTextData));

    int livro_id; // Valor padrão, substitua pelo valor real do livro
    long nota_id; // Valor padrão para indicar que não há ID de nota definido

    Bundle args = getArguments();
    if (args != null && args.containsKey("livroId")) {
        livro_id = args.getInt("livroId");
        int paginasLidas = args.getInt("paginasLidas");
        String anotacao = args.getString("obs");

        editTextPagina.setText(String.valueOf(paginasLidas));
        editTextAnotacao.setText(anotacao);

        // Verificar se o livro já possui uma nota
        if (databaseHelper.livroPossuiNota(livro_id)) {
            // Se possui, carregar o ID da nota existente
            nota_id = databaseHelper.getNotaIdPorLivro(livro_id);
        } else {
            nota_id = -1;
        }
    } else {
        nota_id = -1;
        livro_id = 1;
    }

    buttonAdicionar.setOnClickListener(v -> {
        if (validateFields(editTextData, editTextPagina, editTextAnotacao)) {
            String data = editTextData.getText().toString().trim();
            String paginas = editTextPagina.getText().toString().trim();
            String anotacao = editTextAnotacao.getText().toString().trim();

            int numeroPaginas = Integer.parseInt(paginas);

            int paginasDoLivro = databaseHelper.getPaginasDoLivro(livro_id); // Método fictício para obter as páginas do livro atual
            if (numeroPaginas > paginasDoLivro) {
                editTextPagina.setError("Informe um número menor ou igual a " + paginasDoLivro);
                return;
            }

            // Exemplo básico de validação de data
            if (!isValidDate(data)) {
                editTextData.setError("Data inválida");
                return;
            }

            Notas nota = new Notas(data, numeroPaginas, anotacao, livro_id);

            if (nota_id != -1) {
                // Se nota_id é válido, atualiza a nota existente
                nota.setId((int) nota_id);
                int rowsUpdated = databaseHelper.updateNotas(nota);

                if (rowsUpdated > 0) {
                    Toast.makeText(requireContext(), "Nota actualizada com sucesso", Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(() -> {
                        Fragment andamentoFragment = new AndamentoFragment();
                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.framelayout, andamentoFragment)
                                .addToBackStack(null)
                                .commit();
                    }, 3500);


                } else {
                    Toast.makeText(requireContext(), "Erro ao actualizar nota", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Caso contrário, insere uma nova nota
                long id = databaseHelper.inserirNotas(nota);

                if (id != -1) {
                    Toast.makeText(requireContext(), "Anotação adicionada com sucesso", Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(() -> {
                        Fragment livrosFragment = new LivrosFragment();
                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.framelayout, livrosFragment)
                                .addToBackStack(null)
                                .commit();
                    }, 3500);




                } else {
                    Toast.makeText(requireContext(), "Erro ao adicionar anotação", Toast.LENGTH_SHORT).show();
                }
            }

            clearFields(editTextData, editTextPagina, editTextAnotacao);
        }
    });

    return view;
}


    private boolean validateFields(EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (editText == null || TextUtils.isEmpty(editText.getText().toString().trim())) {
                editText.setError("Campo obrigatório");
                return false;
            } else {
                editText.setError(null);
            }
        }
        return true;
    }

    private void clearFields(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setText("");
        }
    }

    private boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setLenient(false);

        try {
            Date parsedDate = sdf.parse(date);
            return parsedDate != null;
        } catch (Exception e) {
            return false;
        }
    }

private void showDatePickerDialog(EditText editTextData) {
    final Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
            (view, year1, monthOfYear, dayOfMonth) -> {

                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year1, monthOfYear, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String formattedDate = sdf.format(selectedDate.getTime());

                editTextData.setText(formattedDate);
            }, year, month, day);

    datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

    datePickerDialog.show();
}

}
