<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../Fragments/bootstrap.jspf"></jsp:include>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<header class="custom-gradient">
<jsp:include page="../Fragments/logo.jsp"></jsp:include>
</header>
<main>
 <div class="container mt-5">
        <h1>Réinitialisation du mot de passe</h1>
        <form action="${pageContext.request.contextPath}/initialiserMotDePasse" id="resetPasswordForm" method="post" style="padding:3em">
            <div class="form-group">
                <label for="email">Adresse e-mail :</label>
                <input type="email" class="form-control" name="email" id="email" placeholder="Entrez votre adresse e-mail" required>
            </div>
            <button type="submit" class="btn btn-primary">Réinitialiser le mot de passe</button>
        </form>
    </div>
</main>
<jsp:include page="../Fragments/footer.jspf"></jsp:include>
</body>
</html>