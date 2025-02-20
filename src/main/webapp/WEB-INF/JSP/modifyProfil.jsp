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
  <!-- FIX ME : message d'erreur s'affiche à l'accès à la page -->

    <h1 class="text-center">Mon profil - Modifier</h1>
    <c:if test="${!empty error}">
            <div class="alert alert-danger" role="alert">
              ${error}
            </div>
        </c:if>
        <c:if test="${!empty success}">
                    <div class="alert alert-success" role="alert">
                      ${success}
                    </div>
                </c:if>
    <c:if test="${!empty sessionScope.userConnected}">
    	<form method="post" class="form-group row m-5">
            <div class="col-6">
                <label for="idPseudo">Pseudo:</label>
                <input type="text" class="form-control" name="pseudo" id="idPseudo" value="${sessionScope.userConnected.pseudo}">

                <label for="idPrenom">Prenom:</label>
                <input type="text" class="form-control" name="prenom" id="idPrenom" value="${sessionScope.userConnected.prenom}">

                <label for="idTelephone">Telephone:</label>
                <input type="text" class="form-control" name="telephone" id="idTelephone" value="${sessionScope.userConnected.telephone}">

                <label for="idCodePostal">Code Postal:</label>
                <input type="text" class="form-control" name="codePostal" id="idCodePostal" value="${sessionScope.userConnected.codePostal}">

                <label for="idPassword">Mot De Passe Actuel:</label>
                <input type="password" class="form-control" name="password" id="idPassword">

                <label for="idPassword">Nouveau Mot De Passe:</label>
                <input type="password" class="form-control" name="newPassword" id="idPassword">
            </div>    
            <div class="col-6">
                <label for="idNom">Nom:</label>
                <input type="text" class="form-control" name="nom" id="idNom" value="${sessionScope.userConnected.nom}">

                <label for="idEmail">Email:</label>
                <input type="email" class="form-control" name="email" id="idEmail" value="${sessionScope.userConnected.email}">

                <label for="idRue">Rue:</label>
                <input type="text" class="form-control" name="rue" id="idRue" value="${sessionScope.userConnected.rue}">

                <label for="idVille">Ville:</label>
                <input type="text" class="form-control" name="ville" id="idVille" value="${sessionScope.userConnected.ville}">

                <label  for="idConfirmPassword">Confirmation:</label>
                <input type="password" class="form-control" name="confirmNewPassword" id="idConfirmPassword">

            <p>Crédit :  ${credit}</p>

            </div>
            
            <div class="col-12 text-center mt-5">
   				 <button type="submit" class="btn btn-primary" value="modify">Enregistrer</button>
    			<a href="${pageContext.request.contextPath}/confirmDeleteAccount" class="btn btn-primary">Supprimer mon compte</a>
			</div>
        </form>
    </c:if>
</main>
<jsp:include page="../Fragments/footer.jspf"></jsp:include>
</body>
</html>