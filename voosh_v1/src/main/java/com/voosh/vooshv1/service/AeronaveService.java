package com.voosh.vooshv1.service;

import com.voosh.vooshv1.dao.AeronaveDAO;
import com.voosh.vooshv1.dao.ModeloDAO;
import com.voosh.vooshv1.model.Aeronave;
import com.voosh.vooshv1.model.Modelo;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.io.Serializable;
import java.util.List;


@RequestScoped
//implementa uma interface q diz que os objetos dessa class podem ser serializados
public class AeronaveService implements Serializable{

    @Inject
    private AeronaveDAO aeronaveDAO;

    @Inject
    private ModeloDAO modeloDAO;

    public List<Modelo> sugerirModelos(String query) {
        return modeloDAO.buscarPorNome(query);
    }

    public List<Aeronave> listarAeronaves(){

        return aeronaveDAO.listarAeronave();
    }


    public void salvarAeronave(Aeronave aeronave) throws Exception{

        //colocar autonomia
        if(aeronave.getAutonomia() <= 0){
            throw new Exception("Autonomia deve ser maior que zero!");
        }

        //matrica de acordo com paddrão br
        if(aeronave.getMatricula() != null && !aeronave.getMatricula().matches("^(PR|PT|PS|PP)-[A-Z]{3}$")){

            throw new Exception("Matrícula inválida! Use o padrão brasileiro (Ex: PR-YSA).");
        }

        //modelo deve ser selecionado
        if (aeronave.getModelo() == null || aeronave.getModelo().getId() == 0) {
            throw new Exception("Associe a um modelo.");
        }

        if(aeronave.getId() == 0){

            aeronaveDAO.inserir(aeronave);
        }else{
            aeronaveDAO.atualizarAeronave(aeronave);
        }
    }

    public void excluirAeronave(int id) throws Exception{

        boolean deletado = aeronaveDAO.excluirAeronave(id);

        if(!deletado){
            throw new Exception("Não foi possível excluir a aeronave de ID " + id);
        }
    }

    //procura aeronave por id (long int)
    public Aeronave achar(Long id){

            return aeronaveDAO.acharPorID(id);
    }



}
