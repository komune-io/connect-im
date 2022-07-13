package city.smartb.im.organization.api.model.insee

data class InseeAddress(
    val complementAdresseEtablissement: String?,
    val numeroVoieEtablissement: String?,
    val indiceRepetitionEtablissement: String?,
    val typeVoieEtablissement: String?,
    val libelleVoieEtablissement: String?,
    val codePostalEtablissement: String?,
    val libelleCommuneEtablissement: String?,
    val libelleCommuneEtrangerEtablissement: String?,
    val distributionSpecialeEtablissement: String?,
    val codeCommuneEtablissement: String?,
    val codeCedexEtablissement: String?,
    val libelleCedexEtablissement: String?,
    val codePaysEtrangerEtablissement: String?,
    val libellePaysEtrangerEtablissement: String?
)

data class InseeAddress2(
    val complementAdresse2Etablissement: String?,
    val numeroVoie2Etablissement: String?,
    val indiceRepetition2Etablissement: String?,
    val typeVoie2Etablissement: String?,
    val libelleVoie2Etablissement: String?,
    val codePostal2Etablissement: String?,
    val libelleCommune2Etablissement: String?,
    val libelleCommuneEtranger2Etablissement: String?,
    val distributionSpeciale2Etablissement: String?,
    val codeCommune2Etablissement: String?,
    val codeCedex2Etablissement: String?,
    val libelleCedex2Etablissement: String?,
    val codePaysEtranger2Etablissement: String?,
    val libellePaysEtranger2Etablissement: String?
)
