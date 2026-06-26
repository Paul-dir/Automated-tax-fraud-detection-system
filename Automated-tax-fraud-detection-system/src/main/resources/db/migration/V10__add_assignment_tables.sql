-- V10__add_assignment_tables.sql
-- Tables for automatic case assignment

CREATE TABLE IF NOT EXISTS assignment_rule (
                                               id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    rule_type VARCHAR(50) NOT NULL,
    rule_value VARCHAR(255),
    priority INT,
    is_active BOOLEAN DEFAULT TRUE,
    parameters JSONB,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS assignment (
                                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    assignable_type VARCHAR(50) NOT NULL,
    assignable_id UUID NOT NULL,
    assigned_to_id UUID NOT NULL REFERENCES users(id),
    assigned_by_id UUID REFERENCES users(id),
    assignment_type VARCHAR(20) NOT NULL,
    rule_used TEXT,
    justification TEXT,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP,
    status VARCHAR(20) DEFAULT 'ACTIVE'
    );

CREATE INDEX idx_assignment_assigned_to ON assignment(assigned_to_id);
CREATE INDEX idx_assignment_status ON assignment(status);
CREATE INDEX idx_assignment_assignable ON assignment(assignable_type, assignable_id);

-- Insert default assignment rules
INSERT INTO assignment_rule (name, rule_type, rule_value, priority, is_active, description) VALUES
                                                                                                ('Intelligence Officer Default', 'ROLE', 'INTELLIGENCE_OFFICER', 10, TRUE, 'Default assignment for intelligence cases'),
                                                                                                ('Investigation Officer Default', 'ROLE', 'INVESTIGATION_OFFICER', 10, TRUE, 'Default assignment for investigation cases'),
                                                                                                ('Workload Balancing', 'WORKLOAD', 'MIN_ACTIVE_CASES', 5, TRUE, 'Assign to officer with fewest active cases'),
                                                                                                ('High Priority Cases', 'PRIORITY', 'HIGH', 20, TRUE, 'Assign high-priority cases to senior officers');