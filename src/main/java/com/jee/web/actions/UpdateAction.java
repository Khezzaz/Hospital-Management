package com.jee.web.actions;

import java.io.IOException;
import java.util.List;

import com.jee.Models.Document;
import com.jee.business.facade.ApplicationFacade;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

public class UpdateAction extends Action {

    public UpdateAction(ApplicationFacade facade) {
        super(facade);
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            String patientIdStr = request.getParameter("patientId");
            if (patientIdStr == null || patientIdStr.isEmpty()) {
                return "error.html";
            }
            int patientId = Integer.parseInt(patientIdStr);

            String type = request.getParameter("options");

            List<Document> documents = facade.selectDocByIdAndType(patientId, type);

            request.setAttribute("documents", documents);
            request.setAttribute("patientId", patientId);
            request.setAttribute("type", type);

            return "AfterUpdate.jsp";
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "error.html";
        }
    }
}
