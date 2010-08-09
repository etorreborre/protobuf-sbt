import sbt._
import maven._

class ProtobufSbt(info: ProjectInfo) extends PluginProject(info) with IdeaProject with MavenDependencies {
  lazy val publishTo = Resolver.sftp("Personal Repo",
                                     "codahale.com",
                                     "/home/codahale/repo.codahale.com/")

  /**
   * Dependencies
   */
  val protostuffRepo = "Protostuff Repo" at "http://protostuff.googlecode.com/svn/repos/maven2"
  val protostuffApi = "com.dyuproject.protostuff" % "protostuff-api" % "1.0.0.M3" withSources()
  val protostuffCore = "com.dyuproject.protostuff" % "protostuff-core" % "1.0.0.M3" withSources()
  val protostuffCompiler = "com.dyuproject.protostuff" % "protostuff-compiler" % "1.0.0.M3" withSources() intransitive()
  val protostuffParser = "com.dyuproject.protostuff" % "protostuff-parser" % "1.0.0.M3" withSources() intransitive()
  val stringTemplate = "org.antlr" % "stringtemplate" % "3.2.1" withSources() intransitive()
  val antlr = "antlr" % "antlr" % "2.7.7" intransitive()
  val antlrRuntime = "org.antlr" % "antlr-runtime" % "3.2" withSources() intransitive()
}
