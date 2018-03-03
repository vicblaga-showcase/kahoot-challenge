# Top Email Domains

### Requirements

To run this program, you need:
 - Java version 1.8 (or higher)

### Compiling and running

This project uses `gradle` and you can run tasks with the included wrapper.

Generate runnable artifacts and run the application:

```bash
$ ./gradlew installDist
$ cd build/install/kahoot-top-email/bin
$ ./kahoot-top-email
```

By default, the application will read from standard in until it encounters the string `end`.
You can read from a file by supplying the following arguments:

```bash
$ ./kahoot-top-email --source=file --file=/path/to/file/name
```

You can also read from a jar resource by supplying the following arguments:

```bash
$ ./kahoot-top-email --source=resource --resource=emails.txt
```

Running tests (from the root project folder):

```bash
$ ./gradlew test
```
