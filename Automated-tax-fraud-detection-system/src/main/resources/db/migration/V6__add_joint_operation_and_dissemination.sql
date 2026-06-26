-- V6__add_joint_operation_and_dissemination.sql
CREATE TABLE IF NOT EXISTS joint_operation_report (
                                                      id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    case_id UUID REFERENCES case_entity(id) ON DELETE CASCADE,
    prepared_by_team_id UUID,  -- could reference a team, but we keep simple for now
    findings TEXT NOT NULL,
    recommendations TEXT,
    status VARCHAR(20) DEFAULT 'DRAFT' CHECK (status IN ('DRAFT', 'SIGNED', 'SUBMITTED', 'APPROVED', 'REJECTED')),
    digital_signature_hash TEXT,
    approved_by_id UUID REFERENCES users(id),
    approval_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS department (
                                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    code VARCHAR(20) UNIQUE NOT NULL,
    contact_email VARCHAR(100),
    contact_phone VARCHAR(20)
    );

CREATE TABLE IF NOT EXISTS dissemination (
                                             id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    case_id UUID REFERENCES case_entity(id) ON DELETE CASCADE,
    intelligence_report_id UUID REFERENCES intelligence_report(id) ON DELETE SET NULL,
    department_id UUID REFERENCES department(id),
    method VARCHAR(20) CHECK (method IN ('SECURE_ENCRYPTED', 'INTERNAL_PORTAL', 'EMAIL')),
    disseminated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    disseminated_by_id UUID REFERENCES users(id),
    watermark_info TEXT
    );

CREATE INDEX idx_joint_case ON joint_operation_report(case_id);
CREATE INDEX idx_dissemination_case ON dissemination(case_id);