package com.quartic.rule_engine.entity.rules;

import com.quartic.rule_engine.entity.Operation;
import com.quartic.rule_engine.entity.Signal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotEqualsRule extends BaseRule{


    public NotEqualsRule(String signal, String value) {
        super(signal,value);
    }

    @Override
    protected boolean handle(Signal signal) {
        switch (signal.getValueType()){

            case String:
                return !signal.getValue().equals(value);
            case Integer:
                return Integer.parseInt(signal.getValue()) != Integer.parseInt(value);
            case DateTime:
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATEFORMAT);
                    Date inputDate = simpleDateFormat.parse(signal.getValue());
                    Date valueDate = simpleDateFormat.parse(value);
                    return inputDate.compareTo(valueDate) != 0;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
        }
        return false;
    }
    @Override
    public Operation getOperation() {
        return Operation.NOTEQUALS;
    }
}
