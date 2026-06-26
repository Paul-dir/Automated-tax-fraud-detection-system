-- V13__add_risk_score_to_taxpayer.sql
ALTER TABLE taxpayer ADD COLUMN IF NOT EXISTS risk_score INT DEFAULT 0;