package com.voosh.vooshv1.model;

import jakarta.persistence.*;

@Entity
@Table(name = "aeronave")
@Inheritance(strategy = InheritanceType.JOINED)
public class Aeronave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    protected String nome;
    protected String matricula;
    protected double autonomia;
    protected double custoHoraVoo;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "modelo_id")
    protected Modelo modelo;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_enum")
    protected StatusAeronave statusAeronave;

    public Aeronave() {
    }

    public Aeronave(StatusAeronave statusAeronave, Modelo modelo, double custoHoraVoo, double autonomia, String matricula, String nome) {
        this.statusAeronave = statusAeronave;
        this.modelo = modelo;
        this.custoHoraVoo = custoHoraVoo;
        this.autonomia = autonomia;
        this.matricula = matricula;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public double getAutonomia() {
        return autonomia;
    }

    public void setAutonomia(double autonomia) {
        this.autonomia = autonomia;
    }

    public double getCustoHoraVoo() {
        return custoHoraVoo;
    }

    public void setCustoHoraVoo(double custoHoraVoo) {
        this.custoHoraVoo = custoHoraVoo;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public StatusAeronave getStatusAeronave() {
        return statusAeronave;
    }

    public void setStatusAeronave(StatusAeronave statusAeronave) {
        this.statusAeronave = statusAeronave;
    }
}
