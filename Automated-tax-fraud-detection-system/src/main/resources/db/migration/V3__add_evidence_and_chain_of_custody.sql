-- V3__add_evidence_and_chain_of_custody.sql
CREATE TABLE IF NOT EXISTS evidence (
                                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    case_id UUID REFERENCES case_entity(id) ON DELETE CASCADE,
    referral_id UUID REFERENCES referral(id) ON DELETE SET NULL,
    evidence_type VARCHAR(30) NOT NULL CHECK (evidence_type IN ('DOCUMENT', 'INTERVIEW_RECORDING', 'PHOTO', 'EXTERNAL_DATA', 'OTHER')),
    file_name VARCHAR(255) NOT NULL,
    file_path TEXT NOT NULL,
    file_hash VARCHAR(64) NOT NULL,
    file_size_bytes INT,
    mime_type VARCHAR(100),
    description TEXT,
    source VARCHAR(50) CHECK (source IN ('INFORMANT', 'OFFICER_UPLOAD', 'ITAS', 'EXTERNAL_API')),
    chain_of_custody_id UUID,
    status VARCHAR(20) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'VERIFIED', 'ADMISSIBLE', 'REJECTED')),
    uploaded_by_id UUID REFERENCES users(id) ON DELETE SET NULL,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS chain_of_custody (
                                                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    evidence_id UUID REFERENCES evidence(id) ON DELETE CASCADE,
    handler_id UUID REFERENCES users(id) ON DELETE SET NULL,
    action VARCHAR(50) NOT NULL CHECK (action IN ('RECEIVED', 'TRANSFERRED', 'REVIEWED', 'ANALYZED', 'STORED')),
    location VARCHAR(255),
    remarks TEXT,
    handled_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX idx_evidence_case ON evidence(case_id);
CREATE INDEX idx_evidence_status ON evidence(status);
CREATE INDEX idx_custody_evidence ON chain_of_custody(evidence_id);