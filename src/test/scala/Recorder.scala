import io.gatling.recorder.GatlingRecorder
import io.gatling.recorder.config.RecorderPropertiesBuilder

/**
 * @author Abhishek Kadavil
 */
object Recorder extends App {

  val props = new RecorderPropertiesBuilder()
    .simulationsFolder(IDEPathHelper.mavenSourcesDirectory.toString)
    .resourcesFolder(IDEPathHelper.mavenResourcesDirectory.toString)
    .simulationPackage("org.kadavil.perf")

  GatlingRecorder.fromMap(props.build, Some(IDEPathHelper.recorderConfigFile))
}
