package com.quartic.rule_engine.entity;

public class Signal {
    private String signal;
    private String value;
    private ValueType valueType;


    public Signal(String signal, String value, ValueType valueType) {
        this.signal = signal;
        this.value = value;
        this.valueType = valueType;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }
}
