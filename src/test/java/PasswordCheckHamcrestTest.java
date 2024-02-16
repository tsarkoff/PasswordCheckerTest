import net.obvj.junit.utils.matchers.ExceptionMatcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.MatcherAssert.*;

import java.util.List;


public class PasswordCheckHamcrestTest {
    private PasswordChecker passwordChecker;

    @BeforeEach
    void setUp() {
        passwordChecker = new PasswordChecker();
    }

    // Длина пароля не может быть отрицательной, но может быть нулевой или больше нуля
    @ParameterizedTest
    @CsvSource(value = {"-1, false", "0, true", "1, true"})
    void setMinLengthHamcrest(int minLength, boolean expected) {
        if (minLength < 0) {
            assertThat(() -> passwordChecker.setMinLength(minLength),
                    ExceptionMatcher.throwsException(PasswordIllegalArgumentException.class)
            );
        } else {
            boolean actual = passwordChecker.setMinLength(minLength);
            MatcherAssert.assertThat(actual, Matchers.equalTo(expected));
        }
    }

    // Один и тот же символ должен присутствовать в пароле один раз или более
    @ParameterizedTest
    @CsvSource(value = {"-1, false", "0, false", "1, true", "100, true"})
    void setMaxRepeatsHamcrest(int maxRepeats, boolean expected) {
        if (maxRepeats > 0) {
            boolean actual = passwordChecker.setMaxRepeats(maxRepeats);
            MatcherAssert.assertThat(actual, Matchers.equalTo(expected));
        } else {
            assertThat(() -> passwordChecker.setMaxRepeats(maxRepeats),
                    ExceptionMatcher.throwsException(PasswordIllegalArgumentException.class)
            );
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
    void verifyHamcrest(String password, int minLength, int maxRepeats, boolean expected) {
        passwordChecker.setMinLength(minLength);
        passwordChecker.setMaxRepeats(maxRepeats);
        boolean actual = passwordChecker.verify(password);
        MatcherAssert.assertThat(actual, Matchers.equalTo(expected));
    }

    // Проверка выбрасывания правильной ошибки IllegalStateException, если для объекта не установлены мин длина и макс повторения
    @Test
    void checkVerificationWoInitializationHamcrest() {
        assertThat(() -> passwordChecker.verify("password_does_not_matter"),
                ExceptionMatcher.throwsException(IllegalStateException.class)
        );
    }

    // Проверка выбрасывания правильной ошибки NullPointerException, если пароль = null
    @Test
    void checkVerificationWithNullPasswordHamcrest() {
        passwordChecker.setMinLength(1);
        passwordChecker.setMaxRepeats(1);
        assertThat(() -> passwordChecker.verify(null),
                ExceptionMatcher.throwsException(PasswordIllegalArgumentException.class)
        );
    }

    @Test
    void verifyHamcrest() {
        List<String> list = List.of("hello", "netology", "world");
        assertThat(list, Matchers.hasItems("hello", "netology"));
    }
}
