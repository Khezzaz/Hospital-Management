package com.jee.web.actions;

import java.io.IOException;
import java.sql.SQLException;

import com.jee.business.facade.ApplicationFacade;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
public class AfterUpdateAction extends Action {

    public AfterUpdateAction(ApplicationFacade facade) {
        super(facade);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            int documentId = Integer.parseInt(request.getParameter("documentId"));
            int patientId = Integer.parseInt(request.getParameter("patientId"));
            Part filePart = request.getPart("fileUpload");

            if (filePart == null) {
                System.out.println("File part is null");
                return "error.html"; // Erreur lors de la récupération du fichier
            }
            System.out.println(filePart.toString());

            int result = facade.updateDocument(documentId, filePart, patientId);
            if (result > 0) {
                return "succes.html"; // Retourner la page de succès si l'opération réussit
            } else {
                return "error.html"; // Retourner la page d'erreur si l'opération échoue
            }
        } catch (NumberFormatException | IOException | ServletException | SQLException e) {
            e.printStackTrace();
            return "error.html"; // Retourner la page d'erreur en cas d'exception
        }
    }
}
