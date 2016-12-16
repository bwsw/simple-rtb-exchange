UPDATE bidder SET deleted=true;
INSERT INTO bidder (name, endpoint) VALUES
    ('bid1', 'http://{{host}}?price=300&modifier=winnotice-broken&winhost={{host}}'),
    ('bid2', 'http://{{host}}?price=299'),
    ('bid3', 'http://{{host}}?price=298');
