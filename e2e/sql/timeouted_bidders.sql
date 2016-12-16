UPDATE bidder SET deleted=true;
INSERT INTO bidder (name, endpoint) VALUES
    ('bid1', 'http://{{host}}?timeout=5000'),
    ('bid2', 'http://{{host}}?timeout=5000'),
    ('bid3', 'http://{{host}}?timeout=5000')

