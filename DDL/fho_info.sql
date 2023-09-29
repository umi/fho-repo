CREATE TABLE fho_info (
  id int(11) NOT NULL AUTO_INCREMENT,
  youtube_id varchar(256),
  title varchar(256),
  stream_start datetime,
  total int(11),
  is_member tinyint(1),
  is_delete tinyint(1),
    PRIMARY KEY (id)
);