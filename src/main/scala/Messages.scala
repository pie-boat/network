package pieboat.network

object Messages {
  def sendHeartbeat              = "PING"
  def answerHeartbeat            = "PONG"
  def getStatus                  = "STATUS"
  def setSpeed(x: Int)           = "SP" + _formatNumber(x)
  def setDirection(x: Int)       = "DI" + _formatNumber(x)
  def setFrontLights(x: Boolean) = "LF" + (if(x) 1 else 0)
  def setSideLights(x: Boolean)  = "LS" + (if(x) 1 else 0)

  def _formatNumber(x: Int): String = (if(x >= 0) "+" else "-") + math.abs(x)


  def readAndExecute(
    ping: () => String,
    status: () => String,
    speed: (Int) => String,
    direction: (Int) => String,
    frontLights: (Boolean) => String,
    sideLights: (Boolean) => String
  ) = (s: String) => {
    val speedPattern = "SP([-\\+])([0-9]+)".r
    val directionPattern = "DI([-\\+])([0-9]+)".r
    val frontLightsPattern = "LF([01])".r
    val sideLightsPattern = "LS([01])".r

    s match {
      case "PING" => ping()
      case "STATUS" => status()
      case speedPattern(sign, value) => speed(if(sign == "-") -value.toInt else value.toInt)
      case directionPattern(sign, value) => direction(if(sign == "-") -value.toInt else value.toInt)
      case frontLightsPattern(value) => frontLights(value == "1")
      case sideLightsPattern(value) => sideLights(value == "1")
      case _ => "Not implemented"
    }
  }
}
