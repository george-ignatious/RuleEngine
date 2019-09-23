package com.quartic.rule_engine.ruleLoaders;

import com.quartic.rule_engine.entity.rules.Rule;
import com.quartic.rule_engine.engine.RuleFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TextFileRuleManager implements RuleManager {
    File file;
    List<Rule> rules = new ArrayList<>();
    Map<String, Rule> rulesMap = new HashMap();

    public TextFileRuleManager(String filePath) {
        file = new File(filePath);
            initializeRules();
        new ScheduledThreadPoolExecutor(1).schedule(() -> persistRules(), 5, TimeUnit.MINUTES);
    }

    public void initializeRules() {

        try {
            readRules();
            for (Rule newRule : rules) {

                Rule rule = rulesMap.get(newRule.getSignal());
                if (rule != null) {
                    newRule.setNext(rule);
                }
                rulesMap.put(newRule.getSignal(), newRule);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Rule getRule(String signal) {
        return rulesMap.get(signal);
    }

    @Override
    public void addRule(Rule rule) {
        rules.add(rule);
        rulesMap.put(rule.getSignal(), rule);
    }

    @Override
    public void addRule(String rule) {
        addRule(getRuleFromString(rule));
    }

    @Override
    public void clear(){
        rules.clear();
        rulesMap.clear();
    }

    private void readRules() throws FileNotFoundException {
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            Rule newRule = getRuleFromString(line);
            rules.add(newRule);

        }
    }

    private Rule getRuleFromString(String line) {
        String[] segments = line.split(" ");
        String lhs = segments[0];
        String operation = segments[1];
        String rhs = null;
        if (segments.length > 2) {
            rhs = segments[2];
        }
        return RuleFactory.getRule(lhs, operation, rhs);
    }

    void persistRules() {
        try {
            file.delete();
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            for (Rule rule : rules) {
                fileWriter.write(rule.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
