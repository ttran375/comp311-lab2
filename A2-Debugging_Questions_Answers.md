# COMP311 Assignment 2: Debugging exercise

## Questions 1

**What class is the driver for this slice of the application? (0.5)**

The main driver of the application is `UserPrompter.java`, as it handles user interaction directly and starts the process of gathering passenger information and seat assignments. `Manifest.java` serves as a supportive class that manages passenger data and could be used in conjunction with `UserPrompter` to provide a more complete functionality.

## Questions 2

**What major part of the application is replaced by a stub? Name the class(es) that make up this stub. (0.5)**

- `Manifest.java`:
  - `isFull()`: This method is supposed to determine if the flight is full, but currently, it always returns `false`.
  - `isPassenger(Passenger p)`: This method is supposed to check if a given passenger is on the flight, but it currently always returns `false`.

- `SeatReserver.java`: This entire class is a stub. It should handle seat reservations but does not contain any implementation.

- `UserPrompter.java`:
  - `confirmSeat(String passengerName, int seatNumber)`: This method should handle seat confirmation but currently just prints a confirmation message without actually performing any checks or updates.

## Questions 3

**3. List three defects that you located in the original code, and describe then as you would in a tester’s defect report. Describe where user is in using the system and what the user input. Then state how the system response deviated from expected output. (6).**

### Defect 1: Incorrect Handling of Prompt in `getYesNoAnswer()`

- **Defect Description:** The method `getYesNoAnswer()` attempts to modify the prompt by appending additional text each time the user input is invalid. However, it uses `getPrompt()` which fetches the prompt string, but does not update the current instance's prompt.
- **User's Actions:** User is prompted to answer a yes/no question. The user provides input other than 'Y' or 'N'.
- **System's Response:** The system continues to use the original prompt without the additional instruction to answer 'Y' or 'N'.
- **Expected Response:** The system should update the prompt with the additional instruction "Please answer Y or N:" for subsequent attempts.
- **Location:** Method `getYesNoAnswer()` in the `UserPrompter` class.

```java
public boolean getYesNoAnswer() {
 for (int i = 0; i < 3; i++) {
  String answer = getAnswer();
  if (answer == null)
   return false;
  char ans = answer.toUpperCase().charAt(0);
  if (ans == 'Y')
   return true;
  setPrompt(getPrompt() + ". Please answer Y or N: ");
 }
 return false;
}
```

### Defect 2: Incorrect Handling of Prompt in `getPassenger()`

- **Defect Description:** The method `getPassenger()` does not validate user inputs for alphabetic characters, allowing numeric or special character inputs for first name, last name, and initial.
- **User's Actions:** User is prompted to enter first name, last name, and initial. The user provides inputs containing numeric or special characters.
- **System's Response:** The system accepts these inputs without validation and proceeds with the creation of the `Passenger` object.
- **Expected Response:** The system should validate that the inputs for first name, last name, and initial contain only alphabetic characters and prompt the user again if the input is invalid.
- **Location:** Method `getPassenger()` in the `PassengerService` class.

## Question 4

**4. Briefly define the term Step in, Step out, Step over and Breakpoint: (2)**

1. **Step in**: This command allows the debugger to enter into the code of a called function or method. It will pause execution at the first line of the called function, allowing you to examine its execution line by line.

2. **Step out**: This command lets the debugger complete the execution of the current function or method and pause at the line of code immediately after the function call. It is useful for quickly finishing the execution of a function without stepping through each line inside it.

3. **Step over**: This command allows the debugger to execute the current line of code and move to the next line without stepping into any called functions or methods. It is used to proceed to the next line in the current scope.

4. **Breakpoint**: A breakpoint is a designated stopping point in the code where the debugger will pause execution. It allows you to inspect the program's state at specific lines of code, making it easier to diagnose and understand the behavior of your program.

## Question 5

### Defect 1: Incorrect Handling of Prompt in `getYesNoAnswer()`

**5. Give the Screenshots for the import of jar file into the java IDE , Debug mode and fix of no error. (1)**

![](getYesNoAnswer.png)

``` java
public boolean getYesNoAnswer() {
    for (int i = 0; i < 3; i++) {
        String answer = getAnswer();
        if (answer == null)
            return false;
        char ans = answer.toUpperCase().charAt(0);
        if (ans == 'Y')
            return true;
        else if (ans == 'N')
            return false;
        setPrompt("Please answer Y or N: ");
    }
    return false;
}
```

![](_getYesNoAnswer.png)

### Defect 2: Incorrect Handling of Prompt in `getPassenger()`

``` java
public String getAnswer() {
        String answer = null;
        try {
            while (answer == null || answer.trim().isEmpty() || !answer.matches("[a-zA-Z]+")) {
                System.out.print(prompt + " ");
                answer = lineReader.readLine();
                if (!answer.matches("[a-zA-Z]+")) {
                    System.out.println("Please enter only alphabetic characters.");
                }
            }
            return answer.trim();
        } catch (IOException ioe) {
            // if console I/O fails there is no recovery
            return null;
        }
    }

 public String getAnswerId() {
        String answer = null;
        try {
            while (answer == null || answer.trim().isEmpty() || !answer.matches("\\d+")) {
                System.out.print(prompt + " ");
                answer = lineReader.readLine();
                if (!answer.matches("\\d+")) {
                    System.out.println("Please enter only numeric characters.");
                }
            }
            return answer.trim();
        } catch (IOException ioe) {
            // if console I/O fails there is no recovery
            return null;
        }
    }
```

``` java
private Passenger getPassenger() {
    Passenger passenger = null;
    String firstName = new UserPrompter("First name?").getAnswer();
    String lastName = new UserPrompter("Last name?").getAnswer();
    String initial = new UserPrompter("Initial?").getAnswer();
    PassengerName pName = new PassengerName(firstName, initial, lastName);
    if (new UserPrompter("Are you a frequent flyer?").getYesNoAnswer()) {
     String fFlyerId = new UserPrompter("Frequent flyer number?")
       .getAnswerId();
     passenger = new FrequentFlyer(pName, fFlyerId);
    } else if (new UserPrompter("Are you an airline employee?")
      .getYesNoAnswer()) {
     String employeeId = new UserPrompter("Employee ID?").getAnswerId();
     passenger = new StaffPassenger(pName, employeeId);
    } else {
     passenger = new Passenger(pName);
    }
    return passenger;
}
```
