protobuf-sbt
============

*Gonna generate me some Java. Then I'm gonna ignore it.*

protobuf-sbt is a
[simple-build-tool](http://code.google.com/p/simple-build-tool/) plugin for
generating [Protocol Buffers](http://code.google.com/apis/protocolbuffers/)
classes from schema and protocol definitions.


Requirements
------------

* Simple Build Tool
* Protocol Buffers installed, with `protoc` available on the shell path.
* A `.proto` or two.


How To Use
----------

**First**, specify protobuf-sbt as a dependency in
`project/plugins/Plugins.scala`:

    class Plugins(info: sbt.ProjectInfo) extends sbt.PluginDefinition(info) {
      val codaRepo = "Coda Hale's Repository" at "http://repo.codahale.com/"
      val protobufSBT = "com.codahale" % "protobuf-sbt" % "0.1.0"
    }

and add Protocol Buffers itself as a dependency:

    val protobuf = "com.google.protobuf" % "protobuf-java" % "2.3.0" withSources()"

**Second**, put your Protocol Buffer schemas (`*.proto`) into
`src/main/protobuf`. If you want to put them somewhere else, be sure to let
protobuf-sbt know:
    
    override def protobufSchemas = "src" / "main" / "my-special-pb" ** "*.botoprufs"
    
**Third**, and only if you're feeling picky, let it know where you want your
new classes put:
    
    override def protobufOutputPath = "src" / "main" / "generated"

(It defaults to `src/main/java`, which will probably work just fine for you.)

**Finally**, compile your project. protobuf-sbt will generate fresh source files
for your schemas before it compiles things. It should *just work*.

Oh, and if you want protobuf-avro to clean the generated Java sources when it
cleans your compiled `.class` files, do this:
    
    override def cleanAction = super.cleanAction dependsOn(cleanProtobuf)

But please only do this if `protobufOutputPath` doesn't have anything you would
miss. Because `clean-protobuf` will nuke it.


License
-------

Copyright (c) 2010 Coda Hale
Published under The MIT License, see LICENSE