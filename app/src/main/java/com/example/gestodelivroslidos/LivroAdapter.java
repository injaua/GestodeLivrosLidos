////package com.example.gestodelivroslidos;
////
////// No LivroAdapter
////import android.view.LayoutInflater;
////import android.view.View;
////import android.view.ViewGroup;
////import android.widget.ImageView;
////import android.widget.Toast;
////
////import com.example.gestodelivroslidos.models.LivroDetalhes;
////import com.google.android.material.textview.MaterialTextView;
////import androidx.annotation.NonNull;
////import androidx.recyclerview.widget.RecyclerView;
////import java.util.List;
////
////public class LivroAdapter extends RecyclerView.Adapter<LivroAdapter.LivroViewHolder> {
////
////    private List<LivroDetalhes> livrosDetalhesList;
////
////    public void setLivrosDetalhesList(List<LivroDetalhes> livrosDetalhesList) {
////        this.livrosDetalhesList = livrosDetalhesList;
////        notifyDataSetChanged();
////    }
////
////    @NonNull
////    @Override
////    public LivroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_livro, parent, false);
////        return new LivroViewHolder(view);
////    }
////
////    @Override
////    public void onBindViewHolder(@NonNull LivroViewHolder holder, int position) {
////        LivroDetalhes livroDetalhes = livrosDetalhesList.get(position);
////        holder.textTitulo.setText(livroDetalhes.getTitulo());
////        holder.textAutor.setText(livroDetalhes.getAutor());
////        holder.textGenero.setText(livroDetalhes.getGenero());
////        holder.textNumeroPaginas.setText(String.valueOf(livroDetalhes.getNumeroPaginas()));
////        holder.textEdicao.setText(livroDetalhes.getEdicao());
////        holder.textAno.setText(String.valueOf(livroDetalhes.getAno()));
////        holder.textLocal.setText(livroDetalhes.getLocal());
////        holder.textDataInicio.setText(livroDetalhes.getDataInicio());
////        holder.textPaginasLidas.setText(String.valueOf(livroDetalhes.getPaginasLidas()));
////        holder.textAnotacoes.setText(livroDetalhes.getAnotacoes());
////
////
////        holder.addNota.setOnClickListener(v -> {
////            // Lógica para adicionar uma nota
////            Toast.makeText(v.getContext(), "Adicionar nota para ",Toast.LENGTH_SHORT).show();
////        });
////
////        holder.editItem.setOnClickListener(v -> {
////            // Lógica para editar item
////            Toast.makeText(v.getContext(), "Editar ", Toast.LENGTH_SHORT).show();
////        });
////
////        holder.deleteItem.setOnClickListener(v -> {
////            // Lógica para excluir item
////            Toast.makeText(v.getContext(), "Excluir ", Toast.LENGTH_SHORT).show();
////        });
////    }
////
////    @Override
////    public int getItemCount() {
////        return livrosDetalhesList != null ? livrosDetalhesList.size() : 0;
////    }
////
////    static class LivroViewHolder extends RecyclerView.ViewHolder {
////
////        MaterialTextView textTitulo;
////        MaterialTextView textAutor;
////        MaterialTextView textGenero;
////        MaterialTextView textNumeroPaginas;
////        MaterialTextView textEdicao;
////        MaterialTextView textAno;
////        MaterialTextView textLocal;
////        MaterialTextView textDataInicio;
////        MaterialTextView textPaginasLidas;
////        MaterialTextView textAnotacoes;
////
////        ImageView addNota;
////        ImageView deleteItem;
////        ImageView editItem;
////
////        LivroViewHolder(@NonNull View itemView) {
////            super(itemView);
////            textTitulo = itemView.findViewById(R.id.textTitulo);
////            textAutor = itemView.findViewById(R.id.textAutor);
////            textGenero = itemView.findViewById(R.id.textGenero);
////            textNumeroPaginas = itemView.findViewById(R.id.textNumeroPaginas);
////            textEdicao = itemView.findViewById(R.id.textEdicao);
////            textAno = itemView.findViewById(R.id.textAno);
////            textLocal = itemView.findViewById(R.id.textLocal);
////            textDataInicio = itemView.findViewById(R.id.textDataInicio);
////            textPaginasLidas = itemView.findViewById(R.id.textPaginasLidas);
////            textAnotacoes = itemView.findViewById(R.id.textAnotacoes);
////
////            addNota = itemView.findViewById(R.id.add_nota);
////            deleteItem = itemView.findViewById(R.id.delete_item);
////            editItem = itemView.findViewById(R.id.edit_item);
////        }
////    }
////}
//
//package com.example.gestodelivroslidos;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.example.gestodelivroslidos.models.Livro;
//import com.example.gestodelivroslidos.models.LivroDetalhes;
//import com.google.android.material.textview.MaterialTextView;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentActivity;
//import androidx.recyclerview.widget.RecyclerView;
//import java.util.List;
//
//public class LivroAdapter extends RecyclerView.Adapter<LivroAdapter.LivroViewHolder> {
//
//    private List<LivroDetalhes> livrosDetalhesList;
//
//    public LivroAdapter(List<LivroDetalhes> livrosDetalhesList) {
//        this.livrosDetalhesList = livrosDetalhesList;
//    }
//
//    public void setLivrosDetalhesList(List<LivroDetalhes> livrosDetalhesList) {
//        this.livrosDetalhesList = livrosDetalhesList;
//        notifyDataSetChanged();
//    }
//
//    @NonNull
//    @Override
//    public LivroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_livro, parent, false);
//        return new LivroViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull LivroViewHolder holder, int position) {
//        LivroDetalhes livroDetalhes = livrosDetalhesList.get(position);
//        holder.textTitulo.setText("Titulo: " + livroDetalhes.getTitulo());
//        holder.textAutor.setText("Autor: " +livroDetalhes.getAutor());
//        holder.textGenero.setText("Gênero: " +livroDetalhes.getGenero());
//        holder.textNumeroPaginas.setText("Nr. Páginas: " +String.valueOf(livroDetalhes.getNumeroPaginas()));
//        holder.textEdicao.setText("Edição: " +livroDetalhes.getEdicao());
//        holder.textAno.setText("Ano de publicação: " +String.valueOf(livroDetalhes.getAno()));
//        holder.textLocal.setText("Local de publicação: " +livroDetalhes.getLocal());
//
//        String inicio = livroDetalhes.getDataInicio() != null ? livroDetalhes.getDataInicio() : "por ler";
//
//
//        holder.textDataInicio.setText("Início da Leitura: " +inicio);
//        holder.textPaginasLidas.setText("Páginas Lidas: " +String.valueOf(livroDetalhes.getPaginasLidas()));
//
//        String nota = livroDetalhes.getAnotacoes() != null ? livroDetalhes.getAnotacoes() : "sem nota";
//
//        holder.textAnotacoes.setText("Obs: " +nota);
//
//            holder.addNota.setOnClickListener(v -> {
//               // Toast.makeText(v.getContext(), "Adicionar nota para " + livroDetalhes.getId(), Toast.LENGTH_SHORT).show();
//                // Passa o ID do livro para AddNotaFragment
//                Bundle args = new Bundle();
//                args.putInt("livroId", livroDetalhes.getId());
//                args.putString("titulo", livroDetalhes.getTitulo());
//                args.putString("autor", livroDetalhes.getAutor());
//                args.putString("genero", livroDetalhes.getGenero());
//                args.putInt("NrPagina", livroDetalhes.getNumeroPaginas());
//                args.putString("edicao", livroDetalhes.getEdicao());
//                args.putInt("ano", livroDetalhes.getAno());
//                args.putString("local", livroDetalhes.getLocal());
//                Fragment addNotaFragment = new AddNotaFragment();
//                addNotaFragment.setArguments(args);
//
//                FragmentActivity activity = (FragmentActivity) v.getContext();
//                activity.getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.framelayout, addNotaFragment)
//                        .addToBackStack(null)
//                        .commit();
//
//        });
//
//        holder.editItem.setOnClickListener(v -> {
//            Toast.makeText(v.getContext(), "Editar " + livroDetalhes.getTitulo(), Toast.LENGTH_SHORT).show();
//            Bundle args = new Bundle();
//            args.putInt("livroId", livroDetalhes.getId());
//            Fragment adicionarFragment = new AdicionarFragment();
//            adicionarFragment.setArguments(args);
//
//            FragmentActivity activity = (FragmentActivity) v.getContext();
//            activity.getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.framelayout, adicionarFragment)
//                    .addToBackStack(null)
//                    .commit();
//
//        });
//
//        holder.deleteItem.setOnClickListener(v -> {
//            Toast.makeText(v.getContext(), "Excluir " + livroDetalhes.getTitulo(), Toast.LENGTH_SHORT).show();
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return livrosDetalhesList != null ? livrosDetalhesList.size() : 0;
//    }
//
//    static class LivroViewHolder extends RecyclerView.ViewHolder {
//
//        MaterialTextView textTitulo;
//        MaterialTextView textAutor;
//        MaterialTextView textGenero;
//        MaterialTextView textNumeroPaginas;
//        MaterialTextView textEdicao;
//        MaterialTextView textAno;
//        MaterialTextView textLocal;
//        MaterialTextView textDataInicio;
//        MaterialTextView textPaginasLidas;
//        MaterialTextView textAnotacoes;
//
//        ImageView addNota;
//        ImageView deleteItem;
//        ImageView editItem;
//
//        LivroViewHolder(@NonNull View itemView) {
//            super(itemView);
//            textTitulo = itemView.findViewById(R.id.textTitulo);
//            textAutor = itemView.findViewById(R.id.textAutor);
//            textGenero = itemView.findViewById(R.id.textGenero);
//            textNumeroPaginas = itemView.findViewById(R.id.textNumeroPaginas);
//            textEdicao = itemView.findViewById(R.id.textEdicao);
//            textAno = itemView.findViewById(R.id.textAno);
//            textLocal = itemView.findViewById(R.id.textLocal);
//            textDataInicio = itemView.findViewById(R.id.textDataInicio);
//            textPaginasLidas = itemView.findViewById(R.id.textPaginasLidas);
//            textAnotacoes = itemView.findViewById(R.id.textAnotacoes);
//
//            addNota = itemView.findViewById(R.id.add_nota);
//            deleteItem = itemView.findViewById(R.id.delete_item);
//            editItem = itemView.findViewById(R.id.edit_item);
//        }
//    }
//
//
//    private void deleteLivro(LivroDetalhes livro) {
//        LivroDetalhes livro1 = databaseHelper.getLivroById(livro.getId());
//        //verifica se tem nota notaID==vazio ou nr de paginas igual a paginas lidas.
//
//
//        if (notaId != null && paginasLidas>numeroPaginas) {
//            Toast.makeText(mContext,"Leitura em andamento, não pode ser apagado.", Toast.LENGTH_LONG).show();
//        } else {
//            confirmDeleteCliente(cliente);
//        }
//
//
//        private void confirmDeleteLivro(Livro livro) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//            builder.setTitle("Confirmar exclusão");
//            builder.setMessage("Tem certeza de que deseja excluir este cliente?");
//            builder.setPositiveButton("Sim", (dialog, which) -> {
//                boolean isDeleted = databaseHelper.deleteLivro(livro.getId());
//
//                if (isDeleted) {
//                    int position = mListClientes.indexOf(cliente);
//                    if (position != -1) {
//                        mListClientes.remove(position);
//                        notifyItemRemoved(position);
//                        Toast.makeText(mContext, "Cliente excluído com sucesso.", Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Toast.makeText(mContext, "Falha ao excluir o cliente.", Toast.LENGTH_LONG).show();
//                }
//            });
//            builder.setNegativeButton("Não", null);
//            builder.show();
//        }
//
//    }
//

package com.example.gestodelivroslidos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gestodelivroslidos.database.DatabaseHelper;
import com.example.gestodelivroslidos.models.LivroDetalhes;
import com.google.android.material.textview.MaterialTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LivroAdapter extends RecyclerView.Adapter<LivroAdapter.LivroViewHolder> {

    private List<LivroDetalhes> livrosDetalhesList;
    private Context mContext;
    private DatabaseHelper databaseHelper;

    public LivroAdapter(Context context, List<LivroDetalhes> livrosDetalhesList) {
        this.livrosDetalhesList = livrosDetalhesList;
        this.mContext = context;
        this.databaseHelper = new DatabaseHelper(context); // Supondo que você tenha uma classe DatabaseHelper para gerenciar o banco de dados
    }

    public void setLivrosDetalhesList(List<LivroDetalhes> livrosDetalhesList) {
        this.livrosDetalhesList = livrosDetalhesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LivroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_livro, parent, false);
        return new LivroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LivroViewHolder holder, int position) {
        LivroDetalhes livroDetalhes = livrosDetalhesList.get(position);
        holder.textTitulo.setText("Titulo: " + livroDetalhes.getTitulo());
        holder.textAutor.setText("Autor: " + livroDetalhes.getAutor());
        holder.textGenero.setText("Gênero: " + livroDetalhes.getGenero());
        holder.textNumeroPaginas.setText("Nr. Páginas: " + String.valueOf(livroDetalhes.getNumeroPaginas()));
        holder.textEdicao.setText("Edição: " + livroDetalhes.getEdicao());
        holder.textAno.setText("Ano de publicação: " + String.valueOf(livroDetalhes.getAno()));
        holder.textLocal.setText("Local de publicação: " + livroDetalhes.getLocal());

        String inicio = livroDetalhes.getDataInicio() != null ? livroDetalhes.getDataInicio() : "por ler";
        holder.textDataInicio.setText("Início da Leitura: " + inicio);
        holder.textPaginasLidas.setText("Páginas Lidas: " + String.valueOf(livroDetalhes.getPaginasLidas()));

        String nota = livroDetalhes.getAnotacoes() != null ? livroDetalhes.getAnotacoes() : "sem nota";
        holder.textAnotacoes.setText("Obs: " + nota);

        holder.addNota.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putInt("livroId", livroDetalhes.getId());
            args.putString("titulo", livroDetalhes.getTitulo());
            args.putString("autor", livroDetalhes.getAutor());
            args.putString("genero", livroDetalhes.getGenero());
            args.putInt("NrPagina", livroDetalhes.getNumeroPaginas());
            args.putString("edicao", livroDetalhes.getEdicao());
            args.putInt("ano", livroDetalhes.getAno());
            args.putString("local", livroDetalhes.getLocal());
            Fragment addNotaFragment = new AddNotaFragment();
            addNotaFragment.setArguments(args);

            FragmentActivity activity = (FragmentActivity) v.getContext();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.framelayout, addNotaFragment)
                    .addToBackStack(null)
                    .commit();
        });

        holder.editItem.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Editar " + livroDetalhes.getTitulo(), Toast.LENGTH_SHORT).show();
            Bundle args = new Bundle();
            args.putInt("livroId", livroDetalhes.getId());
            Fragment adicionarFragment = new AdicionarFragment();
            adicionarFragment.setArguments(args);

            FragmentActivity activity = (FragmentActivity) v.getContext();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.framelayout, adicionarFragment)
                    .addToBackStack(null)
                    .commit();
        });

        holder.deleteItem.setOnClickListener(v -> {
            deleteLivro(livroDetalhes);
        });
    }

    @Override
    public int getItemCount() {
        return livrosDetalhesList != null ? livrosDetalhesList.size() : 0;
    }

    static class LivroViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView textTitulo;
        MaterialTextView textAutor;
        MaterialTextView textGenero;
        MaterialTextView textNumeroPaginas;
        MaterialTextView textEdicao;
        MaterialTextView textAno;
        MaterialTextView textLocal;
        MaterialTextView textDataInicio;
        MaterialTextView textPaginasLidas;
        MaterialTextView textAnotacoes;

        ImageView addNota;
        ImageView deleteItem;
        ImageView editItem;

        LivroViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitulo = itemView.findViewById(R.id.textTitulo);
            textAutor = itemView.findViewById(R.id.textAutor);
            textGenero = itemView.findViewById(R.id.textGenero);
            textNumeroPaginas = itemView.findViewById(R.id.textNumeroPaginas);
            textEdicao = itemView.findViewById(R.id.textEdicao);
            textAno = itemView.findViewById(R.id.textAno);
            textLocal = itemView.findViewById(R.id.textLocal);
            textDataInicio = itemView.findViewById(R.id.textDataInicio);
            textPaginasLidas = itemView.findViewById(R.id.textPaginasLidas);
            textAnotacoes = itemView.findViewById(R.id.textAnotacoes);

            addNota = itemView.findViewById(R.id.add_nota);
            deleteItem = itemView.findViewById(R.id.delete_item);
            editItem = itemView.findViewById(R.id.edit_item);
        }
    }

    private void deleteLivro(LivroDetalhes livro) {
        // Verifica se o campo notasID está nulo ou se o número de páginas lidas é igual ao número de páginas
        if (livro.getAnotacoes() == null || livro.getPaginasLidas() == livro.getNumeroPaginas()) {
            confirmDeleteLivro(livro);
        } else {
            Toast.makeText(mContext, "Leitura em andamento, não pode ser apagado.", Toast.LENGTH_LONG).show();
        }
    }

    private void confirmDeleteLivro(LivroDetalhes livro) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Confirmar exclusão");
        builder.setMessage("Tem certeza de que deseja excluir este livro?");
        builder.setPositiveButton("Sim", (dialog, which) -> {
            boolean isDeleted = databaseHelper.deleteLivro(livro.getId());

            if (isDeleted) {
                int position = livrosDetalhesList.indexOf(livro);
                if (position != -1) {
                    livrosDetalhesList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(mContext, "Livro excluído com sucesso.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(mContext, "Falha ao excluir o livro.", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Não", null);
        builder.show();
    }
}
