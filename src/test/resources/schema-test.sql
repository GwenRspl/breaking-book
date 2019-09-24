DROP TABLE IF EXISTS breaking_book_user CASCADE;
DROP TABLE IF EXISTS wishlist CASCADE;
DROP TABLE IF EXISTS collection CASCADE;
DROP TABLE IF EXISTS friend CASCADE;
DROP TABLE IF EXISTS book CASCADE;
DROP TABLE IF EXISTS book_collection;
DROP TABLE IF EXISTS book_wishlist;
DROP TABLE IF EXISTS book_friend;

CREATE TABLE IF NOT EXISTS breaking_book_user
(
    breaking_book_user_id       serial primary key,
    breaking_book_user_username character varying(255),
    breaking_book_user_avatar   character varying(255),
    breaking_book_user_email    character varying(255),
    breaking_book_user_password character varying(255),
    breaking_book_user_role     character varying(255)
);
CREATE TABLE IF NOT EXISTS wishlist
(
    wishlist_id                 serial primary key,
    wishlist_name               character varying(255),
    wishlist_breaking_book_user bigint references breaking_book_user (breaking_book_user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS collection
(
    collection_id                 serial primary key,
    collection_name               character varying(255),
    collection_breaking_book_user bigint references breaking_book_user (breaking_book_user_id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS friend
(
    friend_id                 serial primary key,
    friend_name               character varying(255),
    friend_avatar             character varying(255),
    friend_breaking_book_user bigint references breaking_book_user (breaking_book_user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS book
(
    book_id                 serial primary key,
    book_title              character varying(255),
    book_authors            character varying(255)[],
    book_isbn               character varying(255),
    book_image              character varying(255),
    book_language           character varying(255),
    book_publisher          character varying(255),
    book_date_published     date,
    book_pages              integer,
    book_synopsis           text,
    book_rating             integer,
    book_comment            text,
    book_owned              boolean,
    book_status             character varying(255),
    book_breaking_book_user bigint references breaking_book_user (breaking_book_user_id) ON DELETE CASCADE,
    book_friend             bigint references friend (friend_id)
);
CREATE TABLE IF NOT EXISTS book_collection
(
    book_collection_book_id       bigint references book (book_id) ON DELETE CASCADE,
    book_collection_collection_id bigint references collection (collection_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS book_wishlist
(
    book_wishlist_book_id     bigint references book (book_id) ON DELETE CASCADE,
    book_wishlist_wishlist_id bigint references wishlist (wishlist_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS book_friend
(
    book_friend_book_id   bigint references book (book_id) ON DELETE CASCADE,
    book_friend_friend_id bigint references friend (friend_id) ON DELETE CASCADE
);


