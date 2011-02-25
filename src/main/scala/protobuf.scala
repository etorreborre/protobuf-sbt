package protobuf

import sbt._

trait ProtobufCompiler extends DefaultProject {
  override def compileOrder = CompileOrder.JavaThenScala
  def protobufDirectory = "src" / "main" / "protobuf"
  def protobufSchemas = protobufDirectory ** "*.proto"
  def protobufOutputPath = "src" / "main" / "java"
  def protobufIncludePath = List(protobufDirectory)
  
  def generateProtobufAction = task {
    for (schema <- protobufSchemas.get) {
      log.info("Compiling schema %s".format(schema))
    }
    protobufOutputPath.asFile.mkdirs()
    val incPath = protobufIncludePath.map(_.absolutePath).mkString("-I ", " -I ", "")
    <x>protoc {incPath} --java_out={protobufOutputPath.absolutePath} {protobufSchemas.getPaths.mkString(" ")}</x> ! log
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
