```mermaid
erDiagram
    games {
        VARCHAR          id           PK
        VARCHAR          title
        TEXT             description
        DOUBLE_PRECISION price
        TIMESTAMP        release_date
        TIMESTAMP        created_at
    }

    dlcs {
        VARCHAR          id           PK
        VARCHAR          dlc_name
        VARCHAR          game_title
        TEXT             description
        DOUBLE_PRECISION price
        TIMESTAMP        release_date
        TIMESTAMP        created_at
    }

    users {
        BIGSERIAL id        PK
        VARCHAR   username
        VARCHAR   email
        VARCHAR   password
        VARCHAR   firstname
        VARCHAR   lastname
    }

    user_owned_games {
        BIGINT  user_id     FK
        VARCHAR game_id
    }

    user_owned_dlcs {
        BIGINT  user_id     FK
        VARCHAR dlc_id
    }

    users            ||--o{ user_owned_games : "owns"
    users            ||--o{ user_owned_dlcs  : "owns"
    games            ||--o{ user_owned_games : "owned via"
    dlcs             ||--o{ user_owned_dlcs  : "owned via"
```