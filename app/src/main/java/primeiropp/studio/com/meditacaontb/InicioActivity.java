package primeiropp.studio.com.meditacaontb;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import primeiropp.studio.com.meditacaontb.backend.Firebase;


public class InicioActivity extends AppCompatActivity {
    private String []listaPdfs;
    private static ListView listaMeditacoes;
    static Firebase firebase;
    static List<File> listaFiles;
    private LinearLayout linear_inicio;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        listaMeditacoes = (ListView) findViewById(R.id.lista_meditacoes);
        firebase = new Firebase(getApplicationContext(),this);

        firebase.atualizarBase();
        pegarPdf(getApplicationContext());






        listaMeditacoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = listaMeditacoes.getItemAtPosition(i).toString();
                Bundle bundle = new Bundle();
                bundle.putString("pdf",item);
                Frame1.pdf = listaFiles.get(i).getPath();
                Intent intent = new Intent(InicioActivity.this,VisualizarMeditacao.class);
                intent.putExtra("bundle",bundle);
                startActivity(intent);


            }
        });
        listaMeditacoes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                listaMeditacoes.getItemAtPosition(i);

                return false;
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void pegarPdf(Context context){
        List<String> listasPdfs2 = new ArrayList<>();
        listaFiles =  firebase.listarDir();
        for(File f:listaFiles){
            listasPdfs2.add(f.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context
                , android.R.layout.simple_selectable_list_item
                , android.R.id.text1
                , listasPdfs2);
        listaMeditacoes.setAdapter(adapter);
    }
}
