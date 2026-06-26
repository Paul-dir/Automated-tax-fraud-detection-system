-- V2__add_referral_and_intelligence_report.sql
-- Depends on V1 tables (users, informant, taxpayer, case_entity)

-- Referral table (fraud referrals submitted by informants)
CREATE TABLE IF NOT EXISTS referral (
                                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    referral_number VARCHAR(50) UNIQUE NOT NULL,
    case_id UUID REFERENCES case_entity(id) ON DELETE SET NULL,
    informant_id UUID REFERENCES informant(id) ON DELETE SET NULL,
    submission_channel VARCHAR(20) NOT NULL CHECK (submission_channel IN ('WEB_FORM', 'SMS', 'EMAIL', 'PHONE', 'IN_PERSON')),
    allegation_summary TEXT NOT NULL,
    details TEXT,
    attachments_metadata JSONB,
    evaluation_status VARCHAR(20) DEFAULT 'PENDING' CHECK (evaluation_status IN ('PENDING', 'EVALUATED', 'RECOMMENDED', 'APPROVED', 'REJECTED')),
    evaluated_by_id UUID REFERENCES users(id) ON DELETE SET NULL,
    evaluation_date TIMESTAMP,
    evaluation_notes TEXT,
    recommendation VARCHAR(20) CHECK (recommendation IN ('INVESTIGATE', 'CLOSE', 'MORE_INFO')),
    approved_by_id UUID REFERENCES users(id) ON DELETE SET NULL,
    approval_date TIMESTAMP,
    approval_justification TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Intelligence Report table (output of intelligence analysis)
CREATE TABLE IF NOT EXISTS intelligence_report (
                                                   id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    report_number VARCHAR(50) UNIQUE NOT NULL,
    case_id UUID REFERENCES case_entity(id) ON DELETE CASCADE,
    findings TEXT NOT NULL,
    conclusions TEXT,
    recommendations TEXT,
    strength_weakness_doc TEXT,
    prepared_by_id UUID REFERENCES users(id) ON DELETE SET NULL,
    reviewed_by_id UUID REFERENCES users(id) ON DELETE SET NULL,
    approved_by_id UUID REFERENCES users(id) ON DELETE SET NULL,
    review_status VARCHAR(20) DEFAULT 'DRAFT' CHECK (review_status IN ('DRAFT', 'SUBMITTED', 'UNDER_REVIEW', 'APPROVED', 'REJECTED')),
    review_comments TEXT,
    submission_date TIMESTAMP,
    approval_date TIMESTAMP,
    digital_signature_hash TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Indexes for performance
CREATE INDEX idx_referral_case ON referral(case_id);
CREATE INDEX idx_referral_status ON referral(evaluation_status);
CREATE INDEX idx_intelligence_report_case ON intelligence_report(case_id);
CREATE INDEX idx_intelligence_report_status ON intelligence_report(review_status);