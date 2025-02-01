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
```
  # Option 1
  val user = bob
  println(user.birthplace?.city) // returns null and ends the execution early, but doesn't crash
  # Option 2
  user.birthplace?.city?.let {
    println(it) // the value you are evaluting defaults it, for bob it returns nothing, but doesn't crash
  }
  // OR
  user.birthplace?.city?.let { city ->
    println(city) // renaming from it to city
  }
  # Option 3
  if(user.birthplace?.city != null) {
    println(user.birthplace?.city) // boilerplate, ugly
  }
  
}
`
## Readability 

##
