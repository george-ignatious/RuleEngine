package com.quartic.rule_engine.engine;

import com.quartic.rule_engine.entity.Signal;
import com.quartic.rule_engine.entity.rules.Rule;
import com.quartic.rule_engine.ruleLoaders.RuleManager;
import com.quartic.rule_engine.streams.Stream;

public class RuleEngine {
 //   Map<String, Rule> rules = new HashMap<>();
    private Stream<Signal> failureStream;
    private Stream<Signal> inputStream;
    private RuleManager ruleManager;
    private boolean run = false;

    RuleEngine(Stream<Signal> failureStream, Stream<Signal> inputStream, RuleManager ruleManager) {
        this.failureStream = failureStream;
        this.inputStream = inputStream;
        this.ruleManager = ruleManager;
    }


    private void execute(Signal signal) {
        Rule rule = ruleManager.getRule(signal.getSignal());
        boolean pass = true;
        if (rule != null) {
            pass = rule.apply(signal);
        }
        if (!pass) {
            failureStream.insert(signal);
        }
    }

    void startEngine() {
        run = true;
        while (run) {
            while (inputStream.hasNext()) {
                execute(inputStream.next());
                if (!run) {
                    break;
                }
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    protected void stop() {
        run = false;
    }

    boolean isRunning() {
        return run;
    }

}
