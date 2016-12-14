UPDATE bidder SET deleted=true;
INSERT INTO bidder (name, endpoint) VALUES
    ('bid1', 'http://{{host}}?modifier=nobidnocontent'),
    ('bid2', 'http://{{host}}?modifier=nobidemptyjson'),
    ('bid3', 'http://{{host}}?modifier=nobidemptyseatbid')

