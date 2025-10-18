package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;

public class ProductsPage {

    private final SelenideElement pageTitle = $(byXpath("/html/body/div/div[2]/nav/a"));
    private final SelenideElement addButton = $(byXpath("//*[@class='btn-grou mt-2 mb-2']//button[@class='btn btn-primary']"));
    private final SelenideElement productRows = $(byXpath("//*[@class='container-fluid'][.//h5[text()='Список товаров']]//table[@class='table']"));

    @Step("Открыть модальное окно 'Добавление товара'")
    public AddProductModal openAddProductModal() {
        addButton.click();
        return new AddProductModal();
    }

    @Step("Проверить, что товар с наименованием '{productName}' присутствует в таблице")
    public void shouldSeeProduct(String productName) {productRows.find(String.valueOf(text(productName))).shouldBe();
    }

    public SelenideElement getPageTitle() {return  pageTitle; }

    public SelenideElement getAddButton() {
        return addButton;
    }

    public SelenideElement getProductTable() {
        return productRows;
    }
}