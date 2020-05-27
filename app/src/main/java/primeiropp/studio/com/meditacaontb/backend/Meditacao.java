package primeiropp.studio.com.meditacaontb.backend;

import java.util.ArrayList;
import java.util.Set;

public class Meditacao {
    private String pdf;
    private String []versiculosSemana;
    private ArrayList<String> anotacoes;
    public Meditacao(String key){

    }
    public Meditacao(){

    }
    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String[] getVersiculosSemana() {
        return versiculosSemana;
    }

    public void setVersiculosSemana(String[] versiculosSemana) {
        versiculosSemana = versiculosSemana;
    }

    public ArrayList<String> getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(ArrayList<String> anotacoes) {
        this.anotacoes = anotacoes;
    }
    public String getJson(){
        String json = "";
        for(int i =0 ;i<versiculosSemana.length;i++){
            json+=versiculosSemana[i]+"@";
        }

        for(String i:anotacoes){
            json+=i+"@";
        }
        return json;
    }
}
