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

INSERT INTO TYPE VALUES(default, 'Essence');
INSERT INTO TYPE VALUES(default, 'Diesel');
INSERT INTO TYPE VALUES(default, 'Electrique');
INSERT INTO TYPE VALUES(default, 'Hybride');

INSERT INTO CATEGORIE VALUES(default, 'Berline');
INSERT INTO CATEGORIE VALUES(default, 'Cabriolet');
INSERT INTO CATEGORIE VALUES(default, 'SUV');
INSERT INTO CATEGORIE VALUES(default, 'Citadines');
INSERT INTO CATEGORIE VALUES(default, 'Sport');


INSERT INTO MODELE VALUES(default, '5008',10 );
INSERT INTO MODELE VALUES(default, '3008', 8);
INSERT INTO MODELE VALUES(default, '2008', 11);
INSERT INTO MODELE VALUES(default, 'Mégane',8 );
INSERT INTO MODELE VALUES(default, 'GLC', 5);
INSERT INTO MODELE VALUES(default, 'Mustange',25);
INSERT INTO MODELE VALUES(default, 'JEEP', 12);


INSERT INTO VILLE VALUES(default,'Paris', 10784830);
INSERT INTO VILLE VALUES(default,'Lyon', 1659001);
INSERT INTO VILLE VALUES(default,'Lille', 232787);
INSERT INTO VILLE VALUES(default,'Bordeaux', 254436);

INSERT INTO AGENCE VALUES(default,12,1);
INSERT INTO AGENCE VALUES(default,6,2);
INSERT INTO AGENCE VALUES(default,12,3);
INSERT INTO AGENCE VALUES(default,8,4);

INSERT INTO MARQUE VALUES(default, 'Peugeot');
INSERT INTO MARQUE VALUES(default, 'Renault');
INSERT INTO MARQUE VALUES(default, 'Mercedes');
INSERT INTO MARQUE VALUES(default, 'Jeep');
INSERT INTO MARQUE VALUES(default, 'Dacia');

INSERT INTO CLIENT VALUES(default, 'Yassine' , '7 rue Claude Marvel' , 90000 , 1);
INSERT INTO CLIENT VALUES(default, 'Eude', '9 rue Debussy' , 57500 , 2);
INSERT INTO CLIENT VALUES(default, 'Thibault', '4 rue Lulli', 47602 , 2);
INSERT INTO CLIENT VALUES(default, 'Pierre', '14 faubourg de la cathedrale', 25420 , 3);
INSERT INTO CLIENT VALUES(default, 'Marie', '11 rue Massenet', 94500 , 4);

INSERT INTO VEHICULE VALUES(default, '20-04-2001' , 'Neuf' , 4000 , 1 , 1 , 1 , 1 , 1,2);
INSERT INTO VEHICULE VALUES(default, '14-11-2007', 'Neuf' , 756 , 2 , 3 , 2 , 2 , 2,2);
INSERT INTO VEHICULE VALUES(default, '19-09-1992', 'Peu abimée', 24615 , 5 , 3 , 2 , 2,3,3);
INSERT INTO VEHICULE VALUES(default, '29-08-2005', 'Abimé', 90754 , 3 , 3 , 4 , 3 , 3,4);
INSERT INTO VEHICULE VALUES(default, '04-05-2000', 'Presque Neuf', 14654 , 4, 2, 5 , 4 , 4,3);

INSERT INTO CONTRAT VALUES(default,'06/01/2020','12/01/2020', 212.0,261.0,1,1,2);
INSERT INTO CONTRAT VALUES(default,'06/01/2019','14/01/2020', 212.0,261.0,1,1,2);
INSERT INTO CONTRAT VALUES(default,'06/01/2020','13/04/2020', 2216.2,2500.0,2,2,3);
INSERT INTO CONTRAT VALUES(default,'06/08/2020','14/09/2020', 112.0,202.0,3,3,1);
INSERT INTO CONTRAT VALUES(default,'06/05/2020','13/06/2020', 1228.0,1333.3,4,4,4);
INSERT INTO CONTRAT VALUES(default,'07/02/2020','26/03/2020', 5613.5,6000.2,5,5,3);
INSERT INTO CONTRAT VALUES(default,'07/05/2020','30/06/2020', 1210.0,1234.5,5,2,1);

INSERT INTO FACTURE VALUES(default, 22,1);
INSERT INTO FACTURE VALUES(default, 102,2);
INSERT INTO FACTURE VALUES(default, 33,3);
INSERT INTO FACTURE VALUES(default, 461,4);
INSERT INTO FACTURE VALUES(default, 253,5);
INSERT INTO FACTURE VALUES(default, 302,6);


INSERT INTO VEHICULE VALUES(default, '19-09-1992', 'Peu abimée', 200000 , 5 , 3 , 2 , 1,1,1);
INSERT INTO VEHICULE VALUES(default, '29-08-2005', 'Abimé', 150001 , 3 , 3 , 4 , 3 , 1,1);
INSERT INTO VEHICULE VALUES(default, '6-12-2020', 'Presque Neuf', 300000 , 4, 2, 5 , 4 , 1,1);