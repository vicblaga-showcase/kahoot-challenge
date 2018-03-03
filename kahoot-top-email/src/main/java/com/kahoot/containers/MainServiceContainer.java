package com.kahoot.containers;

import com.kahoot.InputChannel;
import com.kahoot.Output;
import com.kahoot.SimpleEmailAddressParser;
import com.kahoot.MainService;
import com.kahoot.io.ConsoleOutput;
import com.typesafe.config.Config;
import joni.dep.CustomContainer;
import joni.dep.Qualifier;

public class MainServiceContainer extends CustomContainer {
    public MainServiceContainer(Config config) {
        super(config);
    }

    public MainService mainService() {
        return new MainService(
                get(InputChannel.class),
                new SimpleEmailAddressParser(),
                get(Output.class)
        );
    }
}
