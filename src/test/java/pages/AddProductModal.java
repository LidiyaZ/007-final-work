package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

public class AddProductModal {

    private final SelenideElement nameInput = $(byXpath("//*[@id = 'name']"));
    private final SelenideElement typeSelect = $(byXpath("//*[@id = 'type']"));
    private final SelenideElement exoticCheckbox = $(byXpath("//*[@id = 'exotic']"));
    private final SelenideElement saveButton = $(byXpath("//*[@id = 'save']"));

    @Step("Ввести наименование: {name}")
    public AddProductModal setName(String name) {
        nameInput.setValue(name);
        return this;
    }

    @Step("Ввести в поле 'Наименование' значение: {name}")
    public AddProductModal typeName(String name) {
        nameInput.setValue(name);
        return this;
    }

    @Step("Выбрать тип: {type}")
    public AddProductModal selectType(String type) {
        typeSelect.selectOption(type);
        return this;
    }

    @Step("Установить флаг 'Экзотический' = {isExotic}")
    public AddProductModal setExotic(boolean isExotic) {
        if (isExotic != exoticCheckbox.isSelected()) {
            exoticCheckbox.click();
        }
        return this;
    }

    @Step("Нажать кнопку 'Сохранить'")
    public void save() {
        saveButton.click();
    }

    public SelenideElement getModalTitle() {
        return $(".modal-title");
    }

    public SelenideElement getNameTitle() {
        return $(byXpath("//*[@for = 'name']"));
    }

    public SelenideElement getNameInput() {
        return nameInput;
    }

    public SelenideElement getTypeSelect() {
        return typeSelect;
    }

    public SelenideElement getNameType () {
        return $(byXpath("//*[@for = 'type']"));
    }

    public SelenideElement getExoticCheckbox() {
        return exoticCheckbox;
    }

    public SelenideElement getNameExotic () {
        return $(byXpath("//*[@for = 'exotic']"));
    }

    public SelenideElement getSaveButton() {
        return saveButton;
    }
}