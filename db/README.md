DB migration
============

[Liquibase](http://www.liquibase.org/) is used for database migration.

Configuration
-------------

\<env\>.liquibase.properties is used for each environment.

Usage
-----

To run Liquibase execute the following command:
    
    make run env=<env> command=<command>
    
* *env=\<env\>* &mdash; a prefix of liquibase properties file.
* *command=\<command\>* &mdash; a liquibase command to be executed.

For example,
    
    make run env=test command=update
    
Required libraries will be downloaded automatically.
