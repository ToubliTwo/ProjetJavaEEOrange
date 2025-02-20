<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Confirm delete account</title>
<jsp:include page="../Fragments/bootstrap.jspf"></jsp:include>
</head>
<body>
	<header class="custom-gradient">
<jsp:include page="../Fragments/logo.jsp"></jsp:include>
</header>
<main>
<div class="card">
    <div class="card-header">
        <h1 class="text-center">Attention</h1>
    </div>
    <div class="card-body">
        <h5 class="card-title">Voulez-vous vraiment supprimer votre compte ?</h5>
        <p class="card-text"><strong class="text-danger">Attention !</strong> Si vous confirmez, il sera impossible de revenir en arrière.</p>
        <form method="post" action="${pageContext.request.contextPath}/deleteAccount">
          <div class="form-group" style="margin-bottom:3em">
                <label for="inputPassword">Mot de passe :</label>
                <input class="col-sm-3" type="password" class="form-control" id="inputPassword" name="password" required>
            </div>
            <div class="text-center" >
            <input type="hidden" name="userId" value="${sessionScope.userConnected.idUtilisateur}">
            <button style="margin-right:5em" type="submit" class="btn btn-primary">Valider</button>
            <a href="${pageContext.request.contextPath}/encheres" class="btn btn-secondary">Annuler</a>
            </div>
        </form>
    </div>
</div>
</main>
<jsp:include page="../Fragments/footer.jspf"></jsp:include>
</body>