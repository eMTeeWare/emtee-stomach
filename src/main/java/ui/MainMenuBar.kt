package ui

import tornadofx.*
import javafx.scene.control.MenuBar
import tornadofx.FX.Companion.application

class MainMenuBar : View() {
    override val root = menubar {
        menu(messages["filemenu"]) {
            item(messages["fileload"], "Shortcut+L").action {
                println("Loading!")
            }
            item(messages["filesave"],"Shortcut+S").action {
                println("Saving!")
            }
            separator()
            item(messages["quit"], "Shortcut+Q").action {
                System.exit(0)
            }
        }
    }

}
