## Events Management (Back-end Project)

### Keywords

* IntelliJ IDE
* Java
* Spring
* JPA
* PostgreSQL
* Multi layer architecture
* AWS EC2
* AWS S3
* AWS RDS

### Main funcitonalities

Add a new tech event, uploading even the event image banner;
Add coupons to this tech events;
Filter events by title, by location and by start date;
Filter the events by a specific page size.


### Main steps

1. Multi-layer project: config, controller, domain, repository and service.
2. Archicteture summary: 
   * VPC with two subnets (one public and one private), each one in a differente AZ;
   * Create a EC2 instance, where we are running our back-end project;
   * Configure a RDS database in the VPC private subnet;
   * The EC2 is allowed to read and to write the files available in the S3 bucket via policy.


### References:

* [Criando BACKEND COMPLETO do ZERO com JAVA SPRING + POSTGRES + AWS] (https://www.youtube.com/watch?v=d0KaNzAMVO4)
