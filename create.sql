DROP TABLE IF EXISTS FACTURE;
DROP TABLE IF EXISTS CONTRAT;
DROP TABLE IF EXISTS VEHICULE;
DROP TABLE IF EXISTS MODELE;
DROP TABLE IF EXISTS CATEGORIE;
DROP TABLE IF EXISTS TYPE;
DROP TABLE IF EXISTS CLIENT;
DROP TABLE IF EXISTS AGENCE;
DROP TABLE IF EXISTS VILLE;
DROP TABLE IF EXISTS MARQUE;


CREATE TABLE VILLE
(
    idVille SERIAL,
    nomVille VARCHAR(255),
    nombreHabitants INT,
    CONSTRAINT VILLE_PK primary key(idVille)
);

CREATE TABLE TYPE (
    idType SERIAL,
    libelleType VARCHAR (255),
    CONSTRAINT TYPE_PK PRIMARY KEY (idType)
);

CREATE TABLE CATEGORIE (
    idCategorie SERIAL,
    libelleCategorie VARCHAR (255),
    CONSTRAINT CATEGORIE_PK PRIMARY KEY(idCategorie)
);

CREATE TABLE MARQUE
(
    idMarque SERIAL,
    nomMarque VARCHAR(255),
    CONSTRAINT MARQUE_PK primary key(idMarque)
);

CREATE TABLE AGENCE
(
    idAgence SERIAL,
    nbEmployes VARCHAR(255),
    idVille INT,
    CONSTRAINT AGENCE_PK primary key(idAgence),
    CONSTRAINT AGENCE_VILLE_FK FOREIGN KEY (idVille) REFERENCES VILLE(idVille)
);

CREATE TABLE MODELE (
    idModele SERIAL,
    denomination VARCHAR (255),
    puissanceFiscale INT,
    CONSTRAINT MODELE_PK PRIMARY KEY (idModele)
);

CREATE TABLE CLIENT
(
    idClient SERIAL,
    nomClient VARCHAR(255),
    adresseClient VARCHAR(255),
    codePostalCLient INT,
    idVille INT,
    CONSTRAINT CLIENT_PK primary key(idClient),
    CONSTRAINT CLIENT_VILLE_FK FOREIGN KEY (idVille) REFERENCES VILLE(idVille)
);

CREATE TABLE VEHICULE
(
    immatriculation SERIAL,
    dateMiseEnCirculation DATE,
    etat varchar(255),
    nbKilometres INT,
    prixParJourDeLocation INT,
    idMarque INT,
    idModele INT,
    idCategorie INT,
    idType INT,
    idAgence INT,
    CONSTRAINT VEHICULE_PK PRIMARY KEY(immatriculation),
    CONSTRAINT VEHICULE_MARQUE_FK FOREIGN KEY (idMarque)REFERENCES MARQUE(idMarque),
    CONSTRAINT VEHICULE_MODELE_FK FOREIGN KEY (idModele)REFERENCES MODELE(idModele),
    CONSTRAINT VEHICULE_CATEGORIE_FK FOREIGN KEY (idCategorie)REFERENCES CATEGORIE(idCategorie),
    CONSTRAINT VEHICULE_TYPE_FK FOREIGN KEY (idType)REFERENCES TYPE(idType),
    CONSTRAINT VEHICULE_AGENCE_FK FOREIGN KEY (idAgence)REFERENCES AGENCE(idAgence)
);

CREATE TABLE CONTRAT (
    idContrat SERIAL,
    dateDeRetrait DATE,
    dateDeRetour DATE,
    kmRetrait INT,
    kmRetour INT,
    idClient INT,
    immatriculation INT,
    idAgenceDeRetour INT,
    CONSTRAINT CONTRAT_PK PRIMARY KEY (idContrat),
    CONSTRAINT CONTRAT_CLIENT_FK FOREIGN KEY(idClient) REFERENCES CLIENT(idClient),
    CONSTRAINT CONTRAT_VEHICULE_FK FOREIGN KEY(immatriculation) REFERENCES VEHICULE(immatriculation),
    CONSTRAINT CONTRAT_AGENCE_FK FOREIGN KEY(idAgenceDeRetour) REFERENCES AGENCE(idAgence)
);

CREATE TABLE FACTURE (
    idFacture SERIAL,
    montant INT,
    idContrat INT,
    CONSTRAINT FACTURE_PK PRIMARY KEY (idFacture),
    CONSTRAINT FACTURE_CONTRAT_FK FOREIGN KEY(idContrat) REFERENCES CONTRAT(idContrat)
);