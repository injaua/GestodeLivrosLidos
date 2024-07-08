package com.example.gestodelivroslidos;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestodelivroslidos.database.DatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ResetPasswordActivity extends AppCompatActivity {

    private TextInputEditText etEmail, etPassword, etConfirmPassword;
    private TextInputLayout tilEmail, tilPassword, tilConfirmPassword;
    private Button btnResetPassword;
    private DatabaseHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        databaseHelper = new DatabaseHelper(this);

        // Inicialização dos componentes da tela
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        tilEmail = findViewById(R.id.til_email);
        tilPassword = findViewById(R.id.til_password);
        tilConfirmPassword = findViewById(R.id.til_confirm_password);
        btnResetPassword = findViewById(R.id.btn_resetPassword);

        // Configuração de clique no botão de resetar senha
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Captura dos valores dos campos de entrada
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                // Validação dos campos
                if (TextUtils.isEmpty(email)) {
                    tilEmail.setError("Campo obrigatório");
                    return;
                }

                if (!isValidEmail(email)) {
                    tilEmail.setError("Email inválido");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    tilPassword.setError("Campo obrigatório");
                    return;
                }

                if (password.length() < 4) {
                    tilPassword.setError("A senha deve ter pelo menos 4 caracteres");
                    return;
                }

                if (TextUtils.isEmpty(confirmPassword)) {
                    tilConfirmPassword.setError("Campo obrigatório");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    tilConfirmPassword.setError("As senhas não coincidem");
                    return;
                }

                // Verificar se o email existe no banco de dados
                if (databaseHelper.checkEmailExists(email)) {
                    // Atualizar senha do usuário no banco de dados
                    if (databaseHelper.updatePassword(email, password)) {
                        Toast.makeText(ResetPasswordActivity.this, "Senha actualizada com sucesso!", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 3500);
                    } else {
                        Toast.makeText(ResetPasswordActivity.this, "Falha ao actualizar senha.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "Email não encontrado.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método para verificar se o email é válido usando Patterns.EMAIL_ADDRESS
    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
