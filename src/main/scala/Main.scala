import java.io._
import java.net.{HttpURLConnection, URL}

/**
 * Created by Maxim Zhukov <crmaxx@ya.ru> on 2015-02-28.
 */
object Main extends App {

  val outputDir = "downloader"
  val file = "5MB.zip"
  val url = new URL("http://ipv4.download.thinkbroadband.com/5MB.zip")

  val connection = url.openConnection().asInstanceOf[HttpURLConnection]
  connection.setRequestMethod("GET")
  connection.setRequestProperty("Accept", "*/*")
  connection.setRequestProperty("Accept-Encoding", "identity")
  connection.setRequestProperty("User-Agent", "Wget/1.16.1 (darwin14.0.0)")

  val in: InputStream = connection.getInputStream
  val stream = new FileOutputStream(s"$outputDir/$file")
  val out: OutputStream = new BufferedOutputStream(stream)

  try {
    val byteArray = Stream.continually(in.read).takeWhile(_ != -1).map(_.toByte).toArray
    out.write(byteArray)
    println("downloaded: %s bytes".format(byteArray.size))
  } catch {
    case e: Exception =>
      println(e.getMessage)
  } finally {
    out.close()
    in.close()
  }
}
