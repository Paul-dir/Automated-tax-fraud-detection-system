-- V7__add_audit_log_and_external_integrations.sql
CREATE TABLE IF NOT EXISTS audit_log (
                                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID REFERENCES users(id) ON DELETE SET NULL,
    action VARCHAR(100) NOT NULL,
    entity_type VARCHAR(50),
    entity_id UUID,
    old_values JSONB,
    new_values JSONB,
    ip_address INET,
    user_agent TEXT,
    digital_signature_hash TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS external_data_source (
                                                    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    endpoint_url VARCHAR(500),
    auth_type VARCHAR(20) CHECK (auth_type IN ('OAUTH2', 'API_KEY', 'MTLS')),
    credentials_encrypted TEXT,
    timeout_ms INT DEFAULT 10000,
    retry_count INT DEFAULT 3,
    is_active BOOLEAN DEFAULT TRUE
    );

CREATE TABLE IF NOT EXISTS external_query_log (
                                                  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    data_source_id UUID REFERENCES external_data_source(id),
    user_id UUID REFERENCES users(id),
    case_id UUID REFERENCES case_entity(id),
    query_params TEXT,
    response_status VARCHAR(20) CHECK (response_status IN ('SUCCESS', 'TIMEOUT', 'ERROR', 'RATE_LIMIT')),
    response_time_ms INT,
    error_message TEXT,
    queried_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS notification (
                                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID REFERENCES users(id) ON DELETE SET NULL,
    case_id UUID REFERENCES case_entity(id) ON DELETE SET NULL,
    channel VARCHAR(10) CHECK (channel IN ('EMAIL', 'SMS', 'IN_APP')),
    subject VARCHAR(255),
    body TEXT,
    status VARCHAR(10) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'SENT', 'FAILED', 'RETRY')),
    sent_at TIMESTAMP,
    retry_at TIMESTAMP,
    retry_count INT DEFAULT 0
    );

-- Create indexes for audit_log
CREATE INDEX idx_audit_user ON audit_log(user_id);
CREATE INDEX idx_audit_entity ON audit_log(entity_type, entity_id);
CREATE INDEX idx_audit_created ON audit_log(created_at);

-- Insert reference data: sampling config defaults
INSERT INTO sampling_config (name, method, parameters, is_default) VALUES
                                                                       ('Statistical Default', 'STATISTICAL', '{"confidence_level": 95, "margin_error": 5}', TRUE),
                                                                       ('Random Sample', 'RANDOM', '{"sample_size": 100}', FALSE),
                                                                       ('Judgmental High Value', 'JUDGMENTAL', '{"threshold": 100000}', FALSE);

-- Insert departments (example – adjust as needed)
INSERT INTO department (name, code, contact_email) VALUES
                                                       ('Intelligence Department', 'INTEL', 'intel@mor.gov.et'),
                                                       ('Investigation Department', 'INVEST', 'invest@mor.gov.et'),
                                                       ('Legal Affairs', 'LEGAL', 'legal@mor.gov.et'),
                                                       ('Enforcement Unit', 'ENFORCE', 'enforce@mor.gov.et');