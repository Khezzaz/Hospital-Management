package com.jee.web.actions;

import com.jee.Models.Doctor;
import com.jee.business.facade.ApplicationFacade;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SignUpAction extends Action {

	public SignUpAction(ApplicationFacade facade) {
		super(facade);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
	    // Récupérer les données du formulaire d'inscription
	    String firstName = request.getParameter("Firstname");
	    String lastName = request.getParameter("Lastname");
	    String email = request.getParameter("Email");
	    String password = request.getParameter("Password");

	    // Créer un nouvel objet Doctor avec les données récupérées
	    Doctor newDoctor = new Doctor(firstName, lastName, email, password);
	    System.out.println(newDoctor);

	    // Ajouter le nouveau docteur à la base de données
	    int res = facade.insertDoctor(newDoctor);
	    if (res == 1) {
	        return "login.html";
	    } else {
	        // Rediriger vers une page d'erreur
	        return "Eror404.html";
	    }
	}

}
