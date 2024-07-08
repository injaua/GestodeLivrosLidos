package com.example.gestodelivroslidos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestodelivroslidos.models.LivroDetalhes;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class LivroEmAndamentoAdapter extends RecyclerView.Adapter<LivroEmAndamentoAdapter.LivroEmAndamentoViewHolder> {

    private List<LivroDetalhes> livrosDetalhesList;

    public LivroEmAndamentoAdapter(List<LivroDetalhes> livrosDetalhesList) {
        this.livrosDetalhesList = livrosDetalhesList;
    }

    public void setLivrosDetalhesList(List<LivroDetalhes> livrosDetalhesList) {
        this.livrosDetalhesList = livrosDetalhesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LivroEmAndamentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_livro_andamento, parent, false);
        return new LivroEmAndamentoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LivroEmAndamentoViewHolder holder, int position) {
        LivroDetalhes livroDetalhes = livrosDetalhesList.get(position);
        holder.editItem.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Editar " + livroDetalhes.getTitulo(), Toast.LENGTH_SHORT).show();
        });

        if (livrosDetalhesList == null) {
            return;
        }
        //LivroDetalhes livroDetalhes = livrosDetalhesList.get(position);
        holder.bind(livroDetalhes);
    }

    @Override
    public int getItemCount() {
        return livrosDetalhesList != null ? livrosDetalhesList.size() : 0;
    }

    static class LivroEmAndamentoViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView textTitulo;
        MaterialTextView textAutor;
        MaterialTextView textPaginasLidas;
        MaterialTextView textAnotacoes;
        ProgressBar progressBar3;
        ImageView editItem;

        LivroEmAndamentoViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitulo = itemView.findViewById(R.id.textTitulo3);
            textAutor = itemView.findViewById(R.id.textAutor3);
            textPaginasLidas = itemView.findViewById(R.id.textPaginasLidas3);
            textAnotacoes = itemView.findViewById(R.id.textAnotacoes3);
            progressBar3 = itemView.findViewById(R.id.progressBar3);
            editItem = itemView.findViewById(R.id.edit_nota3);
        }

        void bind(LivroDetalhes livroDetalhes) {
            textTitulo.setText("Título: " + livroDetalhes.getTitulo());
            textAutor.setText("Autor: " + livroDetalhes.getAutor());
            textPaginasLidas.setText("Páginas Lidas: " + livroDetalhes.getPaginasLidas());
            textAnotacoes.setText("Obs: " + livroDetalhes.getAnotacoes());

            // Atualiza o ProgressBar com base nas páginas lidas e no número total de páginas
            int totalPaginas = livroDetalhes.getNumeroPaginas();
            int paginasLidas = livroDetalhes.getPaginasLidas();
            if (totalPaginas > 0) {
                int progresso = (int) ((paginasLidas / (float) totalPaginas) * 100);
                progressBar3.setProgress(progresso);
            } else {
                progressBar3.setProgress(0);
            }

            editItem.setOnClickListener(v -> {
                Toast.makeText(v.getContext(), "Clicou em " + livroDetalhes.getTitulo(), Toast.LENGTH_SHORT).show();
                // Adicione aqui a lógica para abrir detalhes do livro ou outra ação desejada
                Bundle args = new Bundle();
                args.putInt("livroId", livroDetalhes.getId());
                args.putInt("paginasLidas", livroDetalhes.getPaginasLidas());
                args.putString("obs", livroDetalhes.getAnotacoes());
                Fragment addNotaFragment = new AddNotaFragment();
                addNotaFragment.setArguments(args);

                FragmentActivity activity = (FragmentActivity) v.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.framelayout, addNotaFragment)
                        .addToBackStack(null)
                        .commit();
            });
        }
    }
}
