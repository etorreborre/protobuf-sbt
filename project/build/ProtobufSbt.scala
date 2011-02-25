import sbt._
import maven._

class ProtobufSbt(info: ProjectInfo) extends PluginProject(info) with IdeaProject with MavenDependencies {
  lazy val publishTo = Resolver.sftp("Personal Repo",
                                     "codahale.com",
                                     "/home/codahale/repo.codahale.com/")
}
