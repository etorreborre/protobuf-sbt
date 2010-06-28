package protobuf

import sbt._

trait ProtobufCompiler extends DefaultProject {
  override def compileOrder = CompileOrder.JavaThenScala
  def protobufSchemas = "src" / "main" / "protobuf" ** "*.proto"
  def protobufOutputPath = "src" / "main" / "java"
  
  def generateProtobufAction = task {
    for (schema <- protobufSchemas.get) {
      log.info("Compiling schema %s".format(schema))
    }
    protobufOutputPath.asFile.mkdirs()
    Process("protoc", "--java_out=%s".format(protobufOutputPath.toString) :: protobufSchemas.get.toList.map { _.toString }) ! log
    None
  } describedAs("Generates Java classes from the specified Protobuf schema files.")
  lazy val generateProtobuf = generateProtobufAction
  
  override def compileAction = super.compileAction dependsOn(generateProtobuf)
  
  def cleanProtobufAction = task {
    FileUtilities.clean(protobufOutputPath :: Nil, false, log)
    None
  } describedAs("Deletes the Protobuf output directory.")
  
  lazy val cleanProtobuf = cleanProtobufAction
}