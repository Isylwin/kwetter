package nl.oscar.kwetter.service.kwetter.persistence;

public interface AuthorPersister {
    void addKwetterToAuthor(long id, long author);

    void removeKwetterFromAuthor(long id, long author);
}
