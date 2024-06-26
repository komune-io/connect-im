package io.komune.im.f2.organization.lib.model.insee

data class InseeOrganization(
    val siren: String?,
    val nic: String?,
    val siret: String?,
    val statutDiffusionEtablissement: String?,
    val dateCreationEtablissement: String?,
    val trancheEffectifsEtablissement: String?,
    val anneeEffectifsEtablissement: String?,
    val activitePrincipaleRegistreMetiersEtablissement: String?,
    val dateDernierTraitementEtablissement: String?,
    val etablissementSiege: Boolean?,
    val nombrePeriodesEtablissement: Int?,
    val uniteLegale: InseeLegalUnit?,
    val adresseEtablissement: InseeAddress?,
    val adresse2Etablissement: InseeAddress2?,
    val periodesEtablissement: List<InseePeriod>?
)

/*
    "siren": "844880963",
    "nic": "00013",
    "siret": "84488096300013",
    "statutDiffusionEtablissement": "O",
    "dateCreationEtablissement": "2018-12-11",
    "trancheEffectifsEtablissement": "03",
    "anneeEffectifsEtablissement": "2019",
    "activitePrincipaleRegistreMetiersEtablissement": null,
    "dateDernierTraitementEtablissement": "2021-10-27T09:46:22",
    "etablissementSiege": true,
    "nombrePeriodesEtablissement": 2,
    "uniteLegale": {
      "etatAdministratifUniteLegale": "A",
      "statutDiffusionUniteLegale": "O",
      "dateCreationUniteLegale": "2018-12-11",
      "categorieJuridiqueUniteLegale": "5710",
      "denominationUniteLegale": "SMART B",
      "sigleUniteLegale": null,
      "denominationUsuelle1UniteLegale": null,
      "denominationUsuelle2UniteLegale": null,
      "denominationUsuelle3UniteLegale": null,
      "sexeUniteLegale": null,
      "nomUniteLegale": null,
      "nomUsageUniteLegale": null,
      "prenom1UniteLegale": null,
      "prenom2UniteLegale": null,
      "prenom3UniteLegale": null,
      "prenom4UniteLegale": null,
      "prenomUsuelUniteLegale": null,
      "pseudonymeUniteLegale": null,
      "activitePrincipaleUniteLegale": "70.22Z",
      "nomenclatureActivitePrincipaleUniteLegale": "NAFRev2",
      "identifiantAssociationUniteLegale": null,
      "economieSocialeSolidaireUniteLegale": "N",
      "caractereEmployeurUniteLegale": "O",
      "trancheEffectifsUniteLegale": "03",
      "anneeEffectifsUniteLegale": "2019",
      "nicSiegeUniteLegale": "00013",
      "dateDernierTraitementUniteLegale": "2021-10-27T09:46:22",
      "categorieEntreprise": "PME",
      "anneeCategorieEntreprise": "2019"
    },
    "adresseEtablissement": {
      "complementAdresseEtablissement": null,
      "numeroVoieEtablissement": "2",
      "indiceRepetitionEtablissement": null,
      "typeVoieEtablissement": "RUE",
      "libelleVoieEtablissement": "DU PAVILLON",
      "codePostalEtablissement": "34000",
      "libelleCommuneEtablissement": "MONTPELLIER",
      "libelleCommuneEtrangerEtablissement": null,
      "distributionSpecialeEtablissement": null,
      "codeCommuneEtablissement": "34172",
      "codeCedexEtablissement": null,
      "libelleCedexEtablissement": null,
      "codePaysEtrangerEtablissement": null,
      "libellePaysEtrangerEtablissement": null
    },
    "adresse2Etablissement": {
      "complementAdresse2Etablissement": null,
      "numeroVoie2Etablissement": null,
      "indiceRepetition2Etablissement": null,
      "typeVoie2Etablissement": null,
      "libelleVoie2Etablissement": null,
      "codePostal2Etablissement": null,
      "libelleCommune2Etablissement": null,
      "libelleCommuneEtranger2Etablissement": null,
      "distributionSpeciale2Etablissement": null,
      "codeCommune2Etablissement": null,
      "codeCedex2Etablissement": null,
      "libelleCedex2Etablissement": null,
      "codePaysEtranger2Etablissement": null,
      "libellePaysEtranger2Etablissement": null
    },
    "periodesEtablissement": [
      {
        "dateFin": null,
        "dateDebut": "2019-07-01",
        "etatAdministratifEtablissement": "A",
        "changementEtatAdministratifEtablissement": false,
        "enseigne1Etablissement": null,
        "enseigne2Etablissement": null,
        "enseigne3Etablissement": null,
        "changementEnseigneEtablissement": false,
        "denominationUsuelleEtablissement": null,
        "changementDenominationUsuelleEtablissement": false,
        "activitePrincipaleEtablissement": "70.22Z",
        "nomenclatureActivitePrincipaleEtablissement": "NAFRev2",
        "changementActivitePrincipaleEtablissement": false,
        "caractereEmployeurEtablissement": "O",
        "changementCaractereEmployeurEtablissement": true
      },
      {
        "dateFin": "2019-06-30",
        "dateDebut": "2018-12-11",
        "etatAdministratifEtablissement": "A",
        "changementEtatAdministratifEtablissement": false,
        "enseigne1Etablissement": null,
        "enseigne2Etablissement": null,
        "enseigne3Etablissement": null,
        "changementEnseigneEtablissement": false,
        "denominationUsuelleEtablissement": null,
        "changementDenominationUsuelleEtablissement": false,
        "activitePrincipaleEtablissement": "70.22Z",
        "nomenclatureActivitePrincipaleEtablissement": "NAFRev2",
        "changementActivitePrincipaleEtablissement": false,
        "caractereEmployeurEtablissement": "N",
        "changementCaractereEmployeurEtablissement": false
      }
    ]
  }
 */
