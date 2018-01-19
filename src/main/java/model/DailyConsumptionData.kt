package model

import java.time.LocalDate

data class DailyConsumptionData (val date : LocalDate, val consumption : Int, val mainDish : String)
