# Short Link
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-%23ED8B00.svg?style=for-the-badge&logo=mysql&logoColor=white&color=%233d6ab2)
[![Licence](https://img.shields.io/github/license/Ileriayo/markdown-badges?style=for-the-badge)](./LICENSE)

This link shortening service allows you to generate a shortened link for quick sharing and also offers the functionality to generate a QR code containing the original link. So, in addition to sharing the link directly, you can also provide the QR code for scanning. It's important to note that the QR code does not expire, while the shortened link is valid for 24 hours. After this period, the link is automatically removed to ensure the security and privacy of users.

## Table of Contents

- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Contributing](#contributing)

## Installation

1. Clone the repository:

```bash
git clone https://github.com/mtbassi/short-link.git
```

2. Install dependencies with Maven

3. Create a configuration with your runtime environment variables with your Data Base Credentials that are used in `application.properties`

```properties
# Database settings
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/${DATABASE_NAME}
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.username=${MYSQL_USERNAME}
spring.datasource.password=${MYSQL_PASSWORD}

# Hibernate settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.show_sql=false
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Database initialization
spring.sql.init.mode=always
spring.sql.init.username=${MYSQL_USERNAME}
spring.sql.init.password=${MYSQL_PASSWORD}
```

4. Configurations values

```properties
DATABASE_NAME=VALUE;MYSQL_USERNAME=VALUE2;MYSQL_PASSWORD=VALUE3;
```

## Database
The project utilizes [MySQL](https://www.mysql.com/) as the database.

## Usage

1. Start the application with Maven

2. The API will be accessible at http://localhost:8080/shortlink

3. The Swagger will be accessible at http://localhost:8080/swagger-ui/index.html#/

## API Endpoints
The API provides the following endpoints:

**Short Link**
```markdown
# 1. Shorten link
curl -X POST -H "Content-Type: application/json" -d '{"original_link": "https://example.com"}' http://localhost:8080/shortlink

# 2. Short link and generate QR code.
curl 'http://localhost:8080/shortlink/qr-code?original_link=https://example.com' --output qr_code.png

# 3. Redirect to original link
curl -L http://localhost:8080/shortlink/yourShortenedLink

# 4. Get information about a shortened link
curl http://localhost:8080/shortlink/info/yourShortenedLink
```

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request to the repository.

When contributing to this project, please follow the existing code style, [commit conventions](https://www.conventionalcommits.org/en/v1.0.0/), and submit your changes in a separate branch.
