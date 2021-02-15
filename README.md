# Koukio & CS Digital Media Test

Spring Boot application which reads the last 20 entries of an RSS feed every 5 minutes and saves them in its in-memory h2 database.

This records are exposed by a GraphQL interface and can be accessed with pagination through GraphiQL at [localhost](http://localhost:8080/graphiql).

Setup:
- Run *KoukiotestApplication#main* as Java 11 application. No arguments needed.
- Access GraphiQL and execute the [example query](http://localhost:8080/graphiql?query=%7B%0A%20%20getLastPosts%28page:%200,%20size:%2010%29%20%7B%0A%20%20%20%20id%0A%20%20%20%20title%0A%20%20%20%20publication%0A%20%20%7D%0A%7D%0A):

You can choose which parameters to show in the json response between the following ones:

    {
      getLastPosts(page: 0, size: 10) {
        id
        title
        publication
        description
        imageUrl
      }
    }


The query has two parameters which set the page and the page size.
