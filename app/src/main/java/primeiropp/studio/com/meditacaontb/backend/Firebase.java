package primeiropp.studio.com.meditacaontb.backend;
import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.Arrays;
import java.util.List;


import primeiropp.studio.com.meditacaontb.InicioActivity;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Thread.sleep;

public class Firebase {
    public static  String path;
    public boolean atualizar;
    public Context context;
    public Activity activity;
    public boolean baixar;

    final Handler handler = new Handler();
    public Firebase(Context context,Activity activity){
        this.context = context;
        this.activity = activity;
    }

    public boolean baixarArquivo(String url, final String name) {
        baixar = false;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // this will request for permission when user has not granted permission for the app
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INSTALL_LOCATION_PROVIDER}, 1);
        }
        //Download Script
        final File confereDowload = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getPath());
        int tamDir = confereDowload.list().length;
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setVisibleInDownloadsUi(true);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        request.setDestinationInExternalFilesDir(context,Environment.DIRECTORY_DOCUMENTS,name+".pdf");

        Log.i("AAA DOWLOADS",context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getPath());
        downloadManager.enqueue(request);
        Toast.makeText(context,"Baixando arquivos",Toast.LENGTH_LONG).show();
        try {
            sleep(1000);
            Toast.makeText(context,"ainda baixando arquivos",Toast.LENGTH_LONG).show();
            String [] files = confereDowload.list();
            for(String i:files){
                if(i.contains(name)){
                    Toast.makeText(context,"baixa",Toast.LENGTH_LONG).show();
                    Intent intent= new Intent(activity, InicioActivity.class);
                    activity.finish();
                    activity.startActivity(intent);

                    return true;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public  List<File> listarDir(){
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getPath());
        return Arrays.asList(file.listFiles());
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void atualizarBase(){

        final DatabaseReference nodeMeditacao = FirebaseDatabase.getInstance().getReference("Meditacao");

        nodeMeditacao.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int tam = (int) dataSnapshot.getChildrenCount();
                for(int i =1;i<=tam;i++){
                    String name=dataSnapshot.child(String.valueOf(i)).child("nome").getValue().toString();
                    File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getPath());
                    boolean baixar = true;
                    for(File f:file.listFiles()){
                        if(f.getName().contains(name)){
                            baixar = false;
                            Toast.makeText(activity,"NAO POSSUI NOVOS VERSICULOS",Toast.LENGTH_LONG).show();
                            break;
                        }

                    }
                    if(baixar){
                        String url = dataSnapshot.child(String.valueOf(i)).child("url").getValue().toString();
                        Log.i("aaa",url);
                        String versiculos = dataSnapshot.child(String.valueOf(i)).child("versiculos").getValue().toString();
                        baixarArquivo(url,name);
                        salvarMeditacao(name,versiculos);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }


    private void salvarMeditacao(String nome,String versiculos){
        SharedPreferences preferences = activity.getSharedPreferences(nome+".pdf", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("versiculo",versiculos);
        editor.commit();




    }
}
