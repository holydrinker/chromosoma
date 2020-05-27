# Schema Model
A schema is simply a map of field names and field types. The idea is to keep the schema definition as simple as possible for both user and developer experience.

As you can expect, the simplest way to represent a map of names and types is a file wherein every line we find a pair of values: the name (first) and the type (then).

A schema is provided as follows:
```
name string
surname string
age int
play_some_instruments boolean
``` 

At a data structure level, both names and types have a mapping with dedicated business objects. Check them out in the package [holydrinker.chromosoma.schema](https://github.com/holydrinker/data-generator/tree/master/src/main/scala/holydrinker/chromosoma/schema).