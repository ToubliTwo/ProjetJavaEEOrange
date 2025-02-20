<%@ page import="fr.eni.javaee.projetEE.bo.ArticleVendu"%>
<%@ page import="fr.eni.javaee.projetEE.bo.Retrait"%>
<%@ page import="fr.eni.javaee.projetEE.bo.Enchere"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Enchère</title>
<jsp:include page="../Fragments/bootstrap.jspf"></jsp:include>
</head>
<body>
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
	<div id="creditMessage" style="display: none;"
		class="alert alert-danger" role="alert"></div>

	<div class="container">
		<div class="row">
			<div class="col-md-12 text-center mt-4">
				<h1 style="margin-bottom: 70px;">Détail vente</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<img
					src="/ProjetJavaEEOrange/resources/photos/${articleVendu.image}"
					class="card-img-top img-fluid" alt="Photo de l'article">
			</div>
			<div class="col-md-6">
				<form method="post" style="padding:4em;margin-bottom:2em">
					<table class="table">
						<thead>
							<tr>
								<th colspan="2"><strong>${articleVendu.nomArticle}</strong></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th scope="row">Description</th>
								<td>${articleVendu.description}</td>
							</tr>
							<tr>
								<th scope="row">Catégorie</th>
								<c:forEach var="category" items="${listeCategory}">
									<c:if test="${category.idCategory eq articleVendu.idCategory}">
										<td>${category.libelle}</td>
									</c:if>
								</c:forEach>
							</tr>
							<tr>
								<th scope="row">Meilleure offre</th>
								<td><input type="text" readonly style="border: none;"
									name="prixVente" id="prixVente"
									value="${enchere.montantEnchere != null ? enchere.montantEnchere : articleVendu.miseAPrix}">
									<c:if test="${!empty enchere }">
                        	 par ${userPseudo}
                         </c:if></td>
							</tr>
							<tr>
								<th scope="row">Mise à prix</th>
								<td>${articleVendu.miseAPrix}</td>
							</tr>
							<tr>
								<th scope="row">Fin de l'enchère</th>
								<td><fmt:setLocale value="fr_FR" /> <fmt:formatDate
										value="${articleVendu.dateFinEncheres}"
										pattern="dd MMMM yyyy à HH'h'mm" /></td>
							</tr>
							<tr>
								<th scope="row">Retrait</th>
								<td>${retrait.rue}${retrait.codePostal}${retrait.ville}</td>
							</tr>
							<c:choose>
								<c:when test="${not empty vendeur}">
									<tr>
										<th scope="row">Vendeur</th>
										<td>${vendeur}</td>
									</tr>
								</c:when>
								<c:otherwise>
									<tr>
										<th scope="row">Vendeur</th>
										<td>Aucune information</td>
									</tr>
								</c:otherwise>
							</c:choose>
							<c:if test="${vendeur ne sessionScope.userConnected.pseudo}">
								<tr>
									<th scope="row">Ma proposition</th>
									<td>
										<div class="input-group">
											<input type="text" style="display: none;" name="curentUserId"
												id="curentUserId"
												value="${sessionScope.userConnected.idUtilisateur}">
											<input type="text" style="display: none;"
												name="curentUserCredit" id="curentUserCredit"
												value="${credit}"> <input type="text"
												style="display: none;" name="idArticle" id="idArticle"
												value="${articleVendu.idArticle}"> <input
												type="text" style="display: none;" name="idUserPrevious"
												id="idUserPrevious" value="${enchere.idUtilisateur}">
											<div class="input-group">
												<div class="input-group-prepend">
													<button id="decreaseButton" class="btn btn-danger"
														type="button" onclick="decreasePrice()"
														>-</button>
												</div>
												<input type="number" id="encherPoposition"
													name="encherPoposition" class="form-control text-center"
													value="${enchere.montantEnchere != null ? enchere.montantEnchere : articleVendu.miseAPrix}"
													min="${enchere.montantEnchere != null ? enchere.montantEnchere : articleVendu.miseAPrix}"
													step="1" readonly>
												<div class="input-group-append" style="margin-right: 50px;">
													<button id="increaseButton" class="btn btn-success"
														type="button" onclick="increasePrice()" >+</button>
												</div>
												<div class="input-group-append">
													<button
														formaction="${pageContext.request.contextPath}/afficheEnchere"
														id="enchereButton" class="btn btn-primary"
														style="margin-right: 50px;" type="submit"
														>Enchérir</button>
												</div>
											</div>
										</div>
									</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	<!-- TO DO Mettre le javascript dans un fichier annexe et l'appeler -->
	<script>
    function increasePrice() {
        var miseAPrixField = document.getElementById("encherPoposition");
        var currentPrice = parseInt(miseAPrixField.value);
        miseAPrixField.value = currentPrice + 1;
        checkButtonsState();
        checkCretits();
    }

    function decreasePrice() {
        var miseAPrixField = document.getElementById("encherPoposition");
        var currentPrice = parseInt(miseAPrixField.value);
        var minPrice = parseInt(miseAPrixField.min);
        if (currentPrice - 1 >= minPrice) {
            miseAPrixField.value = currentPrice - 1;
            checkButtonsState();
            checkCretits();
        }
    }

    function checkButtonsState() {
        var miseAPrixField = document.getElementById("encherPoposition");
        var currentPrice = parseInt(miseAPrixField.value);
        var enchereButton = document.getElementById("enchereButton");
        var decreaseButton = document.getElementById("decreaseButton");

        if (currentPrice === ${articleVendu.miseAPrix}) {
            enchereButton.disabled = true;
            decreaseButton.disabled = true;
        } else {
            enchereButton.disabled = false;
            decreaseButton.disabled = false;
        }
    }
    
    function checkCretits() {
    	var miseAPrixField = document.getElementById("encherPoposition");
    	var currentPrice = parseInt(miseAPrixField.value);
    	var addButton = document.getElementById("increaseButton");
    	var userCredit = ${credit};
    	var creditMessage = document.getElementById("creditMessage");
    	
    	if (userCredit <= currentPrice){
    		addButton.disabled = true;
    		creditMessage.style.display = "block";
    		document.getElementById("creditMessage").innerText = "Vous avez " + userCredit + " crédits!";
    	} else {
    		addButton.disabled = false;
    		creditMessage.style.display = "none";
    	}
    }

    window.onload = function () {
        checkButtonsState();
        checkCretits();
    };
	</script>
	</main>
	<jsp:include page="../Fragments/footer.jspf"></jsp:include>
</body>
</html>