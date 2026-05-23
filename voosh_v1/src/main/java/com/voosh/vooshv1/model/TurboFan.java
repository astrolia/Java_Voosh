package com.voosh.vooshv1.model;


import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "turbo_fan")
@PrimaryKeyJoinColumn(name = "aeronave_id")
public class TurboFan extends Aeronave{

    private int nTurbinas;
    private boolean rvsm;

    public TurboFan() {

    }

    public TurboFan(StatusAeronave status, Modelo modelo, double custoHoraVoo, double autonomia, String matricula, String nome, int nTurbinas, boolean rvsm) {
        super(status, modelo, custoHoraVoo, autonomia, matricula, nome);
        this.nTurbinas = nTurbinas;
        this.rvsm = rvsm;
    }

    public int getnTurbinas() {
        return nTurbinas;
    }

    public void setnTurbinas(int nTurbinas) {
        this.nTurbinas = nTurbinas;
    }

    public boolean isRvsm() {
        return rvsm;
    }

    public void setRvsm(boolean rvsm) {
        this.rvsm = rvsm;
    }
}
