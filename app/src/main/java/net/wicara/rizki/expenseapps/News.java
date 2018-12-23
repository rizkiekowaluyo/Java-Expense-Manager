package net.wicara.rizki.expenseapps;

import java.io.Serializable;

public class News implements Serializable{
    private String judul;
    private String kategori;
    private String keterangan;
    private String link;

    public News(){

    }

    public News(String judul, String kategori, String keterangan, String link) {
        this.judul = judul;
        this.kategori = kategori;
        this.keterangan = keterangan;
        this.link = link;
    }


    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
