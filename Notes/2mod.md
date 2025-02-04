# Module 2 Notes

## Architecture Overview
- Event-driven GUI code can become overly complicated and inefficient
- Model-View-Controller (MVC) architecture is useful to solve this
  - Model: Maintains the current state of the system, without regard for the GUI-related components
  - View: Represents the layout and various components that comprise the UI
  - Controller: Responds to user interactions with the View, some of which may update the Model

## Andriod MVC in practice
- Assume we have an Activity (such as MainActivity) and a layout (such as activity_main)
- Model: underlying data representing the entire state of the system at any point in time
    - These are non-UI data items stored in (or referenced by) an Activity
      - Generally all model data is stored in a separate file (more on this later)
      - The model must never include references to any View components (more on this later)
- View: The layout resources files define the view hierarchy, defining everything on the screen
    - All of the components are specified in the layout, but their behaviors aren't defined there
- Controller: Event handlers are defined within `onCreate()` of the Activity
  - Usually `setOnClickListener` is called on each interface Veiw component to specify behavior
    - Lambdas are used to specify a function that will be run later, upon clicking the component
  - The controller code must only modify the Model, and never directly modify the View
      - The last step of each lambda is to delegate updating the View by calling a helper function
        - The helper function name is normally `updateView()`
      - The helper function must only update the View, according with the current state of the Model
        - It must refresh all View components, making no assumptions about the prior state
        - It must never change any of the state information on the Model
      - The last step of `onCreate()` is also to delegate updating the View by calling the helper function
        - This sets the initial configuration of all View components, whenever an Activity is created

## MVC Controller Summary
- Controller cide with a typical activity's `onCreate()` function generally follows this basic pattern:
```
class SomeActivity ... {
   override fun onCreate(...) {
     // various initialization goes here; may generate the Model or fetch it from elsewhere

     binding.someView.setOnClickListener {
         // update *only* the Model here, as needed
         updateView() // <-- this is the last line of the listener
     }

     binding.anotherView.setOnClickListener {
         // update *only* the Model here, as needed
         updateView() // <-- this is the last line of the listener
     }
     // define any additional listeners here, as above
     updateView() // <-- this last line of onCreate() sets the initial View state
  }

   private fun updateView() {
   // refresh the entire View here, based on the Model, but without altering the Model
   }
}
```
