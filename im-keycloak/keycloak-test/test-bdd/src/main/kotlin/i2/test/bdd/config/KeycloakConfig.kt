package i2.test.bdd.config

object KeycloakConfig {
	val url: String
		get() {
			return "http://keycloak-it:8080".orIfGitlabEnv("KEYCLOAK_URL")
		}

	object Admin {


		val username: String
			get() {
				return "admin".orIfGitlabEnv("KEYCLOAK_ADMIN_USERNAME")
			}

		val password: String
			get() {
				return "admin".orIfGitlabEnv("KEYCLOAK_ADMIN_PASSWORD")
			}

		val clientId: String
			get() {
				return "admin-cli".orIfGitlabEnv("KEYCLOAK_ADMIN_CLIENT_ID")
			}
	}

	val mustStartDocker: Boolean
		get() = !isGitlab()

	fun String.orIfGitlabEnv(value: String): String {
		return if (isGitlab()) {
			println("//////////////////////////////////////////////")
			println("//////////////////////////////////////////////")
			println("//////////////////////////////////////////////")
			getEnv(value)
		} else {
			this
		}
	}

	private fun isGitlab() = getEnv("SPRING_PROFILES_ACTIVE") == "gitlab"
	private fun getEnv(value: String): String {
		val envValue = System.getenv(value)
		return if (envValue != null) {
			envValue
		} else {
			println("Env parameter[$value] is null")
			""
		}
	}
}
