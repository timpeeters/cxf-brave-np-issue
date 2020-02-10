package com.github.timpeeters;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.Slf4jNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @ClassRule
    public static final WireMockClassRule WIREMOCK = new WireMockClassRule(WireMockConfiguration.options()
            .port(9999)
            .jettyStopTimeout(10_000L)
            .notifier(new Slf4jNotifier(false)));

    @Autowired
    private CalculatorSoap calculator;

    @Test
    public void emulateSoapFault() {
        WIREMOCK.stubFor(WireMock.post("/").willReturn(
                WireMock.serviceUnavailable()));

        assertThatThrownBy(() -> calculator.divide(1, 0))
                .hasStackTraceContaining("503: Service Unavailable");
    }
}
