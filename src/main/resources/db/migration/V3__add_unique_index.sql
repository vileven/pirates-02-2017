CREATE UNIQUE INDEX IF NOT EXISTS idx_scores_on_user_id
  ON scores (user_id);
