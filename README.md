## NUMBERSAPI [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=MishaPatsiupin_NumBeer&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=MishaPatsiupin_NumBeer)
### Let your statistics tell tales and dates come to life
#### A web service for finding out the fact about numbers
### URL STRUCTURE

Just hit 'http://localhost:8080/info?number=NUMBER&type=TYPE' to get a plain text 

The parameters are as follows:

- `TYPE`: This parameter determines the category of information you want to retrieve. It can be one of the following:
  - `trivia`: Retrieves a random, interesting fact about the number (default if omitted).
  - `math`: Provides mathematical details and properties of the number.
  - `year`: Retrieves historical information or events related to a particular year.

- `NUMBER`: This parameter specifies the number you want to explore. It can be:
  - An integer: Simply enter the desired integer value to obtain information about that number.
  - The keyword `random`: If you use the keyword `random`, the API will attempt to provide a random available fact.

#### Other Endpoints

- Create a new fact about the `NUMBER` 42:

  - `TYPE`: trivia
  - `FACT`: "42 is the answer to the Ultimate Question of Life, the Universe, and Everything."
  - `AUTHOR`: Deep Thought
  - Send a POST request to 'http://localhost:8080/fact/add' with the above parameters.

- Update the fact with ID 123:

  - Change the `NUMBER` to 100
  - `TYPE`: math
  - `FACT`: "100 is a square number."
  - `AUTHOR`: MathFanatic
  - Send a PUT request to 'http://localhost:8080/fact/update' with the updated parameters.

- Delete the fact with ID 456.

  - Send a DELETE request to 'http://localhost:8080/fact/delete' with the ID parameter set to 456.

- Get information about a all fact related to the number 25 in the math category.

  - Access 'http://localhost:8080/info/all?number=25&type=math' for the result.

### Examples

Here are a few examples to demonstrate how to use the NumbersAPI:

1. Retrieve a trivia fact about the number 1:
http://localhost:8080/info?number=1


Response: "1 is the number of Gods in monotheism."

2. Get a random trivia fact:
http://localhost:8080/info


Response: "77 is the atomic number of iridium."

Now you can start exploring the fascinating world of numbers with NumbersAPI and uncover intriguing facts and stories behind them.

