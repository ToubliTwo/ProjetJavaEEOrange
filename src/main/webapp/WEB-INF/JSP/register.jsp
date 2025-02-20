<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
<jsp:include page="../Fragments/bootstrap.jspf"></jsp:include>
</head>
<body>
	<header class="custom-gradient">
<jsp:include page="../Fragments/logo.jsp"></jsp:include>
</header>
<main>
    <h1 class="text-center">Mon profil</h1>
    
    <!-- FIX ME le message d'erreur s'affiche constamment -->
<%--     <c:if test="${!empty error}">
            <div class="alert alert-danger" role="alert">
              ${error}
            </div>
        </c:if>
 --%>    <form method="post" class="form-group row m-5">
        <div class="col-6">
            <label for="idPseudo">Pseudo:</label>
            <input type="text" class="form-control" name="pseudo" id="idPseudo" required>

            <label for="idPrenom">Prenom:</label>
            <input type="text" class="form-control" name="prenom" id="idPrenom" required>

            <label for="idTelephone">Telephone:</label>
            <input type="tel" class="form-control" name="telephone" id="idTelephone" required>

            <label for="idCodePostal">Code Postal:</label>
            <input type="text" class="form-control" name="codePostal" id="idCodePostal" required>

            <label for="idPassword">Mot De Passe:</label>
            <input type="password" class="form-control" name="password" id="idPassword" required>
        </div>

        <div class="col-6">
            <label for="idNom">Nom:</label>
            <input type="text" class="form-control" name="nom" id="idNom" required>

            <label for="idEmail">Email:</label>
            <input type="email" class="form-control" name="email" id="idEmail" required>

            <label for="idRue">Rue:</label>
            <input type="text" class="form-control" name="rue" id="idRue" required>

            <label for="idVille">Ville:</label>
            <input type="text" class="form-control" name="ville" id="idVille" required>

            <label for="idConfirmPassword">Confirmation:</label>
            <input type="password" class="form-control" name="confirmPassword" id="idConfirmPassword" required>


        </div>

        <div class="col-12 text-center mt-5">
            <button type="submit" class="btn btn-primary" value="register">Créer</button>
           <a href="${pageContext.request.contextPath}/encheres" class="text-white"><button class="btn btn-primary">Retour</button></a>
        </div>
    </form>
</main>
<jsp:include page="../Fragments/footer.jspf"></jsp:include>
</body>
</html>