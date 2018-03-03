package com.kahoot.containers;

import com.kahoot.InputChannel;
import com.kahoot.Output;
import com.kahoot.io.ConsoleInputChannel;
import com.kahoot.io.ConsoleOutput;
import com.kahoot.io.FileInputChannel;
import com.kahoot.io.ResourceInputChannel;
import com.typesafe.config.Config;
import joni.dep.CustomContainer;
import joni.dep.Qualifier;

public class IoContainer extends CustomContainer {
    public IoContainer(Config config) {
        super(config);
    }

    public InputChannel inputChannel() {
        String source = config.hasPath("args.source") ? config.getString("args.source") : "console";
        return get(InputChannel.class, source);
    }

    @Qualifier("console")
    public InputChannel consoleInput() {
        return new ConsoleInputChannel(System.in);
    }

    @Qualifier("resource")
    public InputChannel resourceInput() {
        if (!config.hasPath("args.resource")) {
            throw new IllegalArgumentException("No resource name provided. Please provide a --resource resource_name argument");
        }

        return new ResourceInputChannel(config.getString("args.resource"));
    }

    @Qualifier("file")
    public InputChannel fileInput() {
        if (!config.hasPath("args.file")) {
            throw new IllegalArgumentException("No file name provided. Please provide a --file /path/to/file argument");
        }

        return new FileInputChannel(config.getString("args.file"));
    }

    public Output getOutput() {
        return new ConsoleOutput();
    }
}
