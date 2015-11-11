insert into user_account (email) values ('teste@teste.com');

INSERT INTO user_group
(group_id, user_id)
SELECT 1 as group_id, MAX(id) as user_id
FROM user_account;

INSERT INTO authentication
(username, password, user_account_id)
SELECT 'selenium' as username, sha2('teste',256) as password, MAX(id) as user_account_id from user_account; 