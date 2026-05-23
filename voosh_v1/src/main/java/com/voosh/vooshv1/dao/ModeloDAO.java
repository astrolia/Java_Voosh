package com.voosh.vooshv1.dao;

import com.voosh.vooshv1.model.Modelo;
import com.voosh.vooshv1.util.JPAUtil;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

@RequestScoped
public class ModeloDAO {

    public ModeloDAO() {
    }

    public boolean inserirModelo(Modelo modelo){

        //obter gerador e fabrica
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try{
            //começar operação
            tx.begin();

            em.persist(modelo);

            tx.commit();
            return true;

        }catch(Exception e){

            if(tx.isActive()){
                tx.rollback();
            }
            System.out.println("ERRO ao inserir modelo: " + e.getMessage());
            return false;
        }finally {
            em.close();
        }
    }

    public List<Modelo> listarModelo(){
        EntityManager em = JPAUtil.getEntityManager();

        try{
            String jpql = "SELECT m FROM Modelo m";

            List<Modelo> lista = em.createQuery(jpql, Modelo.class).getResultList();
            return lista;
        }catch(Exception e){
            System.out.println("ERRO ao listar modelo: " + e.getMessage());
            return null;
        }finally{
            em.close();
        }

    }

    public boolean excluirModelo(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // tornar monitorado
            Modelo modeloGerenciada = em.find(Modelo.class, id);

            // remove se existir
            if (modeloGerenciada != null) {

                // remove
                em.remove(modeloGerenciada);

                tx.commit();
                System.out.println("Modelo ID " + id + " excluída.");
                return true;
            } else {
                System.out.println("Modelo ID " + id + " não encontrada para exclusão.");
                tx.rollback();
                return false;
            }

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("ERRO ao excluir modelo: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    public boolean atualizarModelo(Modelo modelo) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // O merge analisa o id do objeto
            // Se o id existir ele faz o UPDATE
            em.merge(modelo);

            tx.commit();
            System.out.println("Modelo ID " + modelo.getId() + " atualizado.");
            return true;

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("ERRO ao atualizar modelo: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    public List<Modelo> buscarPorNome(String nomeDigitado) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            //consulta JPQL buscando por nome em Modelo
            String jpql = "SELECT m FROM Modelo m WHERE LOWER(m.nome) LIKE LOWER(:nome)";

            return em.createQuery(jpql, Modelo.class)
                    .setParameter("nome", "%" + nomeDigitado + "%") // O % faz o papel do "contém"
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
