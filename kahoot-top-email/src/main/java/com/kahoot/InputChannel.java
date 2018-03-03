package com.kahoot;

import java.io.IOException;
import java.util.Collection;

/**
 * Produces a collection of email input lines.
 */
public interface InputChannel {
    Collection<String> lines() throws IOException;
}
