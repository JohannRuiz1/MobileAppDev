# Module 5 Notes

## Fragments and Activities
- Fragments give ability to dynamically hide and reveal elements
- Activities can link multiple fragments, or one full-screen fragment, swapped in and out as needed
- Navigating between fragments is easier than navigating between activities

## Fragment lifecyle
- Activity lifecycle recap:
  - In `onCreate`: inflate the layout to initliaze the view binding layout, define event listeners and call updateView()
- Fragment lifecycle
  - `onCreate()`: Perform early init/config (fairly uncommon)
  - `onCreateView()`: Inflate the layout to init the view binding object
  - `onViewCreated()`: Define the event listeners then init the interface and call updateView()
  - `onDestroyView()`: Remove references to any views, typically meaning the view binding object*
- * The Fragment object is retained in memory even after its associated view is destroyed
  - If we leave reference, cannot be garbage collected
 
## Hosting a fragment
- The activity layout uses a FragmentContainerView to host a fragment
- Specify the fully-qualified name of the initial fragment as the `android:name`
- Add attribute `tools:layout="@layout/[some-layout]"` to add layout preview
- FragmentManager to manipulate fragments more precisely in code
  
