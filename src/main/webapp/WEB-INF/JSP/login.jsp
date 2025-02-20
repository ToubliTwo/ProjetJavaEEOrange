<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
<jsp:include page="../Fragments/bootstrap.jspf"></jsp:include>
</head>
<body>
	<header class="custom-gradient">
<jsp:include page="../Fragments/logo.jsp"></jsp:include>
</header>
<main>
    <h1 class="text-center">Veuillez vous connecter</h1>
    <c:if test="${!empty error}">
        <div class="alert alert-danger" role="alert">
          ${error}
        </div>
    </c:if>
    <form method="post" class="form-group row m-2" style="padding:3em">
    <div class="col-12 text-center col-md-6 mx-auto mt-5">
        <label>Email:</label>
        <input class="form-control" type="email" id="email" name="email" ><br><br>
        <label>Password:</label>
        <input  class="form-control" type="password" id="password" name="password" ><br><br>
        <input formaction="${pageContext.request.contextPath}/login" type="submit" value="Se connecter" class="btn btn-primary">
        <button formaction="${pageContext.request.contextPath}/register" class="btn btn-primary">Créer un compte</button>
        </div>
    </form>
<!--bouton checkbox se souvenir de moi-->
<a href="${pageContext.request.contextPath}/initialiserMotDePasse" class="text-center d-block">Mot de passe oublié</a>
</main>
<jsp:include page="../Fragments/footer.jspf"></jsp:include>
</body>
</html>