# TextEditorSmartUndo
Project for COMP-354
###Model Overview
```
EditContainer  -> singleton class that maintains the Edits
GroupContainer -> singleton class that maintains the EditGroups

Interactions between other modules and the Model module should be done through the EditContainer and GroupContainer only
EditView objects can be manipulated by the model to show things in the sidebar
```

###Instructions to run project
The project can be run by executing the main method of the `TextEditorDriver` class located in `src/base`.
###Dependencies in project
Apart from Java's standard libraries, this project has dependencies on `javax`,`Abstract Window Toolkit` and the `JUnit` unit testing library/
###Screenshots
Screenshots of the project can be found in the screenshots directory.
###Coding Standards
```
The following conventions were imposed:
Add docstrings to all methods.
Class names should be pascal-cased.
Function names should be camel-cased.
```
###Project directory structure
The project is divided into two folders: `src` and `screenshots`. The screenshots directory is for the required screenshots.  
The `src` directory is divided as follows
```
base -> contains the modules in their own directories controller, model, and view. Each module contains multiple components
resources -> contains png files used by the view components
tests -> contains a component directory, for component level tests that are split by module
      -> contains an integration module, for integration tests between the modules
```