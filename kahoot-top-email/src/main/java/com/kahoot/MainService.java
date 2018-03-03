package com.kahoot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainService {
    private final Logger logger = LoggerFactory.getLogger(MainService.class);
    private final InputChannel inputChannel;
    private final EmailAddressParser emailAddressParser;
    private final Output output;

    public MainService(InputChannel inputChannel, EmailAddressParser emailAddressParser, Output output) {
        this.inputChannel = inputChannel;
        this.emailAddressParser = emailAddressParser;
        this.output = output;
    }

    public void run() {
        logger.info("Running main service");
        try {
            Map<String, Long> domainCounts = inputChannel.lines().stream()
                    .filter(emailAddressParser::isValid)
                    .map(emailAddressParser::parse)
                    .map(EmailAddress::getDomain)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            Stream<Map.Entry<String, Long>> sortedCounts = domainCounts.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()));

            output.handle(sortedCounts);
        } catch (IOException e) {
            logger.error("Exception", e);
        }
    }
}
