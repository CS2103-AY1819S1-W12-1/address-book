package seedu.lostandfound.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.lostandfound.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.lostandfound.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.Test;

import seedu.lostandfound.logic.commands.EditCommand.EditArticleDescriptor;
import seedu.lostandfound.testutil.EditArticleDescriptorBuilder;

public class EditArticleDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditArticleDescriptor descriptorWithSameValues = new EditArticleDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditArticleDescriptor editedAmy = new EditArticleDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditArticleDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditArticleDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different description -> returns false
        editedAmy = new EditArticleDescriptorBuilder(DESC_AMY).withDescription(VALID_DESCRIPTION_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditArticleDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
