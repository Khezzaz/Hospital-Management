package com.jee.business.docs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;

import com.jee.Models.Document;
import com.jee.dao.manager.DaoLogic;

import jakarta.servlet.http.Part;

import java.awt.Desktop;

public class DocServiceImp implements DocBusinessLogic {

    private DaoLogic docManager ;
    
    public DocServiceImp(DaoLogic docManager) {
        this.docManager = docManager;
    }

    @Override
    public Document selectDocument(int docId) throws FileNotFoundException, IOException {
        
        //1 : Retrieve document's path using its  Id
        Document doc = (Document) this.docManager.select(docId) ;
        String path = doc.getPath() ; 
        
        try {
            // Creating a file : 
            File file = new File(path) ; 
            //Open the file : 
            Desktop.getDesktop().open(file);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return doc ; 
    }

    @Override
    public int insertDocument(Document document, Part filePart) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            // Déterminer le répertoire cible en fonction du type de document
            File target = null;
            if (document.getDocType().equals("pdf")) {
                target = new File(Directories.PDF_DIRECTORY, filePart.getSubmittedFileName());
            } else if (document.getDocType().equals("png")) {
                target = new File(Directories.IMAGE_DIRECTORY, filePart.getSubmittedFileName());
            } else if (document.getDocType().equals("excel")) {
                target = new File(Directories.EXCEL_DIRECTORY, filePart.getSubmittedFileName());
            }

            // Déplacer le fichier
            inputStream = filePart.getInputStream();
            outputStream = new FileOutputStream(target);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // Mettre à jour le chemin du document
            document.setPath(target.getAbsolutePath());

            // Logique pour insérer les détails du document dans la base de données
            // docManager.insert(document); // Assurez-vous d'avoir implémenté cette méthode
            System.out.println(document);
            int numb = docManager.insert(document);
            if(numb > 0) {
           	 System.out.println("Document déplacé et inséré dans la base de données");

            	return 1 ;
            }else {
            	return -1 ;
            }
            

        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les exceptions en cas d'échec du déplacement du fichier
            return -1; // Ou tout autre code d'erreur que vous préférez
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    
    
    @Override
    public int updateDocument(int documentId, Part filePart, int idPatient) throws SQLException {
        String oldFilePath = docManager.selectPath(documentId);

        if (oldFilePath != null) {
            // Supprimer l'ancien fichier
            File fileToDelete = new File(oldFilePath);
            if (fileToDelete.exists() && fileToDelete.isFile()) {
                if (fileToDelete.delete()) {
                    System.out.println("File deleted: " + oldFilePath);
                } else {
                    System.out.println("Failed to delete file: " + oldFilePath);
                    return -1; // Échec de la suppression du fichier
                }
            } else {
                System.out.println("File not found: " + oldFilePath);
                return -1; // Fichier non trouvé
            }

            // Récupérer le document existant de la base de données
            Document existingDocument = (Document) docManager.select(documentId);
            if (existingDocument == null) {
                System.out.println("Document not found for ID: " + documentId);
                return -1; // Document non trouvé
            }
            
            System.out.println(existingDocument);
            existingDocument.setId(documentId); // Assurez-vous que l'ID est correct

            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                // Déterminer le répertoire cible en fonction du type de document
                File target = null;
                String submittedFileName = filePart.getSubmittedFileName();
                if (existingDocument.getDocType().equals("pdf")) {
                    target = new File(Directories.PDF_DIRECTORY, submittedFileName);
                } else if (existingDocument.getDocType().equals("png")) {
                    target = new File(Directories.IMAGE_DIRECTORY, submittedFileName);
                } else if (existingDocument.getDocType().equals("excel")) {
                    target = new File(Directories.EXCEL_DIRECTORY, submittedFileName);
                }

                // Vérifiez que target n'est pas null
                if (target == null) {
                    System.out.println("Invalid document type: " + existingDocument.getDocType());
                    return -1; // Type de document invalide
                }

                // Déplacer le fichier
                inputStream = filePart.getInputStream();
                outputStream = new FileOutputStream(target);

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                // Mettre à jour le chemin du document
                existingDocument.setPath(target.getAbsolutePath());

                // Mettre à jour le document dans la base de données avec le nouveau chemin
                docManager.updateDocument(documentId, existingDocument.getPath());
                return 1; // Succès

            } catch (IOException e) {
                e.printStackTrace();
                // Gérer les exceptions en cas d'échec du déplacement du fichier
                return -1; // Ou tout autre code d'erreur que vous préférez
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("File path not found for ID: " + documentId);
            return -1; // Chemin du fichier non trouvé
        }
    }



    public int deleteDocument(int docId) throws SQLException {
        // 1: Delete the file from the database : 
        String path = this.docManager.delete(docId) ; 
        // 2 : Delete file from the local drive : 
        File fileToDelete = new File(path) ; 

        if (fileToDelete.exists() && fileToDelete.isFile()) {
            if (fileToDelete.delete()) {
                System.err.println("File deleted successfully");
                return 1 ; 
            }
        }

        System.err.println("Failed to delete file ");
        return -1 ;
    }

	
    public List<Document> selecByPidAndType(int patientId, String docType){
    	 return this.docManager.selecByPidAndType(patientId, docType);
    }

	
}
