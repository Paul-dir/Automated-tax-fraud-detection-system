-- V9__add_missing_timestamps_for_base_entities.sql
-- Add missing created_at and updated_at columns to tables that extend BaseEntity

-- 1. users table (has created_at, missing updated_at)
ALTER TABLE users ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- 2. informant table (has created_at, missing updated_at)
ALTER TABLE informant ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- 3. taxpayer table (missing both created_at and updated_at)
ALTER TABLE taxpayer ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE taxpayer ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- 4. sampling_config table (missing both created_at and updated_at)
ALTER TABLE sampling_config ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE sampling_config ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- 5. (Optional) If any other table extends BaseEntity but missing columns, add here.
-- For example, check case_entity already has both, referral has both, etc.