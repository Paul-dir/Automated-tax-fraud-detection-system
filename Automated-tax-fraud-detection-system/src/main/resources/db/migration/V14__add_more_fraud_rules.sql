-- V14__add_more_fraud_rules.sql
INSERT INTO fraud_criteria (name, rule_type, rule_value, priority, is_active, parameters) VALUES
                                                                                              ('High Taxpayer Risk', 'TAXPAYER_RISK', '>70', 25, TRUE, '{"min_risk": 70}'),
                                                                                              ('Sufficient Evidence', 'EVIDENCE_COUNT', '>=3', 5, TRUE, '{"min_evidence": 3}');