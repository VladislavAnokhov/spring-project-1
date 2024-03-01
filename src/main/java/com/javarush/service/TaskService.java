package com.javarush.service;

import com.javarush.dao.TaskDao;
import com.javarush.domain.Status;
import com.javarush.domain.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class TaskService {
    private final TaskDao taskDao;

    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }
    public List<Task> getAll(int offset,int limit){
        return taskDao.getAll(offset,limit);
    }
    public int getAllCount(){
        return taskDao.getAllCount();
    }
    @Transactional
    public Task edit(int id, String description, Status status){
        Task task= taskDao.getById(id);
        if (isNull(task)){
            throw  new RuntimeException("not found");
        }
        task.setDescription(description);
        task.setStatus(status);
        taskDao.saveOrUpdate(task);
        return task;
    }

    public Task create( String description, Status status){
        Task task = new Task();
        task.setDescription(description);
        task.setStatus(status);
        taskDao.saveOrUpdate(task);
        return task;
    }
    @Transactional
    public void delete(int id){
        Task task= taskDao.getById(id);
        if (isNull(task)){
            throw  new RuntimeException("not found");
        }
        taskDao.delete(task);
    }
}
