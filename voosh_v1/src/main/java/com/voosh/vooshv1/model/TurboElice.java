package com.voosh.vooshv1.model;


import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "turbo_elice")
@PrimaryKeyJoinColumn(name = "aeronave_id")
public class TurboElice extends Aeronave{

    private String tipoHelice;
    private boolean capacidadeSTOL;

    public TurboElice() {

    }

    public TurboElice(StatusAeronave status, Modelo modelo, double custoHoraVoo, double autonomia, String matricula, String nome, String tipoHelice, boolean capacidadeSTOL) {
        super(status, modelo, custoHoraVoo, autonomia, matricula, nome);
        this.tipoHelice = tipoHelice;
        this.capacidadeSTOL = capacidadeSTOL;
    }

    public String getTipoHelice() {
        return tipoHelice;
    }

    public void setTipoHelice(String tipoHelice) {
        this.tipoHelice = tipoHelice;
    }

    public boolean isCapacidadeSTOL() {
        return capacidadeSTOL;
    }

    public void setCapacidadeSTOL(boolean capacidadeSTOL) {
        this.capacidadeSTOL = capacidadeSTOL;
    }
}
