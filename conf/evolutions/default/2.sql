# --- !Ups
CREATE TABLE word_up (
  item_id BIGINT NOT NULL,
  email VARCHAR(255) NOT NULL,
  CONSTRAINT PRIMARY KEY (item_id, email)
);

# --- !Downs
DROP TABLE word_up;