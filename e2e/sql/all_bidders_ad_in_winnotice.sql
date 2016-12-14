UPDATE bidder SET deleted=true;
INSERT INTO bidder (name, endpoint) VALUES
    ('bid1', 'http://{{host}}?price=299&modifier=winnotice-withadm&winhost={{host}}'),
    ('bid2', 'http://{{host}}?price=298&modifier=winnotice-withadm&winhost={{host}}'),
    ('bid3', 'http://{{host}}?price=297&modifier=winnotice-withadm&winhost={{host}}'),
    ('bid4', 'http://{{host}}?price=301&modifier=winnotice-withadm&winhost={{host}}'),
    ('bid5', 'http://{{host}}?price=296&modifier=winnotice-withadm&winhost={{host}}'),
    ('bid6', 'http://{{host}}?price=295&modifier=winnotice-withadm&winhost={{host}}'),
    ('bid7', 'http://{{host}}?price=294&modifier=winnotice-withadm&winhost={{host}}');
