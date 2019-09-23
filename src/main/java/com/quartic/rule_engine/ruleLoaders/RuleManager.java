package com.quartic.rule_engine.ruleLoaders;

import com.quartic.rule_engine.entity.rules.Rule;

public interface RuleManager {

     Rule getRule(String signal);

     void addRule(Rule rule);

     void addRule(String rule);

     void clear();
}
