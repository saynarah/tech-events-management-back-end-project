## Events Management (Back-end Project)

### Keywords

* IntelliJ IDE
* Java
* Spring
* JPA
* PostgreSQL
* Multi-layer architecture
* AWS EC2
* AWS S3
* AWS RDS

### Main functionalities

Add a new tech event, including the upload of the event image banner.
Add coupons for these tech events.
Filter events by title, location, and start date.
Filter events by a specific page size.


### Main steps

1. Multi-layer project structure: config, controller, domain, repository, and service.
2. Architecture summary:
   * VPC with two subnets (one public and one private), each located in a different AZ.
   * Create an EC2 instance, which runs our back-end project.
   * Set up an RDS database in the private subnet of the VPC.
   * The EC2 instance is authorized to read and write files in the S3 bucket via policy.

### References:

* [Criando BACKEND COMPLETO do ZERO com JAVA SPRING + POSTGRES + AWS] (https://www.youtube.com/watch?v=d0KaNzAMVO4)
