-- V12__create_fraud_criteria_table.sql
CREATE TABLE IF NOT EXISTS fraud_criteria (
                                              id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    description TEXT,
    rule_type VARCHAR(50) NOT NULL,
    rule_value VARCHAR(255),
    priority INT DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    parameters JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Insert some default criteria
INSERT INTO fraud_criteria (name, rule_type, rule_value, priority, is_active, parameters) VALUES
                                                                                              ('High Priority Case', 'CASE_PRIORITY', 'HIGH', 10, TRUE, '{"threshold": "HIGH"}'),
                                                                                              ('Urgent Priority Case', 'CASE_PRIORITY', 'URGENT', 20, TRUE, '{"threshold": "URGENT"}'),
                                                                                              ('Taxpayer Risk Score > 80', 'TAXPAYER_RISK', '>80', 15, TRUE, '{"min_risk": 80}');