DROP TABLE IF EXISTS reader CASCADE;
CREATE TABLE IF NOT EXISTS reader (
  id serial primary key,
  name character varying(255),
  avatar character varying(255),
  email character varying(255),
  password character varying(15)
);


DROP TABLE IF EXISTS wishlist CASCADE;
CREATE TABLE IF NOT EXISTS wishlist (
  id serial primary key,
  name character varying(255),
  reader bigint references reader(id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS collection CASCADE;
CREATE TABLE IF NOT EXISTS collection (
  id serial primary key,
  name character varying(255)
);


DROP TABLE IF EXISTS friend CASCADE;
CREATE TABLE IF NOT EXISTS friend (
  id serial primary key,
  name character varying(255),
  avatar character varying(255),
  reader bigint references reader(id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS book CASCADE;
CREATE TABLE IF NOT EXISTS book (
  id serial primary key,
  title character varying(255),
  authors character varying(255)[],
  isbn character varying(255),
  image character varying(255),
  language character varying(255),
  publisher character varying(255),
  date_published date,
  edition character varying(255),
  page integer,
  overview character varying(255),
  synopsis text,
  subjects character varying(255)[],
  reviews_api character varying(255),
  reader bigint references reader(id) ON DELETE CASCADE,
  friend bigint references friend(id)
);

DROP TABLE IF EXISTS review;
CREATE TABLE IF NOT EXISTS review (
  id serial primary key,
  rating integer,
  comment character varying(255),
  book bigint references book(id) ON DELETE CASCADE
);


DROP TABLE IF EXISTS book_collection;
CREATE TABLE IF NOT EXISTS book_collection (
  id serial primary key,
  book_id bigint references book(id),
  collection_id bigint references collection(id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS book_wishlist;
CREATE TABLE IF NOT EXISTS book_wishlist (
  id serial primary key,
  book_id bigint references book(id),
  wishlist_id bigint references wishlist(id) ON DELETE CASCADE
);


