package com.voosh.vooshv1.controller;


import com.voosh.vooshv1.model.Modelo;
import com.voosh.vooshv1.service.ModeloService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named("modeloBean")
@ViewScoped
public class ModeloBean implements Serializable {

    @Inject
    private ModeloService modeloService;

    private Modelo modelo;
    private List<Modelo> listaModelo;

    @PostConstruct
    public void init(){
        limpar();
        carregarModelos();
    }

    public void limpar(){

        this.modelo = new Modelo();
    }

    public void carregarModelos(){
        this.listaModelo = modeloService.listarModelos();
    }

    public void salvar() {
        try {
            // Se o modelo já tem ID, atualiza
            if (this.modelo.getId() > 0) {
                modeloService.editarModelo(modelo);
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Modelo atualizado com sucesso!");
            } else {
                modeloService.salvarModelo(modelo);
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Modelo salvo com sucesso!");
            }
            carregarModelos();
            limpar();
        } catch (Exception e) {
            adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Erro de Negócio", e.getMessage());
        }
    }


    public void excluir(Modelo modeloSelecionado) {
        try {
            modeloService.deletarModelo(modeloSelecionado);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Modelo deletado com sucesso!");
            carregarModelos();
            limpar();
        } catch (Exception e) {
            adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Erro de Negócio", e.getMessage());
        }
    }

    public void prepararEditar(Modelo modeloSelecionado) {
        this.modelo = modeloSelecionado;
    }

    private void adicionarMensagem(FacesMessage.Severity severidade, String sumario, String detalhe) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(severidade, sumario, detalhe));
    }

    public List<Modelo> getListaModelo() {
        return listaModelo;
    }

    public void setListaModelo(List<Modelo> listaModelo) {
        this.listaModelo = listaModelo;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }
}
