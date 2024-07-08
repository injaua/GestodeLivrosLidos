package com.example.gestodelivroslidos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestodelivroslidos.database.DatabaseHelper;
import com.example.gestodelivroslidos.models.LivroDetalhes;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class LivrosLidosAdapter extends RecyclerView.Adapter<LivrosLidosAdapter.LivrosLidosViewHolder> {

    private List<LivroDetalhes> livrosDetalhesList;
    private Context mContext;
    private DatabaseHelper databaseHelper;

    public LivrosLidosAdapter(Context context, List<LivroDetalhes> livrosDetalhesList) {
        this.livrosDetalhesList = livrosDetalhesList;
        this.mContext = context;
        this.databaseHelper = new DatabaseHelper(context);
    }

    public void setLivrosDetalhesList(List<LivroDetalhes> livrosDetalhesList) {
        this.livrosDetalhesList = livrosDetalhesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LivrosLidosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_livro_lidos, parent, false);
        return new LivrosLidosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LivrosLidosViewHolder holder, int position) {
        LivroDetalhes livroDetalhes = livrosDetalhesList.get(position);

        if (livroDetalhes != null) {
            holder.textTitulo.setText("Título: " + livroDetalhes.getTitulo());
            holder.textAutor.setText("Autor: " + livroDetalhes.getAutor());
            holder.textGenero.setText("Gênero: " + livroDetalhes.getGenero());
            holder.textAnotacoes.setText("Obs: " + livroDetalhes.getAnotacoes());

            holder.deleteItem.setOnClickListener(v -> {
                deleteLivro(livroDetalhes);
            });
        }
    }



    @Override
    public int getItemCount() {
        return livrosDetalhesList != null ? livrosDetalhesList.size() : 0;
    }

    private void removeItem(int position) {
        if (livrosDetalhesList != null && position >= 0 && position < livrosDetalhesList.size()) {
            livrosDetalhesList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, livrosDetalhesList.size());
        }
    }

    static class LivrosLidosViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView textTitulo;
        MaterialTextView textAutor;
        MaterialTextView textGenero;
        MaterialTextView textAnotacoes;

        ImageView deleteItem;

        LivrosLidosViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitulo = itemView.findViewById(R.id.textTitulo2);
            textAutor = itemView.findViewById(R.id.textAutor2);
            textGenero = itemView.findViewById(R.id.textGenero2);
            textAnotacoes = itemView.findViewById(R.id.textAnotacoes2);

            deleteItem = itemView.findViewById(R.id.delete_item2);
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

