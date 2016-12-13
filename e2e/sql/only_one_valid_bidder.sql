UPDATE bidder SET deleted=true;
INSERT INTO bidder (name, endpoint) VALUES
    ('bid1', 'http://{{host}}?modifier=invalidjson&price=300'),
    ('bid2', 'http://{{host}}?modifier=invaliddata&price=299'),
    ('bid3', 'http://{{host}}?timeout=5000&price=298'),
    ('bid4', 'http://{{host}}?modifier=nobidnocontent&price=297'),
    ('bid5', 'http://{{host}}?modifier=nobidemptyjson&price=296'),
    ('bid6', 'http://{{host}}?modifier=nobidemptyseatbid&price=295'),
    ('bid7', 'http://{{host}}?price=294')

