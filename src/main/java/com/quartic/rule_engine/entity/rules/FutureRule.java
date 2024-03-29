package com.quartic.rule_engine.entity.rules;

import com.quartic.rule_engine.entity.Operation;
import com.quartic.rule_engine.entity.Signal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FutureRule extends BaseRule {


    public FutureRule(String signal) {
        super(signal,null);
    }

    @Override
    protected boolean handle(Signal signal) {
        switch (signal.getValueType()) {

            case Datetime:
                try {
                    Date valueDate = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATEFORMAT);
                    Date inputDate = simpleDateFormat.parse(signal.getValue());
                    return inputDate.compareTo(valueDate) > 0;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
        }
        return false;
    }

    @Override
    public Operation getOperation() {
        return Operation.FUTURE;
    }
}
