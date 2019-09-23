package com.quartic.rule_engine.engine;

import com.quartic.rule_engine.entity.Operation;
import com.quartic.rule_engine.entity.rules.*;


public class RuleFactory {
    public static Rule getRule(String lhs, String operation, String value){
        Operation operationEnum = Operation.valueOf(operation);
        switch(operationEnum){
            case GREATERTHAN:
                return new GreaterThanRule(lhs,value);
            case GREATERTHANEQUALS:
                return new GreaterThanEqualsRule(lhs,value);
            case LESSTHAN:
                return new LessThanRule(lhs,value);
            case LESSTHANEQUALS:
                return new LessThanEqualsRule(lhs,value);
            case EQUALS:
                return new EqualsRule(lhs,value);
            case NOTEQUALS:
                return new NotEqualsRule(lhs,value);
            case FUTURE:
                return new FutureRule(lhs);
            case NOTFUTURE:
                return new NotFutureRule(lhs);
        }
        return null;
    }
}
