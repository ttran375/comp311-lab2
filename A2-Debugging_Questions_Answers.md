# COMP311 Assignment 2: Debugging exercise

## Questions 1

1. What class is the driver for this slice of the application? (0.5)

The main driver of the application is `UserPrompter.java`, as it handles user interaction directly and starts the process of gathering passenger information and seat assignments. `Manifest.java` serves as a supportive class that manages passenger data and could be used in conjunction with `UserPrompter` to provide a more complete functionality.

## Questions 2

2. What major part of the application is replaced by a stub? Name the class(es) that make up this stub.
(0.5)

- `Manifest.java`:
  - `isFull()`: This method is supposed to determine if the flight is full, but currently, it always returns `false`.
  - `isPassenger(Passenger p)`: This method is supposed to check if a given passenger is on the flight, but it currently always returns `false`.

- `SeatReserver.java`: This entire class is a stub. It should handle seat reservations but does not contain any implementation.

- `UserPrompter.java`:
  - `confirmSeat(String passengerName, int seatNumber)`: This method should handle seat confirmation but currently just prints a confirmation message without actually performing any checks or updates.

## Questions 3

3. List three defects that you located in the original code, and describe then as you would in a tester’s
defect report. Describe where user is in using the system and what the user input. Then state how
the system response deviated from expected output. (6).

### Defect 1

`getYesNoAnswer` method does not properly handle edge cases, including `N` inputs and invalid responses.

```java

```
