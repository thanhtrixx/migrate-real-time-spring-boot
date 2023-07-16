# migrate-real-time-spring-boot

The PoC project to migrate real time data

## Start MySql 5.7

```sh
docker run --name mysql-5.7 -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql:5.7-debian
```

```sql
CREATE TABLE contact
(
  id            bigint AUTO_INCREMENT PRIMARY KEY,
  profile_id    varchar(64)                               NOT NULL,
  name          varchar(256)                              NOT NULL,
  is_favourite  tinyint      DEFAULT 0                    NOT NULL,
  version       int          DEFAULT 0                    NOT NULL,
  modified_date timestamp(3) DEFAULT CURRENT_TIMESTAMP(3) NULL,
  created_date  timestamp(3) DEFAULT CURRENT_TIMESTAMP(3) NULL
);

CREATE TABLE recent_contact
(
  profile_id    varchar(100)                              NOT NULL,
  contact_id    bigint                                    NOT NULL,
  created_date  timestamp(3) DEFAULT CURRENT_TIMESTAMP(3) NULL,
  modified_date timestamp(3) DEFAULT CURRENT_TIMESTAMP(3) NULL,
  PRIMARY KEY (profile_id,
               contact_id)
);


CREATE TABLE favorite_contact
(
  profile_id    varchar(100)                              NOT NULL,
  contact_id    bigint                                    NOT NULL,
  created_date  timestamp(3) DEFAULT CURRENT_TIMESTAMP(3) NULL,
  modified_date timestamp(3) DEFAULT CURRENT_TIMESTAMP(3) NULL,
  PRIMARY KEY (profile_id,
               contact_id)
);
```

```http
GET http://localhost:8080/migrate-data/contact-to-recent-contact
```

```sh
java -Xmx2048m -jar .\build\libs\migrate-real-time-spring-boot-0.0.1-SNAPSHOT.jar
```

```sh
./gradlew clean build bootRun
```

```sh
k6 run k6/script.js
```
