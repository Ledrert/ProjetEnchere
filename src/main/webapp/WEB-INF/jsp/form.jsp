<div>
	<form method="post" class="form">
		<div> 
			<label for="pseudo">Pseudo :</label>
			<input type="text" name="pseudo" id="pseudo" maxlength="30" autofocus required>
		</div>
		<div> 
			<label for="prenom">Prénom :</label>
			<input type="text" name="prenom" id="prenom" maxlength="30" required>
		</div>
		<div> 
			<label for="nom">Nom :</label>
			<input type="text" name="nom" id="nom" maxlength="30" required>
		</div>
		<div> 
			<label for="email">Email :</label>
			<input type="email" name="email" id="email" maxlength="50" 
				   placeholder="exemple@exemple.fr" required>
		</div>
		<div> 
			<label for="phone">Téléphone :</label>
			<input type="tel" id="phone" name="phone" pattern="[0-9]{2}-[0-9]{2}-[0-9]
			{2}-[0-9]{2}-[0-9]{2}" placeholder="XX.XX.XX.XX.XX" required>
		</div>
		<div> 
			<label for="adresse">Rue :</label>
			<input type="text" name="adresse" id="adresse" maxlength="50"  required>
		</div>
		<div> 
			<label for="codePostal">Code Postal :</label>
			<input type="text" name="codePostal" id="codePostal" maxlength="15" required>
		</div>
		<div> 
			<label for="ville">Ville :</label>
			<input type="text" name="ville" id="ville" maxlength="30" required>
		</div>		
		<div>
    		<label for="password">Mot de passe :</label>
			<input type="password" name="password" id="password" maxlength="30" required>
		</div>	
		<div> 
			<label for="confirmPassword">Confirmez le mot de passe :</label>
			<input type="password" name="confirmPassword" id="confirmPassword" maxlength="30" required>
		</div>
	</form>
</div>