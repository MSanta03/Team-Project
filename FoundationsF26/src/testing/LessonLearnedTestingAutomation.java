package testing;

import database.Database;
import entityClasses.LessonLearned;
import entityClasses.LessonLearnedList;
import entityClasses.LessonLearnedValidator;


public class LessonLearnedTestingAutomation {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("HW2 Lesson Learned Test Automation");
        System.out.println("=================================");

        // We will add TC-01 through TC-10 here.
        try {

            Database database = new Database();
            database.connectToDatabase();

            // TC-01: Create a valid lesson learned
            LessonLearned lesson1 = new LessonLearned(
                    "Team Communication",
                    "Clear communication helps the team avoid misunderstandings."
            );

            boolean created = database.createLesson(lesson1);

            check(created, "TC-01 - Create a valid lesson learned");

         // TC-02: Create a lesson with missing required information
            String validationMessage =
                    entityClasses.LessonLearnedValidator.validateLesson(
                            "",
                            "This lesson has content but no title."
                    );

            check(
                    validationMessage.equals("Lesson title cannot be blank."),
                    "TC-02 - Reject lesson with blank title"
            );

            System.out.println("Expected Error Message: " + validationMessage);
            
         // TC-03: Read existing lessons
            LessonLearnedList allLessons = database.getAllLessons();

            check(
                    !allLessons.isEmpty(),
                    "TC-03 - Read existing lessons"
            );

            System.out.println("Number of lessons found: " + allLessons.size());
            
         // TC-04: Read an empty subset
            LessonLearnedList emptyResults =
                    database.searchLessons("THIS_SHOULD_NOT_MATCH_ANY_LESSON_12345");

            check(
                    emptyResults.isEmpty(),
                    "TC-04 - Search returns an empty subset"
            );

            System.out.println(
                    "Number of lessons found in empty search: "
                    + emptyResults.size()
            );
            
         // TC-05: Read a subset containing multiple lessons

            LessonLearned lesson2 = new LessonLearned(
                    "Communication Follow-Up",
                    "Good communication should include clear follow-up."
            );

            LessonLearned lesson3 = new LessonLearned(
                    "Communication During Meetings",
                    "Team communication during meetings helps avoid confusion."
            );

            database.createLesson(lesson2);
            database.createLesson(lesson3);

            LessonLearnedList multipleResults =
                    database.searchLessons("communication");

            check(
                    multipleResults.size() >= 2,
                    "TC-05 - Search returns multiple lessons"
            );

            System.out.println(
                    "Number of matching lessons: " + multipleResults.size()
            );
            
         // TC-06: Update an existing lesson

            LessonLearned lessonToUpdate = multipleResults.getLessons().get(0);

            String originalTitle = lessonToUpdate.getTitle();

            lessonToUpdate.setTitle("Updated Communication Lesson");

            boolean updated = database.updateLesson(lessonToUpdate);

            LessonLearned updatedLesson =
                    database.getLessonById(lessonToUpdate.getId());

            check(
                    updated
                    && updatedLesson != null
                    && updatedLesson.getTitle().equals("Updated Communication Lesson"),
                    "TC-06 - Update an existing lesson"
            );

            System.out.println(
                    "Original title: " + originalTitle
            );

            System.out.println(
                    "Updated title: " + updatedLesson.getTitle()
            );
            
         // TC-07: Update a lesson with invalid information

            LessonLearned lessonForInvalidUpdate =
                    database.getLessonById(lessonToUpdate.getId());

            String validationMessageUpdate =
                    entityClasses.LessonLearnedValidator.validateLesson(
                            "",
                            lessonForInvalidUpdate.getContent()
                    );

            boolean invalidUpdateRejected =
                    validationMessageUpdate.equals("Lesson title cannot be blank.");

            check(
                    invalidUpdateRejected,
                    "TC-07 - Reject update with blank title"
            );

            System.out.println(
                    "Expected Error Message: " + validationMessageUpdate
            );
            
         // TC-08: Delete an existing lesson

            LessonLearned lessonToDelete = new LessonLearned(
                    "Temporary Lesson",
                    "This lesson will be deleted during testing."
            );

            database.createLesson(lessonToDelete);

            // Find the lesson we just created
            LessonLearnedList deleteSearch =
                    database.searchLessons("Temporary Lesson");

            LessonLearned foundLesson =
                    deleteSearch.getLessons().get(0);

            boolean deleted =
                    database.deleteLesson(foundLesson.getId());

            LessonLearned deletedLesson =
                    database.getLessonById(foundLesson.getId());

            check(
                    deleted && deletedLesson == null,
                    "TC-08 - Delete an existing lesson"
            );
            
         // TC-09: Delete a nonexistent lesson

            boolean deletedNonexistent =
                    database.deleteLesson(999999);

            check(
                    !deletedNonexistent,
                    "TC-09 - Reject deletion of nonexistent lesson"
            );

            if (!deletedNonexistent) {
                System.out.println(
                        "Expected Error Message: Lesson not found. No lesson was deleted."
                );
            }
            
         // TC-10: Verify persistence between program executions

            LessonLearnedList existingPersistentLessons =
                    database.searchLessons("Persistence Test Lesson");

            if (existingPersistentLessons.isEmpty()) {

                LessonLearned persistentLesson = new LessonLearned(
                        "Persistence Test Lesson",
                        "This lesson should remain after the program is restarted."
                );

                database.createLesson(persistentLesson);

                System.out.println(
                        "TC-10 setup complete. Run the program again to verify persistence."
                );

            } else {

                check(
                        true,
                        "TC-10 - Lesson persists between program executions"
                );

                System.out.println(
                        "Persistent lesson found from a previous execution."
                );
            }
            database.closeConnection();

        } catch (Exception e) {

            System.out.println("FAIL: TC-01 - Exception occurred.");
            e.printStackTrace();
            failed++;
        }

        System.out.println();
        System.out.println("Tests Passed: " + passed);
        System.out.println("Tests Failed: " + failed);
    }

    private static void check(boolean condition, String testName) {

        if (condition) {
            System.out.println("PASS: " + testName);
            passed++;
        } else {
            System.out.println("FAIL: " + testName);
            failed++;
        }
    }
}
