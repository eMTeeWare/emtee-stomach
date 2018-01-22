package controller

import model.DailyConsumptionData
import tornadofx.*
import java.time.LocalDate
import java.time.Month

class DailyConsumptionController : Controller() {
    val dailyConsumes = listOf(
    DailyConsumptionData(LocalDate.of(2018, Month.JANUARY, 1), 2000, "Nudeln mit Soße"),
    DailyConsumptionData(LocalDate.of(2018,Month.JANUARY, 2), 1800, "Rehrücken")
    ).observable()
}