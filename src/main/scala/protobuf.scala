package protobuf

import sbt._
import com.dyuproject.protostuff.compiler.CompilerMain

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

    System.setProperty("output", "java_bean")
    System.setProperty("encoding", "UTF-8")
    System.setProperty("outputDir", protobufOutputPath.toString)
    for (schema <- protobufSchemas.get) {
      System.setProperty("source", schema.toString)
      CompilerMain.main(Array())
    }




//    Process("protoc", "--java_out=%s".format(protobufOutputPath.toString) :: protobufSchemas.get.toList.map { _.toString }) ! log
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
