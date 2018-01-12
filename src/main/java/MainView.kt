import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import tornadofx.*

class MainView : View("My View") {
    override val root = VBox()

    init {
        root += Button("Press Me")
        root += Label("Waiting")
    }
}
