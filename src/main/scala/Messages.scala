package pieboat.network

object Messages {
  def sendHeartbeat              = "PING"
  def answerHeartbeat            = "PONG"
  def getStatus                  = "STATUS"
  def setSpeed(x: Int)           = "SP" + _formatNumber(x)
  def setDirection(x: Int)       = "DI" + _formatNumber(x)
  def setFrontLights(x: Boolean) = "LF" + (if(x) 1 else 0)
  def setSideLights(x: Boolean)  = "LS" + (if(x) 1 else 0)
  def setDebugMA(x: Int)         = "MA" + _formatNumber(x)
  def setDebugMB(x: Int)         = "MB" + _formatNumber(x)
  def setDebugMC(x: Int)         = "MC" + _formatNumber(x)

  def _formatNumber(x: Int): String = (if(x >= 0) "+" else "-") + math.abs(x)


  def readAndExecute(
    ping: () => String,
    status: () => String,
    speed: (Int) => String,
    direction: (Int) => String,
    frontLights: (Boolean) => String,
    sideLights: (Boolean) => String,
    debugMA: (Int) => String,
    debugMB: (Int) => String,
    debugMC: (Int) => String
  ) = (s: String) => {
    val speedPattern = "SP([-\\+])([0-9]+)".r
    val directionPattern = "DI([-\\+])([0-9]+)".r
    val frontLightsPattern = "LF([01])".r
    val sideLightsPattern = "LS([01])".r
    val debugMAPattern = "MA([-\\+])([0-9]+)".r
    val debugMBPattern = "MB([-\\+])([0-9]+)".r
    val debugMCPattern = "MC([-\\+])([0-9]+)".r

    s match {
      case "PING" => ping()
      case "STATUS" => status()
      case speedPattern(sign, value) => speed(if(sign == "-") -value.toInt else value.toInt)
      case directionPattern(sign, value) => direction(if(sign == "-") -value.toInt else value.toInt)
      case frontLightsPattern(value) => frontLights(value == "1")
      case sideLightsPattern(value) => sideLights(value == "1")
      case debugMAPattern(sign, value) => debugMA(if(sign == "-") -value.toInt else value.toInt)
      case debugMBPattern(sign, value) => debugMB(if(sign == "-") -value.toInt else value.toInt)
      case debugMCPattern(sign, value) => debugMC(if(sign == "-") -value.toInt else value.toInt)
      case _ => "Not implemented"
    }
  }
}
