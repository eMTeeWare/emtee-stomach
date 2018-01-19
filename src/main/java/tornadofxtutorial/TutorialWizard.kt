package tornadofxtutorial

import tornadofx.*

/**
 * Created by MThomas on 17.01.2018.
 */
class TutorialWizard : Wizard("Create customer", "Provide customer information")  {
    val customer: PersonModel by inject()
    init {
        add(BasicData::class)
        add(AddressInput::class)
    }

}

class BasicData : View("Basic Data") {
    val customer: PersonModel by inject()
    override val root = form {
        fieldset(title) {
            field("Name") {
                textfield(customer.name).required()
            }
        }
    }
}
class AddressInput : View("Address") {
    val customer: PersonModel by inject()
    override val root = form {
        fieldset(title) {
            field("title") {
                textfield(customer.title)
            }
        }
    }
}