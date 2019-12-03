# Usage of DataBaseInitializer

The class **DataBaseInitializer** uses functions from the class **FetchFromWeb** to get books information from [FNAC's](https://www.fnac.pt) website.

When running the class **DataBaseInitializer**, it will display in the terminal the list of books fetched. It will also save the html pages into the folder [*html_pages*](/data_base/source/html_pages) and output a file named **data_base.json** with the information of the books confined into json format.

You needed to use the *json-simple-1.1.jar* library to compile the class DataBaseInitializer.

The library is situated on the [*root*](https://github.com/digas99/library-management) folder, in a folder named [*lib*](/lib).

***Changing CLASSPATH localy in the terminal:***
```
$ export CLASSPATH=.:/path/to/this/folder/library-management/lib/json-simple-1.1.jar
```

***Compiling without changing the CLASSPATH of the terminal:***
```
$ javac -cp /path/to/this/folder/library-management/lib/json-simple-1.1.jar *.java
```
