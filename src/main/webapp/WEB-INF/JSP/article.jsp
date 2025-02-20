<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="../Fragments/bootstrap.jspf"></jsp:include>
<title>Nouvelle Vente</title>
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
		<c:if test="${!empty error}">
			<div class="alert alert-danger" role="alert">${error}</div>
		</c:if>
		<c:if test="${!empty success}">
			<div class="alert alert-success" role="alert">${success}</div>
		</c:if>
		<h1 class="mb-4 text-center">Ajouter un nouvel article</h1>
		<div class="container mt-7" style="margin: 5rem; padding: 5rem">
			<form method="post" enctype="multipart/form-data">
				<div class="form-group col-md-7 col-sm-7 col-xs-4 mb-3">
					<label for="nomArticle">Nom de l'article :</label> <input
						type="text" class="form-control" id="nomArticle" name="nomArticle"
						value="${!empty nomArticle ? nomArticle : ''}" required>
				</div>
				<div class="form-group col-md-7 col-sm-7 col-xs-4 mb-3">
					<label for="description">Description :</label>
					<textarea class="form-control" id="description" name="description"
						rows="3" required>${!empty description ? description : ''}</textarea>
				</div>
				<div class="form-row">
					<div class="form-group col-md-5 col-sm-6 col-xs-3 mb-3">
						<label for="idCategorie">Catégorie :</label> <select
							class="form-control" id="noCategorie" name="noCategorie">
							<option></option>
							<c:forEach var="category" items="${listeCategory}">
								<option value="${category.idCategory}">${category.libelle}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group col-md-6 col-xs-4 mb-3">
					<label for="photo">Photo :</label> <input type="file"
						class="form-control-file" id="photo" name="photo"
						accept="image/jpeg, ,image/jpg, image/png, image/gif, image/bmp, image/svg"
						required><br> <small class="form-text text-muted">Taille
						maximale : 8 Mo</small>
				</div>
				<div class="form-row">
					<div class="form-group col-md-2 col-sm-3 col-xs-2 mb-3">
						<label for="miseAPrix">Mise à prix :</label> <input type="number"
							class="form-control" id="miseAPrix" name="miseAPrix"
							value="${!empty miseAPrix ? miseAPrix : ''}" required>
					</div>
				</div>
				<div class="form-row">
					<div class="form-group col-md-4 col-sm-5 col-xs-2 mb-3">
						<label for="dateDebutEncheres">Date de début des enchères
							:</label> <input type="datetime-local" class="form-control"
							id="dateDebutEncheres" name="dateDebutEncheres"
							value="${!empty dateDebutEncheres ? dateDebutEncheres : ''}"
							required>
					</div>
					<div class="form-group col-md-4 col-sm-5 col-xs-2 mb-3">
						<label for="dateFinEncheres">Date de fin des enchères :</label> <input
							type="datetime-local" class="form-control" id="dateFinEncheres"
							name="dateFinEncheres"
							value="${!empty dateFinEncheres ? dateFinEncheres : ''}" required>
					</div>
				</div>
				<fieldset class="border rounded p-2">
					<legend class="float-none w-auto">Adresse de retrait</legend>
					<div class="form-group col-md11 col-sm-11 col-xs-4 mb-3">
						<label for="rue">Rue :</label> <input type="text"
							class="form-control" id="rue" name="rue">
					</div>
					<div class="form-row col-md-5 col-sm-5 col-xs-2 mb-3">
						<label for="codePostal">Code Postal :</label> <input type="text"
							class="form-control" id="codePostal" name="codePostal">
					</div>
					<div class="form-row col-md-7 col-sm-7 col-xs-4 mb-3">
						<label for="ville">Ville :</label> <input type="text"
							class="form-control" id="ville" name="ville">
					</div>
				</fieldset>
				<div class="form-group" style="margin-top: 2rem">
					<button
						formaction="${pageContext.request.contextPath}/nouvelleVente"
						type="submit" class="btn btn-primary">Ajouter l'article</button>
					<a href="${pageContext.request.contextPath}/encheres"
						class="btn btn-secondary">Annuler</a>
				</div>
			</form>
		</div>
		<script
			src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<script
			src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
		<script
			src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	</main>
	<jsp:include page="../Fragments/footer.jspf"></jsp:include>
</body>
</html>