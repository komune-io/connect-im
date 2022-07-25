package city.smartb.im.organization.lib.model.insee

data class InseePeriod(
    val dateFin: String?,
    val dateDebut: String,
    val etatAdministratifEtablissement: String,
    val changementEtatAdministratifEtablissement: Boolean,
    val enseigne1Etablissement: String?,
    val enseigne2Etablissement: String?,
    val enseigne3Etablissement: String?,
    val changementEnseigneEtablissement: Boolean,
    val denominationUsuelleEtablissement: String?,
    val changementDenominationUsuelleEtablissement: Boolean,
    val activitePrincipaleEtablissement: String,
    val nomenclatureActivitePrincipaleEtablissement: String,
    val changementActivitePrincipaleEtablissement: Boolean,
    val caractereEmployeurEtablissement: String,
    val changementCaractereEmployeurEtablissement: Boolean
)
