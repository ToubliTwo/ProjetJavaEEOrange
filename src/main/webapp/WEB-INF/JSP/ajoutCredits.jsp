<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ajout des Credits</title>
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
	<div class="container">
    	<div class="row">
	        <div class="col-md-12 text-center mt-4">
	            <h1 style="margin-bottom: 70px;">Ajout de crédits au compte</h1>
	        </div>
    	</div>
		<form method="post" action ="#" id="formulaire">
	    	<div style="display: flex; justify-content: space-between; width: 100%;">
		    	<button value="5" onclick="ajoutMontant(this.value)" style="flex: 1;margin: 5px;" class="btn btn-primary">5€</button>
				<button value="10" onclick="ajoutMontant(this.value)" style="flex: 1;margin: 5px;" class="btn btn-primary">10€</button>
				<button value="20" onclick="ajoutMontant(this.value)" style="flex: 1;margin: 5px;" class="btn btn-primary">20€</button>
	    	</div>
	    	<div style="display: flex; justify-content: space-between; width: 100%;">
		    	<button value="50" onclick="ajoutMontant(this.value)" style="flex: 1;margin: 5px;" class="btn btn-primary">50€</button>
				<button value="100" onclick="ajoutMontant(this.value)" style="flex: 1;margin: 5px;" class="btn btn-primary">100€</button>
				<button value="200" onclick="ajoutMontant(this.value)" style="flex: 1;margin: 5px;" class="btn btn-primary">200€</button>
	    	</div>
	    	<div class="input-group">
                <input type="number" id="montantP" class="form-control text-center" min=0 style="flex: 1;margin: 5px;" placeholder="Montant personnalisé">
                <button formaction="#" class="btn btn-primary" style="margin-right: 50px;flex: 1;margin: 5px;" id="validerMontantPerso" name="montant" onclick="montantPerso()">Valider montant personnalisé</button>
          	</div>
		</form>
    	
    	
    </div>

	<script>
	
	function ajoutMontant(montant) {
            var lienBouton = "${pageContext.request.contextPath}/ajoutCredits?montant=" + encodeURIComponent(montant);
            document.getElementById("formulaire").setAttribute('action', lienBouton);
    }
        function montantPerso() {
            var montant = parseInt(document.getElementById("montantP").value);

            // Vérifiez si l'input number est rempli
            if (montant > 0) {
                // Mettez à jour le lien du bouton avec la valeur de l'input number
                var lienBouton = "${pageContext.request.contextPath}/ajoutCredits?montant=" + encodeURIComponent(montant);
                document.getElementById("validerMontantPerso").setAttribute('formaction', lienBouton);
            } else {
                alert("Veuillez entrer un montant avant de cliquer sur le bouton.");
            }
        }
    </script>

	<jsp:include page="../Fragments/footer.jspf"></jsp:include>

	</main>
	<!-- Javascript pour les animations dynamiques de bouttons -->
	<jsp:include page="../Fragments/indexButton.jsp"></jsp:include>
</body>
</html>