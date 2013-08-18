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
  def notImplemented()           = "NI" // The knights who say…
  def notImplemented(x: Any): String = this.notImplemented

  def _formatNumber(x: Int): String = (if(x >= 0) "+" else "-") + math.abs(x)


  def readAndExecute(
    ping: () => String = this.notImplemented,
    status: () => String = this.notImplemented,
    speed: (Int) => String = this.notImplemented,
    direction: (Int) => String = this.notImplemented,
    frontLights: (Boolean) => String = this.notImplemented,
    sideLights: (Boolean) => String = this.notImplemented,
    debugMA: (Int) => String = this.notImplemented,
    debugMB: (Int) => String = this.notImplemented,
    debugMC: (Int) => String = this.notImplemented
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
      case _ => this.notImplemented
    }
  }
}
