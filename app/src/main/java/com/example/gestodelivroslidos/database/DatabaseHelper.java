package com.example.gestodelivroslidos.database;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.gestodelivroslidos.models.Livro;
import com.example.gestodelivroslidos.models.LivroDetalhes;
import com.example.gestodelivroslidos.models.Notas;
import com.example.gestodelivroslidos.models.Usuario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "books.db";
    private static final int DATABASE_VERSION = 1;

    // Tabela Usuario
    private static final String TABLE_USUARIO = "usuario";
    private static final String COL_USUARIO_ID = "id";
    private static final String COL_NOME = "nome";
    private static final String COL_APELIDO = "apelido";
    private static final String COL_EMAIL = "email";
    private static final String COL_MORADA = "morada";
    private static final String COL_PASSWORD = "password";

    // Tabela Livro
    private static final String TABLE_LIVRO = "livro";
    private static final String COL_LIVRO_ID = "id";
    private static final String COL_TITULO = "titulo";
    private static final String COL_AUTOR = "autor";
    private static final String COL_GENERO = "genero";
    private static final String COL_NUMERO_PAGINAS = "numero_paginas";
    private static final String COL_EDICAO = "edicao";
    private static final String COL_ANO = "ano";
    private static final String COL_LOCAL = "local";
    private static final String COL_NOTAS_ID2 = "notas_id"; // Chave estrangeira para a tabela Notas

    // Tabela Notas
    private static final String TABLE_NOTAS = "notas";
    private static final String COL_NOTAS_ID = "id";
    private static final String COL_DATA_INICIO = "data_inicio";
    private static final String COL_PAGINAS_LIDAS = "paginas_lidas";
    private static final String COL_ANOTACOES = "anotacoes";
    private static final String COL_LIVRO_ID_NOTAS = "livro_id"; // Chave estrangeira para a tabela Livro

    // Criação das tabelas
    private static final String CREATE_TABLE_USUARIO = "CREATE TABLE " + TABLE_USUARIO + " ("
            + COL_USUARIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NOME + " TEXT, "
            + COL_APELIDO + " TEXT, "
            + COL_EMAIL + " TEXT, "
            + COL_MORADA + " TEXT, "
            + COL_PASSWORD + " TEXT)";

    private static final String CREATE_TABLE_LIVRO = "CREATE TABLE " + TABLE_LIVRO + " ("
            + COL_LIVRO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_TITULO + " TEXT, "
            + COL_AUTOR + " TEXT, "
            + COL_GENERO + " TEXT, "
            + COL_NUMERO_PAGINAS + " INTEGER, "
            + COL_EDICAO + " TEXT, "
            + COL_ANO + " INTEGER, "
            + COL_LOCAL + " TEXT, "
            + COL_NOTAS_ID2 + " INTEGER, "
            + "FOREIGN KEY(" + COL_NOTAS_ID2 + ") REFERENCES " + TABLE_NOTAS + "(" + COL_NOTAS_ID + "))";

    private static final String CREATE_TABLE_NOTAS = "CREATE TABLE " + TABLE_NOTAS + " ("
            + COL_NOTAS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_DATA_INICIO + " TEXT, "
            + COL_PAGINAS_LIDAS + " INTEGER, "
            + COL_ANOTACOES + " TEXT, "
            + COL_LIVRO_ID_NOTAS + " INTEGER UNIQUE, "
            + "FOREIGN KEY(" + COL_LIVRO_ID_NOTAS + ") REFERENCES " + TABLE_LIVRO + "(" + COL_LIVRO_ID + "))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USUARIO);
        db.execSQL(CREATE_TABLE_LIVRO);
        db.execSQL(CREATE_TABLE_NOTAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIVRO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTAS);
        onCreate(db);
    }

    public void onRecreate() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Drop das tabelas se já existirem
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIVRO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTAS);

        // Criação das tabelas novamente
        db.execSQL(CREATE_TABLE_USUARIO);
        db.execSQL(CREATE_TABLE_LIVRO);
        db.execSQL(CREATE_TABLE_NOTAS);

        // Fechar conexão com o banco de dados
        db.close();
    }


    // Métodos CRUD para tabela Usuario

    public long inserirUsuario(Usuario usuario, String confirmPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NOME, usuario.getNome());
        values.put(COL_APELIDO, usuario.getApelido());
        values.put(COL_EMAIL, usuario.getEmail());
        values.put(COL_MORADA, usuario.getMorada());
        values.put(COL_PASSWORD, usuario.getPassword());

        // Verifica se as senhas coincidem antes de inserir no banco de dados
        if (usuario.getPassword().equals(confirmPassword)) {
            long result = db.insert(TABLE_USUARIO, null, values);
            db.close(); // Fechar conexão com o banco de dados
            return result;
        } else {
            Log.e("DatabaseHelper", "As senhas não coincidem");
            db.close(); // Fechar conexão com o banco de dados
            return -1; // Retornar -1 indicando falha
        }
    }

    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USUARIO + " WHERE " + COL_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    // Método para atualizar a senha do usuário no banco de dados
    public boolean updatePassword(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PASSWORD, newPassword);

        // Atualiza a linha correspondente ao email fornecido
        int rowsAffected = db.update(TABLE_USUARIO, values, COL_EMAIL + " = ?", new String[]{email});

        // Verifica se houve alguma linha afetada (deve ser 1, pois o email é único)
        boolean success = (rowsAffected > 0);

        db.close(); // Fechar conexão com o banco de dados
        return success;
    }

    // Métodos CRUD para tabela Livro

    public long inserirLivro(Livro livro) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TITULO, livro.getTitulo());
        values.put(COL_AUTOR, livro.getAutor());
        values.put(COL_GENERO, livro.getGenero());
        values.put(COL_NUMERO_PAGINAS, livro.getNumeroPaginas());
        values.put(COL_EDICAO, livro.getEdicao());
        values.put(COL_ANO, livro.getAno());
        values.put(COL_LOCAL, livro.getLocal());
        values.put(COL_NOTAS_ID2, livro.getNotasId());
        long id = db.insert(TABLE_LIVRO, null, values);
        db.close();
        return id;
    }

    @SuppressLint("Range")
    public List<Livro> getAllLivros() {
        List<Livro> livros = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_LIVRO;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Livro livro = new Livro();
                livro.setId((int) cursor.getLong(cursor.getColumnIndex(COL_LIVRO_ID)));
                livro.setTitulo(cursor.getString(cursor.getColumnIndex(COL_TITULO)));
                livro.setAutor(cursor.getString(cursor.getColumnIndex(COL_AUTOR)));
                livro.setGenero(cursor.getString(cursor.getColumnIndex(COL_GENERO)));
                livro.setNumeroPaginas(cursor.getInt(cursor.getColumnIndex(COL_NUMERO_PAGINAS)));
                livro.setEdicao(cursor.getString(cursor.getColumnIndex(COL_EDICAO)));
                livro.setAno(cursor.getInt(cursor.getColumnIndex(COL_ANO)));
                livro.setLocal(cursor.getString(cursor.getColumnIndex(COL_LOCAL)));
                livro.setNotasId(cursor.getInt(cursor.getColumnIndex(COL_NOTAS_ID2)));
                livros.add(livro);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return livros;
    }

    // Métodos CRUD para tabela Notas

    public long inserirNotas(Notas notas) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DATA_INICIO, notas.getDataInicio());
        values.put(COL_PAGINAS_LIDAS, notas.getPaginasLidas());
        values.put(COL_ANOTACOES, notas.getAnotacoes());
        values.put(COL_LIVRO_ID_NOTAS, notas.getLivroId());
        long id = db.insert(TABLE_NOTAS, null, values);
        db.close();
        return id;
    }


    public int updateNotas(Notas notas) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DATA_INICIO, notas.getDataInicio());
        values.put(COL_PAGINAS_LIDAS, notas.getPaginasLidas());
        values.put(COL_ANOTACOES, notas.getAnotacoes());
        values.put(COL_LIVRO_ID_NOTAS, notas.getLivroId());
        return db.update(TABLE_NOTAS, values, COL_NOTAS_ID + "=?", new String[]{String.valueOf(notas.getId())});
    }


//Livros lidos

    public List<Livro> getLivrosLidos() {
        List<Livro> livros = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_LIVRO + " l JOIN " + TABLE_NOTAS + " n ON l." + COL_LIVRO_ID + " = n." + COL_LIVRO_ID_NOTAS
                + " WHERE l." + COL_NUMERO_PAGINAS + " = n." + COL_PAGINAS_LIDAS;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Livro livro = new Livro(
                        cursor.getInt(cursor.getColumnIndex(COL_LIVRO_ID)),
                        cursor.getString(cursor.getColumnIndex(COL_TITULO)),
                        cursor.getString(cursor.getColumnIndex(COL_AUTOR)),
                        cursor.getString(cursor.getColumnIndex(COL_GENERO)),
                        cursor.getInt(cursor.getColumnIndex(COL_NUMERO_PAGINAS)),
                        cursor.getString(cursor.getColumnIndex(COL_EDICAO)),
                        cursor.getInt(cursor.getColumnIndex(COL_ANO)),
                        cursor.getString(cursor.getColumnIndex(COL_LOCAL)),
                        cursor.getInt(cursor.getColumnIndex(COL_NOTAS_ID2))
                );
                livros.add(livro);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return livros;
    }

    //lidro em andamento
    public List<Livro> getLivrosEmAndamento() {
        List<Livro> livros = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_LIVRO + " l JOIN " + TABLE_NOTAS + " n ON l." + COL_LIVRO_ID + " = n." + COL_LIVRO_ID_NOTAS
                + " WHERE l." + COL_NUMERO_PAGINAS + " > n." + COL_PAGINAS_LIDAS;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Livro livro = new Livro(
                        cursor.getInt(cursor.getColumnIndex(COL_LIVRO_ID)),
                        cursor.getString(cursor.getColumnIndex(COL_TITULO)),
                        cursor.getString(cursor.getColumnIndex(COL_AUTOR)),
                        cursor.getString(cursor.getColumnIndex(COL_GENERO)),
                        cursor.getInt(cursor.getColumnIndex(COL_NUMERO_PAGINAS)),
                        cursor.getString(cursor.getColumnIndex(COL_EDICAO)),
                        cursor.getInt(cursor.getColumnIndex(COL_ANO)),
                        cursor.getString(cursor.getColumnIndex(COL_LOCAL)),
                        cursor.getInt(cursor.getColumnIndex(COL_NOTAS_ID2))
                );
                livros.add(livro);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return livros;
    }

//Por generos
public Map<String, Integer> getLivrosPorGenero2() {
    Map<String, Integer> generoCount = new HashMap<>();
    SQLiteDatabase db = this.getReadableDatabase();
    String query = "SELECT " + COL_GENERO + ", COUNT(*) as count FROM " + TABLE_LIVRO + " GROUP BY " + COL_GENERO;
    Cursor cursor = db.rawQuery(query, null);
    if (cursor.moveToFirst()) {
        do {
            @SuppressLint("Range") String genero = cursor.getString(cursor.getColumnIndex(COL_GENERO));
            @SuppressLint("Range") int count = cursor.getInt(cursor.getColumnIndex("count"));
            generoCount.put(genero, count);
        } while (cursor.moveToNext());
    }
    cursor.close();
    db.close();
    return generoCount;
}

    public Map<String, Integer> getLivrosPorGenero() {
        Map<String, Integer> generoCount = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COL_GENERO + ", COUNT(*) as count " +
                "FROM " + TABLE_LIVRO + " l " +
                "INNER JOIN " + TABLE_NOTAS + " n " +
                "ON l." + COL_LIVRO_ID + " = n." + COL_LIVRO_ID_NOTAS + " " +
                "WHERE l." + COL_NUMERO_PAGINAS + " = n." + COL_PAGINAS_LIDAS + " " +
                "GROUP BY " + COL_GENERO;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String genero = cursor.getString(cursor.getColumnIndex(COL_GENERO));
                @SuppressLint("Range") int count = cursor.getInt(cursor.getColumnIndex("count"));
                generoCount.put(genero, count);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return generoCount;
    }


//livros por autor
    public Map<String, Integer> getLivrosPorAutor() {
        Map<String, Integer> autorCount = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COL_AUTOR + ", COUNT(*) as count " +
                "FROM " + TABLE_LIVRO + " l " +
                "INNER JOIN " + TABLE_NOTAS + " n " +
                "ON l." + COL_LIVRO_ID + " = n." + COL_LIVRO_ID_NOTAS + " " +
                "WHERE l." + COL_NUMERO_PAGINAS + " >= n." + COL_PAGINAS_LIDAS + " " +
                "GROUP BY " + COL_AUTOR;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String autor = cursor.getString(cursor.getColumnIndex(COL_AUTOR));
                @SuppressLint("Range") int count = cursor.getInt(cursor.getColumnIndex("count"));
                autorCount.put(autor, count);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return autorCount;
    }


    //lidos neste mes
public List<Livro> getLivrosLidosNesteMes() {
    List<Livro> livros = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();
    Calendar cal = Calendar.getInstance();
    int currentMonth = cal.get(Calendar.MONTH) + 1; // Note: Calendar.MONTH is zero-based
    int currentYear = cal.get(Calendar.YEAR);

    String query = "SELECT * FROM " + TABLE_LIVRO + " l JOIN " + TABLE_NOTAS + " n ON l." + COL_LIVRO_ID + " = n." + COL_LIVRO_ID_NOTAS
            + " WHERE strftime('%m', n." + COL_DATA_INICIO + ") = ? AND strftime('%Y', n." + COL_DATA_INICIO + ") = ?";
    @SuppressLint("DefaultLocale") Cursor cursor = db.rawQuery(query, new String[]{String.format("%02d", currentMonth), String.valueOf(currentYear)});
    if (cursor.moveToFirst()) {
        do {
            @SuppressLint("Range") Livro livro = new Livro(
                    cursor.getInt(cursor.getColumnIndex(COL_LIVRO_ID)),
                    cursor.getString(cursor.getColumnIndex(COL_TITULO)),
                    cursor.getString(cursor.getColumnIndex(COL_AUTOR)),
                    cursor.getString(cursor.getColumnIndex(COL_GENERO)),
                    cursor.getInt(cursor.getColumnIndex(COL_NUMERO_PAGINAS)),
                    cursor.getString(cursor.getColumnIndex(COL_EDICAO)),
                    cursor.getInt(cursor.getColumnIndex(COL_ANO)),
                    cursor.getString(cursor.getColumnIndex(COL_LOCAL)),
                    cursor.getInt(cursor.getColumnIndex(COL_NOTAS_ID2))
            );
            livros.add(livro);
        } while (cursor.moveToNext());
    }
    cursor.close();
    db.close();
    return livros;
}


    // No DatabaseHelper
    public List<LivroDetalhes> getAllBooksAndNotes() {
        List<LivroDetalhes> livrosDetalhesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT l.*, n." + COL_DATA_INICIO + ", n." + COL_PAGINAS_LIDAS + ", n." + COL_ANOTACOES +
                " FROM " + TABLE_LIVRO + " l" +
                " LEFT JOIN " + TABLE_NOTAS + " n ON l." + COL_LIVRO_ID + " = n." + COL_LIVRO_ID_NOTAS;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                LivroDetalhes livroDetalhes = new LivroDetalhes();
              livroDetalhes.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_LIVRO_ID)));
                livroDetalhes.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow(COL_TITULO)));
                livroDetalhes.setAutor(cursor.getString(cursor.getColumnIndexOrThrow(COL_AUTOR)));
                livroDetalhes.setGenero(cursor.getString(cursor.getColumnIndexOrThrow(COL_GENERO)));
                livroDetalhes.setNumeroPaginas(cursor.getInt(cursor.getColumnIndexOrThrow(COL_NUMERO_PAGINAS)));
                livroDetalhes.setEdicao(cursor.getString(cursor.getColumnIndexOrThrow(COL_EDICAO)));
                livroDetalhes.setAno(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ANO)));
                livroDetalhes.setLocal(cursor.getString(cursor.getColumnIndexOrThrow(COL_LOCAL)));
                livroDetalhes.setDataInicio(cursor.getString(cursor.getColumnIndexOrThrow(COL_DATA_INICIO)));
                livroDetalhes.setPaginasLidas(cursor.getInt(cursor.getColumnIndexOrThrow(COL_PAGINAS_LIDAS)));
                livroDetalhes.setAnotacoes(cursor.getString(cursor.getColumnIndexOrThrow(COL_ANOTACOES)));

                livrosDetalhesList.add(livroDetalhes);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return livrosDetalhesList;
    }

    public List<LivroDetalhes> getCompletedBooks() {
        List<LivroDetalhes> completedBooksList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT l.*, n." + COL_DATA_INICIO + ", n." + COL_PAGINAS_LIDAS + ", n." + COL_ANOTACOES +
                " FROM " + TABLE_LIVRO + " l" +
                " INNER JOIN " + TABLE_NOTAS + " n ON l." + COL_LIVRO_ID + " = n." + COL_LIVRO_ID_NOTAS +
                " WHERE l." + COL_NUMERO_PAGINAS + " = n." + COL_PAGINAS_LIDAS;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                LivroDetalhes livroDetalhes = new LivroDetalhes();
                livroDetalhes.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_LIVRO_ID)));
                livroDetalhes.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow(COL_TITULO)));
                livroDetalhes.setAutor(cursor.getString(cursor.getColumnIndexOrThrow(COL_AUTOR)));
                livroDetalhes.setGenero(cursor.getString(cursor.getColumnIndexOrThrow(COL_GENERO)));
                livroDetalhes.setNumeroPaginas(cursor.getInt(cursor.getColumnIndexOrThrow(COL_NUMERO_PAGINAS)));
                livroDetalhes.setEdicao(cursor.getString(cursor.getColumnIndexOrThrow(COL_EDICAO)));
                livroDetalhes.setAno(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ANO)));
                livroDetalhes.setLocal(cursor.getString(cursor.getColumnIndexOrThrow(COL_LOCAL)));
                livroDetalhes.setDataInicio(cursor.getString(cursor.getColumnIndexOrThrow(COL_DATA_INICIO)));
                livroDetalhes.setPaginasLidas(cursor.getInt(cursor.getColumnIndexOrThrow(COL_PAGINAS_LIDAS)));
                livroDetalhes.setAnotacoes(cursor.getString(cursor.getColumnIndexOrThrow(COL_ANOTACOES)));

                completedBooksList.add(livroDetalhes);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return completedBooksList;
    }

    public List<LivroDetalhes> getInProgressBooks() {
        List<LivroDetalhes> inProgressBooksList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT l.*, n." + COL_DATA_INICIO + ", n." + COL_PAGINAS_LIDAS + ", n." + COL_ANOTACOES +
                " FROM " + TABLE_LIVRO + " l" +
                " INNER JOIN " + TABLE_NOTAS + " n ON l." + COL_LIVRO_ID + " = n." + COL_LIVRO_ID_NOTAS +
                " WHERE l." + COL_NUMERO_PAGINAS + " > n." + COL_PAGINAS_LIDAS;


        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                LivroDetalhes livroDetalhes = new LivroDetalhes();
                livroDetalhes.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_LIVRO_ID)));
                livroDetalhes.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow(COL_TITULO)));
                livroDetalhes.setAutor(cursor.getString(cursor.getColumnIndexOrThrow(COL_AUTOR)));
                livroDetalhes.setGenero(cursor.getString(cursor.getColumnIndexOrThrow(COL_GENERO)));
                livroDetalhes.setNumeroPaginas(cursor.getInt(cursor.getColumnIndexOrThrow(COL_NUMERO_PAGINAS)));
                livroDetalhes.setEdicao(cursor.getString(cursor.getColumnIndexOrThrow(COL_EDICAO)));
                livroDetalhes.setAno(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ANO)));
                livroDetalhes.setLocal(cursor.getString(cursor.getColumnIndexOrThrow(COL_LOCAL)));
                livroDetalhes.setDataInicio(cursor.getString(cursor.getColumnIndexOrThrow(COL_DATA_INICIO)));
                livroDetalhes.setPaginasLidas(cursor.getInt(cursor.getColumnIndexOrThrow(COL_PAGINAS_LIDAS)));
                livroDetalhes.setAnotacoes(cursor.getString(cursor.getColumnIndexOrThrow(COL_ANOTACOES)));

                inProgressBooksList.add(livroDetalhes);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return inProgressBooksList;
    }


    @SuppressLint("Range")
    public int getPaginasDoLivro(int livroId) {
        SQLiteDatabase db = this.getReadableDatabase();
        int paginasDoLivro = 0;

        String query = "SELECT " + COL_NUMERO_PAGINAS + " FROM " + TABLE_LIVRO +
                " WHERE " + COL_LIVRO_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(livroId)});
        if (cursor != null && cursor.moveToFirst()) {
            paginasDoLivro = cursor.getInt(cursor.getColumnIndex(COL_NUMERO_PAGINAS));
            cursor.close();
        }

        return paginasDoLivro;
    }


    public boolean deleteLivro(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_LIVRO, COL_LIVRO_ID + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }


    public boolean livroPossuiNota(int livro_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_NOTAS +
                " WHERE " + COL_LIVRO_ID_NOTAS + " = ?";
        String[] selectionArgs = { String.valueOf(livro_id) };

        Cursor cursor = db.rawQuery(query, selectionArgs);
        int count = 0;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            cursor.close();
        }

        return count>0;
    }


    @SuppressLint("Range")
    public long getNotaIdPorLivro(int livro_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        long nota_id = -1;

        String query = "SELECT " + COL_NOTAS_ID + " FROM " + TABLE_NOTAS +
                " WHERE " + COL_LIVRO_ID_NOTAS + " = ?";
        String[] selectionArgs = { String.valueOf(livro_id) };

        Cursor cursor = db.rawQuery(query, selectionArgs);
        if (cursor != null && cursor.moveToFirst()) {
            nota_id = cursor.getLong(cursor.getColumnIndex(COL_NOTAS_ID));
            cursor.close();
        }

        return nota_id;
    }


    public boolean atualizarLivro(Livro livro) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TITULO, livro.getTitulo());
        values.put(COL_AUTOR, livro.getAutor());
        values.put(COL_GENERO, livro.getGenero());
        values.put(COL_NUMERO_PAGINAS, livro.getNumeroPaginas());
        values.put(COL_EDICAO, livro.getEdicao());
        values.put(COL_ANO, livro.getAno());
        values.put(COL_LOCAL, livro.getLocal());

        int result = db.update(TABLE_LIVRO, values, COL_LIVRO_ID + " = ?",
                new String[]{String.valueOf(livro.getId())});
        db.close();
        return result > 0;
    }

    public Livro getLivroById(int livroId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Livro livro = null;

        Cursor cursor = db.query(TABLE_LIVRO,
                new String[]{COL_LIVRO_ID, COL_TITULO, COL_AUTOR, COL_GENERO, COL_NUMERO_PAGINAS, COL_EDICAO, COL_ANO, COL_LOCAL, COL_NOTAS_ID2},
                COL_LIVRO_ID + "=?",
                new String[]{String.valueOf(livroId)}, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                livro = new Livro(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_LIVRO_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_TITULO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_AUTOR)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_GENERO)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_NUMERO_PAGINAS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_EDICAO)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_ANO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_LOCAL))
                );
            }
            cursor.close();
        }

        db.close();
        return livro;
    }

}
