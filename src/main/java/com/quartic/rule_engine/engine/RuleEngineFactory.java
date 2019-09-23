package com.quartic.rule_engine.engine;

import com.quartic.rule_engine.entity.Signal;
import com.quartic.rule_engine.ruleLoaders.RuleManager;
import com.quartic.rule_engine.streams.Stream;

public class RuleEngineFactory {
    public static RuleEngine createAndGetEngine(Stream<Signal> failureStream, Stream<Signal> inputStream, RuleManager ruleManager){
        return new RuleEngine(failureStream, inputStream, ruleManager);
    }
}
