<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="../Fragments/bootstrap.jspf"></jsp:include>
<title>Mes Enchères</title>
</head>
<body >
<div>
	<header class="custom-gradient">
		<section class="row">
			<article class="col-sm-2">
				<jsp:include page="../Fragments/logo.jsp" />
			</article>
			<article class="col-sm-10  text-right ">
				<jsp:include page="../Fragments/navigation.jsp"></jsp:include>
			</article>
		</section>
	</header>
	<main>
		<c:if test="${!empty error}">
			<div class="alert alert-danger" role="alert">${error}</div>
		</c:if>
		<h1 class="text-center">Liste des mes enchères</h1>
		<form method="post">
		<section class="container">
			<div class="row">
				<c:forEach var="article" items="${listeArticles}"
					varStatus="status">
					<article class="col-md-4 mb-3">
						<div class="card h-100 bg-secondary text-white">
						<input type="hidden" name="idArticle" value="${article.idArticle}" />
							<button formaction="${pageContext.request.contextPath}/afficheMesArticles?idArticle=${article.idArticle}" type="submit" style="border: none; background-color: transparent;">
            			<img src="/ProjetJavaEEOrange/resources/photos/${article.image}" class="card-img-top img-fluid" alt="Photo de l'article"></button>
							<div class="card-body d-flex flex-column">
								<h5 class="card-title">${article.nomArticle}</h5>
								<p class="card-text">Prix actuel: ${article.miseAPrix}</p>
									<p class="card-text">Début de l'enchère:
									${article.dateDebutEncheres}</p>
								<p class="card-text">Fin de l'enchère:
									${article.dateFinEncheres}</p>
							</div>
						</div>
					</article>
				</c:forEach>
			</div>
		</section>
		</form>
	</main>
</div>
	<jsp:include page="../Fragments/footer.jspf"></jsp:include>
</body>
</html>