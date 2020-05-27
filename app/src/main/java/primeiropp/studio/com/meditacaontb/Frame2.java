package primeiropp.studio.com.meditacaontb;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import primeiropp.studio.com.meditacaontb.backend.Meditacao;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frame2 extends Fragment {
    Meditacao meditacao;

    public static String [] versiculos;
    public String pdf;
    private LinearLayout linear_versiculo;
    private ScrollView scroll_versiculo;
    DownloadManager downloadManager;
    private String semana[] = {"SEGUNDA","TERÇA","QUARTA","QUINTA","SEXTA","nao foi possivel carregar"};
    ScrollView scrollView;

    public Frame2() {
        // Required empty public constructor
    }


    @SuppressLint("WrongConstant")
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frame2, container, false);
        linear_versiculo  = (LinearLayout) view.findViewById(R.id.linear_versiculo);


       for( int i =0;i<5;i++){
           final Button textView2 = new Button(this.getContext(),null,0,R.style.cabecalho);
           textView2.setText(semana[i]);
           textView2.setTypeface(null, Typeface.ITALIC | Typeface.BOLD);
           textView2.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Bundle bundle = new Bundle();
                   bundle.putString("pdf",pdf);
                   int indice = indice(textView2.getText().toString());
                   bundle.putString("versiculo",versiculos[indice]);
                   bundle.putString("dia",semana[indice]);
                   Intent intent = new Intent(getActivity(),Anotacoes.class);
                   intent.putExtra("meditacao",bundle);
                   startActivity(intent);

               }
           });
           linear_versiculo.addView(textView2);
           TextView textView = new TextView(this.getContext(),null,0,R.style.estilo_texto);
            textView.setText(versiculos[i]);
            textView.setTypeface(null, Typeface.ITALIC|Typeface.BOLD);
            linear_versiculo.addView(textView);

       }

        return view;
    }
    private int indice(String str){
        for(int i =0;i<semana.length;i++){
            if(semana[i].contains(str)){
                return i;
            }
        }
        return 5;
    }
}
