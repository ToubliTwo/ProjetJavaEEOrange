
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand-lg navbar-light">
  <div class="container">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarResponsive">
      <ul class="navbar-nav">
       	<c:choose>
					<c:when test="${sessionScope.userConnected == null}">
						<li class="nav-item"><a class="nav-link text-white"
							href="${pageContext.request.contextPath}/login">Se connecter
								- S'inscrire</a></li>
					</c:when>
					<c:otherwise>
						<li class="nav_item nav-link text-white">Bienvenue <strong>${userConnected.prenom}</strong>
						<li class="nav-item"><a class="nav-link text-white"
							href="${pageContext.request.contextPath}/afficheMesArticles">Mes Enchères</a></li>
						<li class="nav-item"><a class="nav-link text-white"
							href="${pageContext.request.contextPath}/nouvelleVente">Vendre un article</a></li>
						<li class="nav-item"><a class="nav-link text-white" href="${pageContext.request.contextPath}/profil?idUtilisateur=${sessionScope.userConnected.idUtilisateur}">Profil de ${userConnected.pseudo}</a></li>
						
						<li class="nav-item"><a class="nav-link text-white" href="${pageContext.request.contextPath}/ajoutCredits">Ajout de Credits (${credit})</a></li>
						
						<li class="nav-item"><a class="nav-link text-white"
							href="${pageContext.request.contextPath}/logout">Déconnexion</a>
						</li>
					</c:otherwise>
			</c:choose>
      </ul>
    </div>
  </div>
</nav>


