<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.jee.Models.Document" %>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>princiaple page </title>
    <!-- CSS -->
    <link href="../css/delete2style.css" rel="stylesheet" />
    <!-- Boxicons CSS -->
    <link href="https://unpkg.com/boxicons@2.1.2/css/boxicons.min.css" rel="stylesheet">

    <link href="https://cdn.jsdelivr.net/boxicons/2.0.7/css/boxicons.min.css" rel="stylesheet">
    <link href="https://unpkg.com/boxicons@2.1.2/css/boxicons.min.css" rel="stylesheet">
    <link href="https://unpkg.com/boxicons@2.1.2/css/boxicons.min.css" rel="stylesheet">

</head>
<body>
    <nav>
        
        <div class="logo">
          <i class="bx bx-menu menu-icon"></i>
          <span class="logo-name">Docshere</span>
        </div>
        <div class="sidebar">
          <div class="logo">
            <i class=" bx bx-menu menu-icon"></i>
            <span class="logo-name">Docshere</span>
          </div>
  
          <div class="sidebar-content">
            <ul class="lists">
              <li class="list">
                <a href="home2.html" class="nav-link">
                  <i class="bx bx-home-alt icon"></i>
                  <span class="link">Home</span>
                </a>
              </li>
              <li class="list">
                <a href="insert.html" class="nav-link">
                  <i class="bx bxs-file"></i>
                  <span class="link">Insert Document</span>
                </a>
              </li>
              <li class="list">
                <a href="select.html" class="nav-link">
                  <i class="bx bxs-file selected-icon"></i>
                  <span class="link">Select Document</span>
                </a>
              </li>
              <li class="list">
                <a href="update.html" class="nav-link">
                  <i class="bx bx-edit"></i>
                  <span class="link">Update Document</span>
                </a>
              </li>
              <li class="list">
                <a href="delete.html" class="nav-link">
                  <i class="bx bx-trash"></i>
                  <span class="link">Delete Document</span>
                </a>
              </li>
          
              <li class="list">
                <a href="access.jsp" class="nav-link">
                  <i class="bx bx-folder-open icon"></i>
                  <span class="link">Access Management</span>
                </a>
              </li>
            </ul>
  
            <div class="bottom-cotent">
              <li class="list">
                <a href="#" class="nav-link">
                  <i class="bx bx-cog icon"></i>
                  <span class="link">Settings</span>
                </a>
              </li>
              <li class="list">
                <a href="home.html" class="nav-link">
                  <i class="bx bx-log-out icon"></i>
                  <span class="link">Logout</span>
                </a>
              </li>
            </div>
          </div>
        </div>
      </nav>
    <div class="container">
        <h1>Liste des Documents</h1>
        <p>Path: <%= request.getAttribute("path") %></p>
<table>
    <tr>
        <th>Nom du Document</th>
        <th>Date de cr�ation</th>
        <th>Action</th>
    </tr>
    <% List<Document> documents = (List<Document>) request.getAttribute("documents");
    if (documents != null) {
        for (Document document : documents) { %>
            <tr>
                <td><%= document.getDescription() %></td>
                <td><%= document.getToc() %></td>
                <td>
                    <form action="AfterUpdateAction.do" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="documentId" value="<%= document.getId() %>">
                        <input type="hidden" name="patientId" value="<%= request.getAttribute("patientId") %>">
                        <input type="hidden" name="type" value="<%= request.getAttribute("type") %>">
                        <label for="fileUpload">Select a file:</label>
                        <input type="file" id="fileUpload" name="fileUpload" required>
                        <button type="submit">Update</button>
                    </form>
                </td>
            </tr>
        <% }
    } %>
</table>

    </div>
    <section class="overlay"></section>

    <script>
        const navBar = document.querySelector("nav"),
       menuBtns = document.querySelectorAll(".menu-icon"),
       overlay = document.querySelector(".overlay");

     menuBtns.forEach((menuBtn) => {
       menuBtn.addEventListener("click", () => {
         navBar.classList.toggle("open");
       });
     });

     overlay.addEventListener("click", () => {
       navBar.classList.remove("open");
     });
    </script>
  </body>
</html>

