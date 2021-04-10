# TextEditorSmartUndo
Project for COMP-354

```
EditContainer  -> singleton class that maintains the Edits
GroupContainer -> singleton class that maintains the EditGroups

Interactions between other modules and the Model module should be done through the EditContainer and GroupContainer only
EditView objects can be manipulated by the model to show things in the sidebar
```