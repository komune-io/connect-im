# Test tasks that need a provisioned Keycloak to be running.
#
# Defined once and included by both libs.mk (which excludes them) and docker.mk (which
# runs them), so the two can never disagree about which suites need an environment.
INTEGRATION_TESTS = \
	:im-bdd:test \
	:im-script:im-script-space-config:test \
	:im-script:im-script-space-create:test
