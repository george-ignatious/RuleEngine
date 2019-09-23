package com.quartic.rule_engine;

import com.quartic.rule_engine.engine.RuleEngine;
import com.quartic.rule_engine.engine.RuleEngineFactory;
import com.quartic.rule_engine.engine.RuleEngineManager;
import com.quartic.rule_engine.entity.Signal;
import com.quartic.rule_engine.entity.ValueType;
import com.quartic.rule_engine.ruleLoaders.RuleManager;
import com.quartic.rule_engine.ruleLoaders.TextFileRuleManager;
import com.quartic.rule_engine.streams.InMemoryStream;
import com.quartic.rule_engine.streams.Stream;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Runner {
    public static void main(String args[]) throws Exception {
        if (args.length != 1||args.length != 2) {
            System.out.println("Please pass signal json and rules file as arguments");
        }
        Stream<Signal> inputStream = new InMemoryStream<>();
        Stream<Signal> failureStream = new InMemoryStream<>();
        RuleManager ruleManager = new TextFileRuleManager(args.length==2?args[1]:"test_files/rules.txt");
        RuleEngine engine = RuleEngineFactory.createAndGetEngine(failureStream, inputStream, ruleManager);
        RuleEngineManager manager = new RuleEngineManager(engine);
        manager.start();
        JSONParser parser = new JSONParser();
        Object object = parser.parse(new FileReader(args[0]));
        JSONArray listOfSignals = (JSONArray) object;
        Iterator iterator = listOfSignals.iterator();
        while (iterator.hasNext()) {
            JSONObject signalObject = (JSONObject) iterator.next();

            String signal = (String) signalObject.get("signal");
            String value = (String) signalObject.get("value");
            String value_type = (String) signalObject.get("value_type");
            inputStream.insert(new Signal(signal, value, ValueType.valueOf(value_type)));
        }
        System.out.println("Failed signals");
        while (inputStream.hasNext()) {
            System.out.println(inputStream.next().toString());
        }
    }
}
