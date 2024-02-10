# maintainer

## 문서 정리

문서 정리는 docs 폴더에 요구사항,의문사항을 정리하고 있습니다.

[요구사항정리](docs/요구사항정리.md)

[의문사항정리](docs/의문사항정리.md)

## 개발 환경

window 10

springboot 3.2.2

java 21

mysql 5.7

redis:latest(개발당시 7)

## 사용법

만약 쓰시는 sql이 없다면, 릴리즈에있는 compose.yaml를 사용하세요.

릴리즈에 있는 schema.sql를 실행해주세요.(더미 데이터도 필요하다면,test_dat.sql도 실행해주세요 )

도커 이미지는 만드셔도 되고, 아래 링크를 통해서 바로 받으실수 있습니다.

만드는 법은 프로젝트 루트화면에서

```agsl
./gradelw  build
```

```agsl
docker build -t {docker 이미지이름} {작성한 Dockerfile 위치} 
```

로 만드실 수 있습니다.

```agsl
https://hub.docker.com/repository/docker/wjdgustn15/maintainer/general
```

현재 데이터베이스 비밀번호는 초기값으로 해두었습니다.

서버 sql 현재 설정

```agsl 
        
      - 'MYSQL_DATABASE=mydatabase'
      - 'MYSQL_PASSWORD=secret'
      - 'MYSQL_ROOT_PASSWORD=verysecret'
      - 'MYSQL_USER=myuser'
    ports:
      - '3306:3306'
```

도커 컴포즈를 잘아신다면, 서버 이미지를 추가하시고, 이미지 환경을 변경 하셔서,보안적이고 편하게 사용하세요!

