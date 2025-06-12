package com.jee.web.actions;

import java.sql.SQLException;

import com.jee.business.facade.ApplicationFacade;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteDoctorAction extends Action {

	public DeleteDoctorAction(ApplicationFacade facade) {
		super(facade);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
        // Récupérer l'ID du docteur à supprimer à partir des paramètres de la requête
        int doctorId = Integer.parseInt(request.getParameter("doctorId"));
        
        try {
            // Supprimer le docteur à l'aide de la façade
            facade.deleteDoctor(doctorId);
            
            // Rediriger vers une page de confirmation ou une autre page appropriée
            return "succes.html";
        } catch (SQLException e) {
            // Gérer l'erreur de suppression du docteur
            e.printStackTrace();
            // Rediriger vers une page d'erreur
            return "error.html";
        }
    }
}