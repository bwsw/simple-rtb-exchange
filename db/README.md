DB migration
============

[Liquibase](http://www.liquibase.org/) is used for database migration.

Configuration
-------------

\<env\>*.liquibase.properties is used for each environment.

Usage
-----

To run Liquibase execute the following command:
    
    make run env=<env> command=<liquibase command>
    
where \<env\> is a prefix of liquibase properties file,

e.g.
    
    make run env=unit command=update
    
Required libraries will be downloaded automatically.
