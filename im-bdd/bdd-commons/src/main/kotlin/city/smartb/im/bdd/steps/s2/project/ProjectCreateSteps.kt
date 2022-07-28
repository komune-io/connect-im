//package city.smartb.im.bdd.steps.s2.project
//
//import city.smartb.im.bdd.CucumberStepsDefinition
//import city.smartb.im.bdd.assertion.AssertionBdd
//import city.smartb.im.bdd.assertion.project
//import city.smartb.im.bdd.data.TestContextKey
//import city.smartb.im.s2.project.api.ProjectAggregateService
//import city.smartb.im.s2.project.api.entity.ProjectRepository
//import city.smartb.im.s2.project.domain.automate.ProjectState
//import city.smartb.im.s2.project.domain.command.ProjectCreateCommand
//import io.cucumber.datatable.DataTable
//import io.cucumber.java8.En
//import org.springframework.beans.factory.annotation.Autowired
//
//class ProjectCreateSteps: En, CucumberStepsDefinition() {
//
//    @Autowired
//    private lateinit var projectAggregateService: ProjectAggregateService
//
//    @Autowired
//    private lateinit var projectRepository: ProjectRepository
//
//    private lateinit var command: ProjectCreateCommand
//
//    init {
//        DataTableType(::projectInitParams)
//
//        When("I create a project") {
//            step {
//                createProject(projectInitParams(null))
//            }
//        }
//
//        When("I create a project:") { params: ProjectInitParams ->
//            step {
//                createProject(params)
//            }
//        }
//
//        Given("A project is created") {
//            step {
//                createProject(projectInitParams(null))
//            }
//        }
//
//        Given("A project is created:") { params: ProjectInitParams ->
//            step {
//                createProject(params)
//            }
//        }
//
//        Given("Some projects are created:") { dataTable: DataTable ->
//            step {
//                dataTable.asList(ProjectInitParams::class.java)
//                    .forEach { createProject(it) }
//            }
//        }
//
//        Then("The project should be created") {
//            step {
//                val projectId = context.projectIds.lastUsed
//                AssertionBdd.project(projectRepository).assertThat(projectId).hasFields(
//                    status = ProjectState.DRAFT,
//                    name = command.name,
//                    target = command.target,
//                    address = command.address,
//                    supervisor = command.supervisor,
//                    withCounseling = command.withCounseling,
//                )
//            }
//        }
//    }
//
//    private suspend fun createProject(params: ProjectInitParams) = context.projectIds.register(params.identifier) {
//        command = ProjectCreateCommand(
//            name = params.name,
//            target = "ze target",
//            address = "tmp",
//            supervisor = "idk",
//            withCounseling = false
//        )
//        projectAggregateService.create(command).id
//    }
//
//    private fun projectInitParams(entry: Map<String, String>?) = ProjectInitParams(
//        identifier = entry?.get("identifier").orRandom(),
//        name = entry?.get("name") ?: "Projekt",
//    )
//
//    private data class ProjectInitParams(
//        val identifier: TestContextKey,
//        val name: String,
//    )
//}
