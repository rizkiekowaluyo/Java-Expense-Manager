package net.wicara.rizki.expenseapps;

import java.io.Serializable;

public class Asisten implements Serializable {
    private String nama;
    private String email;
    private String nohp;
    private String alamat;

    public Asisten(){

    }

    public Asisten(String nama,String email,String nohp,String alamat){
        this.nama = nama;
        this.email = email;
        this.nohp = nohp;
        this.alamat = alamat;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) { this.nama = nama; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getNohp() { return nohp; }

    public void setNohp(String nohp) { this.nohp = nohp; }

    public String getAlamat() { return alamat; }

    public void setAlamat(String alamat) { this.alamat = alamat; }
}
