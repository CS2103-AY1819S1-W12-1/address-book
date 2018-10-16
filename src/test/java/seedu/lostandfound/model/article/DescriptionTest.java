package seedu.lostandfound.model.article;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.lostandfound.testutil.Assert;

public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // null description
        Assert.assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // invalid descriptions
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only

        // valid descriptions
        assertTrue(Description.isValidDescription("Blk 456, Den Road, #01-355"));
        assertTrue(Description.isValidDescription("-")); // one character
        assertTrue(Description.isValidDescription(
                "Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long description
    }
}
