package com.jee.web.actions;

import java.sql.SQLException;
import java.util.List;

import com.jee.Models.Doctor;
import com.jee.business.facade.ApplicationFacade;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ListerDoctorsAction extends Action {

	public ListerDoctorsAction(ApplicationFacade facade) {
		super(facade);
		// TODO Auto-generated constructor stub
	}

	@Override
	 public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Récupérer la liste des docteurs depuis la facade
            List<Doctor> doctors = facade.selectAllDoctors();
            // Ajouter la liste des docteurs à la requête pour l'affichage dans la JSP
            request.setAttribute("doctors", doctors);
            // Retourner la vue JSP qui affiche la liste des docteurs
            return "access.jsp";
        } catch (SQLException e) {
            e.printStackTrace();
            // En cas d'erreur, retourner une vue d'erreur
            return "error.html";
        }
    }

}
