### How to use this library

- Write a brute force solution in the BruteSolution file.
- Write code for the test-case generation in the TestsGenerator file.
- In properties.properties, write the respective file locations.
- In Checker.java, change the field `propertyFilePath` to the respective properties file location on your system.
- In Checker.java, reference and call your intended solution directly, the way it's done here :
  https://github.com/rahulkhairwar/dsa-library/blob/master/src/com/testingUtil/Checker.java#L50
- Make any required changes to the number of tests in Checker.java, and run it.
