package com.voosh.vooshv1.service;

import com.voosh.vooshv1.dao.ModeloDAO;
import com.voosh.vooshv1.model.Modelo;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.io.Serializable;
import java.io.SyncFailedException;
import java.util.List;


@RequestScoped
public class ModeloService implements Serializable {

    @Inject
    private ModeloDAO dao;

    public void salvarModelo(Modelo modelo) throws Exception{
        try{
            dao.inserirModelo(modelo);

        }catch (Exception e){

            throw new Exception("Falha ao salvar o modelo Service");
        }
    }

    public void deletarModelo(Modelo modelo) throws Exception{
        try{
            dao.excluirModelo(modelo.getId());

        }catch (Exception e){

            throw new Exception("Falha ao deletar o modelo Service");
        }

    }

    public List<Modelo> listarModelos(){
        return dao.listarModelo();
    }

    public void editarModelo(Modelo modelo){
        dao.atualizarModelo(modelo);
    }
}
