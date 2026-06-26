-- V8__add_missing_timestamp_columns.sql
-- Add missing created_at and updated_at columns to tables that extend BaseEntity

-- 1. department table
ALTER TABLE department ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE department ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- 2. joint_operation_report table (has created_at, missing updated_at)
ALTER TABLE joint_operation_report ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- 3. external_data_source table
ALTER TABLE external_data_source ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE external_data_source ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- 4. sampling_result table (has created_at, missing updated_at)
ALTER TABLE sampling_result ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Optional: add any other missing columns if you find them
-- For example, if any entity expects 'created_by' or 'updated_by', add those too.