To run liquibase execute the following command:

**make run env=\<environment\> command=\<liquibase command\>**

where \<environment\> is a prefix of liquibase properties file,

e.g.

_make run env=unit command=update_

Required libraries will be downloaded automatically.
