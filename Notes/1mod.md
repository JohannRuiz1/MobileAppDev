# Module 1 Notes

Android is more popular in the world, but iOS is more popular in the US

Kotlin is similar to Java with an emphasis on lambdas and other functional programming techniques

## Kotlin basics from a Java perspective
- Methods are called "functions" and fields are called "properties"
- Syntax
  - Semicolons are optional and discouraged
  - type specification appear after the declaration, separated by a colon
    - Kotlin: fun countSpaces(name: String, trim: Boolean): Int { ... }
    - Java: public int countSpaces(String name, boolean trim) { ... }
    - Python: def countSpaces(name: str, trim: bool) -> int: (Not required though)
  - Variables types can often be inferred by the compiler (and should be omitted in these cases)
  - Properties are accessed or assigned via '=' notation rather than getter/setter calls
  - Types don't allow null values by default; you must append '?' to the type to allow null values (Like C#)
    - Use '?.' or '!!.' to force access to a nullable value, or '?:' to specify a fallback value
    - val nullStr: String? = null // Need to declare the type if going to make nullable
    - val length = nullStr?.length // Safe call: returns null if str is null
    - val nameLength = person?.name?.length // Safe call chain
    - val length = nullStr!!.length // Non-null assertion, stating the object should not be null, throws NullPointerException if it is
    - val length = nullStr?.length ?: 0 // Is str is null, return 0
  - No primitives, everything is an object (wrapper are treated specially though)
    - Use '==' for object equivalence comparisons (including comparing to null)
  - Lambda expressions must generally appear within braces: '{ ... }'
    - Default name for a single lambda parameter is 'it' (but can be overridden in each lambda)

## BNRG Chapter 1
- App Basics
  - An activity is an instance of **Activity**. Entry point into application and responsible for managing user interaction with a screen of information
  - Write subclasses of Activity to implement the functionality your app requires; amount of subclasses varies on complexity
  - A layout defines a set of UI objects and the objects' positions on the screen
    - Made up of definitions written in XML
    - Each definition is used to create an object that appears on the screen, like a button or text