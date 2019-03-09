DROP TABLE IF EXISTS reader CASCADE;
CREATE TABLE IF NOT EXISTS reader (
  reader_id serial primary key,
  reader_name character varying(255),
  reader_avatar character varying(255),
  reader_email character varying(255),
  reader_password character varying(15)
);

DROP TABLE IF EXISTS wishlist CASCADE;
CREATE TABLE IF NOT EXISTS wishlist (
  wishlist_id serial primary key,
  wishlist_name character varying(255),
  wishlist_reader bigint references reader(reader_id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS collection CASCADE;
CREATE TABLE IF NOT EXISTS collection (
  collection_id serial primary key,
  collection_name character varying(255)
);

DROP TABLE IF EXISTS friend CASCADE;
CREATE TABLE IF NOT EXISTS friend (
  friend_id serial primary key,
  friend_name character varying(255),
  friend_avatar character varying(255),
  friend_reader bigint references reader(reader_id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS book CASCADE;
CREATE TABLE IF NOT EXISTS book (
  book_id serial primary key,
  book_title character varying(255),
  book_authors character varying(255)[],
  book_isbn character varying(255),
  book_image character varying(255),
  book_language character varying(255),
  book_publisher character varying(255),
  book_date_published date,
  book_page integer,
  book_synopsis text,
  book_reader bigint references reader(reader_id) ON DELETE CASCADE,
  book_friend bigint references friend(friend_id)
);

DROP TABLE IF EXISTS review;
CREATE TABLE IF NOT EXISTS review (
  review_id serial primary key,
  review_rating integer,
  review_comment character varying(255),
  review_book bigint references book(book_id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS book_collection;
CREATE TABLE IF NOT EXISTS book_collection (
  book_collection_id serial primary key,
  book_collection_book_id bigint references book(book_id),
  book_collection_collection_id bigint references collection(collection_id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS book_wishlist;
CREATE TABLE IF NOT EXISTS book_wishlist (
  book_wishlist_id serial primary key,
  book_wishlist_book_id bigint references book(book_id),
  book_wishlist_wishlist_id bigint references wishlist(wishlist_id) ON DELETE CASCADE
);


