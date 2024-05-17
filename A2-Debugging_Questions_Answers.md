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

### Defect Report

#### Defect 1: Incorrect Handling of Prompt in `getYesNoAnswer()`

- **Defect Description:** The method `getYesNoAnswer()` attempts to modify the prompt by appending additional text each time the user input is invalid. However, it uses `getPrompt()` which fetches the prompt string, but does not update the current instance's prompt.
- **User's Actions:** User is prompted to answer a yes/no question. The user provides input other than 'Y' or 'N'.
- **System's Response:** The system continues to use the original prompt without the additional instruction to answer 'Y' or 'N'.
- **Expected Response:** The system should update the prompt with the additional instruction "Please answer Y or N:" for subsequent attempts.
- **Location:** Method `getYesNoAnswer()` in the `UserPrompter` class.

```java
setPrompt(getPrompt() + ". Please answer Y or N: ");
```

#### Defect 2: Missing Return Value in `assignSeat()`

- **Defect Description:** The method `assignSeat(SeatingClass sClass)` does not correctly handle the situation where a seat is assigned to a passenger. If a random seat is already taken, the method should find the first available seat in the section but does not return `null` correctly.
- **User's Actions:** User attempts to book a seat when the initial randomly assigned seat is already taken.
- **System's Response:** The system may throw an `IndexOutOfBoundsException` or improperly assign a seat.
- **Expected Response:** The system should find and assign the first available seat in the section.
- **Location:** Method `assignSeat(SeatingClass sClass)` in the `SeatReserver` class.

```java
Seat seat = seats.get(seatNumber);
if (seat.getTicket() != null) {
    seat = findFirstEmptySeat(seats, sClass);
    if (seat == null) {
        return null;
    }
}
```

#### Defect 3: Incorrect Calculation of `numSeatsSold` in `SeatingClass`

- **Defect Description:** The method `setNumSeatsSold(int numSeatsSold)` in the `SeatingClass` enum updates the number of sold seats, but the logic does not account for boundary conditions, such as exceeding the total number of seats.
- **User's Actions:** User attempts to sell a ticket when the number of sold seats is near or equal to the total seats available.
- **System's Response:** The system incorrectly updates the number of sold seats, potentially allowing overbooking.
- **Expected Response:** The system should validate and prevent the number of sold seats from exceeding the total seats available.
- **Location:** Method `setNumSeatsSold(int numSeatsSold)` in the `SeatingClass` enum.

```java
public void setNumSeatsSold(int numSeatsSold) {
    if (numSeatsSold <= this.numSeats) {
        this.numSeatsSold = numSeatsSold;
    } else {
        throw new IllegalArgumentException("Number of seats sold cannot exceed total seats.");
    }
}
```
