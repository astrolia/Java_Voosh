package com.voosh.vooshv1.dao;

import com.voosh.vooshv1.model.Aeronave;
import com.voosh.vooshv1.model.Modelo;
import com.voosh.vooshv1.util.JPAUtil;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

@RequestScoped
public class AeronaveDAO {

    // O construtor fica vazio, não guarda estado de conexões
    public AeronaveDAO() {
    }

    public boolean inserir(Aeronave aeronave) {
        // obter gerenciador e fabrica
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            //Inicia a transação
            tx.begin();

            // associar modelo à aeronave sem fazer um SELECT
            if (aeronave.getModelo() != null && aeronave.getModelo().getId() != 0) {
                Modelo modeloGerenciado = em.getReference(Modelo.class, aeronave.getModelo().getId());
                aeronave.setModelo(modeloGerenciado);
            }

            // Salva a aeronave.
            em.persist(aeronave);

            // confirma as alterações no banco de dados
            tx.commit();
            return true;

        } catch (Exception e) {
            // Se der erro, desfaz a operação
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("ERRO ao inserir Aeronave: " + e.getMessage());
            return false;
        } finally {
            // Fecha o EntityManager
            em.close();
        }
    }


    public static List<Aeronave> listarAeronave(){
        EntityManager em = JPAUtil.getEntityManager();

        try{
            String jpql = "SELECT a FROM Aeronave a JOIN FETCH a.modelo";

            List<Aeronave> lista = em.createQuery(jpql, Aeronave.class).getResultList();
            return lista;
        }catch(Exception e){
            System.out.println("ERRO ao listar aeronaves: " + e.getMessage());
            return null;
        }finally{
            em.close();
        }

    }

    public boolean excluirAeronave(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // tornar monitorado
            Aeronave aeroGerenciada = em.find(Aeronave.class, id);

            // remove se existir
            if (aeroGerenciada != null) {

                // remove
                em.remove(aeroGerenciada);

                tx.commit();
                System.out.println("Aeronave ID " + id + " excluída.");
                return true;
            } else {
                System.out.println("Aeronave ID " + id + " não encontrada para exclusão.");
                tx.rollback();
                return false;
            }

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("ERRO ao excluir aeronave: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    public boolean atualizarAeronave(Aeronave aeronave) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // vincula o modelo
            if (aeronave.getModelo() != null && aeronave.getModelo().getId() != 0) {
                Modelo modeloGerenciado = em.getReference(Modelo.class, aeronave.getModelo().getId());
                aeronave.setModelo(modeloGerenciado);
            }

            //atualizar
            em.merge(aeronave);

            tx.commit();
            System.out.println("Aeronave ID " + aeronave.getId() + " atualizada.");
            return true;

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("ERRO ao atualizar aeronave: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    //procura aeronave por id (long int)
    public Aeronave acharPorID(Long id){
        EntityManager em = JPAUtil.getEntityManager();

        return em.find(Aeronave.class, id);

    }
}