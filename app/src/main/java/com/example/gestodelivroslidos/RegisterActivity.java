package com.example.gestodelivroslidos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestodelivroslidos.database.DatabaseHelper;
import com.example.gestodelivroslidos.models.Usuario;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etNome, etApelido, etEmail, etMorada, etPassword, etConfirmPassword;
    private Button btnRegister;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Toast.makeText(RegisterActivity.this, "Este aplicativo nao foi desenvolvido por mim.", Toast.LENGTH_LONG).show();
        databaseHelper = new DatabaseHelper(this);

        // Inicialização dos componentes da tela
        etNome = findViewById(R.id.et_nome);
        etApelido = findViewById(R.id.et_apelido);
        etEmail = findViewById(R.id.et_email);
        etMorada = findViewById(R.id.et_morada);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnRegister = findViewById(R.id.btn_register);

        // Configuração de clique no botão de registro
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Captura dos valores dos campos de entrada
                String nome = etNome.getText().toString().trim();
                String apelido = etApelido.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String morada = etMorada.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                // Validação dos campos
                if (TextUtils.isEmpty(nome)) {
                    etNome.setError("Campo obrigatório");
                    return;
                }

                if (TextUtils.isEmpty(apelido)) {
                    etApelido.setError("Campo obrigatório");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    etEmail.setError("Campo obrigatório");
                    return;
                }

                if (!isValidEmail(email)) {
                    etEmail.setError("Email inválido");
                    return;
                }

                if (TextUtils.isEmpty(morada)) {
                    etMorada.setError("Campo obrigatório");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    etPassword.setError("Campo obrigatório");
                    return;
                }

                if (password.length() < 4) {
                    etPassword.setError("A senha deve ter pelo menos 4 caracteres");
                    return;
                }

                if (TextUtils.isEmpty(confirmPassword)) {
                    etConfirmPassword.setError("Campo obrigatório");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    etConfirmPassword.setError("As senhas não coincidem");
                    return;
                }

                // Verifica se o email já está registrado no banco de dados
                if (!databaseHelper.checkEmailExists(email)) {
                    // Criação de um novo objeto Usuario com os dados inseridos
                    Usuario usuario = new Usuario(nome, apelido, email, morada, password);

                    // Inserir usuário no banco de dados usando o DatabaseHelper
                    long result = databaseHelper.inserirUsuario(usuario, confirmPassword);

                    // Verifica se a operação de inserção foi bem sucedida
                    if (result != -1) {
                        // Exibição de uma mensagem de sucesso
                        Toast.makeText(RegisterActivity.this, "Registro realizado com sucesso para: " + email, Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 3500);

                    } else {
                        // Caso ocorra algum problema na inserção, exibe uma mensagem de erro
                        Toast.makeText(RegisterActivity.this, "Erro ao registrar usuário", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Exibe mensagem indicando que o email já está registrado
                    Toast.makeText(RegisterActivity.this, "O Email já existe, informe outro", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método para verificar se o email é válido usando Patterns.EMAIL_ADDRESS
    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
