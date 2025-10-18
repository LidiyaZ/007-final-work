package tests;

import com.codeborne.selenide.CollectionCondition;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import pages.AddProductModal;
import pages.ProductsPage;
import utils.TestBase;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.Random;
import java.util.stream.Stream;
import static com.codeborne.selenide.Condition.*;

@ExtendWith({AllureJunit5.class})
public class ProductTests extends TestBase {

    @Test
    @DisplayName("Страница 'Список товаров' содержит ссылку с названием 'QualIT'")
    @Description("Проверка структуры страницы — наличие ссылки с названием 'QualIT'")
    void pageTitleTest() {
        ProductsPage productsPage = new ProductsPage();

        productsPage.getPageTitle().shouldHave(text("QualIT"));
    }


    @Test
    @DisplayName("Страница 'Список товаров' содержит таблицу и кнопку 'Добавить'")
    @Description("Проверка структуры страницы — наличие таблицы с нужными колонками и кнопкой 'Добавить'")
    void productListPageStructureTest() {
        ProductsPage productsPage = new ProductsPage();

        // Наличие таблицы
        productsPage.getProductTable().shouldBe(visible);

        // Заголовки таблицы
        productsPage.getProductTable().$$("th").shouldHave(
                CollectionCondition.containExactTextsCaseSensitive("Наименование", "Тип", "Экзотический")
        );

        // Наличие кнопки 'Добавить'
        productsPage.getAddButton().shouldBe(visible)
                .shouldHave(text("Добавить"))
                .shouldHave(cssValue("background-color", "rgba(0, 123, 255, 1)"));
    }

    @Test
    @DisplayName("Кнопка 'Добавить' открывает модальное окно")
    @Description("При нажатии на кнопку 'Добавить' открывается окно 'Добавление товара'")
    void addButtonOpensModalTest() {
        ProductsPage productsPage = new ProductsPage();
        AddProductModal modal = productsPage.openAddProductModal();

        // Проверка заголовков
        modal.getModalTitle().shouldBe(visible)
                .shouldHave(text("Добавление товара"));
        modal.getNameTitle().shouldHave(text("Наименование"));
        modal.getNameType().shouldHave(text("Тип"));
        modal.getNameExotic().shouldHave(text("Экзотический"));

        //Проверка видимости полей
        modal.getNameInput().shouldBe(visible);
        modal.getTypeSelect().shouldBe(visible);
        modal.getExoticCheckbox().shouldBe(visible);
        modal.getSaveButton().shouldBe(visible);
    }

    @ParameterizedTest(name = "Проверка поля 'Наименование' со значением: {0}")
    @MethodSource("nameFieldValues")
    @DisplayName("Поле 'Наименование' принимает любые символы и не ограничено по длине")
    @Description("Проверка, что поле 'Наименование' принимает пробелы, спецсимволы, разные алфавиты и длинные строки")
    void nameFieldAcceptsAnySymbols(String input) {
        ProductsPage productsPage = new ProductsPage();
        AddProductModal modal = productsPage.openAddProductModal();

        enterNameValue(modal, input);

        modal.getNameInput().shouldHave(value(input));
    }

    @Step("Вводим значение в поле 'Наименование': {input}")
    private void enterNameValue(AddProductModal modal, String input) {
        modal.typeName(input);
    }

    static Stream<String> nameFieldValues() {
        return Stream.of(
                // Пробел
                " ",

                // Цифры и спецсимволы
                "0123456789 !@#$%^&*()_+{}[]|:;\"'<>,.?/~`№-=\\",

                // Буквы латиницы, кириллицы и японский иероглиф
                "AbcXYZ абвгдеёжз 忍 (терпение)",

                // Случайная строка длиной 1000 символов
                generateRandomString(1000)
        );
    }

    private static String generateRandomString(int length) {
        String chars = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдейиймновпрстуфхчяABCabcuoi 0123456789!@#$%^&*()_+-=[]{}|;:,.<>?";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    @DisplayName("Добавление товаров")
    public class AddProductTest {

        @ParameterizedTest(name = "Добавление {0} ({1}, экзотический = {2})")
        @CsvSource({
                "Манго, Фрукт, true",
                "Кабачок, Овощ, false"
        })
        @Description("Проверка, что товары (фрукты и овощи) успешно добавляются в список")
        void addNewProductTest(String name, String type, boolean isExotic) {
            ProductsPage productsPage = new ProductsPage();
            AddProductModal modal = productsPage.openAddProductModal();

            modal.setName(name)
                    .selectType(type)
                    .setExotic(isExotic)
                    .save();

            productsPage.shouldSeeProduct(name);
        }
    }
}