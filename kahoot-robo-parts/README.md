# Kahoot Robo Parts

### Summary

REST interface implemented with the [sparkjava](http://sparkjava.com) mini framework.

Database has a simple structure with 2 tables:

 - `parts`
    - `id` : int
    - `name`: string 
    - (I didn't implement the other fields, because the logic is not much different from the name)
 - `compatibilities` 
    - `part_id_1` : int (foreign key to `parts.id`) 
    - `part_id_2` : int (foreign key to `parts.id`)
 
The `compatibilities` table is a many to many association
that associates `part` entities with each other.
Through this table we can resolve compatibility related queries.

The API contains 2 implementations for the database-related logic:
 - Memory based: useful for testing - all objects are kept in memory
 - Hibernate based: using `hibernate` ORM to manage the DB

The client is implemented with react and redux (see `./client` folder)

Documentation is written in swagger (see `./docs` folder).

### How to run

Run backend:

```bash
$ gradle run
```

Run client:
```bash
$ cd client/react_client
$ npm run start
```

API documentation:
```bash
$ cd docs/html
$ open index.html
```

## Scalability and high-availability issues

### Database

This API is a simple access layer on top of a database,
so under heavy load the bottleneck will be the database.

The database can be scaled vertically (bigger server) 
or horizontally (master-slave replication).
Under a master-slave replication, 
application servers can be redirected to read from different slaves,
thus spreading the read load.
Writes will have go through the master,
but this probably less of a problem since 
writes will be less frequent than reads.

Alternatively, if there is a high write throughput, 
we could use a more high-performance database, 
such as [Apache Cassandra](https://cassandra.apache.org).

### Application + Deployment

The application should run in a container (through Docker) 
and then deployed with a container management service (such as Kubernetes or Amazon ECS).
This service can easily take care of replication and scalability.

If deployed in AWS, we would probably use ECS,
The application servers should run under a load balancer.

## Security

In this implementation, security has not been addressed,
but in a real life scenario we would use one of the following strategies (or probably both):

- Secure access at an application level through something like OAuth
- Secure access at an infrastructure / network level 
(for example, have the API run in a private network, 
or in a closed security group) and 
allow only pre-determined clients to access it