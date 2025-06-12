<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.jee.Models.Doctor"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Admins et Docteurs</title>
	<link rel="stylesheet" href="../css/Access.css">
</head>
<body>
    <div class="container">
        <h1> Admins</h1>
        <div class="grid">
            <div class="card">
                <img src="../img/5.jpg" alt="Admin 1">
                <h2>Zaynab Er-reghay</h2>
                <p>admin </p>
            </div>
            <div class="card">
                <img src="../img/2.jpg" alt="Admin 2">
                <h2>Fatima azzahra hasnaoui</h2>
                <p>admin</p>
            </div>
            <div class="card">
                <img src="../img/3.jpg" alt="Admin 3">
                <h2>Bilal lahfari</h2>
                <p>admin</p>
            </div>
            <div class="card">
                <img src="../img/3.jpg" alt="Admin 4">
                <h2>Ayoub khezzaz</h2>
                <p>admin</p>
            </div>
        </div>
    </div>

 <div class="container">
        <h1>Liste des Docteurs</h1>
        <%-- Affichage de la liste des docteurs si elle n'est pas null --%>
        <% List<Doctor> doctors = (List<Doctor>) request.getAttribute("doctors");
        if (doctors != null) { %>
            <form action="deletedoctor.do" method="post">
                <table>
                    <tr>
                        <th></th>
                        <th>Nom</th>
                        <th>Prénom</th>
                        <th>Login</th>
                        <th>Password</th>
                    </tr>
                    <% for (Doctor doctor : doctors) { %>
                        <tr>
                            <td><input type="radio" name="selectedDoctor" value="<%= doctor.getId() %>" required="required"></td>
                            <td><%= doctor.getName() %></td>
                            <td><%= doctor.getFirstname() %></td>
                            <td><%= doctor.getLogin() %></td>
                            <td><%= doctor.getPassword() %></td>
                            <input type="hidden" name="doctorId" id="doctorId_<%= doctor.getId() %>" value="">
                            
                        </tr>
                    <% } %>
                </table>
                <button type="submit">Delete Doctor</button>
            </form>
        <% } %>
    </div>
     <%-- Script JavaScript pour mettre à jour la valeur du champ de formulaire caché lors de la sélection --%>
    <script>
        var radios = document.querySelectorAll('input[type="radio"][name="selectedDoctor"]');
        radios.forEach(function(radio) {
            radio.addEventListener('change', function() {
                var doctorId = this.value;
                document.getElementById('doctorId_' + doctorId).value = doctorId;
            });
        });
    </script>
                <div style="text-align: center; margin-top: 20px;">
 <%-- Bouton pour lister les docteurs --%>
    <div style="text-align: center; margin-top: 20px;">
        <form action="ListerDoct.do" method="get">
            <button type="submit" class="button">Afficher les docteurs</button>
        </form>
    </div>                
                <button  class="button" >
                <a href="home2.html">home Page</a>
                </button>
                </div>
                </div>
                
                <script>
                    function toggleDoctors() {
                        var hiddenCards = document.querySelectorAll('#doctor-grid .hidden');
                        hiddenCards.forEach(function(card) {
                            if (card.style.display === "none" || card.style.display === "") {
                                card.style.display = "block";
                                setTimeout(function() {
                                    card.style.opacity = "1";
                                }, 10);
                            } else {
                                card.style.opacity = "0";
                                setTimeout(function() {
                                    card.style.display = "none";
                                }, 200);
                            }
                        });
                    }
                </script>
</body>
</html>       
