-- V1__create_core_tables.sql
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS users (
                                     id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL CHECK (role IN ('INTELLIGENCE_OFFICER', 'INVESTIGATION_OFFICER', 'TEAM_LEADER', 'PROCESS_OWNER', 'DIRECTOR', 'ADMIN')),
    department_id UUID,
    mfa_enabled BOOLEAN DEFAULT FALSE,
    public_key_cert TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS informant (
                                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(200),
    contact_info VARCHAR(200),
    tax_id VARCHAR(50),
    is_anonymous BOOLEAN DEFAULT FALSE,
    preferred_channel VARCHAR(20) CHECK (preferred_channel IN ('SMS', 'EMAIL', 'PORTAL', 'PHONE')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS taxpayer (
                                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tin VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(200) NOT NULL,
    registration_number VARCHAR(50),
    business_type VARCHAR(100),
    address TEXT,
    contact_person VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100)
    );

CREATE TABLE IF NOT EXISTS case_entity (
                                           id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    case_number VARCHAR(50) UNIQUE NOT NULL,
    case_type VARCHAR(20) NOT NULL CHECK (case_type IN ('INTELLIGENCE', 'REFERRAL', 'INVESTIGATION', 'JOINT')),
    status VARCHAR(30) NOT NULL DEFAULT 'REGISTERED' CHECK (status IN ('REGISTERED', 'ASSIGNED', 'ANALYSIS', 'REPORT_PREPARATION', 'REVIEW', 'APPROVED', 'INVESTIGATION', 'CLOSED', 'REJECTED')),
    priority VARCHAR(10) NOT NULL DEFAULT 'MEDIUM' CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH', 'URGENT')),
    informant_id UUID REFERENCES informant(id) ON DELETE SET NULL,
    taxpayer_id UUID REFERENCES taxpayer(id) ON DELETE SET NULL,
    assigned_officer_id UUID REFERENCES users(id) ON DELETE SET NULL,
    assigned_team_leader_id UUID REFERENCES users(id) ON DELETE SET NULL,
    source_channel VARCHAR(20) CHECK (source_channel IN ('SMS', 'EMAIL', 'PORTAL', 'PHONE', 'INTERNAL_ITAS', 'SOCIAL_MEDIA')),
    description TEXT,
    notes TEXT,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    target_completion_date DATE,
    actual_completion_date DATE,
    fraud_eligible BOOLEAN DEFAULT FALSE,
    closure_reason TEXT,
    metadata JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX idx_case_status ON case_entity(status);
CREATE INDEX idx_case_assigned_officer ON case_entity(assigned_officer_id);
CREATE INDEX idx_case_taxpayer ON case_entity(taxpayer_id);
CREATE INDEX idx_case_registration_date ON case_entity(registration_date);
CREATE INDEX idx_users_role ON users(role);