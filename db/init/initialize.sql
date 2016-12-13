CREATE USER rtb_user WITH PASSWORD 'rtb_user';
CREATE DATABASE rtb_exchange_test;
GRANT ALL PRIVILEGES ON DATABASE rtb_exchange_test to rtb_user;
CREATE DATABASE rtb_exchange_e2e;
GRANT ALL PRIVILEGES ON DATABASE rtb_exchange_e2e to rtb_user;
