package com.quartic.rule_engine.engine;

public class RuleEngineManager {
    RuleEngine engine;

    public RuleEngineManager(RuleEngine engine){
        this.engine = engine;
    }

    public void start() throws Exception {
        if (engine.isRunning()) {
            throw new Exception("Rule engine is already running");
        }
        new Thread(() -> engine.startEngine()).start();
    }

    public void stop() {
        engine.stop();
    }
}
