package org.deliveroo.cronparser.parser;

import org.deliveroo.cronparser.model.CronField;

public interface Parser {
    CronField parse(String expression);
}
