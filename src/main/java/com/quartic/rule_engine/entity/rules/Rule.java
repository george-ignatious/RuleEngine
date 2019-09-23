package com.quartic.rule_engine.entity.rules;

import com.quartic.rule_engine.entity.Operation;
import com.quartic.rule_engine.entity.Signal;

public interface Rule {
    String DATEFORMAT = "dd/MM/yyyy HH:mm:ss";

    boolean apply(Signal signal);

    void setNext(Rule rule);

    Rule getNext();

    String getSignal();

    Operation getOperation();
}
