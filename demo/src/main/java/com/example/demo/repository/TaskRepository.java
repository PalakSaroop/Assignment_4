package com.example.demo.repository;

import com.example.demo.entity.TaskEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class TaskRepository {

    @PersistenceContext
    private EntityManager em;

    public List<TaskEntity> findAll() {
        return em.createQuery("SELECT t FROM TaskEntity t", TaskEntity.class)
                .getResultList();
    }

    // TODO A
    public TaskEntity findById(Long id) {
        return em.find(TaskEntity.class, id);
    }

    public TaskEntity save(TaskEntity task) {

        if (task.getId() == null) {
            em.persist(task);
            return task;
        } else {
            return em.merge(task);
        }

    }

    // TODO B
    public void deleteById(Long id) {

        TaskEntity task = em.find(TaskEntity.class, id);

        if (task != null) {
            em.remove(task);
        }

    }
}