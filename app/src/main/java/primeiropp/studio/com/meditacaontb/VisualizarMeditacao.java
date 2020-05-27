package primeiropp.studio.com.meditacaontb;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;
import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;



public class VisualizarMeditacao extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static  String pdf;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        pdf = bundle.getString("pdf");
        pegarVersiculos();
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},1);
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        DemoCollectionPagerAdapter demoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(demoCollectionPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    public static String getNomePdf(){
        return pdf;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private  void pegarVersiculos(){
        SharedPreferences preferences = this.getSharedPreferences(pdf, Context.MODE_PRIVATE);
        Frame2.versiculos = preferences.getString("versiculo","Nao tem versiculos ;-;").split("@");
        Log.i("versiculo",String.join("\n",Frame2.versiculos));
    }
}
