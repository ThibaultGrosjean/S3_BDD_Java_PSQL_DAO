/* VILLE */

INSERT INTO VILLE(nomVille, nombreHabitants) VALUES ('aaaa' , 15) ;
SELECT * FROM VILLE ;
SELECT * FROM VILLE WHERE idVille = 1 ;
UPDATE VILLE set nomVille = 'bbbb', nombreHabitants = 15  WHERE idVille = 1 ;
DELETE FROM VILLE WHERE idVille = 1 ;

/* TYPE */

INSERT INTO TYPE(libelleType) VALUES ('libelleType') ;
SELECT * FROM TYPE;
SELECT * FROM TYPE WHERE idType = 1 ;
UPDATE TYPE set libelleType = 'lybelleTypeEdit' WHERE idType = 1 ;
DELETE FROM TYPE WHERE idType = 1 ;

/* CATEGORIE */

INSERT INTO CATEGORIE(libelleCategorie) VALUES ('libelleCategorie') ;
SELECT * FROM CATEGORIE;
SELECT * FROM CATEGORIE WHERE idCategorie = 1 ;
UPDATE CATEGORIE set libelleCategorie = 'libelleCategorieEdit' WHERE idCategorie = 1 ;
DELETE FROM CATEGORIE WHERE idCategorie = 1 ;

/* MARQUE */

INSERT INTO MARQUE(nomMarque) VALUES ('nomMarque') ;
SELECT * FROM MARQUE;
SELECT * FROM MARQUE WHERE idMarque = 1 ;
UPDATE MARQUE set nomMarque = 'nomMarqueEdit' WHERE idMarque = 1 ;
DELETE FROM MARQUE WHERE idMarque = 1 ;

/* AGENCE */

INSERT INTO AGENCE(nbEmployes, idVille) VALUES (1,2) ;
SELECT * FROM AGENCE;
SELECT * FROM AGENCE WHERE idAgence = 1 ;
UPDATE AGENCE set nbEmployes = 2 , idVille = 2 WHERE idAgence = 1 ;
DELETE FROM AGENCE WHERE idAgence = 1 ;

/* MODELE */

INSERT INTO MODELE(denomination, puissanceFiscale) VALUES ('Denomination',1) ;
SELECT * FROM MODELE;
SELECT * FROM MODELE WHERE idModele = 1 ;
UPDATE MODELE set denomination = 'DenominationEdit' , puissanceFiscale = 2 WHERE idModele = 1 ;
DELETE FROM MODELE WHERE idModele = 1 ;

/* CLIENT */

INSERT INTO CLIENT(nomClient, adresseClient, codePostalCLient, idVille) VALUES ('nom','adresse', 22222,1) ;
SELECT * FROM CLIENT;
SELECT * FROM CLIENT WHERE idClient = 1 ;
UPDATE CLIENT set nomClient = 'nomEdit' ,adresseClient = 'adresseEdit' ,codePostalCLient = 01010 , idVille = 2 WHERE idClient = 1 ;
DELETE FROM CLIENT WHERE idClient = 1 ;

/* VEHICULE */

INSERT INTO VEHICULE(dateMiseEnCirculation, etat, nbKilometres, prixParJourDeLocation, idMarque, idModele, idCategorie, idType, idAgence)
VALUES ('01-01-1970','etat',1,1,1,1,1,1,1) ;
SELECT * FROM VEHICULE;
SELECT * FROM VEHICULE WHERE immatriculation = 1 ;
UPDATE VEHICULE set dateMiseEnCirculation = '02-02-1970' ,etat = 'etatEdit' ,nbKilometres = 2 , prixParJourDeLocation = 2, idMarque = 2, idModele = 2, idCategorie = 2, idType = 2, idAgence = 2 WHERE immatriculation = 1 ;
DELETE FROM VEHICULE WHERE immatriculation = 1 ;

/* CONTRAT */

INSERT INTO CONTRAT(dateDeRetrait, dateDeRetour, kmRetrait, kmRetour, idClient, immatriculation, idAgenceDeRetour)
VALUES ('01-01-1970','02-02-1970', 1,2,1,1,1) ;
SELECT * FROM CONTRAT;
SELECT * FROM CONTRAT WHERE idContrat = 1 ;
UPDATE CONTRAT set dateDeRetrait = '02-02-1970', dateDeRetour = '03-03-1970', kmRetrait = 2, kmRetour = 3 , idClient = 2, immatriculation = 2 , idAgenceDeRetour = 2 WHERE idContrat = 1 ;
DELETE FROM CONTRAT WHERE idContrat = 1 ;

/* FACTURE */

INSERT INTO FACTURE(montant, idContrat) VALUES (1,1) ;
SELECT * FROM FACTURE;
SELECT * FROM FACTURE WHERE idFacture = 1 ;
UPDATE FACTURE set montant = 2 , idContrat = 2 WHERE idFacture = 1 ;
DELETE FROM FACTURE WHERE idFacture = 1 ;


/* 5 */

select SUM(f.montant) as ChiffreAffaire
from AGENCE a
         join CONTRAT C on a.idAgence = C.idAgenceDeRetour
         join FACTURE f on C.idContrat = f.idcontrat
where c.idAgenceDeRetour = 2
  AND EXTRACT(MONTH FROM dateDeRetour) = '1' AND EXTRACT(YEAR FROM dateDeRetour) = '2020';

/* 6 */

SELECT m.nomMarque, COUNT(v.immatriculation) AS nbVehiculeParMarque
FROM MARQUE AS m
         JOIN VEHICULE AS v
              ON m.idMarque = v.idMarque
GROUP BY m.idMarque ;

/* 8 */

select c2.libelleCategorie ,  SUM(f.montant) as ChiffreAffaire
from FACTURE f
         join CONTRAT C on C.idContrat = f.idContrat
         join VEHICULE V on C.immatriculation = V.immatriculation
         join categorie c2 on v.idCategorie = c2.idcategorie
group by c2.libelleCategorie;

/* 9 */
select t.libelleType ,  SUM(f.montant) as ChiffreAffaire
from FACTURE f
         join CONTRAT C on C.idContrat = f.idContrat
         join VEHICULE V on C.immatriculation = V.immatriculation
         join type t on V.idType = t.idtype
group by t.libelleType;

/* 10 */

SELECT COUNT(*)
FROM VEHICULE
WHERE EXTRACT(YEAR FROM dateMiseEnCirculation) < EXTRACT(YEAR FROM NOW()) - 2
  AND nbKilometres > 150000 ;

/* 11 */

SELECT a.idagence ,SUM(f.montant) AS ChiffreAffaire
FROM AGENCE a
         JOIN CONTRAT C ON a.idAgence = C.idAgenceDeRetour
         JOIN FACTURE f ON C.idContrat = f.idcontrat
         JOIN VILLE AS v ON a.idville = v.idville
WHERE EXTRACT(YEAR FROM dateDeRetour) = '2020'
GROUP BY a.idagence;