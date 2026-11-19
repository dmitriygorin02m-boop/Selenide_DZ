package ru.netology.Selenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

public class BookingTest {

    @Test
    void shouldSubmitFormWithManualDateEntry() {

        open("http://localhost:9999");

        // Получаем текущую дату и прибавляем 5 дней
        LocalDate meetingDate = LocalDate.now().plusDays(5);

        // Форматируем дату для HTML-поля
        String dateForInput = meetingDate.toString();

        // Форматируем дату для отображения
        String formattedDate = meetingDate.format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        //  Город
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=city] input")
                .should(Condition.value("Москва"));
        //  Дата
        $("[data-test-id=date] input").click();
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input.input__control")
                .setValue(formattedDate);
        $("[data-test-id=date] input.input__control")
                .should(Condition.value(formattedDate));
        $("[data-test-id=date] .input__control")
                .should(Condition.value(formattedDate));

        //  Имя и Фамилия
        $("[data-test-id=name] input")
                .setValue("Иванов Иван");
        $("[data-test-id=name] input")
                .should(Condition.value("Иванов Иван"));

        //  Телефон
        $("[data-test-id=phone] input")
                .setValue("+79991234567");
        $("[data-test-id=phone] input")
                .should(Condition.value("+79991234567"));

        //  Согласие на обработку
        $("[data-test-id=agreement] input")
                .shouldNot(Condition.selected);
        $("[data-test-id=agreement]").click();
        $("[data-test-id=agreement] input")
                .shouldBe(Condition.selected);

        $$("button").find(Condition.exactText("Забронировать")).click();

        // Индикатор загрузки
        $(".button__icon .spin").should(Condition.visible);

        // Уведомление
        $("[data-test-id=notification]")
                .should(Condition.visible, Duration.ofSeconds(15));
        $(".button__icon .spin")
                .should(Condition.disappear, Duration.ofSeconds(15));
        $(".notification__title").should(Condition.text("Успешно!"));
        $(".notification__content").should(Condition.text(
                "Встреча успешно забронирована на " + formattedDate));

        //закрытие уведомления
        $(".notification__closer").should(Condition.visible);
        $(".notification__closer .icon_name_close").should(Condition.visible);
        $(".notification__closer").click();
        $("[data-test-id=notification]").should(Condition.not(Condition.visible));
    }

    //<---Установлены тайминги для визуальной проверки прохождения теста--->
    @Test
    void shouldSubmitFormWithCityDropdownAndCalendar() {

        open("http://localhost:9999");
        sleep(500);
        $("[data-test-id=city] input").click();
        sleep(500);
        $("[data-test-id=city] input").sendKeys("М");
        sleep(200);
        $("[data-test-id=city] input").sendKeys("о");
        sleep(300);
        $$(".menu-item__control").find(Condition.exactText("Москва")).click();
        $("[data-test-id=city] input").shouldHave(Condition.value("Москва"));

        $("[data-test-id=date] input").click();
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a");
        sleep(600);
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        sleep(200);
        $(".calendar").shouldBe(Condition.visible);

        //  неделя
        LocalDate meetingDate = LocalDate.now().plusWeeks(1);

        //  timestamp в миллисекундах
        long meetingTimestamp = meetingDate
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
        //  нужная дата
        $$(".calendar__day")
                .findBy(attribute("data-day", String.valueOf(meetingTimestamp)))
                .click();
        // Проверяем выбранную дату
        $("[data-test-id=date] input.input__control")
                .shouldHave(Condition.value(
                        meetingDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                ));

        $("[data-test-id=name] input").click();
        $("[data-test-id=name] input").sendKeys("И");
        sleep(200);
        $("[data-test-id=name] input").sendKeys("в");
        sleep(200);
        $("[data-test-id=name] input").sendKeys("а");
        sleep(200);
        $("[data-test-id=name] input").sendKeys("н");
        sleep(200);
        $("[data-test-id=name] input").sendKeys("о");
        sleep(200);
        $("[data-test-id=name] input").sendKeys("в");
        sleep(200);
        $("[data-test-id=name] input").sendKeys(" ");
        sleep(200);
        $("[data-test-id=name] input").sendKeys("И");
        sleep(200);
        $("[data-test-id=name] input").sendKeys("в");
        sleep(200);
        $("[data-test-id=name] input").sendKeys("а");
        sleep(200);
        $("[data-test-id=name] input").sendKeys("н");
        $("[data-test-id=name] input").shouldHave(Condition.value("Иванов Иван"));

        $("[data-test-id=phone] input").click();
        $("[data-test-id=phone] input").sendKeys("+");
        sleep(200);
        $("[data-test-id=phone] input").sendKeys("7");
        sleep(200);
        $("[data-test-id=phone] input").sendKeys("9");
        sleep(200);
        $("[data-test-id=phone] input").sendKeys("9");
        sleep(200);
        $("[data-test-id=phone] input").sendKeys("9");
        sleep(200);
        $("[data-test-id=phone] input").sendKeys("1");
        sleep(200);
        $("[data-test-id=phone] input").sendKeys("2");
        sleep(200);
        $("[data-test-id=phone] input").sendKeys("3");
        sleep(200);
        $("[data-test-id=phone] input").sendKeys("4");
        sleep(200);
        $("[data-test-id=phone] input").sendKeys("5");
        sleep(200);
        $("[data-test-id=phone] input").sendKeys("6");
        sleep(200);
        $("[data-test-id=phone] input").sendKeys("7");
        $("[data-test-id=phone] input").shouldHave(Condition.value("+79991234567"));

        $("[data-test-id=agreement]").click();

        $$("button").findBy(exactText("Забронировать")).click();

        $(".button__icon .spin").should(Condition.visible);


        $("[data-test-id=notification]")
                .should(Condition.visible, Duration.ofSeconds(15));
        $(".button__icon .spin")
                .should(Condition.disappear, Duration.ofSeconds(15));
        $(".notification__title").should(Condition.text("Успешно!"));
        $(".notification__content").should(Condition.text(
                "Встреча успешно забронирована на " +
                        meetingDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));


        $(".notification__closer").should(Condition.visible);
        $(".notification__closer .icon_name_close").should(Condition.visible);
        $(".notification__closer").click();
        $("[data-test-id=notification]").should(Condition.not(Condition.visible));
    }
}

