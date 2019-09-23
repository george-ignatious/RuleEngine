# RuleEngine

Briefly describe the conceptual approach you chose! What are the trade-offs?

Created a map with signal as key and a linked list of rules as value for that signal.For  each signals we get the rules from map and iterate the rules till the rules end or any of the rule fails.

The trade off with this approach if the number of signals is huge, there will be a map with huge number of keys.
The advantage would be each signal could be checked with the rules very fast.

What's the runtime performance? What is the complexity? Where are the bottlenecks?

The worst case complexity will be O(no of rules) in case where all rules are for a single signal

The bottle neck will be no of types of signals as it would create a map with large no of keys 

If you had more time, what improvements would you make, and in what order of priority? 

1)Add more tests
2)Make a better persistence layer