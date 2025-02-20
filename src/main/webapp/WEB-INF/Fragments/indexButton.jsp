	<script>
	
		function updateCheckboxes(radio) {
			var achatsRadio = document.getElementById('achats');
			var ventesRadio = document.getElementById('ventes');

			var enchereOuvertes = document.getElementById('enchereOuvertes');
			var mesEncheres = document.getElementById('mesEncheres');
			var encheresRemportees = document.getElementById('encheresRemportees');
			var mesVentes = document.getElementById('mesVentes');
			var ventesNonDebutees = document.getElementById('ventesNonDebutees');
			var ventesTerminees = document.getElementById('ventesTerminees');

			if (radio === ventesRadio) {
				enchereOuvertes.disabled = true;
				mesEncheres.disabled = true;
				encheresRemportees.disabled = true;
				
				 enchereOuvertes.checked = false;
			        mesEncheres.checked = false;
			        encheresRemportees.checked = false;
		       

				mesVentes.disabled = false;
				ventesNonDebutees.disabled = false;
				ventesTerminees.disabled = false;
			} else if (radio === achatsRadio) {
				enchereOuvertes.disabled = false;
				mesEncheres.disabled = false;
				encheresRemportees.disabled = false;
				
				 mesVentes.checked = false;
			        ventesNonDebutees.checked = false;
			        ventesTerminees.checked = false;
			        
				mesVentes.disabled = true;
				ventesNonDebutees.disabled = true;
				ventesTerminees.disabled = true;
			}
		}
	</script>