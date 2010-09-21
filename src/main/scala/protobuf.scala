package protobuf

import sbt._
import Process._

trait ProtobufCompiler extends DefaultProject {
  override def compileOrder = CompileOrder.JavaThenScala
  def protobufDirectory = "src" / "main" / "protobuf"
  def protobufSchemas = protobufDirectory ** "*.proto"
  def protobufOutputPath = "src" / "main" / "java"
  
  def generateProtobufAction = task {
    for (schema <- protobufSchemas.get) {
      log.info("Compiling schema %s".format(schema))
    }
    protobufOutputPath.asFile.mkdirs()
    <x>protoc -I {protobufDirectory.absolutePath} --java_out={protobufOutputPath.absolutePath} {protobufSchemas.getPaths.mkString(" ")}</x> ! log
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
