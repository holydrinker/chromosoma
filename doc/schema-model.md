# Schema Model
A schema is simply a map of field names and field types. The idea is to keep the schema definition as simple as possible for both user and developer experience.

As you can expect, the simplest way to represent a map of names and types is a file where in every line we find a pair of values: the name (first) and the type (then).

A schema is provided as follows:
```
name string
surname string
age int
play_some_instruments boolean
``` 

At data structure level, both names and type have a mapping with dedicated business objects. Check them out in the [schema package](https://github.com/holydrinker/data-generator/tree/master/src/main/scala/holydrinker/chromosoma/schema).