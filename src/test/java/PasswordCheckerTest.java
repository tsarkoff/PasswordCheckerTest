import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PasswordCheckerTest {
    private PasswordChecker passwordChecker;

    @BeforeEach
    void setUp() {
        passwordChecker = new PasswordChecker();
    }

    // Длина пароля не может быть отрицательной, но может быть нулевой или больше нуля
    @ParameterizedTest
    @CsvSource(value = {
            "-1, false",
            "0, true",
            "1, true"
    })
    void setMinLength(int minLength, boolean expected) {
        if (minLength < 0) {
            Assertions.assertThrows(PasswordIllegalArgumentException.class, () -> {
                passwordChecker.setMinLength(minLength);
            });
        } else {
            boolean actual = passwordChecker.setMinLength(minLength);
            Assertions.assertEquals(expected, actual);
        }
    }

    // Один и тот же символ должен присутствовать в пароле один раз или более
    @ParameterizedTest
    @CsvSource(value = {
            "-1, false",
            "0, false",
            "1, true",
            "100, true"
    })
    void setMaxRepeats(int maxRepeats, boolean expected) {
        if (maxRepeats > 0) {
            boolean actual = passwordChecker.setMaxRepeats(maxRepeats);
            Assertions.assertEquals(expected, actual);
        } else {
            Assertions.assertThrows(PasswordIllegalArgumentException.class, () -> {
                passwordChecker.setMaxRepeats(maxRepeats);
            });
        }
    }

    // Тест проверки пароля:
    // FALSE - пароль короче указанной мин длины или включает повторение символа более разрешенного кол-ва раз подряд
    // TRUE - пароль длиннее или равен минимальной длине и включает повторение символа не более или равное разрешенному кол-ву раз подряд
    @ParameterizedTest
    @CsvSource(value = {
            // "{password}, {minLength}, {maxRepeats}, {expected}",
            "voodoo, 6, 4, true", // voodoo = 6, "o" повторяется не более 4х раз
            "voodoo, 7, 4, false", // voodoo < 7, "o" повторяется не более 4х раз
            "voodoo, 6, 1, false", // voodoo = 6, "o" повторяется больше 4х раз
            "_, 0, 1, true", // пароль может быть пустой, разрешено 1 повторение
    })
    void verify(String password, int minLength, int maxRepeats, boolean expected) {
        passwordChecker.setMinLength(minLength);
        passwordChecker.setMaxRepeats(maxRepeats);
        boolean actual = passwordChecker.verify(password);
        Assertions.assertEquals(expected, actual);
    }

    // Проверка выбрасывания правильной ошибки IllegalStateException, если для объекта не установлены мин длина и макс повторения
    @Test
    void checkVerificationWoInitialization() {
        // ! НЕ УСТАНОВЛЕНО passwordChecker.setMinLength(minLength);
        // ! НЕ УСТАНОВЛЕНО passwordChecker.setMaxRepeats(maxRepeats);
        Assertions.assertThrows(IllegalStateException.class, () -> {
            passwordChecker.verify("password_does_not_matter");
        });
    }

    // Проверка выбрасывания правильной ошибки NullPointerException, если пароль = null
    @Test
    void checkVerificationWithNullPassword() {
        passwordChecker.setMinLength(1);
        passwordChecker.setMaxRepeats(1);
        Assertions.assertThrows(PasswordIllegalArgumentException.class, () -> {
            passwordChecker.verify(null);
        });
    }
}