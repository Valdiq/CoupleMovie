# CoupleMovie

CoupleMovie is a web application designed to help users find movies and TV series based on the emotions they want to experience. The platform aims to enhance the movie-watching experience by providing personalized recommendations tailored to users' moods. Instead of searching by genre alone, users can select specific emotions, such as excitement, romance, or nostalgia, and receive suggestions that align with those feelings.

The project is ideal for couples, friends, or individuals who want to discover movies and TV shows that match their current emotional state, making every movie night a more meaningful and enjoyable experience. 
## Features

- **Movie and TV Show Recommendations**: Users can find content based on their mood and emotions, pick a random movie or simply find by name.
- **Genre & Emotion Mapping**: The system categorizes movies and series by genre and associated emotions.
- **Database Integration**: Uses MySQL for storing user and movies data and MongoDB for storing logs.
- **Caching with Redis**: Optimizes performance using Redis for caching.
- **Elasticsearch**: Enables powerful and fast search capabilities.
- **Cloud Storage with AWS S3**: Handles media and image storage.
- **Other Features Comming Soon!**


## Technologies Used

- **Backend**: Java, Spring Boot, Spring WebFlux, Spring R2DBC, Hibernate, Mapstruct
- **Frontend**: JavaScript, React.js
- **Database**: MySQL, MongoDB
- **Caching**: Redis
- **Search Engine**: Elasticsearch
- **Cloud Storage**: AWS S3
- **Containerization**: Docker, Docker Compose

## Environment Variables

The project uses a `.env` file for managing environment variables. The following variables are required:

- `MYSQL_ROOT_PASSWORD` - Root password for MySQL.
- `MYSQL_DATABASE` - MySQL database name.
- `MONGODB_DATABASE` - MongoDB database name.
- `REDIS_HOSTNAME` - Redis hostname.
