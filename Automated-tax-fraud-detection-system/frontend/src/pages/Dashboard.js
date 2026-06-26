import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { caseService } from '../services/api';

function Dashboard() {
    const navigate = useNavigate();
    const [caseData, setCaseData] = useState({
        caseType: 'INTELLIGENCE',
        priority: 'MEDIUM',
        sourceChannel: 'PORTAL',
        description: '',
        notes: '',
    });
    const [message, setMessage] = useState('');
    const [registeredCase, setRegisteredCase] = useState(null);

    const handleLogout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('username');
        localStorage.removeItem('role');
        navigate('/login');
    };

    const handleChange = (e) => {
        setCaseData({ ...caseData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await caseService.register(caseData);
            setRegisteredCase(response.data);
            setMessage(`Case ${response.data.caseNumber} registered successfully!`);
            setCaseData({
                caseType: 'INTELLIGENCE',
                priority: 'MEDIUM',
                sourceChannel: 'PORTAL',
                description: '',
                notes: '',
            });
        } catch (err) {
            setMessage('Error registering case');
        }
    };

    const username = localStorage.getItem('username');
    const role = localStorage.getItem('role');

    return (
        <div style={styles.container}>
            <div style={styles.header}>
                <h2>TFIS Dashboard</h2>
                <div>
                    <span style={styles.userInfo}>{username} ({role})</span>
                    <button onClick={handleLogout} style={styles.logoutButton}>Logout</button>
                </div>
            </div>
            <div style={styles.content}>
                <div style={styles.card}>
                    <h3>Register New Case</h3>
                    {message && <p style={message.includes('success') ? styles.success : styles.error}>{message}</p>}
                    <form onSubmit={handleSubmit}>
                        <label>Case Type</label>
                        <select name="caseType" value={caseData.caseType} onChange={handleChange} style={styles.input}>
                            <option value="INTELLIGENCE">Intelligence</option>
                            <option value="REFERRAL">Referral</option>
                            <option value="INVESTIGATION">Investigation</option>
                            <option value="JOINT">Joint Operation</option>
                        </select>

                        <label>Priority</label>
                        <select name="priority" value={caseData.priority} onChange={handleChange} style={styles.input}>
                            <option value="LOW">Low</option>
                            <option value="MEDIUM">Medium</option>
                            <option value="HIGH">High</option>
                            <option value="URGENT">Urgent</option>
                        </select>

                        <label>Source Channel</label>
                        <select name="sourceChannel" value={caseData.sourceChannel} onChange={handleChange} style={styles.input}>
                            <option value="PORTAL">Portal</option>
                            <option value="SMS">SMS</option>
                            <option value="EMAIL">Email</option>
                            <option value="PHONE">Phone</option>
                            <option value="INTERNAL_ITAS">Internal ITAS</option>
                            <option value="SOCIAL_MEDIA">Social Media</option>
                        </select>

                        <label>Description</label>
                        <textarea name="description" value={caseData.description} onChange={handleChange} rows="3" style={styles.textarea} required />

                        <label>Notes (optional)</label>
                        <textarea name="notes" value={caseData.notes} onChange={handleChange} rows="2" style={styles.textarea} />

                        <button type="submit" style={styles.button}>Register Case</button>
                    </form>
                </div>

                {registeredCase && (
                    <div style={styles.card}>
                        <h3>Recently Registered Case</h3>
                        <p><strong>Case Number:</strong> {registeredCase.caseNumber}</p>
                        <p><strong>Status:</strong> {registeredCase.status}</p>
                        <p><strong>Priority:</strong> {registeredCase.priority}</p>
                        <p><strong>Description:</strong> {registeredCase.description}</p>
                    </div>
                )}
            </div>
        </div>
    );
}

const styles = {
    container: { fontFamily: 'Arial, sans-serif' },
    header: {
        backgroundColor: '#007bff',
        color: 'white',
        padding: '15px 20px',
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
    },
    userInfo: { marginRight: '15px' },
    logoutButton: {
        backgroundColor: '#dc3545',
        color: 'white',
        border: 'none',
        padding: '5px 10px',
        borderRadius: '4px',
        cursor: 'pointer',
    },
    content: { padding: '20px', display: 'flex', gap: '20px', flexWrap: 'wrap' },
    card: {
        backgroundColor: 'white',
        padding: '20px',
        borderRadius: '8px',
        boxShadow: '0 2px 4px rgba(0,0,0,0.1)',
        flex: '1',
        minWidth: '300px',
    },
    input: { width: '100%', padding: '8px', margin: '8px 0', borderRadius: '4px', border: '1px solid #ddd' },
    textarea: { width: '100%', padding: '8px', margin: '8px 0', borderRadius: '4px', border: '1px solid #ddd' },
    button: {
        width: '100%',
        padding: '10px',
        backgroundColor: '#28a745',
        color: 'white',
        border: 'none',
        borderRadius: '4px',
        cursor: 'pointer',
        marginTop: '10px',
    },
    success: { color: 'green' },
    error: { color: 'red' },
};

export default Dashboard;
