package com.jee.dao.manager;

import com.jee.dao.connection.SQLDataSource;
import com.jee.Models.Admin;
import com.jee.Models.Doctor;
import com.jee.Models.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;


public class AdminManager implements DaoLogic {

    private SQLDataSource ds;
    private Connection cnc;

    public AdminManager(SQLDataSource ds) throws SQLException {
        this.ds = ds;
        this.cnc = this.ds.getConnection();
    }

    @Override
    public int insert(Object obj) {
        if (!(obj instanceof Admin)) {
            throw new IllegalArgumentException("Object must be of type Admin");
        }
        Admin admin = (Admin) obj;
        String sql = "INSERT INTO admin (firstname, lastname, username, passwd) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = cnc.prepareStatement(sql)) {
            stmt.setString(1, admin.getFirstname());
            stmt.setString(2, admin.getLastname());
            stmt.setString(3, admin.getUsername());
            stmt.setString(4, admin.getPasswd());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Admin select(int id) {
        String sql = "SELECT * FROM admin WHERE id = ?";
        try (PreparedStatement stmt = cnc.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Admin(rs.getString("username"), rs.getString("passwd"),
                        rs.getString("firstname"), rs.getString("lastname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int update(Object obj) {
        if (!(obj instanceof Admin)) {
            throw new IllegalArgumentException("Object must be of type Admin");
        }
        Admin admin = (Admin) obj;
        String sql = "UPDATE admin SET firstname=?, lastname=?, username=?, passwd=? WHERE id=?";
        try (PreparedStatement stmt = cnc.prepareStatement(sql)) {
            stmt.setString(1, admin.getFirstname());
            stmt.setString(2, admin.getLastname());
            stmt.setString(3, admin.getUsername());
            stmt.setString(4, admin.getPasswd());
            stmt.setInt(5, admin.getId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public String delete(int id) {
        String sql = "DELETE FROM admin WHERE id = ?";
        try (PreparedStatement stmt = cnc.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                return "Admin deleted successfully.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Failed to delete admin.";
    }

    @Override
    public List<Document> selecByPidAndType(int patientId, String docType) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getDocumentType(int documentId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateDocument(int documentId,  String newPath) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int verify(String login, String password) {
        try {
            String sql = "SELECT COUNT(*) AS count_admin FROM  admin WHERE login = '" + login + "' AND password = '"
                    + password + "'";
            int count = 0;

            Statement stmt = this.cnc.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                count = rs.getInt("count_docteur");
                return count;
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public String selectPath(int docId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectPath'");
    }

	@Override
	public List<Doctor> selectAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
