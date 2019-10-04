-- DROP TABLE IF EXISTS breaking_book_user CASCADE;
-- DROP TABLE IF EXISTS wishlist CASCADE;
-- DROP TABLE IF EXISTS collection CASCADE;
-- DROP TABLE IF EXISTS friend CASCADE;
-- DROP TABLE IF EXISTS book CASCADE;
-- DROP TABLE IF EXISTS book_collection;
-- DROP TABLE IF EXISTS book_wishlist;
-- DROP TABLE IF EXISTS book_friend;

CREATE TABLE IF NOT EXISTS breaking_book_user
(
    breaking_book_user_id       serial primary key not null,
    breaking_book_user_username character varying(255) not null,
    breaking_book_user_avatar   character varying(255),
    breaking_book_user_email    character varying(255) not null,
    breaking_book_user_password character varying(255) not null,
    breaking_book_user_role     character varying(255) not null
);
CREATE TABLE IF NOT EXISTS wishlist
(
    wishlist_id                 serial primary key not null,
    wishlist_name               character varying(255) not null,
    wishlist_breaking_book_user bigint references breaking_book_user (breaking_book_user_id) ON DELETE CASCADE not null
);

CREATE TABLE IF NOT EXISTS collection
(
    collection_id                 serial primary key not null,
    collection_name               character varying(255) not null,
    collection_breaking_book_user bigint references breaking_book_user (breaking_book_user_id) ON DELETE CASCADE not null
);
CREATE TABLE IF NOT EXISTS friend
(
    friend_id                 serial primary key not null,
    friend_name               character varying(255) not null,
    friend_avatar             character varying(255),
    friend_breaking_book_user bigint references breaking_book_user (breaking_book_user_id) ON DELETE CASCADE not null
);

CREATE TABLE IF NOT EXISTS book
(
    book_id                 serial primary key not null,
    book_title              character varying(255) not null,
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
    book_owned              boolean not null,
    book_status             character varying(255) not null,
    book_breaking_book_user bigint references breaking_book_user (breaking_book_user_id) ON DELETE CASCADE not null,
    book_friend             bigint references friend (friend_id)
);
CREATE TABLE IF NOT EXISTS book_collection
(
    book_collection_book_id       bigint references book (book_id) ON DELETE CASCADE not null,
    book_collection_collection_id bigint references collection (collection_id) ON DELETE CASCADE not null
);

CREATE TABLE IF NOT EXISTS book_wishlist
(
    book_wishlist_book_id     bigint references book (book_id) ON DELETE CASCADE not null,
    book_wishlist_wishlist_id bigint references wishlist (wishlist_id) ON DELETE CASCADE not null
);

CREATE TABLE IF NOT EXISTS book_friend
(
    book_friend_book_id   bigint references book (book_id) ON DELETE CASCADE not null,
    book_friend_friend_id bigint references friend (friend_id) ON DELETE CASCADE not null
);


