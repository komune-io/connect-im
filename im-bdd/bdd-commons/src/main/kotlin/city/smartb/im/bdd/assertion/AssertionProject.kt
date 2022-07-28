//package city.smartb.im.bdd.assertion
//
//import city.smartb.im.s2.project.api.entity.ProjectEntity
//import city.smartb.im.s2.project.api.entity.ProjectRepository
//import city.smartb.im.s2.project.domain.automate.ProjectId
//import city.smartb.im.s2.project.domain.automate.ProjectState
//import org.assertj.core.api.Assertions
//
//fun AssertionBdd.project(projectRepository: ProjectRepository) = AssertionProject(projectRepository)
//
//class AssertionProject(
//    override val repository: ProjectRepository
//): AssertionPostgresEntity<ProjectEntity, ProjectId, AssertionProject.ProjectAssert>() {
//
//    override fun assertThat(entity: ProjectEntity) = ProjectAssert(entity)
//
//    inner class ProjectAssert(
//        private val project: ProjectEntity
//    ) {
//        fun hasFields(
//            id: ProjectId = project.id,
//            status: ProjectState = project.status,
//            name: String = project.name,
//            target: String = project.target,
//            address: String = project.address,
//            supervisor: String = project.supervisor,
//            withCounseling: Boolean = project.withCounseling
//        ) = also {
//            Assertions.assertThat(project.id).isEqualTo(id)
//            Assertions.assertThat(project.status).isEqualTo(status)
//            Assertions.assertThat(project.name).isEqualTo(name)
//            Assertions.assertThat(project.target).isEqualTo(target)
//            Assertions.assertThat(project.address).isEqualTo(address)
//            Assertions.assertThat(project.supervisor).isEqualTo(supervisor)
//            Assertions.assertThat(project.withCounseling).isEqualTo(withCounseling)
//        }
//    }
//}
