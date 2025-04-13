# Module 2 Notes

## Testing

### Unit Testing (JVM/Local Tests)
- Junit Tests, no emulator
- @Test, @Before, @After
- assertEquals(expectedValue, actualValue)
- Ensures model and view model are working properly

### Instrumented Testing (UI Testing)
- Tests use **Espresso** framework and run on a device/emulator
- @Test, @Before, @After
  - Before we launch a starting activity, after closing that activity
- Works with views, via `onView()` and matchers (View IDs)
- `perform(click())`, `check(matches(...))` will assert the view has certain properties
- Calling `scenario.recreate()` destory and recreate actvity (Simulates device rotations)
- Espresso waits until prior interactions are completed before starting a new interfact
  - Animations can cause timing issues, can be disabled

## Intents and results
- 
