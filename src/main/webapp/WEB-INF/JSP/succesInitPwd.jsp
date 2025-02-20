<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../Fragments/bootstrap.jspf"></jsp:include>
<meta charset="UTF-8">
<title>Résultat demande de réinitialisation</title>
</head>
<body>
	<header class="custom-gradient">
<jsp:include page="../Fragments/logo.jsp"></jsp:include>
</header>
<main>
 <c:choose>
  <c:when test="${!empty error}">
        <div class="alert alert-danger" role="alert">
          ${error}
        </div>
        </c:when>
        <c:otherwise>
         <h3 class="text-center">Votre mot de passe a été réinitialisé avec succès</h3>
         <p class="text-center">Veuillez vous connecter avec le mot de passe envoyé par mail. <br> 
         Pensez-ensuite à faire la modification dans votre profil. <br>
         </p>
         <a href="${pageContext.request.contextPath}/login" class="text-white"><button class="btn btn-primary">Retour</button></a>
</c:otherwise>
</c:choose>    
</main>
<jsp:include page="../Fragments/footer.jspf"></jsp:include>
</body>
</html>