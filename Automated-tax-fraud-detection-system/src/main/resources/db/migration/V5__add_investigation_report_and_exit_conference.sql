-- V5__add_investigation_report_and_exit_conference.sql
CREATE TABLE IF NOT EXISTS investigation_report (
                                                    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    case_id UUID REFERENCES case_entity(id) ON DELETE CASCADE,
    prepared_by_id UUID REFERENCES users(id),
    reviewed_by_id UUID REFERENCES users(id),
    approved_by_id UUID REFERENCES users(id),
    findings TEXT NOT NULL,
    conclusions TEXT,
    recommendations TEXT,
    penalty_calculation TEXT,
    report_status VARCHAR(20) DEFAULT 'DRAFT' CHECK (report_status IN ('DRAFT', 'UNDER_REVIEW', 'APPROVED', 'REJECTED')),
    review_comments TEXT,
    exit_conference_date TIMESTAMP,
    exit_conference_minutes TEXT,
    digital_signature_hash TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Exit conference is part of investigation_report, but we also track separately if needed
-- Add any missing indexes
CREATE INDEX idx_investigation_report_case ON investigation_report(case_id);