UPDATE bidder SET deleted=true;
INSERT INTO bidder (name, endpoint) VALUES
    ('bid1', 'http://{{host}}?price=299'),
    ('bid2', 'http://{{host}}?price=298'),
    ('bid3', 'http://{{host}}?price=297'),
    ('bid4', 'http://{{host}}?price=301'),
    ('bid5', 'http://{{host}}?price=296'),
    ('bid6', 'http://{{host}}?price=295'),
    ('bid7', 'http://{{host}}?price=294')

