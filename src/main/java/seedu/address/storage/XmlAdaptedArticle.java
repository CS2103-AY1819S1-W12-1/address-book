package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.article.Address;
import seedu.address.model.article.Email;
import seedu.address.model.article.Name;
import seedu.address.model.article.Article;
import seedu.address.model.article.Phone;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Article.
 */
public class XmlAdaptedArticle {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Article's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedArticle.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedArticle() {}

    /**
     * Constructs an {@code XmlAdaptedArticle} with the given article details.
     */
    public XmlAdaptedArticle(String name, String phone, String email, String address, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Article into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedArticle
     */
    public XmlAdaptedArticle(Article source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted article object into the model's Article object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted article
     */
    public Article toModelType() throws IllegalValueException {
        final List<Tag> articleTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            articleTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(articleTags);
        return new Article(modelName, modelPhone, modelEmail, modelAddress, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedArticle)) {
            return false;
        }

        XmlAdaptedArticle otherArticle = (XmlAdaptedArticle) other;
        return Objects.equals(name, otherArticle.name)
                && Objects.equals(phone, otherArticle.phone)
                && Objects.equals(email, otherArticle.email)
                && Objects.equals(address, otherArticle.address)
                && tagged.equals(otherArticle.tagged);
    }
}
