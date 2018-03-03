package com.kahoot;

import com.kahoot.stubs.StubInputChannel;
import com.kahoot.stubs.StubOutput;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class MainServiceTest {
    private StubInputChannel inputChannel = new StubInputChannel();
    private EmailAddressParser emailAddressParser = new SimpleEmailAddressParser();
    private StubOutput output = new StubOutput();
    private MainService mainService = new MainService(inputChannel, emailAddressParser, output);

    @Test
    public void run() {
        // setup
        inputChannel.load("one@d1.com", "two@d1.com", "three@d1.com",
                "one@d2.com", "two@d2.com",
                "one@d3.com",
                "one@d4.com", "two@d4.com", "three@d4.com", "one@d4.com", "two@d4.com", "three@d4.com",
                "email-invalid-here");
        mainService = new MainService(inputChannel, emailAddressParser, output);

        // when
        mainService.run();

        // then
        List<Map.Entry<String, Long>> counts = output.getSortedCounts();
        assertEntry(counts.get(0), "d4.com", 6);
        assertEntry(counts.get(1), "d1.com", 3);
        assertEntry(counts.get(2), "d2.com", 2);
        assertEntry(counts.get(3), "d3.com", 1);
    }

    private void assertEntry(Map.Entry<String, Long> entry, String expectedKey, long expectedValue) {
        assertEquals(expectedKey, entry.getKey());
        assertEquals(expectedValue, entry.getValue().longValue());
    }
}