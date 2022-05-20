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
