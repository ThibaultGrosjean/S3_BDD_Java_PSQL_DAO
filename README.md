# S3_BDD_DAO_psql

## Contexte et modèle :

Le modèle relationnel ci-dessous modélise de façon simplifiée un loueur de voiture. Un véhicule est caractérisé, entres autres, par une marque (Peugeot, Renault, Mercedes...), un modèle (3008, Mégane, GLC...), un type (essence, diesel...) et une catégorie (berline, cabriolet, SUV...). Un client peut louer une voiture dans une agence et la rendre dans une autre agence. A chaque location est associé un contrat qui donne lieu à une facture.

Ville(idVille, nomVille, nombreHabitants)

Agence(idAgence, nbEmployés, #idVille)

Marque(idMarque, nomMarque)

Client(idClient, nomClient, adresseClient, codePostalClient, #idVille)

Véhicule(immatriculation, dateMiseEnCirculation, état, nbKilomètres, prixParJourDeLocation, #idMarque, id#idModele, #idCategorie, #idType, #idAgence)

Type(idType, libelléType)

Catégorie(idCatégorie, LibelléCatégorie)

Modèle(idModèle, dénomination, puissanceFiscale)

Contrat(idContrat, dateDeRetrait, dateDeRetour, kmRetrait, kmRetour, #idClient, #immatriculatidon,#idAgenceDeRetour)

Facture(idFacture, montant, #idContrat)

## Sujet

En utilisant JDBC et DAO, vous implanterez les traitements suivants :

1.Toutes les opérations CRUD pour chacune des tables du modèle.

2.La location d’une voiture par un client donné, à une date et pour une durée données. L’agence de retour devra être différente de l’agence de départ dans laquelle se situe le véhicule.

3.Le retour du véhicule loué précédemment.

4.L’établissement de la facture pour la location précédente.

5.Le chiffre d’affaire d’une agence donnée pour un mois donné.

6.Le nombre de véhicules pour chaque marque.

7.Le(s) client(s) ayant réalisé(s) le plus de locations pour une agence donnée et pour une année donnée.

8.Le chiffre d’affaire par catégorie.

9.Le chiffre d’affaire par type.

10.Le nombre de véhicule(s) de plus de 2 ans et de plus de 150 000 km pour chacune des agences.

11.Le chiffre d’affaire pour une année donnée pour chacune des agences.
