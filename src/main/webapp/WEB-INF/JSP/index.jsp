<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="../Fragments/bootstrap.jspf"></jsp:include>
<title>Enchères</title>
</head>
<body >
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
		<h1 class="text-center">Liste des enchères</h1>
		<fieldset class="container">
			<form action="${pageContext.request.contextPath}/encheres"
				method="post">
				<section class="row" style="margin: 5rem; padding: 6rem">
					<article class="col-md-8">
						<div class="form-group mb-4">
							<label for="filtre">Filtre :</label> <input class="col-md-7"
								type="text" id="filtre" name="filtre"
								placeholder="Entrez votre filtre">
						</div>
						<div class="form-group">
							<label for="categories">Catégories :</label>
							<div class="col-md-8">
								<select class="form-control" id="noCategorie" name="noCategorie">
									<option></option>
									<c:forEach var="category" items="${listeCategory}">
										<option value="${category.idCategory}">${category.libelle}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</article>
					<c:if test="${not empty userConnected}">
						<fieldset>
							<div class="row">
								<div class="col">
									<input type="radio" id="achats" name="affichage" value="achats"
										onclick="updateCheckboxes(this)"> <label for="achats">Achats</label>
<!-- Vérifier ici pourquoi les checkbox ne se desactivent plus après une
validation de recherche -->
								</div>
								<div class="col">
									<input type="radio" id="ventes" name="affichage" value="ventes"
										onclick="updateCheckboxes(this)" checked> <label
										for="ventes">Ventes</label>
								</div>
							</div>
							<div class="row">
								<div class="col">
									<div>
										<input type="checkbox" id="enchereOuvertes"
											name="enchereOuvertes"> <label for="enchereOuvertes">Enchères
											ouvertes</label>
									</div>
									<div>
										<input type="checkbox" id="mesEncheres" name="mesEncheres">
										<label for="mesEncheres">Mes enchères en cours</label>
									</div>
									<div>
										<input type="checkbox" id="encheresRemportees"
											name="encheresRemportees"> <label
											for="encheresRemportees">Mes enchères remportées</label>
									</div>
								</div>
								<div class="col">
									<div>
										<input type="checkbox" id="mesVentes" name="mesVentes">
										<label for="mesVentes">Mes ventes en cours</label>
									</div>
									<div>
										<input type="checkbox" id="ventesNonDebutees"
											name="ventesNonDebutees"> <label
											for="ventesNonDebutees">Ventes non débutées</label>
									</div>
									<div>
										<input type="checkbox" id="ventesTerminees"
											name="ventesTerminees"> <label for="ventesTerminees">Ventes
											terminées</label>
									</div>
								</div>
							</div>
						</fieldset>
					</c:if>
					<aside class="col-md-4 mt-4 mt-sm-4">
						<article class="form-group text-right">
							<button type="submit" class="btn btn-primary" name="rechercher">Rechercher</button>
						</article>
					</aside>
				</section>
			</form>
		</fieldset>
		<section class="container">
			<div class="row">
				<c:forEach var="article" items="${resultatsRecherche}"
					varStatus="status">
					<article class="col-md-4 mb-3">
						<div class="card h-100 bg-secondary text-white">
							<a href="${pageContext.request.contextPath}/afficheEnchere?idArticle=${article.idArticle}&vendeur=${article.utilisateur.pseudo}">
            			<img src="/ProjetJavaEEOrange/resources/photos/${article.image}" class="card-img-top img-fluid" alt="Photo de l'article"></a>
							<div class="card-body d-flex flex-column">
								<h5 class="card-title">${article.nomArticle}</h5>
								<p class="card-text">Prix actuel: ${article.miseAPrix}</p>
								<p class="card-text">Fin de l'enchère:
									${article.dateFinEncheres}</p>
								
								<p class="card-text">
									Vendeur : <a
										href="${pageContext.request.contextPath}/profil?idUtilisateur=${article.utilisateur.idUtilisateur}">${article.utilisateur.pseudo}</a>
								</p>
							</div>
						</div>
					</article>
				</c:forEach>
			</div>
		</section>
	</main>
	<jsp:include page="../Fragments/footer.jspf"></jsp:include>

	<!-- Javascript pour les animations dynamiques de bouttons -->
<jsp:include page="../Fragments/indexButton.jsp"></jsp:include>
</body>
</html>