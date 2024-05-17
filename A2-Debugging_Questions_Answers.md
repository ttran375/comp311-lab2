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

The `getYesNoAnswer` method in the `UserPrompter` class has issues:

1. The current implementation of `getYesNoAnswer` does not correctly handle both 'Y', 'N', and invalid responses.
2. **Prompt adjustment within loop**: The prompt adjustment for invalid inputs should be handled properly without continuously appending to the prompt.
3. **Clearer instructions and error handling**: It's useful to provide clearer instructions and better error handling.

Here's an updated version of the `UserPrompter` class with the necessary fixes:

```java
package com.cc.airline.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserPrompter {

    protected String prompt = "?";
    protected BufferedReader lineReader;

    public UserPrompter() {
        lineReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public UserPrompter(String prompt) {
        this();
        setPrompt(prompt);
    }

    public String getAnswer() {
        try {
            String answer = null;
            while (answer == null || answer.length() < 1) {
                System.out.print(prompt + " ");
                answer = lineReader.readLine().trim();
            }
            return answer;
        } catch (IOException ioe) {
            // if console I/O fails there is no recovery
            return null;
        }
    }

    public boolean getYesNoAnswer() {
        String originalPrompt = getPrompt();
        for (int i = 0; i < 3; i++) {
            String answer = getAnswer();
            if (answer == null) {
                return false;
            }
            answer = answer.trim().toUpperCase();
            if (answer.equals("Y")) {
                return true;
            } else if (answer.equals("N")) {
                return false;
            } else {
                setPrompt(originalPrompt + " Please answer Y or N: ");
            }
        }
        return false;
    }

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

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
```
