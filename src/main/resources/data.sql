INSERT INTO public.reader(
  reader_name, reader_avatar, reader_email, reader_password)
VALUES ('name', 'avatar', 'mail', 'mdp');

INSERT INTO public.friend(
  friend_name, friend_avatar, friend_reader)
VALUES ('friend', 'avatar', 1);

INSERT INTO public.book(
  book_title, book_authors, book_isbn, book_image, book_language, book_publisher, book_date_published, book_page,
  book_synopsis, book_reader, book_friend)
VALUES ('titre', '{author 1, author 2}', 7984653, 'image', 'language','publisher', '2013-06-01', 123, 'synopsis', 1, 1);

INSERT INTO public.review(
  review_rating, review_comment, review_book)
VALUES (4, 'blabla', 1);

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