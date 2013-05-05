# --- !Ups
CREATE TABLE profile (
  nickname VARCHAR(25) NOT NULL UNIQUE,
  user_id BIGINT UNIQUE NOT NULL,
  CONSTRAINT pk_profile PRIMARY KEY (nickname)
);

# --- !Downs
DROP TABLE profile;