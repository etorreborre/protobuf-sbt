class ProtobufSbt(info: sbt.ProjectInfo) extends sbt.PluginProject(info) {
  /**
   * Publish to a local temp repo, then rsync the files over to repo.codahale.com.
   */
  override def managedStyle = sbt.ManagedStyle.Maven
  val publishTo = sbt.Resolver.file("Local Cache", ("." / "target" / "repo").asFile)
  def publishToLocalRepoAction = super.publishAction
  override def publishAction = task {
    log.info("Uploading to repo.codahale.com")
    sbt.Process("rsync", "-avz" :: "target/repo/" :: "codahale.com:/home/codahale/repo.codahale.com" :: Nil) ! log
    None
  } describedAs("Publish binary and source JARs to repo.codahale.com") dependsOn(test, publishToLocalRepoAction)
}