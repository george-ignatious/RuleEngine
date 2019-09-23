import com.quartic.rule_engine.engine.RuleEngine;
import com.quartic.rule_engine.engine.RuleEngineFactory;
import com.quartic.rule_engine.engine.RuleEngineManager;
import com.quartic.rule_engine.entity.Signal;
import com.quartic.rule_engine.entity.ValueType;
import com.quartic.rule_engine.ruleLoaders.RuleManager;
import com.quartic.rule_engine.ruleLoaders.TextFileRuleManager;
import com.quartic.rule_engine.streams.InMemoryStream;
import com.quartic.rule_engine.streams.Stream;
import io.cucumber.java8.En;

import java.util.ArrayList;
import java.util.List;


public class RulesStepDefinitions implements En {


    public RulesStepDefinitions() {
        List<Exception> exceptions = new ArrayList<>();
        Stream<Signal> inputStream = new InMemoryStream<>();
        Stream<Signal> failureStream = new InMemoryStream<>();
        RuleManager ruleManager = new TextFileRuleManager("test_files/rules.txt");
        RuleEngine engine = RuleEngineFactory.createAndGetEngine(failureStream, inputStream, ruleManager);
        RuleEngineManager manager = new RuleEngineManager(engine);
        Before(() -> {

        });
        After(() -> {
            inputStream.clear();
            failureStream.clear();
            exceptions.clear();
            ruleManager.clear();
        });
        Given("I load rule {}",
                (String rule) -> ruleManager.addRule(rule));

        Given("I started the rule engine",
                () -> manager.start());

        Given("Clear rules", () -> {
            ruleManager.clear();
        });

        When("I start the rule engine again",
                () -> {
                    try {
                        manager.start();
                    } catch (Exception e) {
                        exceptions.add(e);
                    }
                });

        Then("It fails with exception {}", (String exception) -> {
            assert !exceptions.isEmpty();
            assert exceptions.get(0).getMessage().equals("Rule engine is already running");
        });

        When("I send a signal with name as {}, value as {} and value type as {}", (String name, String value, String valueType) -> {
            inputStream.insert(new Signal(name, value, ValueType.valueOf(valueType)));
        });

        Then("There should be {int} failed signals", (Integer count) -> {
            assert count == failureStream.count();
        });
        When("Wait for {int} milliseconds", (Integer millisecond) -> {
            Thread.sleep(millisecond);
        });

        Then("There should be a failed signal with name as {}, value as {} and value type as {}", (String name, String value, String valueType) -> {
            assert failureStream.count() != 0;
            Signal next = failureStream.next();
            assert next.getSignal().equals(name);
            assert next.getValue().equals(value);
            assert next.getValueType().toString().equals(valueType);
        });

    }
}
