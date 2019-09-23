package com.quartic.rule_engine.entity.rules;

import com.quartic.rule_engine.entity.Signal;

public abstract class BaseRule implements Rule {
    private String signal;
    Rule nextRule;
    protected String value;

    protected BaseRule(String signal, String value) {
        this.signal = signal;
        this.value = value;
    }


    @Override
    public void setNext(Rule rule) {
        nextRule = rule;
    }

    @Override
    public Rule getNext() {
        return nextRule;
    }

    @Override
    public boolean apply(Signal signal) {
        boolean pass = true;
        if (nextRule != null) {
            pass = nextRule.apply(signal);
        }
        if (pass) {
            try {
                return handle(signal);
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public String getSignal() {
        return signal;
    }

    protected abstract boolean handle(Signal signal);

    @Override
    public String toString() {
        String string = signal + " " + getOperation().toString();
        if (value == null) {
            return string;
        } else {
            return string + " " + value;
        }
    }
}
