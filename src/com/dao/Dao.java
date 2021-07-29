package com.dao;

import com.model.Tasks;
import com.model.Users;
import java.util.List;

public interface Dao {
    public boolean createAccount(Users users);
    
    public boolean checkUserByUsername(String username);
    
    public Users checkUser(String username,String password);
    
    public List<Tasks> getAllTasks();
    
    public boolean addTask(Tasks newTask);
    
    public boolean updateTask(Tasks Task);
    
    public List<Tasks> search(String keyword);
    
    public List<Tasks> filterByDay(int minDay,int maxDay);
    
    public boolean solveTask(int selectedId);
    
    public boolean deleteTask(int selectedId);
    
    public List<Tasks> filterByStatus(String status);
}
