-- SQL Language Support Demo
/* 
   This is a sample SQL file to demonstrate
   syntax highlighting capabilities.
*/

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

-- Insert some sample data
INSERT INTO users (username, email, is_active)
VALUES 
    ('john_doe', 'john@example.com', TRUE),
    ('jane_smith', 'jane@test.org', FALSE),
    ('admin_user', 'admin@system.local', TRUE);

-- Select active users with specific conditions
SELECT 
    id, 
    username, 
    UPPER(email) as formatted_email,
    CASE 
        WHEN is_active THEN 'Active'
        ELSE 'Inactive'
    END as status
FROM users
WHERE 
    created_at > '2023-01-01' 
    AND (username LIKE 'j%' OR email LIKE '%@example.com')
ORDER BY created_at DESC
LIMIT 10;

-- Update user status
UPDATE users
SET is_active = FALSE, updated_at = NOW()
WHERE id = 1;

-- Aggregate query with grouping
SELECT 
    COUNT(*) as total_users,
    SUM(CASE WHEN is_active THEN 1 ELSE 0 END) as active_count
FROM users;

-- Join example
SELECT u.username, p.profile_pic
FROM users u
LEFT JOIN profiles p ON u.id = p.user_id
WHERE p.bio IS NOT NULL;

DROP TABLE IF EXISTS temporary_logs;
