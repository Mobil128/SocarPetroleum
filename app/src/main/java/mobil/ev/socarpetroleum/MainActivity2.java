package mobil.ev.socarpetroleum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import mobil.ev.socarpetroleum.databinding.ActivityMain2Binding;


public class MainActivity2 extends AppCompatActivity {
    ActivityMain2Binding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new OlcuFragment());

        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.olc :
                    replaceFragment(new OlcuFragment());
                    break;
                case R.id.but_ol :
                    replaceFragment(new HamisiFragment());
                    break;
                case R.id.tenz :
                    replaceFragment(new TenzimFragment());
                    break;
                case R.id.sifaris :
                    replaceFragment(new UserFragment());
                    break;
            }return true;
        });

    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}