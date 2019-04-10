INSERT INTO public.reader(
  reader_username, reader_avatar, reader_email, reader_password, reader_role)
VALUES ('username', 'avatar', 'mail', 'mdp', 'ROLE_USER');

INSERT INTO public.friend(
  friend_name, friend_avatar, friend_reader)
VALUES ('friend', 'avatar', 1);

INSERT INTO public.book(
  book_title, book_authors, book_isbn, book_image, book_language, book_publisher, book_date_published, book_pages,
  book_synopsis, book_rating, book_comment, book_read, book_owned, book_reader, book_friend)
VALUES ('titre', '{author 1, author 2}', 7984653, 'image', 'language','publisher', '2013-06-01', 123, 'synopsis', 3, 'blabla', FALSE, TRUE, 1, 1),
       ('titre2', '{author 1, author 2}', 98456845414, 'image2', 'language5','publisher', '2013-06-01', 488, 'synopsis', 2, 'blabla', TRUE, TRUE, 1, null);

INSERT INTO public.collection(
  collection_name)
VALUES ('collection 1');

INSERT INTO public.wishlist(
  wishlist_name, wishlist_reader)
VALUES ('wishlist 1', 1);

INSERT INTO public.book_collection(
  book_collection_book_id, book_collection_collection_id)
VALUES (1, 1);

INSERT INTO public.book_wishlist(
  book_wishlist_book_id, book_wishlist_wishlist_id)
VALUES (1, 1);

INSERT INTO public.book_friend(
  book_friend_book_id, book_friend_friend_id)
VALUES (2, 1);