import tornadofx.*

class EmteeStomach: App(TutorialView::class, MyStyle::class) {
    init {
        reloadStylesheetsOnFocus()
    }
}

