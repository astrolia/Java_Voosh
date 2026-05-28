package com.voosh.vooshv1.controller;


import com.voosh.vooshv1.model.*;
import com.voosh.vooshv1.service.AeronaveService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;


import java.io.Serializable;
import java.util.List;


//nome para o xhtml e tempo de vida do objeto
@Named("aeronaveBean")
@ViewScoped
public class AeronaveBean implements Serializable{

    private Aeronave aeronave;
    private List<Aeronave> listaAeronave;
    private String tipoAeronave = "TURBOFAN";
    private int idModeloDigitado;

    //injeta dependencia service
    @Inject
    private AeronaveService aeronaveService;

    //contruir imediatamente
    @PostConstruct
    public void init(){
        limpar();
        carregarAeronaves();
    }

    //pesquisa modelos por nome
    public List<Modelo> sugerirModelos(String query) {

        return aeronaveService.sugerirModelos(query);
    }

    public void carregarAeronaves(){
        this.listaAeronave = aeronaveService.listarAeronaves();
    }

    //carrega os campos de acordo com o tipoAeronave selecionado (TurboFan por default)
    public void limpar(){

        if("TURBOELICE".equals(tipoAeronave)){
            this.aeronave = new TurboElice();
        }else{
            this.aeronave = new TurboFan();
        }

    }

    public void salvar(){
        try{
            aeronaveService.salvarAeronave(this.aeronave);

            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Aeronave salva com sucesso!");
            carregarAeronaves();
            limpar();
        }catch (Exception e){
            adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Erro de Negócio", e.getMessage());
        }
    }


    public void excluir(){

        try{
            aeronaveService.excluirAeronave(this.aeronave.getId());
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Excluído", "Aeronave removida.");
            limpar();
            carregarAeronaves();
        }catch (Exception e){
            adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Erro ao excluir", e.getMessage());
        }
    }

    //exibir uma msg na tela estilo pop up
    private void adicionarMensagem(FacesMessage.Severity severidade, String sumario, String detalhe) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(severidade, sumario, detalhe));
    }

    //getters e setters
    public Aeronave getAeronave() {
        return aeronave;
    }

    public void setAeronave(Aeronave aeronave) {
        this.aeronave = aeronave;
    }

    public List<Aeronave> getListaAeronave() {
        return listaAeronave;
    }

    public void setListaAeronave(List<Aeronave> listaAeronave) {
        this.listaAeronave = listaAeronave;
    }

    public String getTipoAeronave() {
        return tipoAeronave;
    }

    public void setTipoAeronave(String tipoAeronave) {
        this.tipoAeronave = tipoAeronave;
    }

    public int getIdModeloDigitado() {
        return idModeloDigitado;
    }

    public void setIdModeloDigitado(int idModeloDigitado) {
        this.idModeloDigitado = idModeloDigitado;
    }

    public StatusAeronave[] getStatusOpcoes() {
        return StatusAeronave.values();
    }


    //pegar o tipo dda aeronave
    public String getTipoAeronaveSelecionada() {
        if (this.aeronave == null) {
            return "";
        }
        // retorna TurboFan ou TurboElice
        return this.aeronave.getClass().getSimpleName();
    }

    //faz o casting da clase aeronave para pegar atributos especificos
    public TurboFan getAsTurboFan() {
        if (this.aeronave instanceof TurboFan) {
            return (TurboFan) this.aeronave;
        }
        return null;
    }

    //faz o casting da clase aeronave para pegar atributos especificos
    public TurboElice getAsTurboElice() {
        if (this.aeronave instanceof TurboElice) {
            return (TurboElice) this.aeronave;
        }
        return null;
    }


    //procura aeronave por id para exibir as informações
    public void carregarPorId() {

        //pega o id dentro da URL
        String paramId = jakarta.faces.context.FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get("id");

        if (paramId != null && !paramId.isEmpty()) {
            try {
                // tranforma o id em long int
                Long idDesejado = Long.parseLong(paramId);

                this.aeronave = aeronaveService.achar(idDesejado);

            } catch (NumberFormatException e) {
                System.out.println("Erro ao converter o ID da URL: " + paramId);
            }
        }
    }

    //muddar o tipo de aeronave de acordo com a selecionada
    public void prepararEdicao(Aeronave aero) {
        this.aeronave = aero;

        // mudda o tipo da aeronave
        if (aero instanceof TurboFan) {
            this.tipoAeronave = "TURBOFAN";
        } else if (aero instanceof TurboElice) {
            this.tipoAeronave = "TURBOELICE";
        }
    }
}
