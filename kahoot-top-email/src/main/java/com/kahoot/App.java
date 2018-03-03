package com.kahoot;

import com.typesafe.config.Config;
import joni.dep.ConfigParser;
import joni.dep.Container;
import joni.dep.ContainerBuilder;

public class App {
    public static void main(String[] args) {
        // setup the configuration and the dependency container
        Config config = new ConfigParser().parse(args);
        Container container = new ContainerBuilder(config, "com.kahoot.containers").build();

        // get the main service instance
        MainService mainService = container.get(MainService.class);

        // run the dependency
        mainService.run();
    }
}
