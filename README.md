# Koukio & CS Digital Media Test

Setup:
- Run as Java 11 application.
- Access GraphiQL

Example query:

http://localhost:8080/graphiql?query=%7B%0A%20%20getLastPosts(page%3A%200%2C%20size%3A%2010)%20%7B%0A%20%20%20%20id%0A%20%20%20%20title%0A%20%20%20%20publication%0A%20%20%7D%0A%7D%0A
