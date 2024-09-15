# Super-Saving Supermarket Point of Sales (POS) System
Project Description

This project is a Point of Sales (POS) system designed for the Super-Saving supermarket chain. The system allows a cashier to enter item codes to add grocery items to a customer’s bill. For each item, the system retrieves details such as price, weight/size, manufacturing and expiry dates, and manufacturer name (hardcoded for this exercise). The system also handles item-specific discounts that range from 0-75%.

In addition, the POS system includes the following features:

Handling pending bills: The cashier can save the bill as pending and resume it later, enabling them to continue servicing other customers.
Custom exception handling for invalid item codes through a user-defined ItemCodeNotFound exception.
The final bill includes the cashier's name, branch, customer details, item list (with unit price, quantity, discount, and net price), total discount, total price, and the date and time when the bill was generated.

Key Features
Add items to a bill based on item codes.
Handles pending bills using Java serialization.
Implements a custom exception (ItemCodeNotFound) to handle incorrect item codes and allow re-entry of valid codes.
Prints a well-formatted receipt with customer and cashier details.
Getting Started
Prerequisites
Java Development Kit (JDK) 8 or higher
An IDE or text editor like IntelliJ IDEA, Eclipse, or Visual Studio Code.
Installation
Clone the repository to your local machine:

bash
Copy code
git clone https://github.com/rashG1/POS_system.git
Open the project in your preferred IDE.

Compile and run the program.

Running the Program
The program starts by asking the cashier to enter item codes. If the item code is invalid, the system will throw the custom ItemCodeNotFound exception and ask the cashier to re-enter the item code. The cashier can also save a bill as pending if a customer needs to pause the billing process temporarily.

After all items are entered, the program prints the bill with item details, discounts, and total prices.

Code Structure
Main.java: The entry point of the program. Manages the overall flow of the POS system.
POS.java: Contains the core functionality for processing bills, handling pending bills, and item retrieval.
GroceryItem.java: Represents a grocery item with attributes like price, weight, and discount.
ItemCodeNotFound.java: Custom exception to handle invalid item codes.
Exception Handling
The program demonstrates the use of a custom exception, ItemCodeNotFound, in the getItemDetails() method. This ensures that if a cashier enters an invalid item code, the program will handle the error gracefully and prompt for re-entry.

Serialization for Pending Bills
When a bill is paused, the system uses Java serialization to save the current state of the bill. It can later be resumed to complete the transaction.

Additional Information
Pending Bills: The cashier can mark a bill as pending, and the state is saved for later retrieval.
Date & Time: The receipt includes the accurate date and time when the bill was generated.
