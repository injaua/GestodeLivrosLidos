package com.example.gestodelivroslidos;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Toast.makeText(HomeActivity.this, "Este aplicativo nao foi desenvolvido por mim.", Toast.LENGTH_LONG).show();
        bottomNavigationView = findViewById(R.id.bottonNavegationView);

        // Remover o itemIconTint programaticamente
        bottomNavigationView.setItemIconTintList(null);


        String openFragment = getIntent().getStringExtra("openFragment");
        if (openFragment != null && openFragment.equals("dashboard")) {
            openFragment(new DashboardFragment());
        }

        DashboardFragment dashboardFragment = new DashboardFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout, dashboardFragment) // Substitua 'R.id.frame_layout' pelo ID do seu FrameLayout
                .addToBackStack(null)
                .commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_concluidos) {
                fragment = new ConcluidosFragment();
            } else if (itemId == R.id.navigation_Livros) {
                fragment = new LivrosFragment();
            } else if (itemId == R.id.navigation_dashboard) {
                fragment = new DashboardFragment();
            } else if (itemId == R.id.navigation_em_andamento) {
                fragment = new AndamentoFragment();
            } else if (itemId == R.id.navigation_add) {
                fragment = new AdicionarFragment();
            }

            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.framelayout, fragment);
                transaction.addToBackStack(null); // Adiciona ao back stack para permitir navegação de retorno
                transaction.commit();
                return true;
            }

            return false;
        });

    }

    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.framelayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
