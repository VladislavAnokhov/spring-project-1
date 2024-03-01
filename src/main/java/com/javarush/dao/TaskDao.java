package com.javarush.dao;



import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import com.javarush.domain.Task;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



@Repository
public class TaskDao {
        private final SessionFactory sessionFactory;

    public TaskDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<Task> getAll(int offset,int limit){
       Query<Task> taskQuery = getSession().createQuery("select t from Task t", Task.class);
       taskQuery.setFirstResult(offset);
       taskQuery.setMaxResults(limit);
       return taskQuery.getResultList();
    }
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int getAllCount(){
        Query<Long> query= getSession().createQuery("select count(t) from Task t", Long.class);
        return Math.toIntExact(query.uniqueResult());
    }
    @Transactional( propagation = Propagation.REQUIRED)
    public Task getById(int id){
        Query<Task> query = getSession().createQuery("select t from Task t where t.id = :ID",Task.class);
        query.setParameter("ID",id);
        return query.uniqueResult();
    }
    @Transactional( propagation = Propagation.REQUIRED)
    public void saveOrUpdate(Task task){
        getSession().persist(task);
    }
    @Transactional( propagation = Propagation.REQUIRED)
    public void delete(Task task){
        getSession().remove(task);
    }

    private Session getSession (){
        return sessionFactory.getCurrentSession();
    }
}
