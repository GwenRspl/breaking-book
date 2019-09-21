package com.projects.breakingbook.business.service.utils;

import com.projects.breakingbook.business.entity.Book;

public class ServiceUtils {

    public static <T> T getValueOrDefaultString(final T value, final T defaultValue) {
        return value == null ? defaultValue : value;
    }

    public static int getValueOrDefaultInteger(final int value, final int defaultValue) {
        return value == 0 ? defaultValue : value;
    }

    public static Book generateBooksAttributes(final Book newBook, final Book originalBook) {
        newBook.setTitle(getValueOrDefaultString(newBook.getTitle(), originalBook.getTitle()));
        newBook.setAuthors(getValueOrDefaultString(newBook.getAuthors(), originalBook.getAuthors()));
        newBook.setIsbn(getValueOrDefaultString(newBook.getIsbn(), originalBook.getIsbn()));
        newBook.setImage(getValueOrDefaultString(newBook.getImage(), originalBook.getImage()));
        newBook.setLanguage(getValueOrDefaultString(newBook.getLanguage(), originalBook.getLanguage()));
        newBook.setPublisher(getValueOrDefaultString(newBook.getPublisher(), originalBook.getPublisher()));
        newBook.setDatePublished(getValueOrDefaultString(newBook.getDatePublished(), originalBook.getDatePublished()));
        newBook.setPages(getValueOrDefaultInteger(newBook.getPages(), originalBook.getPages()));
        newBook.setSynopsis(getValueOrDefaultString(newBook.getSynopsis(), originalBook.getSynopsis()));
        newBook.setUser(getValueOrDefaultString(newBook.getUser(), originalBook.getUser()));
        newBook.setRating(getValueOrDefaultInteger(newBook.getRating(), originalBook.getRating()));
        newBook.setComment(getValueOrDefaultString(newBook.getComment(), originalBook.getComment()));
        return newBook;
    }
}
