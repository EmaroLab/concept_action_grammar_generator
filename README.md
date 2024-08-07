# Concept Action Grammar Generator (CAGG)

This Java library implements an evaluator of grammars represented through the
[Backus-Naur Form](https://en.wikipedia.org/wiki/Backus%E2%80%93Naur_form) syntax. 
It can compile the syntax into a tree and evaluate it with an input sentence as a String.

#### Publications

For more details, check the following publication and the related [presentation](https://www.researchgate.net/publication/311654501_An_Open_Framework_to_Develop_and_Validate_Techniques_for_Speech_Analysis_The_Concept-Action_Grammar_Generator_architecture)
 
> L. Buoncompagni and F. Mastrogiovanni,
> [“An Open Framework to Develop and Validate Techniques for Speech Analysis”](https://ceur-ws.org/Vol-1834/paper3.pdf),
> published in the Proceedings of the 3rd Italian Workshop on Artificial Intelligence and Robotics, AI*IA, Genova, Italy, 2016.

## The CAGG Language

```
// An hello-world example of a CAGG grammar.
#CAGv1;  
!start <MAIN>;
<MAIN>	: 	<R1> {@?;@~;} | <R2> {@T;@bb;};
<R1> 	: 	hi | hello;
<R2> 	: 	(!optional(good {@!T;}) bye) | !repeat(bye{@!!;}, 1, 2);
```

A CAGG grammar defines *rules* (identified by `<` and `>`) as a tree. In particular, 
the grammar divides rules into *sub-rules*, which represent a logic expression as a 
binary tree. Rules have an identifying name and an expression, separated by `:`. 
Each line in the grammar must end with `;`, and the CAGG syntax supports C-like 
comments and annotations.

Some *directives* can be introduced in the rules, which are denoted by `!`. 
Specifically,
 - `!start` defines the root rule of the syntax,
 - `!optional` identifies that a specific sub-rule can be omitted, 
 - `!repeat` enables looping over a rule within a lower and an upper bound on the range.  
 
Sub-rules or terms (i.e., words in a sentence, which are an expression’s leaves) can
be aggregated using an empty space to denote an *and* logical operator, or with `|` 
to identify an *x-or* operator. 

Each rule or term can be semantically tagged using `{@...;...}`, which specifies an 
array of symbols (i.e., tags) used to identify the rule path identified by the CAGG 
evaluator given an input string. Tags are used to implement different behaviours 
based on the evaluation of an input string. Special tags can be also used, such as:
 - `?` is replaced during compilation with the rule’s head or term (e.g., `R1`),
 - `~` is replaced at compilation time with the leaf term evaluated by CAGG (e.g., `hi` or `hello`),
 - `!` removes a tag given its symbol (e.g., `T`),
 - `!!` removes all tags for that specific rule.

Given the hello-world grammar above, examples of evaluated tags are:
 - If the string `"hi"` is given, then the tag `{R1\hi}` is returned, while `"hello"` provides `{R1/hello}`.
 - If the string `"bye"` is given, then `{T\bb}` is retrieved, if `"good bye"` is given then the tag would be `{bb, T\bb}`, and for `"bye bye"` no tags are returned during the evaluation.

## Examples and Debugging

To know more about the code, check out the example 
[grammars](https://github.com/EmaroLab/concept_action_grammar_generator/tree/master/file/grammars) 
and [runners](https://github.com/EmaroLab/concept_action_grammar_generator/tree/master/src/it/emarolab/cagg/example). 
In addition, this software comes with a GUI to debug developed grammars 
(as shown below), please, check this 
[running file]([https://github.com/EmaroLab/concept_action_grammar_generator/blob/master/src/it/emarolab/cagg/example/GUIRunner.java) to use it.

![alt text](https://github.com/EmaroLab/concept_action_grammar_generator/blob/master/file/GUI-parse.png)
![alt text](https://github.com/EmaroLab/concept_action_grammar_generator/blob/master/file/GUI-eval.png)

---
**Author**: Luca Buoncompagni  (buon_luca@yahoo.com)
