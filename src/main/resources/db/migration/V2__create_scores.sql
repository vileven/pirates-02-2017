CREATE TABLE IF NOT EXISTS scores (
  user_id BIGINT REFERENCES users(id) NOT NULL ,
  score   BIGINT NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_scores_on_user_id_scores
  ON scores (user_id, score);
