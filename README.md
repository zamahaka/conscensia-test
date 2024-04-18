# conscensia-test

# Requirements

- Activity + Fragment
- ImageView (40 * 40) contained in layout of your choice (MATCH_PARENT width and height)
- Drag and drop inside parent layout
- Relative ImageView positioning
- Restore ImageView position after restart
- After successful drop show SnackBar with confirmation message

# Key concepts

- [RelativePosition](app/src/main/java/com/yuste/conscensia/domain/model/RelativePosition.kt): Models image box positioning inside bonds with cutoff prevention
- [PositionRepository](app/src/main/java/com/yuste/conscensia/domain/repository/PositionRepository.kt): Abstract data storage
- [DragAndDrop](app/src/main/java/com/yuste/conscensia/presentation/DragAndDrop.kt): Listeners for drag and drop initiation and completion
- [DragAndDropState](app/src/main/java/com/yuste/conscensia/presentation/DragAndDropState.kt): State for drag and drop ui
- [DragAndDropViewModel](app/src/main/java/com/yuste/conscensia/presentation/DragAndDropViewModel.kt): State holder and ui event consumer
- [DragAndDropFragment](app/src/main/java/com/yuste/conscensia/presentation/DragAndDropFragment.kt): Ui wiring and state binding
- [DragAndDropAlert](app/src/main/java/com/yuste/conscensia/presentation/DragAndDropAlert.kt): Alert for DragAndDrop ui