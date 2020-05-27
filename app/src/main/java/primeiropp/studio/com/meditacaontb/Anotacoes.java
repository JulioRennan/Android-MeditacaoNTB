package primeiropp.studio.com.meditacaontb;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Anotacoes extends AppCompatActivity {
    private TextView txt_versiculo;
    private EditText anotacoes;
    String versiculo;
    String pdf;
    String dia;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotacoes);
        txt_versiculo = (TextView) findViewById(R.id.txt_versiculo);
        anotacoes = (EditText) findViewById(R.id.anotacoes);
        Bundle bundle = getIntent().getBundleExtra("meditacao");
        versiculo = bundle.getString("versiculo", "nao foi possivel carregar versiculo");
        pdf = VisualizarMeditacao.getNomePdf();
        dia = bundle.getString("dia","SABADO");
        txt_versiculo.setText(versiculo);
        anotacoes.setText(lerArquivo());
        this.setTitle("Anotacoes");


    }

    @Override
    public void onBackPressed() {
        gravarArquivo(anotacoes.getText().toString());
        super.onBackPressed();
    }

    private void gravarArquivo(String txt) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(pdf+dia+".txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(txt);
            Toast.makeText(this, "Anotacoes salvas", Toast.LENGTH_SHORT).show();
            outputStreamWriter.close();

        } catch (IOException e) {
            Log.v("MainActivity", e.toString());
        }
    }

    private String lerArquivo() {
        String resultado = "";
        try {
            InputStream arq = openFileInput(pdf+dia+".txt");
            if (arq != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(arq);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String linha = "";
                while ((linha = bufferedReader.readLine()) != null) {
                    resultado += linha;
                }
                arq.close();

            }
        } catch (IOException e) {
            Log.v("MainActivity", e.toString());
        }
        return resultado;
    }
}