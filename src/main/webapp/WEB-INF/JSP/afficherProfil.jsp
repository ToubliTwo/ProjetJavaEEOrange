<%@ page import="fr.eni.javaee.projetEE.bo.Utilisateur"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="../Fragments/bootstrap.jspf"></jsp:include>
</head>
<body>

	<header class="custom-gradient">

<jsp:include page="../Fragments/logo.jsp"></jsp:include>

</header>
 <main>
    <h1 class="text-center">Profil de l'utilisateur</h1>

<c:choose>
<c:when test="${utilisateur.idUtilisateur eq userConnected.idUtilisateur}">
<div class="container">
		
        <table style="margin-bottom: 3rem" class="table">
        <thead>${userConnected.pseudo}</thead>
        <tbody>
            <tr>
                <th scope="row">Prénom</th>
                <td>${userConnected.prenom}</td>
            </tr>
            <tr>
                <th scope="row">Nom</th>
                <td>${userConnected.nom}</td>
            </tr>
            <tr>
                <th scope="row">Email</th>
                <td>${userConnected.email}</td>
            </tr>
            <tr>
                <th scope="row">Téléphone</th>
                <td>${userConnected.telephone}</td>
            </tr>
            <tr>
                <th scope="row">Rue</th>
                <td>${userConnected.rue}</td>
            </tr>
            <tr>
                <th scope="row">Code postal</th>
                <td>${userConnected.codePostal}</td>
            </tr>
            <tr>
                <th scope="row">Ville</th>
                <td>${userConnected.ville}</td>
            </tr>
        </tbody>
    </table>
<div class="text-center" style="margin-bottom: 3rem">
    <a type="button" class="btn btn-primary" href="${pageContext.request.contextPath}/modify">Modifier</a>
</div>
      </div>
      </c:when>
<c:otherwise>
	<div class="container">
		
        <table style="margin-bottom: 100px" class="table">
        <thead ><strong>${utilisateur.pseudo}</strong></thead>
        <tbody>
            <tr>
                <th scope="row">Prénom</th>
                <td>${utilisateur.prenom}</td>
            </tr>
            <tr>
                <th scope="row">Nom</th>
                <td>${utilisateur.nom}</td>
            </tr>
            <tr>
                <th scope="row">Email</th>
                <td>${utilisateur.email}</td>
            </tr>
            <tr>
                <th scope="row">Téléphone</th>
                <td>${utilisateur.telephone}</td>
            </tr>
            <tr>
                <th scope="row">Rue</th>
                <td>${utilisateur.rue}</td>
            </tr>
            <tr>
                <th scope="row">Code postal</th>
                <td>${utilisateur.codePostal}</td>
            </tr>
            <tr>
                <th scope="row">Ville</th>
                <td>${utilisateur.ville}</td>
            </tr>
        </tbody>
    </table>
      </div>
</c:otherwise>
</c:choose>
 </main>
<jsp:include page="../Fragments/footer.jspf"></jsp:include>
</body>
</html>