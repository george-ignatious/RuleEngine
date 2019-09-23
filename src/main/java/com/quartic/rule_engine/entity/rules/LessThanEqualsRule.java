package com.quartic.rule_engine.entity.rules;

import com.quartic.rule_engine.entity.Operation;
import com.quartic.rule_engine.entity.Signal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LessThanEqualsRule extends BaseRule{


    public LessThanEqualsRule(String lhs, String value) {
        super(lhs,value);
    }

    @Override
    protected boolean handle(Signal signal) {
        switch (signal.getValueType()){

            case Integer:
                try {
                    int valueInt = Integer.parseInt(value);
                    return Integer.parseInt(signal.getValue()) <= valueInt;
                } catch (NumberFormatException n) {
                    return false;
                }
            case DateTime:
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATEFORMAT);
                    Date inputDate = simpleDateFormat.parse(signal.getValue());
                    Date valueDate = simpleDateFormat.parse(value);
                    return inputDate.compareTo(valueDate) <= 0;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
        }
        return false;
    }
    @Override
    public Operation getOperation() {
        return Operation.LESSTHANEQUALS;
    }
}
