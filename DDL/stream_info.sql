CREATE TABLE `stream_info` (
  `stream_id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `id` int(11),
  `time` time,
  `description` varchar(1024),
  `is_delete` tinyint(1),
    FULLTEXT INDEX `idx_fulltext_description` (`description`) WITH PARSER ngram
);