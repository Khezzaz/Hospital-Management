package com.jee.business.models;

import java.sql.SQLException;
import java.util.List;

import com.jee.Models.Doctor;
import com.jee.dao.connection.MySQLDataSource;
import com.jee.dao.manager.DaoLogic;
import com.jee.dao.manager.DoctorManager;


public class DoctorBusiness implements ModelBusinessLogic {
    DaoLogic manager;
    
	public DoctorBusiness(DaoLogic manager) {
        this.manager = manager;
    }

    @Override
    public int insert(Object o) {
        return this.manager.insert(o);
    }

 
    public Object select(int id) {
        return this.manager.select(id);
    }

  
    public int update(Object o) {
        return this.manager.update(o);
    }

    
    public String delete(int id) throws SQLException {
        return this.manager.delete(id);
    }
    public boolean verify(String login, String password) {
		 if(manager.verify(login, password)==1) {
			 return true;
		 }
		 return false;
	 }
    public List<Doctor> selectAll() throws SQLException{
    	return manager.selectAll();
    }
	

}
