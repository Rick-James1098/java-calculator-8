package calculator;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {
    @Test
    void 커스텀_구분자_사용() {
        assertSimpleTest(() -> {
            run("//;\\n1");
            assertThat(output()).contains("결과 : 1");
        });
    }

    @Test
    void 양의_실수_사용() { // 정확한 실수 계산
        assertSimpleTest(() -> {
            run("0.1,0.2,0.3");
            assertThat(output().contains("결과 : 0.6"));
        });
    }

    @Test
    void input_error() {
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("-1,2,3"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void custom_separator_input_format() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//!n1,2,3"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void multiple_custom_separator() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//.\\n//;\\n1,2,3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void dot_defined_to_custom_separator() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//.\\n1,2,3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void start_or_end_with_comma() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException(",1,2,3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );

        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1,2,3,"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void double_separator() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1,,2,3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void not_registered_separator() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1;2;3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }


    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
