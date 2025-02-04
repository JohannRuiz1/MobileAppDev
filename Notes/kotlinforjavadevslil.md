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
`
    val temp =  80
    val isAirConditionerOn = if(temp >= 80){
        println("It is warm")
        true // The last variable defined is what is returned, doesn't need to specify return
    } else {
        print("It is not so warm")
        false
    }
`
- when replaces the switch statement
- Example

`
  val burgersOrdered = 1
  
  // Simple switch case statement
  when(burgersOrdered){
    0 -> println("Not hungry")
    1 -> println("Hungry")
    2 -> println("Very hungry")
    else -> {
      println("Are you sure?")
    }
  }

  // Combine statements
  when(burgersOrdered){
    0 -> println("Not hungry")
    1, 2 -> println("Hungry")
    3 -> println("Very hungry")
    else -> {
      println("Are you sure?")
    }
  }

  // Comparing to result of expression
  when(burgersOrdered){
    Math.abs(burgersOrdered) -> println("Ordered 0 or more burgers") // Comparsing Math.abs(burgersOrdered) == burgersOrdered
    else -> {
      println("Orders less than 0")
    }
  }

  // Comparing in a range
  when(burgersOrdered){
    0 -> println("Not hungry")
    in 1..4 -> println("Hungry")
    in 5..9 -> println("Very hungry")
    else -> {
      println("Are you sure?")
    }
  }

  // Used as an if else statement (compact)
  when {
    burgersOrdered <= 0 -> println("Not hungry")
    burgersOrdered % 2 == 1 -> println("Hungry")
    burgersOrdered % 2 == 0 -> println("Very hungry")
    // Notice no else statement because the list if exhaustive
  }
`

  - while loops is the same as java
  - for statement works with any object that has an iterator 
  - Example

for(item in 1..10){
   println($item)
}
  
for(ch in "biscuit"){
}
  
var index = 0;
for(item in 10.rangeTo(20).step(2)) {
  print("${++index}) $item, ")
}

for((index, item) in 10.rangeTo(20).step(2).withIndex()){
  print("${index}) $item, ")
}

val myArray = arrayOf(10, 20, 30, 40, 50)
for(item in myArray.indices) { // Array does not have an iterator without indices
  println("At index $item is ${myArray[item]}")
}
`
  - Functions
`
fun myFunction(param1: Int, param2: Int): Int {
  return param1 + param2
}

println("10 + 20 =  ${myFunction(10,20)}")

OR
// Expression body function 
fun myFunction1(param1: Int, param2: Int): Int = param1 + param2 // Only if the function returns a single line and returns a value

// The return type can be inferred
fun myFunction2(param1: Int, param2: Int) = param1 + param2

fun myDefaults(param1: Int = 1, param2: Int = 5, message: String = "Hi"): Int {
  val results = param1 + param2
  println(message)
  return results 
}

  - Packages should map file structure, but doesn't have to be
  - Kotlin has default imports


f



