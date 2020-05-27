package primeiropp.studio.com.meditacaontb;

import android.Manifest;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;




/**
 * A simple {@link Fragment} subclass.
 */
public class Frame1 extends Fragment {
    private WebView webView;
    PDFView pdfView;
    public static String pdf;
    public Frame1() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frame1, container, false);
        pdfView = (PDFView)view.findViewById(R.id.pdfView);
        pdfView.fromFile(new File(pdf)).defaultPage(0).load();
        return view;
    }
}
