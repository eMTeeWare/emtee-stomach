import tornadofx.*
import java.util.*

class EmteeStomach: App(TutorialModel::class) {
    init {
        reloadStylesheetsOnFocus()
        FX.locale = Locale.GERMAN
    }
}

