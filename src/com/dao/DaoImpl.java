package com.dao;

import com.model.Tasks;
import com.model.Users;
import com.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DaoImpl implements Dao{

    @Override
    public boolean createAccount(Users users) {
        boolean result = false;
        Connection c = null;
        PreparedStatement ps = null;
        String sql = "insert into todolist.users(name,surname,username,password,mail,address)\n" +
"values('"+users.getName()+"','"+users.getSurname()+"','"+users.getUsername()+"','"+users.getPassword()+"',"
                + "'"+users.getEmail()+"','"+users.getAddress()+"')";
        c = DBhelper.getConnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(sql);
                ps.execute();
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                Util.closeAll(c, ps, null);
            }  
        } else {
            System.err.println("Not Connection!");
        }
        return result;
    }

    @Override
    public boolean checkUserByUsername(String username) {
        boolean result = false;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from todolist.users\n" +
"where name='"+username+"'";
        c = DBhelper.getConnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                Util.closeAll(c, ps, rs);
            }
        }else{
            System.err.println("Not Connection!");
        }
        
        return result;
    }

    @Override
    public Users checkUser(String username, String password) {
        Users u = new Users();
        boolean result = false;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select id,name,surname,username,password,mail,address from todolist.users\n" +
"where username='"+username+"' and password='"+password+"'";
        c = DBhelper.getConnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs.next()) {
                    u.setId(rs.getInt("id"));
                    u.setName(rs.getString("name"));
                    u.setSurname(rs.getString("surname"));
                    u.setUsername(rs.getString("username"));
                    u.setPassword(rs.getString("password"));
                    u.setEmail(rs.getString("mail"));
                    u.setAddress(rs.getString("address"));
                } else{
                    u = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                Util.closeAll(c, ps, rs);
            }
        }else{
            System.err.println("Not Connection!");
        }
        
        return u;
    }

    @Override
    public List<Tasks> getAllTasks() {
        List<Tasks> resultList = new ArrayList<Tasks>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "Select id,task,day,category,status,date From todolist.tasks";
        c = DBhelper.getConnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {                    
                    Tasks t = new Tasks();
                    t.setId(rs.getInt("id"));
                    t.setTask(rs.getString("task"));
                    t.setDay(rs.getInt("day"));
                    t.setCategory(rs.getString("category"));
                    t.setStatus(rs.getString("status"));
                    t.setDate(rs.getString("date"));
                    resultList.add(t);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                Util.closeAll(c, ps, rs);
            }
        } else {
            System.err.println("Not Connection!");
        }
        return resultList;
    }

    @Override
    public boolean addTask(Tasks newTask) {
        boolean result = false;
        Connection c = null;
        PreparedStatement ps = null;
        String sql = "insert into todolist.tasks(task,day,category)\n" +
"values('"+newTask.getTask()+"',"+newTask.getDay()+",'"+newTask.getCategory()+"')";
        c = DBhelper.getConnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(sql);
                ps.execute();
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                Util.closeAll(c, ps, null);
            }
        } else {
            System.err.println("Not Connection");
        }
        
        return result;
    }

    @Override
    public boolean updateTask(Tasks Task) {
        boolean result = false;
        Connection c = null;
        PreparedStatement ps = null;
        String sql = "update todolist.tasks\n" +
"set task='"+Task.getTask()+"',day="+Task.getDay()+",category='"+Task.getCategory()+"'\n" +
"where id="+Task.getId()+"";
        c = DBhelper.getConnection();
        if (c != null) {
            try {
               ps = c.prepareStatement(sql);
               ps.execute(); 
               result = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                Util.closeAll(c, ps, null);
            }
        } else {
            System.err.println("Not Connection!");
        }
        
        return result;    
    }   

    @Override
    public List<Tasks> search(String keyword) {
        List<Tasks> resultList = new ArrayList<Tasks>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select id,task,day,category,status,date from todolist.tasks\n" +
"where task like '%"+keyword+"%' or day like '%"+keyword+"%' or category like '%"+keyword+"%' or status like '%"+keyword+"%';";
        c = DBhelper.getConnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {                    
                    Tasks t = new Tasks();
                    t.setId(rs.getInt("id"));
                    t.setTask(rs.getString("task"));
                    t.setDay(rs.getInt("day"));
                    t.setCategory(rs.getString("category"));
                    t.setStatus(rs.getString("status"));
                    t.setDate(rs.getString("date"));
                    resultList.add(t);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Util.closeAll(c, ps, rs);
            }
        } else {
            System.err.println("Not Connection!");
        }
        return resultList;
    }

    @Override
    public List<Tasks> filterByDay(int minDay, int maxDay) {
        List<Tasks> resultList = new ArrayList<Tasks>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select id,task,day,category,status,date from todolist.tasks\n" +
"where day between "+minDay+" and "+maxDay+"";
        c = DBhelper.getConnection();
        if (c != null) {
            try {
               ps = c.prepareCall(sql);
               rs = ps.executeQuery();
                while (rs.next()) {                    
                    Tasks t = new Tasks();
                    t.setId(rs.getInt("id"));
                    t.setTask(rs.getString("task"));
                    t.setDay(rs.getInt("day"));
                    t.setCategory(rs.getString("category"));
                    t.setStatus(rs.getString("status"));
                    t.setDate(rs.getString("date"));
                    resultList.add(t);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                Util.closeAll(c, ps, rs);
            } 
        } else {
            System.err.println("Not Connection");
        }
        
        return resultList;
    }

    @Override
    public boolean solveTask(int selectedId) {
        boolean result = false;
        Connection c = null;
        PreparedStatement ps = null;
        String sql = "update todolist.tasks\n" +
"set status = 'SOLVED'\n" +
"where id = "+selectedId+"";
        c = DBhelper.getConnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(sql);
                ps.execute();
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                Util.closeAll(c, ps, null);
            }
        } else {
            System.err.println("Not Connection!");
        }
        return result;
    }

    @Override
    public boolean deleteTask(int selectedId) {
        boolean result = false;
        Connection c = null;
        PreparedStatement ps = null;
        String sql = "delete from todolist.tasks\n" +
"where id = "+selectedId+"";
        c = DBhelper.getConnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(sql);
                ps.execute();
                result = true;
            } catch (Exception e) {
            }
        } else {
            System.err.println("Not Connection!");
        }
        return result;
    }

    @Override
    public List<Tasks> filterByStatus(String status) {
        List<Tasks> resultList = new ArrayList<Tasks>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select id,task,day,category,status,date from todolist.tasks\n" +
"where status = '"+status+"'";
        c = DBhelper.getConnection();
        if (c != null) {
            try {
               ps = c.prepareStatement(sql);
               rs = ps.executeQuery(); 
                while (rs.next()) {                    
                    Tasks t = new Tasks();
                    t.setId(rs.getInt("id"));
                    t.setTask(rs.getString("task"));
                    t.setDay(rs.getInt("day"));
                    t.setCategory(rs.getString("category"));
                    t.setStatus(rs.getString("status"));
                    t.setDate(rs.getString("date"));
                    resultList.add(t);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                Util.closeAll(c, ps, rs);
            }    
        } else {
            System.err.println("Not Connection!");
        }
        return resultList;
    }
}