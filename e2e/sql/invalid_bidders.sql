UPDATE bidder SET deleted=true;
INSERT INTO bidder (name, endpoint) VALUES
    ('bid1', 'http://{{host}}?modifier=invalidjson'),
    ('bid2', 'http://{{host}}?modifier=invaliddata'),
    ('bid3', 'http://{{host}}?modifier=invalidjson')

