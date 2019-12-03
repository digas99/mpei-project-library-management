# Usage of DataBaseInitializer

The class **DataBaseInitializer** uses the class **DataBaseCreator**, by passing the links of the [FNAC's](https://www.fnac.pt) pages.

The class **DataBaseCreator** uses functions from the class **FetchFromWeb** to get books information from [FNAC's](https://www.fnac.pt) website.

When running the class **DataBaseInitializer**, it will display in the terminal the list of books fetched. It will also output a file named **data_base.json** with the information of the books confined into json format.

You need to use the *json-simple-1.1.jar* library to compile the class DataBaseInitializer.

The library is situated on the [*root*](https://github.com/digas99/library-management) folder, in a folder named [*lib*](/lib).

***Changing CLASSPATH localy in the terminal:***
```
$ export CLASSPATH=.:/path/to/this/folder/library-management/lib/json-simple-1.1.jar
```

***Compiling without changing the CLASSPATH of the terminal:***
```
$ javac -cp /path/to/this/folder/library-management/lib/json-simple-1.1.jar *.java
```
