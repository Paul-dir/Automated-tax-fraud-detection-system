-- V4__add_investigation_plan_and_sampling.sql
CREATE TABLE IF NOT EXISTS investigation_plan (
                                                  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    case_id UUID REFERENCES case_entity(id) ON DELETE CASCADE,
    prepared_by_id UUID REFERENCES users(id),
    objectives TEXT NOT NULL,
    scope TEXT,
    methodology TEXT,
    resources TEXT,
    timeline TEXT,
    risk_assessment TEXT,
    status VARCHAR(20) DEFAULT 'DRAFT' CHECK (status IN ('DRAFT', 'SUBMITTED', 'APPROVED', 'REJECTED')),
    approved_by_id UUID REFERENCES users(id),
    approval_date TIMESTAMP,
    rejection_reason TEXT,
    version INT DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS sampling_config (
                                               id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    method VARCHAR(20) NOT NULL CHECK (method IN ('STATISTICAL', 'RANDOM', 'JUDGMENTAL')),
    parameters JSONB,
    is_default BOOLEAN DEFAULT FALSE
    );

CREATE TABLE IF NOT EXISTS sampling_result (
                                               id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    investigation_plan_id UUID REFERENCES investigation_plan(id) ON DELETE CASCADE,
    sampling_config_id UUID REFERENCES sampling_config(id),
    data_category VARCHAR(20) CHECK (data_category IN ('REVENUE', 'EXPENSE', 'TRANSACTION')),
    population_size INT,
    sample_size INT,
    sample_items JSONB,
    test_results JSONB,
    anomalies_count INT DEFAULT 0,
    material_discrepancy_found BOOLEAN DEFAULT FALSE,
    escalated_to_id UUID REFERENCES users(id),
    completed_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX idx_plan_case ON investigation_plan(case_id);
CREATE INDEX idx_sampling_plan ON sampling_result(investigation_plan_id);