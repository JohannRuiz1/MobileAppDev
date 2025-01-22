# Kotlin for Java Developers Linkedin Learning 

## Kotlin as a Better Java
- Java has boilerplates, getters and setters, null pointer exceptions, lacks modern programming features
- Kotlin is 100% compatiable with Java, elimates null pointer, syntax is clean, runs with JVM


## Get Started with Kotlin

fun main(args: Array<String>) {
  val name = if(args.any()) args[0] else "Kotlin"
  println("Hello, $name")
}


## Basic Kotlin Programming

### val vs. var
- val creates an immutable variable, var is mutable
- val is preferred and considered a best practice
- Immutable objects are thread-safe, code parallelization, cached, less moving parts, easier to understand

### Type system
- Only specify a type if the compiler cannot determine it
- Another example is providig a type if the type can be null
- If a variable is not declared immediately, then the type needs to be included
- Examples
  - val aSentence = "I'm a string"
  - val miPi = 3.14
  - var aString: String
  - val aDouble: Double
  - val aInt: Int
  - var myFloat = 1.9F
  - var myLong =  123112312L
  - var myInt = 199
  - var myShort: Short = 12
  - var myByte: Byte: 127
  - Doubles are the default is a . is provided
  - val aLongNumber = 123_345_789 is equal to val theSameNumber = 123456789
- Need to be explicit about conversions
  - val anInt: Int = 1
  - val aLong: Long = anInt.toLong()

## Control Flow  
- if is an expression, not a statement (if is a statement in java)
  - statements return no value, can't be on thr right side of an equal side, expressions return values
- Ternary statement is unnecessary
- Java: int lowest = (a<b) ? a : b;
- Kotlin: val lowest = if (a<b) a else b
  - Must contain an else clause
- Example

'''
    val temp =  80
    val isAirConditionerOn = if(temp >= 80){
        println("It is warm")
        true // The last variable defined is what is returned, doesn't need to specify return
    } else {
        print("It is not so warm")
        false
    }
'''
