🛒 Amazon Shopping Cart Automation
==================================

This project automates adding the cheapest (in our case) Snickers and Skittles to your Amazon cart, verifies that prices are correct, and checks if you're redirected to the signup page at checkout. It is built using **Selenium WebDriver** and **Cucumber** to ensure easy-to-read test scenarios.

🚀 Features
-----------

*   **Navigates to Amazon**: Opens the Amazon website.
    
*   **Sets Delivery Address**: Adjusts your delivery location to the United States Min.
    
*   **Searches for Products**: Looks for Snickers and Skittles.
    
*   **Adds the Cheapest Items**: Selects the least expensive options for both products.
    
*   **Verifies Prices**: Ensures that cart prices match displayed prices.
    
*   **Checks for Signup Redirect**: Confirms redirection to the signup page when proceeding to checkout.
    

🛠 Setup Guide
--------------

### 🔹 Prerequisites

Before you begin, make sure you have the following:

*   **Java** (JDK 11 or newer)
    
*   **Maven** (to manage dependencies)
    
*   **WebDriver** (such as ChromeDriver) for your browser
    
*   **An IDE** (IntelliJ IDEA or Eclipse is recommended but optional)
    

### 🔹 Installation Steps

#### Clone the Repository

    git clone https://github.com/Lucian2403/AmazonTestCaseStudy
    cd AmazonTestCaseStudy

#### Install Dependencies

    mvn clean install

#### Configure WebDriver

1.  Download the appropriate **WebDriver** (e.g., ChromeDriver) for your browser.
    
2.  Place it in the `src/test/resources/drivers` folder.
    
3.  Update the `config.properties` file with the correct WebDriver path.
    

#### Customize Products (Optional)

Modify the `.feature` file if you want to test different products.

▶️ Running the Tests
--------------------

To execute the tests, use the following command:

    mvn test

Alternatively, you can run the **TestRunner** class directly from your IDE.

📌 How the Test Works
---------------------

1.  **Opens Amazon**: Navigates to the Amazon homepage.
    
2.  **Sets Delivery Address**: Updates the location to the United States Min.
    
3.  **Searches for Snickers and Skittles**: Finds both products.
    
4.  **Adds the Cheapest Items**: Selects the most affordable options.
    
5.  **Verifies Cart Prices**: Ensures the correct prices appear in the cart.
    
6.  **Checks Checkout Redirect**: Confirms redirection to the registration page.
    

📊 Expected Output
------------------

If everything works correctly, you will see output similar to this:

    Product page price of Snickers: 8.00
    Product page price of Skittles: 8.98

    ____ENTERING SHOPPING CART PAGE____
    Shopping Cart price of Snickers: 8.00
    Shopping Cart price of Skittles: 7.98
    Price of Snickers matches the cart price: 8.00
    Price of Skittles matches the cart price: 8.98
    Price of Skittles does NOT match the cart price. 
    Product price: 8.98, Cart price: 7.98
    LOGIN FORM IS PRESENT AFTER CHECKOUT.

If any issues arise, the output will indicate what went wrong.

🔧 Customization & Configuration
--------------------------------

*   **Modify Products**: Edit the `.feature` file to test different items.
    
*   **Update Delivery Address**: Adjust the address in the `.feature` file.
    
*   **Switch Browsers**: Change the browser configuration in `config.properties`.
    

🛠 Troubleshooting
------------------

### WebDriver Issues

*   Ensure your **WebDriver version matches your browser version**.
    
*   Verify the **WebDriver path** in `config.properties`.
    

### Element Not Found

*   Amazon may have updated its website structure. Check and update the **locators** in the page object classes.
    

### Test Failures

*   Review **console logs** for error messages.
    
*   Confirm that the **products and delivery address** are valid.
    

🤝 Contributing
---------------

If you’d like to contribute:

1.  **Fork the repository**.
    
2.  **Create a new branch** for your changes.
    
3.  **Submit a pull request** with a description of your updates.
    

📜 License
----------

This project is open-source under the **MIT License**. Feel free to use and modify it!

📩 Contact
----------

For questions or support, reach out:

*   **Your Name**: lucianciubotaru2403@gmail.com
    
*   **Project Repository**: [GitHub Repo](https://github.com/Lucian2403/AmazonTestCaseStudy)
