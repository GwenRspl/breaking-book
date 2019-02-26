INSERT INTO public.reader(
  name, avatar, email, password)
VALUES ('name', 'avatar', 'mail', 'mdp');

INSERT INTO public.friend(
  name, avatar, reader)
VALUES ('friend', 'avatar', 1);

INSERT INTO public.book(
  title, authors, isbn, image, language, publisher, date_published, edition, page, overview, synopsis, subjects, reviews_api, reader, friend)
VALUES ('titre', '{author 1, author 2}', 7984653, 'image', 'language','publisher', '2013-06-01', 'edition', 123, 'overview', 'synopsis', '{subject 1, subject 2}', 'review api', 1, 1);

INSERT INTO public.review(
  rating, comment, book)
VALUES (4, 'blabla', 1);

INSERT INTO public.collection(
  name)
VALUES ('collection 1');

INSERT INTO public.wishlist(
  name, reader)
VALUES ('wishlist 1', 1);

INSERT INTO public.book_collection(
  book_id, collection_id)
VALUES (1, 1);

INSERT INTO public.book_wishlist(
  book_id, wishlist_id)
VALUES (1, 1);