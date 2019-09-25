INSERT INTO public.breaking_book_user(breaking_book_user_username, breaking_book_user_avatar, breaking_book_user_email,
                                      breaking_book_user_password, breaking_book_user_role)
VALUES ('username', 'avatar', 'mail', 'mdp', 'ROLE_USER');

INSERT INTO public.friend(friend_name, friend_avatar, friend_breaking_book_user)
VALUES ('friendId', 'avatar', 1);

INSERT INTO public.book(book_title, book_authors, book_isbn, book_image, book_language, book_publisher,
                        book_date_published, book_pages,
                        book_synopsis, book_rating, book_comment, book_status, book_owned, book_breaking_book_user,
                        book_friend)
VALUES ('titre', '{author 1, author 2}', 7984653,
        'http://books.google.com/books/content?id=oc2KMwEACAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api',
        'language', 'publisher', '2013-06-01', 123, 'synopsis', 3,
        'blabla', 'UNREAD', TRUE, 1, 1),
       ('titre2', '{author 1, author 2}', 98456845414, 'https://memegenerator.net/img/images/16143029.jpg', 'language5',
        'publisher', '2013-06-01', 488,
        'synopsis', 2, 'blabla', 'ONGOING', TRUE, 1, null);

INSERT INTO public.collection(collection_name, collection_breaking_book_user)
VALUES ('collection 1', 1);

INSERT INTO public.wishlist(wishlist_name, wishlist_breaking_book_user)
VALUES ('wishlist 1', 1);

INSERT INTO public.book_collection(book_collection_book_id, book_collection_collection_id)
VALUES (1, 1),
       (2, 1);

INSERT INTO public.book_wishlist(book_wishlist_book_id, book_wishlist_wishlist_id)
VALUES (1, 1),
       (2, 1);

INSERT INTO public.book_friend(book_friend_book_id, book_friend_friend_id)
VALUES (2, 1);
