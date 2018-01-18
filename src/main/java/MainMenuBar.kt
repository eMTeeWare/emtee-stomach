import tornadofx.*
import javafx.scene.control.MenuBar
import tornadofx.FX.Companion.application

/**
 * Created by MThomas on 18.01.2018.
 */
class MainMenuBar : View("My View") {
    override val root = menubar {
        menu("File") {
            item("Load", "Shortcut+L").action {
                println("Loading!")
            }
            item("Save","Shortcut+S").action {
                println("Saving!")
            }
            item("Quit", "Shortcut+Q").action {
                System.exit(0)
            }
        }
    }

}
