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

#### Defect 1: Incorrect Default Passenger Initialization

- **Location in Code**: `Passenger` class constructor
- **User Input**: None (default constructor usage)
- **System Response**: Creates a `Passenger` object with the name "T. B. A."
- **Expected Output**: Should allow default initialization to an empty or null `PassengerName`, or prompt user input.
- **Description**: The `Passenger` class default constructor initializes a passenger with a hardcoded name "T. B. A.", which is not appropriate. This should either be an empty `PassengerName` or prompt the user for input.

#### Defect 2: Typo in `Discountable` Interface Method

- **Location in Code**: `Discountable` interface and `StaffPassenger` class
- **User Input**: Calling the `disountPrice` method
- **System Response**: No method found or incorrect method behavior
- **Expected Output**: Method correctly calculates and returns the discounted price.
- **Description**: There is a typo in the method name `disountPrice` in the `Discountable` interface and `StaffPassenger` class. The method should be named `discountPrice` for clarity and correctness. This typo can cause issues when trying to implement or call this method.

#### Defect 3: Ticket Issuance Step Omitted

- **Location in Code**: `SeatReserver` class, `sellTicket` method
- **User Input**: Attempting to sell a ticket
- **System Response**: Prints "Ticket issued: ..." but does not actually store the ticket.
- **Expected Output**: Ticket should be stored or added to a list of issued tickets.
- **Description**: In the `sellTicket` method of `SeatReserver` class, there is a comment "// important step omitted here" where the ticket should be stored. This step is crucial to ensure the ticket is recorded and managed appropriately within the system.

### User Interaction and System Response

1. **Default Passenger Initialization**
   - **Where User Is**: System initializes a new `Passenger` object using the default constructor.
   - **User Input**: None
   - **System Response Deviation**: Initializes the `Passenger` object with the name "T. B. A.", which is inappropriate.

2. **Calling Discount Price Method**
   - **Where User Is**: System attempts to calculate a discount price for a `StaffPassenger`.
   - **User Input**: None (method invocation within code)
   - **System Response Deviation**: Method `disountPrice` is incorrectly spelled, leading to potential method not found errors or incorrect behavior.

3. **Selling a Ticket**
   - **Where User Is**: System sells a ticket to a passenger.
   - **User Input**: Input provided via `UserPrompter` for passenger details.
   - **System Response Deviation**: Prints "Ticket issued: ..." but does not actually store the issued ticket, leading to loss of ticket information.
