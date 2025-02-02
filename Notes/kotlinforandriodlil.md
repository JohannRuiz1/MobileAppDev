# Kotlin for Andriod LinkedIn Learning

## Properties and Fields
- Creating getters
`
private val greeting: TextView
  get() = findViewById(R.id.greeting)
`
-  Everytime greeting is called, it calls findViewById, which is expensive and we only want to do it once
-  Lazy initialization: Find the value conce, and reuse it each time
`
private cal greeting: TextView by lazy {
  findViewById<TextView>(R.id.greeting) // Need to be explicit for the type because it can't be inferred 
}
`
  - This may leak memory however, since the findViewById is storing in this activity
- lateinit // Tells the compiler I will set the value before using it
` private lateinit var greeting: TextView `
- Can use andriod extensions to help
  - build.gradle: apply plugin: 'kotlin-andriod-extensions'
  - MainActivity.kt: import kotlinx.andriod.synthetic.main.activity_main.*
  - Now can remove the lateinit of the resource, and everything in activity_main.xml will be imported to the file
 
## Conditionals
- Elvis operator, set a default value if null
  - val name = intent.getStringExtra("name") ?: getString(R.string.world)
    - intent is command line args for mobile
- Scope functions: let, runs a block of code if the value is not null
```
class User(val birthplace: Place? = null)
class Place(val country: String? = null, val city: String? = null)
fun main() {
  val alice = User {
    birthplace = Place(country = "United States", city = "Houston")
  }
  val bob = User {
    birthplace = Place(countr = "Japan")
  }
}
```
  # Option 1
```
  val user = bob
  println(user.birthplace?.city) // returns null and ends the execution early, but doesn't crash
```
# Option 2
```  
  user.birthplace?.city?.let {
    println(it) // the value you are evaluting defaults it, for bob it returns nothing, but doesn't crash
  }
  // OR
  user.birthplace?.city?.let { city ->
    println(city) // renaming from it to city
  }
```
  # Option 3
```  
  if(user.birthplace?.city != null) {
    println(user.birthplace?.city) // boilerplate, ugly
  }
```  
- Scope function: apply, configure a variable after configuting it
  ```
  fun greet(v: View) {
    val name = nameView.text.toString().trim()
    val intent = Intent(this@MainActivity, GreetActivity::class.java).apply {
      putExtra(GreetActivity.KEY_NAME, name)
    }
  }
  ```
- also, with, run are other scope functions
- Sealed classes to specify cases for a when statement, removes the need of when since the class defines all of the options
## Readability 
- data class has a default nice printing objects (toString)
- implements also equals, hashCode, and copy (deep)
## Functional Programming
- map function iterates on every element in a structure and performs an operation
```
  private fun update(){
    // line1, line2, and line3 are CheckBox (checked or not checked
    name.text = listOf(line1, line2, list3)
        .map {
          if(it.isChecked) {
            "1"
          }
          else {
            "0"
          }
        }
        .reduce { acc, ele -> // Takes two parameters
          // Converts into one string
            acc + ele
        }
        .let { key ->
          hashMap[key]
        }
  }

```
##
- find get the first element in the list that meets a condition
- filter gets all element in the list that meets a condition
- all returns true or false if all elements in the list meets a condition
- any returns true or false if an element in the list meets a condition
- foreach iterates between each item in the list
- take returns the first specified number of elements, dropping the remainder
- drop skips the first specified number of elements, returning the remainder
- zip combines this stream with another list, to become a list of Pair objects
    - One elemnt is taken from each list, until ether list runs out fo elements
    - The `Pair` can be used as-is, with first and second as the indiviual components `{ /* some lambda expression 
```
data class Food(val name: String, val isHealthy: Boolean, val isSnack: Boolean)

fun main(){
  val cabbage = Food("cabbage", isHealthy=true, isSnack=false)
  val carrots = Food("carrot", isHealthy=true, isSnack=true)
  val chips =  Food("chips", isHealthy=false, isSnack=true)

  val groceries = listOf(cabbage, carrots, chips)

  val healthySnack = groceries.find { it -> it.isHealthy } // return cabbage
  val allSnacks = groceries.filter { it -> it.isSnack } // returns carrots and chips
  val noSnacks = groceries.all { it -> !it.isSnack } // returns false
  val anyUnhealthy = groceries.any { it -> !it.isHealthy } // return true
  groceries.forEach {
    println(it)
  }
}

```
- KTX is a set of Kotlin extensions (includes the forEach)
  - implementation 'androidx.core:core-ktx:1.0.1' in build.gradle.kt
  - 



  
