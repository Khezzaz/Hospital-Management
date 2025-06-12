package com.jee.dao.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jee.dao.connection.SQLDataSource;
import com.jee.Models.Doctor;
import com.jee.Models.Document;

public class DocumentManager implements DaoLogic {

    private SQLDataSource ds;
    private Connection cnc;

    public DocumentManager(SQLDataSource ds) {
        this.ds = ds;
        this.cnc = this.ds.getConnection();
    }

    @Override
    public int insert(Object obj) {
        if (!(obj instanceof Document)) {
            throw new IllegalArgumentException("Object must be of type Document");
        }
        Document document = (Document) obj;
        int rowsAffected = 0;
        String sql = "INSERT INTO hospital.document (patientId, docType, path, toc, description) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = cnc.prepareStatement(sql)) {
            stmt.setInt(1, document.getPatientId());
            stmt.setString(2, document.getDocType());
            stmt.setString(3 , document.getPath());
            stmt.setTimestamp(4, document.getToc());
            stmt.setString(5, document.getDescription());

            System.err.println("Record inserted successfully.");
            rowsAffected = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }


    @Override
    public Object select(int id) {
        Document document = null;
        String sql = "SELECT * FROM hospital.document WHERE id = ?";
        try (PreparedStatement stmt = cnc.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                document = new Document();
                document.setId(rs.getInt("id"));
                document.setPatientId(rs.getInt("patientId"));
                document.setDocType(rs.getString("docType"));
                document.setPath(rs.getString("path"));
                document.setToc(rs.getTimestamp("toc"));
                document.setDescription(rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return document;
    }


    public void updateDocument(int iddocument, String newPath) throws SQLException {
        String updateSql = "UPDATE document SET path = ? WHERE id = ?"; // Correction de la requête SQL
        
        try (PreparedStatement updateStatement = cnc.prepareStatement(updateSql)) {
            updateStatement.setString(1, newPath);
            updateStatement.setInt(2, iddocument);
            updateStatement.executeUpdate();
        }
    }

	
	public String getDocumentType(int documentid) throws SQLException {
		String selectSql = "SELECT docType FROM document WHERE Id = ? ";
		String docType = null;

		try (PreparedStatement selectStatement =  cnc.prepareStatement(selectSql)) {
			selectStatement.setInt(1, documentid);
			ResultSet resultSet = selectStatement.executeQuery();
			if (resultSet.next()) {
				docType = resultSet.getString("docType");
			}
		}

		return docType;
	}

	



   public String selectPath(int docId) {
        // Initialize the path : 
        String path = null ; 
        
        //Formulating the query : 
        String query = "SELECT path FROM document WHERE id = ?" ; 

        //Creating a statement and execute : 
        try(PreparedStatement stmt = this.cnc.prepareStatement(query)) {
            // Set the parameters of the statement : 
            stmt.setInt(1, docId);
            // Getting the result of the query : 
            ResultSet rs = stmt.executeQuery() ; 

            if (rs.next()) {
                path = rs.getString("path");
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return path ; 
   }

@Override
public String delete(int id) throws SQLException {
    // Getting the path of the specified document : 
    String path = selectPath(id) ; 
    int rowsAffected = 0;
    // Formulating the query : 
    String query = "DELETE FROM document WHERE id = ?";
    // Creating a statement and execute : 
    try(PreparedStatement stmt = this.cnc.prepareStatement(query)) {
        //Setting the parameters for the statement : 
        stmt.setInt(1, id);
        rowsAffected = stmt.executeUpdate() ; 

        if (rowsAffected >0) {
            System.out.println("Document deleted successfully");
        }
        else {
            System.out.println("Cannot delete document");
        }

    }catch(Exception e ) {
        e.printStackTrace();
    }


    return path ;
}

@Override
public List<Document> selecByPidAndType(int patientId, String docType) {
    // Initialisation de la liste :
    List<Document> list = new ArrayList<>();

    // 1 : Formuler la requête :
    String query = "SELECT * FROM document WHERE doctype = ? AND patientId = ?";

    // 2 : Créer un PreparedStatement :
    try (PreparedStatement stmt = this.cnc.prepareStatement(query)) {
        // Définir les paramètres de la requête :
        stmt.setString(1, docType);
        stmt.setInt(2, patientId);

        // Créer un ResultSet :
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Document doc = new Document();
            doc.setId(rs.getInt("id"));
            doc.setDocType(rs.getString("doctype"));
            doc.setPath(rs.getString("path"));
            doc.setToc(rs.getTimestamp("toc"));
            doc.setDescription(rs.getString("description"));

            list.add(doc);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return list;
}


@Override
public int update(Object o) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public int verify(String login, String password) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public List<Doctor> selectAll() throws SQLException {
	// TODO Auto-generated method stub
	return null;
}


}

        
    


