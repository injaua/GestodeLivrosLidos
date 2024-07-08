package com.example.gestodelivroslidos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gestodelivroslidos.database.DatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etEmail, etPassword;
    private TextInputLayout tilEmail, tilPassword;
    private Button btnLogin;
    private TextView tvRegister, tvResetPassword;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialização dos componentes da tela
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        tilEmail = findViewById(R.id.til_email);
        tilPassword = findViewById(R.id.til_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tv_register);
        tvResetPassword = findViewById(R.id.tv_reset_password);

        databaseHelper = new DatabaseHelper(this);

        // Configuração de clique no botão de login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        // Configuração de clique no texto de registro
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre a atividade de registro (RegisterActivity)
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Configuração de clique no texto de redefinição de senha
        tvResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre a atividade de redefinição de senha (ResetPasswordActivity)
                Intent intent = new Intent(MainActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    // Método para validar o email
    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    // Método para realizar o login
    private void login() {
        // Captura dos valores dos campos de entrada
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

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

        // Autenticação do usuário
        boolean loginSuccessful = authenticateUser(email, password);
        if (loginSuccessful) {
            // Login bem-sucedido, exibe uma mensagem
            Toast.makeText(MainActivity.this, "Login bem-sucedido para " + email, Toast.LENGTH_SHORT).show();
              new Handler().postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                      startActivity(intent);
                      finish();
                  }
              }, 1500);

        } else {
            // Login falhou, exibe uma mensagem de erro
            Toast.makeText(MainActivity.this, "Email ou senha inválidos", Toast.LENGTH_SHORT).show();
            etEmail.setText("");
            etPassword.setText("");
        }
    }

    // Método para autenticar o usuário
    private boolean authenticateUser(String email, String password) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] columns = {"id"};
        String selection = "email = ? AND password = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query("usuario", columns, selection, selectionArgs, null, null, null);

        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }
}
