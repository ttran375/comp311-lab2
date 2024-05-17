# COMP311 Assignment 2: Debugging exercise

## Questions 1

1. What class is the driver for this slice of the application? (0.5)

The driver class for this slice of the application is the class that contains the `main` method, which is responsible for initiating the execution of the program. In the provided code, the `UserPrompter` class itself serves as the driver class because it includes the `main` method.

```java
public static void main(String[] args) {
    UserPrompter up = new UserPrompter();
    up.setPrompt("What is your name?");
    System.out.println("hello " + up.getAnswer());

    UserPrompter confirmer = new UserPrompter("Are you ready?");
    if (confirmer.getYesNoAnswer()) {
        System.out.println("OK, let's go!");
    } else {
        System.out.println("Sorry to hear that.");
    }
}
```

This `main` method creates instances of `UserPrompter`, sets prompts, and processes user input, driving the flow of the application. Therefore, the `UserPrompter` class is the driver for this slice of the application.

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
