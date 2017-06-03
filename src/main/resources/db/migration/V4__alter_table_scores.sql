ALTER TABLE scores ADD COLUMN kills BIGINT NOT NULL;
ALTER TABLE scores ADD COLUMN max_combo BIGINT NOT NULL;

DROP INDEX idx_scores_on_user_id_scores;

CREATE INDEX idx_scores_on_user_id_scores
  ON scores(user_id, score, kills, max_combo);
